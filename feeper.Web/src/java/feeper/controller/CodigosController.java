package feeper.controller;

import feeper.Data.entity.CodigoFonte;
import feeper.Data.entity.Exercicio;
import feeper.Data.entity.Pessoa;
import feeper.Data.entity.Resposta;
import feeper.Data.entity.RespostaCodigoFonte;
import feeper.Data.entity.Turma;
import feeper.Data.model.EPerfil;
import feeper.Data.model.ETipoLog;
import feeper.Data.model.Util;
import feeper.Data.service.CodigoFonteMarcacaoService;
import feeper.Data.service.CodigoFonteService;
import feeper.Data.service.ExercicioService;
import feeper.Data.service.RespostaCodigoFonteMarcacaoService;
import feeper.Data.service.RespostaCodigoFonteService;
import feeper.Data.service.RespostaService;
import feeper.Data.service.TurmaService;
import feeper.model.PaginadorUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value="/codigos")
public class CodigosController extends ApplicationController {
       
    @RequestMapping(method=RequestMethod.GET)
    public String list(
            Model model, 
            HttpSession session) {
        
        return list(1, 5, "DataCadastro", "desc", "", model, null, session);
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public String list(
            @ModelAttribute("currentPage") int currentPage,
            @ModelAttribute("pageSize") int pageSize,
            @ModelAttribute("sortField") String sortField, 
            @ModelAttribute("sortDirection") String sortDirection, 
            @ModelAttribute("gridAction") String gridAction, 
            Model model, 
            BindingResult result,
            HttpSession session) {
        
        //Configurações do Paginador
        if (gridAction.equals("PageSizeChanged") || gridAction.equals("Sorted") || gridAction.equals("Searched"))
            currentPage = 1;

        if (currentPage == 0) currentPage = 1;
        if (pageSize == 0) pageSize = 5;
        if (sortField != null && !sortField.isEmpty()) sortField = "DataCadastro";
        if (sortDirection != null && !sortDirection.isEmpty()) sortDirection = "Desc";
        //-------------------------
        
        Pessoa usuarioLogado = (Pessoa)session.getAttribute("UsuarioLogado");
        
        PaginadorUtil<CodigoFonte> paginador = new PaginadorUtil<CodigoFonte>();
        
        String sql = "select * from CodigoFonte where IdAutor = " + usuarioLogado.getId() + " and Favorito = 1";
        
        List<CodigoFonte> lista = paginador.Execute(
                                        CodigoFonte.class, 
                                        model, 
                                        sql, 
                                        null, 
                                        currentPage, 
                                        pageSize, 
                                        sortField, 
                                        sortDirection);
        
        model.addAttribute("listaCodigo", lista);
        
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
        
        String fonte = codigoFonte.getFonte();
        codigoFonte.setFonte(Util.prepareCodeForSave(fonte));
        
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
        
        RespostaCodigoFonte respostaCodigoFonte = repoResposta.getRespostaCodigoFonte(idRespostaCodigoFonte);
        Resposta resposta = repoResposta.getById(respostaCodigoFonte.getIdResposta());
        
        String fonte = respostaCodigoFonte.getFonte();
        respostaCodigoFonte.setFonte(Util.prepareCodeForSave(fonte));
        
        mav.setViewName("codigos/showversion");
        mav.addObject("idExercicio", resposta.getIdExercicio());
        mav.addObject("idCodigoFonte", respostaCodigoFonte.getId());
        mav.addObject("CodigoFonte", respostaCodigoFonte);
        mav.addObject("isVersion", true);
        
        RespostaCodigoFonteMarcacaoService repoRespostaCodigoFonteMarcacao = new RespostaCodigoFonteMarcacaoService();
        List<Object> listaDuvidas = repoRespostaCodigoFonteMarcacao.getLinhasDuvida(idRespostaCodigoFonte);
        
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
    
    @RequestMapping(value="/downloadpkgversion/{idResposta}/{idAluno}", method=RequestMethod.GET)
    public void downloadpkgversion(
        @PathVariable int idResposta, 
        @PathVariable int idAluno, 
        HttpSession session,
        HttpServletRequest request,
        HttpServletResponse response) {
        
        RespostaService repoResposta = new RespostaService();
        
        Pessoa usuarioLogado = (Pessoa)session.getAttribute("UsuarioLogado");
        if (usuarioLogado.getIdPerfil() == EPerfil.PROFESSOR)
        {
            //Validação para verificar se a pessoa logada é professor do autor do código fonte
            TurmaService repoTurma = new TurmaService();
            if (!repoTurma.verificaProfessorDoAluno(usuarioLogado.getId(), idAluno))
                return;
        }
        else if (usuarioLogado.getIdPerfil() == EPerfil.ALUNO)
        {
            //Validação para verificar se a pessoa logada é o autor do código fonte
            if (idAluno != usuarioLogado.getId())
                return;
        }
        
        List<RespostaCodigoFonte> codigosFonte = repoResposta.getListRespostaCodigoFonte(idResposta);

        if (codigosFonte != null && codigosFonte.size() > 0)
        {
            try {

                ArrayList<byte[]> arquivos = new ArrayList<byte[]>();
                ArrayList<String> nomes = new ArrayList<String>();

                for (RespostaCodigoFonte codigoFonte : codigosFonte) {
                    arquivos.add(codigoFonte.getFonte().getBytes("UTF-8"));
                    nomes.add(codigoFonte.getClasse());
                }

                byte[] arquivoZip = Util.zipFiles(arquivos, nomes);
                InputStream stream = new ByteArrayInputStream(arquivoZip);
                IOUtils.copy(stream, response.getOutputStream());

                log(usuarioLogado.getId(), "SUCESSO: ID RESPOSTA: " + idResposta, ETipoLog.CODIGO_BAIXADO);

                response.setContentType("application/force-download");
                response.setHeader("Content-Disposition", "attachment; filename=Resposta"+idResposta+".zip");
                response.flushBuffer();

            } catch (IOException ex) {
            }
        }
    }
    
    @RequestMapping(value="/downloadfileversion/{idRespostaCodigoFonte}/{idAluno}", method=RequestMethod.GET)
    public void downloadfileversion(
        @PathVariable int idRespostaCodigoFonte, 
        @PathVariable int idAluno, 
        HttpSession session,
        HttpServletRequest request,
        HttpServletResponse response) {
        
        RespostaService repoResposta = new RespostaService();
        
        Pessoa usuarioLogado = (Pessoa)session.getAttribute("UsuarioLogado");
        if (usuarioLogado.getIdPerfil() == EPerfil.PROFESSOR)
        {
            //Validação para verificar se a pessoa logada é professor do autor do código fonte
            TurmaService repoTurma = new TurmaService();
            if (!repoTurma.verificaProfessorDoAluno(usuarioLogado.getId(), idAluno))
                return;
        }
        else if (usuarioLogado.getIdPerfil() == EPerfil.ALUNO)
        {
            //Validação para verificar se a pessoa logada é o autor do código fonte
            if (idAluno != usuarioLogado.getId())
                return;
        }
        
        RespostaCodigoFonte codigoFonte = repoResposta.getRespostaCodigoFonte(idRespostaCodigoFonte);

        if (codigoFonte != null)
        {
            try {

                ArrayList<byte[]> arquivos = new ArrayList<byte[]>();
                ArrayList<String> nomes = new ArrayList<String>();

                arquivos.add(codigoFonte.getFonte().getBytes("UTF-8"));
                nomes.add(codigoFonte.getClasse());

                byte[] arquivoZip = Util.zipFiles(arquivos, nomes);
                InputStream stream = new ByteArrayInputStream(arquivoZip);
                IOUtils.copy(stream, response.getOutputStream());

                log(usuarioLogado.getId(), "SUCESSO: ID RESPOSTA CODIGO FONTE: " + idRespostaCodigoFonte, ETipoLog.CODIGO_BAIXADO);

                response.setContentType("application/force-download");
                response.setHeader("Content-Disposition", "attachment; filename="+codigoFonte.getClasse()+".zip");
                response.flushBuffer();

            } catch (IOException ex) {
            }
        }
    }
    
    @RequestMapping(value="/savefavorite/{idExercicio}/{idCodigoFonte}", method=RequestMethod.GET)
    @ResponseBody
    public String savefavorite(
            @PathVariable int idExercicio,
            @PathVariable int idCodigoFonte,
            HttpSession session) {
        
        Pessoa pessoa = (Pessoa)session.getAttribute("UsuarioLogado");
        CodigoFonteService repoCodigoFonte = new CodigoFonteService();
        CodigoFonte codigoFonte = repoCodigoFonte.getById(idExercicio, pessoa.getId(), idCodigoFonte);
        
        if (codigoFonte == null) return "erro";
        
        codigoFonte.setFavorito(!codigoFonte.isFavorito());

        if (repoCodigoFonte.update(codigoFonte))
        {
            log(pessoa.getId(), "SUCESSO: ID: " + idCodigoFonte, ETipoLog.CODIGO_FAVORITO);
            return "" + codigoFonte.isFavorito();
        }
        else
        {
            log(pessoa.getId(), "ERRO: ID: " + idCodigoFonte, ETipoLog.CODIGO_FAVORITO);
            return "erro";
        }
            
    }
    
    @RequestMapping(value="/showversionquestion/{idExercicio}/{idRespostaCodigoFonte}/{linha}", method=RequestMethod.GET)
    public String showversionquestion(
            @PathVariable int idExercicio, 
            @PathVariable int idRespostaCodigoFonte, 
            @PathVariable int linha, 
            Model model, 
            HttpServletRequest request) {
        
        HttpSession session = request.getSession(false);
        Pessoa pessoa = (Pessoa)session.getAttribute("UsuarioLogado");
        
        RespostaCodigoFonteService repoRespostaCodigoFonte = new RespostaCodigoFonteService();
        
        List<Object> dados;
        if (pessoa.getIdPerfil() == EPerfil.ALUNO)
            dados = repoRespostaCodigoFonte.getMarcacaoAutor(idExercicio, pessoa.getId(), idRespostaCodigoFonte, linha);
        else
            dados = repoRespostaCodigoFonte.getMarcacaoLeitor(idExercicio, idRespostaCodigoFonte, linha);
        
        model.addAttribute("lista", dados);
        model.addAttribute("idUsuarioLogado", pessoa.getId());
        
        return "exercicios/mensagens";
    }
    
    @RequestMapping(value="/saveversionquestion", method=RequestMethod.POST)
    public ModelAndView saveversionquestion(
            @ModelAttribute("hdnQuestaoIdExercicio") int id, 
            @ModelAttribute("hdnQuestaoIdRespostaCodigoFonte") int idRespostaCodigoFonte, 
            @ModelAttribute("hdnQuestaoLinha") int linha, 
            HttpSession session,
            HttpServletRequest request,
            BindingResult result) {
        
        ModelAndView mav = new ModelAndView();
        
        Pessoa pessoa = (Pessoa)session.getAttribute("UsuarioLogado");
        Turma turma = (Turma)session.getAttribute("TurmaSelecionada");
        
        RespostaCodigoFonteService repoRespostaCodigoFonte = new RespostaCodigoFonteService();
        ExercicioService repoExercicio = new ExercicioService();
        Exercicio exercicio = repoExercicio.getMeuExercicio(turma.getId(), id);
        
        if (exercicio != null)
        {
            try
            {
                String questao = request.getParameter("questaoRespostaCodigoFonte") == null ? "" : request.getParameter("questaoRespostaCodigoFonte").toString();

                if (!questao.isEmpty())
                {
                    int idRemetente = pessoa.getId();
                    int idDestinatario = turma.getIdProfessor();
                    
                    if (pessoa.getIdPerfil() == EPerfil.PROFESSOR)
                    {
                        //Buscar o id do aluno
                        RespostaService repoResposta = new RespostaService();
                        RespostaCodigoFonte respostaCodigoFonte = repoRespostaCodigoFonte.getById(idRespostaCodigoFonte);
                        Resposta resposta = repoResposta.getById(respostaCodigoFonte.getIdResposta());
                        idDestinatario = resposta.getIdAutor();
                    }
                    
                    if (repoRespostaCodigoFonte.inserirPergunta(id, idRemetente, pessoa.getNome(), idRespostaCodigoFonte, linha, idDestinatario, questao))
                        log(pessoa.getId(), "SUCESSO: ID EXERCICIO: " + exercicio.getId() + " ID RESPOSTACODIGOFONTE: " + idRespostaCodigoFonte + " LINHA: " + linha, ETipoLog.REALIZAR_PERGUNTA);
                    else
                        log(pessoa.getId(), "ERRO: ID EXERCICIO: " + exercicio.getId() + " ID RESPOSTACODIGOFONTE: " + idRespostaCodigoFonte + " LINHA: " + linha, ETipoLog.REALIZAR_PERGUNTA);
                }
            }
            catch(Exception e) {
                log(pessoa.getId(), "ERRO: ID EXERCICIO: " + exercicio.getId(), ETipoLog.REALIZAR_PERGUNTA);
            }
        
        }
        
        mav.setView(new RedirectView("/codigos/showversionquestion/" + id + "/" + idRespostaCodigoFonte + "/" + linha, true, true, false));
        return mav;
        
    }
    
    @RequestMapping(value="/showversionannotation/{idExercicio}/{idRespostaCodigoFonte}/{linha}", method=RequestMethod.GET)
    @ResponseBody
    public Object showversionannotation(
            @PathVariable int idExercicio, 
            @PathVariable int idRespostaCodigoFonte, 
            @PathVariable int linha, 
            Model model, 
            HttpServletRequest request) {
        
        HttpSession session = request.getSession(false);
        Pessoa pessoa = (Pessoa)session.getAttribute("UsuarioLogado");
        
        RespostaCodigoFonteService repoRespostaCodigoFonte = new RespostaCodigoFonteService();
        Object dados = repoRespostaCodigoFonte.getAnotacao(idExercicio, idRespostaCodigoFonte, linha);
        
        return dados != null ? dados : "";
    }
    
    @RequestMapping(value="/saveversionannotation", method=RequestMethod.POST)
    public ModelAndView saveversionannotation(
            @ModelAttribute("hdnAnotacaoIdExercicio") int id, 
            @ModelAttribute("hdnAnotacaoIdRespostaCodigoFonte") int idRespostaCodigoFonte, 
            @ModelAttribute("hdnAnotacaoLinha") int linha, 
            HttpSession session,
            HttpServletRequest request,
            BindingResult result) {
        
        ModelAndView mav = new ModelAndView();
        
        Pessoa pessoa = (Pessoa)session.getAttribute("UsuarioLogado");
        Turma turma = (Turma)session.getAttribute("TurmaSelecionada");
        
        RespostaCodigoFonteService repoRespostaCodigoFonte = new RespostaCodigoFonteService();
        ExercicioService repoExercicio = new ExercicioService();
        Exercicio exercicio = repoExercicio.getMeuExercicio(turma.getId(), id);
        
        if (exercicio != null)
        {
            try
            {
                String anotacao = request.getParameter("anotacaoRespostaCodigoFonte") == null ? "" : request.getParameter("anotacaoRespostaCodigoFonte").toString();
                //boolean publico = request.getParameter("chkPublico") == null ? false : request.getParameter("chkPublico").toString().equals("1");

                if (!anotacao.isEmpty())
                {
                    if (repoRespostaCodigoFonte.inserirAnotacao(id, pessoa.getId(), idRespostaCodigoFonte, linha, anotacao))
                        log(pessoa.getId(), "SUCESSO: ID EXERCICIO: " + exercicio.getId() + " ID RESPOSTACODIGOFONTE: " + idRespostaCodigoFonte + " LINHA: " + linha, ETipoLog.CODIGO_COMENTADO);
                    else
                        log(pessoa.getId(), "ERRO: ID EXERCICIO: " + exercicio.getId() + " ID RESPOSTACODIGOFONTE: " + idRespostaCodigoFonte + " LINHA: " + linha, ETipoLog.CODIGO_COMENTADO);
                }
            }
            catch(Exception e) {
                log(pessoa.getId(), "ERRO: ID EXERCICIO: " + exercicio.getId(), ETipoLog.CODIGO_COMENTADO);
            }
        
        }
        
        mav.setView(new RedirectView("/codigos/showversionannotation/" + id + "/" + idRespostaCodigoFonte + "/" + linha, true, true, false));
        return mav;
        
    }
    
}