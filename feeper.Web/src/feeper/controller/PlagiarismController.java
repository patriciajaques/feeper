/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.controller;

import feeper.Data.entity.Exercicio;
import feeper.Data.entity.MensagemPredefinida;
import feeper.Data.entity.Pessoa;
import feeper.Data.entity.Turma;
import feeper.Data.service.TurmaService;
import feeper.plagiarism.PlagiarismDetector;
import feeper.plagiarism.PlagiarismParentItem;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author gilvani
 */
@Controller
@RequestMapping(value = "/plagiarism")
public class PlagiarismController extends ApplicationController {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String edit() {
        return "plagiarism/list";
    }

    @RequestMapping(value = "/getJson", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public List<Object> getJson(HttpServletRequest request) {

        HttpSession session = request.getSession();
        Turma turma = (Turma) session.getAttribute("TurmaSelecionada");

        TurmaService service = new TurmaService();
        return service.getExercicios(turma.getId());
    }

    @RequestMapping(value = "/performCheck", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public List<PlagiarismParentItem> performCheck(@RequestBody int exercicioId, HttpServletRequest request) {

        try {
            HttpSession session = request.getSession();
            Pessoa usuarioLogado = (Pessoa) session.getAttribute("UsuarioLogado");
            Turma turma = (Turma) session.getAttribute("TurmaSelecionada");

            PlagiarismDetector detector = new PlagiarismDetector();
            return detector.performCheck(usuarioLogado.getId(), turma.getId(), exercicioId);

        } catch (Exception e) {
            return null;
        }
    }
}
