package feeper.controller;

import feeper.Data.entity.Pessoa;
import feeper.Data.model.Util;
import feeper.Data.service.LogService;
import feeper.model.DontValidateAccess;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApplicationController {
    
    private LogService repoLog;
    
    public ApplicationController() {
        repoLog = new LogService();
    }
    
    @DontValidateAccess
    @RequestMapping(value="/closemodal", method=RequestMethod.GET)
    public String closeModal() {
        return "closemodal";
    }
    
    @DontValidateAccess
    @RequestMapping(value="/timeout", method=RequestMethod.GET)
    @ResponseBody
    public String timeout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Pessoa pessoa = (Pessoa)session.getAttribute("UsuarioLogado");
        return pessoa.getNome();
    }
    
    @DontValidateAccess
    @RequestMapping(value="/tratastring/{text}", method=RequestMethod.GET)
    @ResponseBody
    public String trataString(@PathVariable String text) {
        return Util.prepareStringForSave(text);
    }
    
    public void log(int idPessoa, String msg, int idTipoLog)
    {
        //int idPessoa = ((Pessoa)session.getAttribute("UsuarioLogado")).getId();
        repoLog.log(idPessoa, msg, idTipoLog);
    }
    
}
