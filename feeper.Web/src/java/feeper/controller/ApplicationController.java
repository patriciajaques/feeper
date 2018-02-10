package feeper.controller;

import feeper.Data.entity.Pessoa;
import feeper.Data.model.ETipoLog;
import feeper.Data.model.Util;
import feeper.Data.service.LogService;
import feeper.Data.service.MedalhaPessoaService;
import feeper.model.DontValidateAccess;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApplicationController {

    private LogService repoLog;
    private MedalhaPessoaService medalhaPessoaService;

    public ApplicationController() {
        repoLog = new LogService();
        medalhaPessoaService = new MedalhaPessoaService();
    }

    @DontValidateAccess
    @RequestMapping(value = "/closemodal", method = RequestMethod.GET)
    public String closeModal() {
        return "closemodal";
    }

    @DontValidateAccess
    @RequestMapping(value = "/timeout", method = RequestMethod.GET)
    @ResponseBody
    public String timeout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Pessoa pessoa = (Pessoa) session.getAttribute("UsuarioLogado");
        return pessoa.getNome();
    }

    @DontValidateAccess
    @RequestMapping(value = "/tratastring/{text}", method = RequestMethod.GET)
    @ResponseBody
    public String trataString(@PathVariable String text) {
        return Util.prepareStringForSave(text);
    }

    @DontValidateAccess
    @RequestMapping(value = "/feedback", method = RequestMethod.POST)
    @ResponseBody
    public String feedback(
            @ModelAttribute("email") String email,
            @ModelAttribute("mensagem") String mensagem) {

        try {

            if (mensagem.isEmpty()) {
                return "erro1";
            }

            StringBuilder sb = new StringBuilder();
            sb.append("<p>Novo feedback recebido!</p><p>E-mail: ").append(email).append("<br>Mensagem: ").append(mensagem).append("</p>");

            if (Util.sendMail("feedback", sb.toString())) {
                return "ok";
            } else {
                return "erro2";
            }

        } catch (Exception e) {
            return "erro3";
        }
    }

    public void log(int idPessoa, String msg, int idTipoLog) {
        //int idPessoa = ((Pessoa)session.getAttribute("UsuarioLogado")).getId();
        repoLog.log(idPessoa, msg, idTipoLog);
        try {
            if(idTipoLog == ETipoLog.LOGIN){
                medalhaPessoaService.medalhaLogin(idPessoa);
            }
        } catch (Exception e) {
        }
    }

}
