package feeper.controller;

import feeper.Data.entity.Exercicio;
import feeper.Data.entity.Pessoa;
import feeper.Data.entity.Turma;
import feeper.Data.model.EPerfil;
import feeper.Data.service.ExercicioService;
import feeper.Data.service.TurmaService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/notas")
public class NotasController extends ApplicationController {
    
    @RequestMapping(method=RequestMethod.GET)
    public String list() {
        return "notas/list";
    }
    
    @RequestMapping(value="/results", method=RequestMethod.GET)
    public ModelAndView results(HttpSession session) {
        
        ModelAndView mav = new ModelAndView();
        
        Pessoa usuarioLogado = (Pessoa)session.getAttribute("UsuarioLogado");
        Turma turma = (Turma)session.getAttribute("TurmaSelecionada");
        
        TurmaService repoTurma = new TurmaService();
        ExercicioService repoExercicio = new ExercicioService();
        
        if (usuarioLogado.getIdPerfil() == EPerfil.PROFESSOR)
        {
            List<Object> grade = repoTurma.getGradeResultadosTurma(turma.getId());
            mav.addObject("grade", grade);
        }
        else if (usuarioLogado.getIdPerfil() == EPerfil.ALUNO)
        {
            List<Object> grade = repoTurma.getGradeResultadosAluno(turma.getId(), usuarioLogado.getId());
            mav.addObject("grade", grade);
        }       
        
        List<Exercicio> exercicios = repoExercicio.getExercioByTurma(turma.getId());
        mav.addObject("listaExercicios", exercicios);
        
        mav.setViewName("notas/results");
        return mav;
        
    }
    
}