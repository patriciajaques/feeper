package feeper.controller;

import feeper.Data.entity.Pessoa;
import feeper.Data.entity.Turma;
import feeper.Data.model.ETipoLog;
import feeper.Data.service.PessoaService;
import feeper.Data.service.TurmaService;
import feeper.model.DontValidateAccess;
import java.util.Date;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@DontValidateAccess
@Controller
@RequestMapping(value = "/login")
public class LoginController extends ApplicationController {

    @RequestMapping(method = RequestMethod.GET)
    public String list(ServletRequest request, ServletResponse response) throws Exception {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        //Nao remova, soluciona o erro de ter que fazer login 2 vezes
        if (session.isNew()) {
            // New session? OK, redirect to encoded URL with jsessionid in it (and implicitly also set cookie).
            res.sendRedirect(res.encodeRedirectURL(req.getRequestURI()));
            return "login/login";
        } else if (session.getAttribute("verified") == null) {
            // Session has not been verified yet? OK, mark it verified so that we don't need to repeat this.
            session.setAttribute("verified", true);
            if (req.isRequestedSessionIdFromCookie()) {
                // Supports cookies? OK, redirect to unencoded URL to get rid of jsessionid in URL.
                res.sendRedirect(req.getRequestURI().split(";")[0]);
                return "login/login";
            }
        }

        return "login/login";
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public ModelAndView validate(HttpServletRequest request, final RedirectAttributes flash) {

        ModelAndView mav = new ModelAndView();

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        PessoaService repoPessoa = new PessoaService();
        Pessoa pessoa = repoPessoa.validaLoginSenha(email, senha);
        if (pessoa == null || pessoa.getId() == 0) {
            mav.setView(new RedirectView("/login", true, true, false));
            flash.addFlashAttribute("MSG_ERRO", "Dados inválidos!");
        } else {
            log(pessoa.getId(), "IP: " + request.getRemoteAddr(), ETipoLog.LOGIN);

            pessoa.setDataUltimoAcesso(new Date());
            repoPessoa.update(pessoa);

            TurmaService repoTurma = new TurmaService();

            pessoa.setTurmas(repoTurma.getTurmasByIdPessoa(pessoa.getId()));

            HttpSession session = request.getSession(true);
            session.setAttribute("UsuarioLogado", pessoa);

            if (pessoa.getTurmas().size() > 0) {
                
                Turma turmaSelecionada = null;
                if (pessoa.getIdTurmaSelecionada() > 0) {
                    turmaSelecionada = repoTurma.getById(pessoa.getIdTurmaSelecionada());
                }
                if (turmaSelecionada == null) {
                    turmaSelecionada = pessoa.getTurmas().get(0);
                }
                session.setAttribute("TurmaSelecionada", turmaSelecionada);
            }
            
            if(pessoa.getTermo()==0){
                mav.setView(new RedirectView("/termo", true, true, false));    
            }else{
                mav.setView(new RedirectView("/", true, true, false));   
            }
        }
        return mav;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }

}
