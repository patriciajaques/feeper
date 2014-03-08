package feeper.controller;

import feeper.Data.entity.CodigoFonte;
import feeper.Data.entity.Exercicio;
import feeper.Data.entity.Pessoa;
import feeper.Data.entity.RespostaCodigoFonte;
import feeper.Data.model.EPerfil;
import feeper.Data.service.CodigoFonteMarcacaoService;
import feeper.Data.service.CodigoFonteService;
import feeper.Data.service.ExercicioService;
import feeper.Data.service.RespostaService;
import feeper.Data.service.TurmaService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value="/codigos")
public class CodigosController extends ApplicationController {
    
    @RequestMapping(method=RequestMethod.GET)
    public String list() {
        return "codigos/list";
    }
    
    @RequestMapping(value="/show/{idExercicio}/{idCodigoFonte}/{linha}", method=RequestMethod.GET)
    public ModelAndView show(
            @PathVariable int idExercicio,
            @PathVariable int idCodigoFonte,
            @PathVariable int linha,
            HttpSession session,
            Model model) {
        
        ModelAndView mav = new ModelAndView();
                
        CodigoFonteService repoCodigoFonte = new CodigoFonteService();
        CodigoFonte codigoFonte = repoCodigoFonte.getById(idExercicio, idCodigoFonte);
        
        Pessoa usuarioLogado = (Pessoa)session.getAttribute("UsuarioLogado");
        if (usuarioLogado.getIdPerfil() == EPerfil.PROFESSOR)
        {
            //Validação para verificar se a pessoa logada é professor do autor do código fonte
            TurmaService repoTurma = new TurmaService();
            if (!repoTurma.verificaProfessorDoAluno(usuarioLogado.getId(), codigoFonte.getIdAutor()))
            {
                mav.setView(new RedirectView("/closemodal", true, true, false));
                return mav;
            }
        }
        else if (usuarioLogado.getIdPerfil() == EPerfil.ALUNO)
        {
            //Validação para verificar se a pessoa logada é o autor do código fonte
            if (codigoFonte.getIdAutor() != usuarioLogado.getId())
            {
                mav.setView(new RedirectView("/closemodal", true, true, false));
                return mav;
            }
        }
        
        mav.setViewName("codigos/show");
        mav.addObject("CodigoFonte", codigoFonte);
        mav.addObject("idExercicio", idExercicio);
        mav.addObject("idCodigoFonte", idCodigoFonte);
        mav.addObject("linha", linha);
        mav.addObject("isVersion", false);
        
        CodigoFonteMarcacaoService repoCodigoFonteMarcacao = new CodigoFonteMarcacaoService();
        List<Object> listaDuvidas = repoCodigoFonteMarcacao.getLinhasDuvida(idCodigoFonte);
        
        String duvidas = "";
        
        for (Object item : listaDuvidas) {
            if (duvidas.length() == 0)
                duvidas = duvidas.concat(item.toString());
            else
                duvidas = duvidas.concat(",").concat(item.toString());
        }
        
        mav.addObject("questions", "[" + duvidas + "]");
        
        return mav;
    }
    
    @RequestMapping(value="/results/{idExercicio}/{idAluno}", method=RequestMethod.GET)
    public ModelAndView results(
            @PathVariable int idExercicio,
            @PathVariable int idAluno,
            HttpSession session,
            Model model) {
        
        ModelAndView mav = new ModelAndView();
                
        RespostaService repoResposta = new RespostaService();
        
        Pessoa usuarioLogado = (Pessoa)session.getAttribute("UsuarioLogado");
        if (usuarioLogado.getIdPerfil() == EPerfil.PROFESSOR)
        {
            //Validação para verificar se a pessoa logada é professor do autor do código fonte
            TurmaService repoTurma = new TurmaService();
            if (!repoTurma.verificaProfessorDoAluno(usuarioLogado.getId(), idAluno))
            {
                mav.setView(new RedirectView("/closemodal", true, true, false));
                return mav;
            }
        }
        else if (usuarioLogado.getIdPerfil() == EPerfil.ALUNO)
        {
            //Validação para verificar se a pessoa logada é o autor do código fonte
            if (idAluno != usuarioLogado.getId())
            {
                mav.setView(new RedirectView("/closemodal", true, true, false));
                return mav;
            }
        }
        
        List<Object> respostas = repoResposta.getDetailsByIdExercicio(idExercicio, idAluno);
        
        ExercicioService repoExercicio = new ExercicioService();
        Exercicio exercicio = repoExercicio.getById(idExercicio);
        
        mav.setViewName("codigos/results");
        mav.addObject("Respostas", respostas);
        mav.addObject("nomeExercicio", exercicio.getNome());
        mav.addObject("idAluno", idAluno);
        
        return mav;
    }
    
    @RequestMapping(value="/listcode/{idResposta}/{idAluno}", method=RequestMethod.GET)
    public ModelAndView listcode(
            @PathVariable int idResposta,
            @PathVariable int idAluno,
            HttpSession session,
            Model model) {
        
        ModelAndView mav = new ModelAndView();
                
        RespostaService repoResposta = new RespostaService();
        
        Pessoa usuarioLogado = (Pessoa)session.getAttribute("UsuarioLogado");
        if (usuarioLogado.getIdPerfil() == EPerfil.PROFESSOR)
        {
            //Validação para verificar se a pessoa logada é professor do autor do código fonte
            TurmaService repoTurma = new TurmaService();
            if (!repoTurma.verificaProfessorDoAluno(usuarioLogado.getId(), idAluno))
            {
                mav.setView(new RedirectView("/closemodal", true, true, false));
                return mav;
            }
        }
        else if (usuarioLogado.getIdPerfil() == EPerfil.ALUNO)
        {
            //Validação para verificar se a pessoa logada é o autor do código fonte
            if (idAluno != usuarioLogado.getId())
            {
                mav.setView(new RedirectView("/closemodal", true, true, false));
                return mav;
            }
        }
        
        List<RespostaCodigoFonte> codigos = repoResposta.getListRespostaCodigoFonte(idResposta);
        
        mav.setViewName("codigos/listcode");
        mav.addObject("codigos", codigos);
        mav.addObject("idAluno", idAluno);
        
        return mav;
    }
    
    @RequestMapping(value="/showversion/{idRespostaCodigoFonte}/{idAluno}", method=RequestMethod.GET)
    public ModelAndView showversion(
            @PathVariable int idRespostaCodigoFonte,
            @PathVariable int idAluno,
            HttpSession session,
            Model model) {
        
        ModelAndView mav = new ModelAndView();
                
        RespostaService repoResposta = new RespostaService();
        
        Pessoa usuarioLogado = (Pessoa)session.getAttribute("UsuarioLogado");
        if (usuarioLogado.getIdPerfil() == EPerfil.PROFESSOR)
        {
            //Validação para verificar se a pessoa logada é professor do autor do código fonte
            TurmaService repoTurma = new TurmaService();
            if (!repoTurma.verificaProfessorDoAluno(usuarioLogado.getId(), idAluno))
            {
                mav.setView(new RedirectView("/closemodal", true, true, false));
                return mav;
            }
        }
        else if (usuarioLogado.getIdPerfil() == EPerfil.ALUNO)
        {
            //Validação para verificar se a pessoa logada é o autor do código fonte
            if (idAluno != usuarioLogado.getId())
            {
                mav.setView(new RedirectView("/closemodal", true, true, false));
                return mav;
            }
        }
        
        RespostaCodigoFonte codigoFonte = repoResposta.getRespostaCodigoFonte(idRespostaCodigoFonte);
        
        mav.setViewName("codigos/show");
        
        mav.addObject("CodigoFonte", codigoFonte);
        mav.addObject("isVersion", true);
        
        return mav;
    }
    
}