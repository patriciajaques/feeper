package feeper.controller;

import feeper.model.DontValidateAccess;
import feeper.model.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    
    public void log(int idPessoa, String msg, int idTipoLog)
    {
        //int idPessoa = ((Pessoa)session.getAttribute("UsuarioLogado")).getId();
        repoLog.log(idPessoa, msg, idTipoLog);
    }
    
}
