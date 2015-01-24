/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.controller;

import feeper.Data.entity.Exercicio;
import feeper.Data.entity.ExercicioClasse;
import feeper.Data.entity.ExercicioSolucao;
import feeper.Data.entity.ExercicioSolucaoClasse;
import feeper.Data.entity.ExercicioSolucaoErro;
import feeper.Data.entity.Pessoa;
import feeper.Data.model.EPerfil;
import feeper.Data.service.ExercicioService;
import feeper.Data.service.ExercicioSolucaoClasseService;
import feeper.Data.service.ExercicioSolucaoErroService;
import feeper.Data.service.ExercicioSolucaoService;
import feeper.Data.service.TurmaService;
import feeper.model.PaginadorUtil;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author gilvani
 */
@Controller
@RequestMapping(value = "/solucoes")
public class SolucoesController {

    @RequestMapping(value = "/results/{idExercicio}/{idAluno}", method = RequestMethod.GET)
    public ModelAndView results(
            @PathVariable int idExercicio,
            @PathVariable int idAluno,
            HttpSession session,
            Model model) {

        ModelAndView mav = new ModelAndView();

        ExercicioSolucaoService repoSolucao = new ExercicioSolucaoService();

        Pessoa usuarioLogado = (Pessoa) session.getAttribute("UsuarioLogado");
        if (usuarioLogado.getIdPerfil() == EPerfil.PROFESSOR) {
            //Validação para verificar se a pessoa logada é professor do autor do código fonte
            TurmaService repoTurma = new TurmaService();
            if (!repoTurma.verificaProfessorDoAluno(usuarioLogado.getId(), idAluno)) {
                mav.setView(new RedirectView("/closemodal", true, true, false));
                return mav;
            }
        } else if (usuarioLogado.getIdPerfil() == EPerfil.ALUNO) {
            //Validação para verificar se a pessoa logada é o autor do código fonte
            if (idAluno != usuarioLogado.getId()) {
                mav.setView(new RedirectView("/closemodal", true, true, false));
                return mav;
            }
        }

        List<ExercicioSolucao> solucoes = repoSolucao.getByIdExercicio(idExercicio, idAluno);

        ExercicioService repoExercicio = new ExercicioService();
        Exercicio exercicio = repoExercicio.getById(idExercicio);

        mav.setViewName("solucoes/results");
        mav.addObject("Solucoes", solucoes);
        mav.addObject("nomeExercicio", exercicio.getNome());
        mav.addObject("idAluno", idAluno);

        return mav;
    }

    @RequestMapping(value = "/listerros/{idSolucao}/{idAluno}", method = RequestMethod.GET)
    public ModelAndView listerros(
            @PathVariable int idSolucao,
            @PathVariable int idAluno,
            HttpSession session,
            Model model) {

        ModelAndView mav = new ModelAndView();

        Pessoa usuarioLogado = (Pessoa) session.getAttribute("UsuarioLogado");
        if (usuarioLogado.getIdPerfil() == EPerfil.PROFESSOR) {
            //Validação para verificar se a pessoa logada é professor do autor do código fonte
            TurmaService repoTurma = new TurmaService();
            if (!repoTurma.verificaProfessorDoAluno(usuarioLogado.getId(), idAluno)) {
                mav.setView(new RedirectView("/closemodal", true, true, false));
                return mav;
            }
        } else if (usuarioLogado.getIdPerfil() == EPerfil.ALUNO) {
            //Validação para verificar se a pessoa logada é o autor do código fonte
            if (idAluno != usuarioLogado.getId()) {
                mav.setView(new RedirectView("/closemodal", true, true, false));
                return mav;
            }
        }

        ExercicioSolucaoErroService repoerros = new ExercicioSolucaoErroService();
        List<ExercicioSolucaoErro> erros = repoerros.getAllByIdSolucao(idSolucao);

        mav.setViewName("solucoes/listerros");
        mav.addObject("erros", erros);
        mav.addObject("idAluno", idAluno);

        return mav;
    }
}
