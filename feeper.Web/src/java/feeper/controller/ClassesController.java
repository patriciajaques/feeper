package feeper.controller;

import feeper.Data.entity.Exercicio;
import feeper.Data.entity.ExercicioSolucaoClasse;
import feeper.Data.entity.Pessoa;
import feeper.Data.entity.Resposta;
import feeper.Data.entity.RespostaClasse;
import feeper.Data.entity.Turma;
import feeper.Data.model.EPerfil;
import feeper.Data.model.ETipoLog;
import feeper.Data.model.Util;
import feeper.Data.service.ClasseMarcacaoService;
import feeper.Data.service.ExercicioService;
import feeper.Data.service.ExercicioSolucaoClasseService;
import feeper.Data.service.RespostaClasseMarcacaoService;
import feeper.Data.service.RespostaClasseService;
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
@RequestMapping(value = "/classes")
public class ClassesController extends ApplicationController {

    @RequestMapping(method = RequestMethod.GET)
    public String list(
            Model model,
            HttpSession session) {

        return list(1, 5, "DataCadastro", "desc", "", model, null, session);
    }

    @RequestMapping(method = RequestMethod.POST)
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
        if (gridAction.equals("PageSizeChanged") || gridAction.equals("Sorted") || gridAction.equals("Searched")) {
            currentPage = 1;
        }

        if (currentPage == 0) {
            currentPage = 1;
        }
        if (pageSize == 0) {
            pageSize = 5;
        }
        if (sortField != null && !sortField.isEmpty()) {
            sortField = "DataCadastro";
        }
        if (sortDirection != null && !sortDirection.isEmpty()) {
            sortDirection = "Desc";
        }
        //-------------------------

        Pessoa usuarioLogado = (Pessoa) session.getAttribute("UsuarioLogado");

        PaginadorUtil<ExercicioSolucaoClasse> paginador = new PaginadorUtil<ExercicioSolucaoClasse>();

        String sql = "select * from ExercicioSolucaoClasse where IdAluno = " + usuarioLogado.getId() + " and Favorito = 1";

        List<ExercicioSolucaoClasse> lista = paginador.Execute(
                ExercicioSolucaoClasse.class,
                model,
                sql,
                null,
                currentPage,
                pageSize,
                sortField,
                sortDirection);

        model.addAttribute("listaClasses", lista);

        return "classes/list";
    }

    @RequestMapping(value = "/show/{idExercicio}/{idClasse}/{linha}", method = RequestMethod.GET)
    public ModelAndView show(
            @PathVariable int idExercicio,
            @PathVariable int idClasse,
            @PathVariable int linha,
            HttpSession session,
            Model model) {

        ModelAndView mav = new ModelAndView();

        ExercicioSolucaoClasseService repoClasse = new ExercicioSolucaoClasseService();
        ExercicioSolucaoClasse classe = repoClasse.getById(idClasse);

        Pessoa usuarioLogado = (Pessoa) session.getAttribute("UsuarioLogado");
        if (usuarioLogado.getIdPerfil() == EPerfil.PROFESSOR) {
            //Validação para verificar se a pessoa logada é professor do autor do código fonte
            TurmaService repoTurma = new TurmaService();
            if (!repoTurma.verificaProfessorDoAluno(usuarioLogado.getId(), classe.getIdAluno())) {
                mav.setView(new RedirectView("/closemodal", true, true, false));
                return mav;
            }
        } else if (usuarioLogado.getIdPerfil() == EPerfil.ALUNO) {
            //Validação para verificar se a pessoa logada é o autor do código fonte
            if (classe.getIdAluno() != usuarioLogado.getId()) {
                mav.setView(new RedirectView("/closemodal", true, true, false));
                return mav;
            }
        }

        mav.setViewName("classes/show");
        mav.addObject("Classe", classe);
        mav.addObject("idExercicio", idExercicio);
        mav.addObject("idClasse", idClasse);
        mav.addObject("linha", linha);
        mav.addObject("isVersion", false);

        ClasseMarcacaoService repoClasseMarcacao = new ClasseMarcacaoService();
        List<Object> listaDuvidas = repoClasseMarcacao.getLinhasDuvida(idClasse);

        String duvidas = "";

        for (Object item : listaDuvidas) {
            if (duvidas.length() == 0) {
                duvidas = duvidas.concat(item.toString());
            } else {
                duvidas = duvidas.concat(",").concat(item.toString());
            }
        }

        mav.addObject("questions", "[" + duvidas + "]");

        return mav;
    }

    @RequestMapping(value = "/results/{idExercicio}/{idAluno}", method = RequestMethod.GET)
    public ModelAndView results(
            @PathVariable int idExercicio,
            @PathVariable int idAluno,
            HttpSession session,
            Model model) {

        ModelAndView mav = new ModelAndView();

        RespostaService repoResposta = new RespostaService();

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

        List<Object> respostas = repoResposta.getDetailsByIdExercicio(idExercicio, idAluno);

        ExercicioService repoExercicio = new ExercicioService();
        Exercicio exercicio = repoExercicio.getById(idExercicio);

        mav.setViewName("classes/results");
        mav.addObject("Respostas", respostas);
        mav.addObject("nomeExercicio", exercicio.getNome());
        mav.addObject("idAluno", idAluno);

        return mav;
    }

    @RequestMapping(value = "/listclasses/{idResposta}/{idAluno}", method = RequestMethod.GET)
    public ModelAndView listcode(
            @PathVariable int idResposta,
            @PathVariable int idAluno,
            HttpSession session,
            Model model) {

        ModelAndView mav = new ModelAndView();

        RespostaService repoResposta = new RespostaService();

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

        List<RespostaClasse> classes = repoResposta.getListRespostaClasse(idResposta);

        mav.setViewName("classes/listclasses");
        mav.addObject("classes", classes);
        mav.addObject("idAluno", idAluno);

        return mav;
    }

    @RequestMapping(value = "/showversion/{idRespostaClasse}/{idAluno}", method = RequestMethod.GET)
    public ModelAndView showversion(
            @PathVariable int idRespostaClasse,
            @PathVariable int idAluno,
            HttpSession session,
            Model model) {

        ModelAndView mav = new ModelAndView();

        RespostaService repoResposta = new RespostaService();

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

        RespostaClasse respostaClasse = repoResposta.getRespostaClasse(idRespostaClasse);
        Resposta resposta = repoResposta.getById(respostaClasse.getIdResposta());


        mav.setViewName("classes/showversion");
        mav.addObject("idExercicio", resposta.getIdExercicio());
        mav.addObject("idClasse", respostaClasse.getId());
        mav.addObject("Classe", respostaClasse);
        mav.addObject("isVersion", true);

        RespostaClasseMarcacaoService repoRespostaClasseMarcacao = new RespostaClasseMarcacaoService();
        List<Object> listaDuvidas = repoRespostaClasseMarcacao.getLinhasDuvida(idRespostaClasse);

        String duvidas = "";

        for (Object item : listaDuvidas) {
            if (duvidas.length() == 0) {
                duvidas = duvidas.concat(item.toString());
            } else {
                duvidas = duvidas.concat(",").concat(item.toString());
            }
        }

        mav.addObject("questions", "[" + duvidas + "]");

        return mav;
    }

    @RequestMapping(value = "/downloadpkgversion/{idResposta}/{idAluno}", method = RequestMethod.GET)
    public void downloadpkgversion(
            @PathVariable int idResposta,
            @PathVariable int idAluno,
            HttpSession session,
            HttpServletRequest request,
            HttpServletResponse response) {

        RespostaService repoResposta = new RespostaService();

        Pessoa usuarioLogado = (Pessoa) session.getAttribute("UsuarioLogado");
        if (usuarioLogado.getIdPerfil() == EPerfil.PROFESSOR) {
            //Validação para verificar se a pessoa logada é professor do autor do código fonte
            TurmaService repoTurma = new TurmaService();
            if (!repoTurma.verificaProfessorDoAluno(usuarioLogado.getId(), idAluno)) {
                return;
            }
        } else if (usuarioLogado.getIdPerfil() == EPerfil.ALUNO) {
            //Validação para verificar se a pessoa logada é o autor do código fonte
            if (idAluno != usuarioLogado.getId()) {
                return;
            }
        }

        List<RespostaClasse> classes = repoResposta.getListRespostaClasse(idResposta);

        if (classes != null && classes.size() > 0) {
            try {

                ArrayList<byte[]> arquivos = new ArrayList<byte[]>();
                ArrayList<String> nomes = new ArrayList<String>();

                for (RespostaClasse classe : classes) {
                    arquivos.add(classe.getFonte().getBytes("UTF-8"));
                    nomes.add(classe.getClasse());
                }

                byte[] arquivoZip = Util.zipFiles(arquivos, nomes);
                InputStream stream = new ByteArrayInputStream(arquivoZip);
                IOUtils.copy(stream, response.getOutputStream());

                log(usuarioLogado.getId(), "SUCESSO: ID RESPOSTA: " + idResposta, ETipoLog.CODIGO_BAIXADO);

                response.setContentType("application/force-download");
                response.setHeader("Content-Disposition", "attachment; filename=Resposta" + idResposta + ".zip");
                response.flushBuffer();

            } catch (IOException ex) {
            }
        }
    }

    @RequestMapping(value = "/downloadfileversion/{idRespostaClasse}/{idAluno}", method = RequestMethod.GET)
    public void downloadfileversion(
            @PathVariable int idRespostaClasse,
            @PathVariable int idAluno,
            HttpSession session,
            HttpServletRequest request,
            HttpServletResponse response) {

        RespostaService repoResposta = new RespostaService();

        Pessoa usuarioLogado = (Pessoa) session.getAttribute("UsuarioLogado");
        if (usuarioLogado.getIdPerfil() == EPerfil.PROFESSOR) {
            //Validação para verificar se a pessoa logada é professor do autor do código fonte
            TurmaService repoTurma = new TurmaService();
            if (!repoTurma.verificaProfessorDoAluno(usuarioLogado.getId(), idAluno)) {
                return;
            }
        } else if (usuarioLogado.getIdPerfil() == EPerfil.ALUNO) {
            //Validação para verificar se a pessoa logada é o autor do código fonte
            if (idAluno != usuarioLogado.getId()) {
                return;
            }
        }

        RespostaClasse classe = repoResposta.getRespostaClasse(idRespostaClasse);

        if (classe != null) {
            try {

                ArrayList<byte[]> arquivos = new ArrayList<byte[]>();
                ArrayList<String> nomes = new ArrayList<String>();

                arquivos.add(classe.getFonte().getBytes("UTF-8"));
                nomes.add(classe.getClasse());

                byte[] arquivoZip = Util.zipFiles(arquivos, nomes);
                InputStream stream = new ByteArrayInputStream(arquivoZip);
                IOUtils.copy(stream, response.getOutputStream());

                log(usuarioLogado.getId(), "SUCESSO: ID RESPOSTA CLASSE: " + idRespostaClasse, ETipoLog.CODIGO_BAIXADO);

                response.setContentType("application/force-download");
                response.setHeader("Content-Disposition", "attachment; filename=" + classe.getClasse() + ".zip");
                response.flushBuffer();

            } catch (IOException ex) {
            }
        }
    }

    @RequestMapping(value = "/savefavorite/{idExercicio}/{idClasse}", method = RequestMethod.GET)
    @ResponseBody
    public String savefavorite(
            @PathVariable int idExercicio,
            @PathVariable int idClasse,
            HttpSession session) {

        Pessoa pessoa = (Pessoa) session.getAttribute("UsuarioLogado");
        ExercicioSolucaoClasseService repoClasse = new ExercicioSolucaoClasseService();
        ExercicioSolucaoClasse classe = repoClasse.getById(idClasse);

        if (classe == null) {
            return "erro";
        }

        classe.setFavorito(!classe.isFavorito());

        if (repoClasse.update(classe)) {
            log(pessoa.getId(), "SUCESSO: ID: " + idClasse, ETipoLog.CODIGO_FAVORITO);
            return "" + classe.isFavorito();
        } else {
            log(pessoa.getId(), "ERRO: ID: " + idClasse, ETipoLog.CODIGO_FAVORITO);
            return "erro";
        }

    }

    @RequestMapping(value = "/showversionquestion/{idExercicio}/{idRespostaClasse}/{linha}", method = RequestMethod.GET)
    public String showversionquestion(
            @PathVariable int idExercicio,
            @PathVariable int idRespostaClasse,
            @PathVariable int linha,
            Model model,
            HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Pessoa pessoa = (Pessoa) session.getAttribute("UsuarioLogado");

        RespostaClasseService repoRespostaClasse = new RespostaClasseService();

        List<Object> dados;
        if (pessoa.getIdPerfil() == EPerfil.ALUNO) {
            dados = repoRespostaClasse.getMarcacaoAutor(idExercicio, pessoa.getId(), idRespostaClasse, linha);
        } else {
            dados = repoRespostaClasse.getMarcacaoLeitor(idExercicio, idRespostaClasse, linha);
        }

        model.addAttribute("lista", dados);
        model.addAttribute("idUsuarioLogado", pessoa.getId());

        return "exercicios/mensagens";
    }

    @RequestMapping(value = "/saveversionquestion", method = RequestMethod.POST)
    public ModelAndView saveversionquestion(
            @ModelAttribute("hdnQuestaoIdExercicio") int id,
            @ModelAttribute("hdnQuestaoIdRespostaClasse") int idRespostaClasse,
            @ModelAttribute("hdnQuestaoLinha") int linha,
            HttpSession session,
            HttpServletRequest request,
            BindingResult result) {

        ModelAndView mav = new ModelAndView();

        Pessoa pessoa = (Pessoa) session.getAttribute("UsuarioLogado");
        Turma turma = (Turma) session.getAttribute("TurmaSelecionada");

        RespostaClasseService repoRespostaClasse = new RespostaClasseService();
        ExercicioService repoExercicio = new ExercicioService();
        Exercicio exercicio = repoExercicio.getMeuExercicio(turma.getId(), id);

        if (exercicio != null) {
            try {
                String questao = request.getParameter("questaoRespostaClasse") == null ? "" : request.getParameter("questaoRespostaClasse").toString();

                if (!questao.isEmpty()) {
                    int idRemetente = pessoa.getId();
                    int idDestinatario = turma.getIdProfessor();

                    if (pessoa.getIdPerfil() == EPerfil.PROFESSOR) {
                        //Buscar o id do aluno
                        RespostaService repoResposta = new RespostaService();
                        RespostaClasse respostaClasse = repoRespostaClasse.getById(idRespostaClasse);
                        Resposta resposta = repoResposta.getById(respostaClasse.getIdResposta());
                        idDestinatario = resposta.getIdAutor();
                    }

                    if (repoRespostaClasse.inserirPergunta(id, idRemetente, pessoa.getNome(), idRespostaClasse, linha, idDestinatario, questao)) {
                        log(pessoa.getId(), "SUCESSO: ID EXERCICIO: " + exercicio.getId() + " ID RESPOSTACLASSE: " + idRespostaClasse + " LINHA: " + linha, ETipoLog.REALIZAR_PERGUNTA);
                    } else {
                        log(pessoa.getId(), "ERRO: ID EXERCICIO: " + exercicio.getId() + " ID RESPOSTACCLASSE: " + idRespostaClasse + " LINHA: " + linha, ETipoLog.REALIZAR_PERGUNTA);
                    }
                }
            } catch (Exception e) {
                log(pessoa.getId(), "ERRO: ID EXERCICIO: " + exercicio.getId(), ETipoLog.REALIZAR_PERGUNTA);
            }

        }

        mav.setView(new RedirectView("/classes/showversionquestion/" + id + "/" + idRespostaClasse + "/" + linha, true, true, false));
        return mav;

    }

    @RequestMapping(value = "/showversionannotation/{idExercicio}/{idRespostaClasse}/{linha}", method = RequestMethod.GET)
    @ResponseBody
    public Object showversionannotation(
            @PathVariable int idExercicio,
            @PathVariable int idRespostaClasse,
            @PathVariable int linha,
            Model model,
            HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Pessoa pessoa = (Pessoa) session.getAttribute("UsuarioLogado");

        RespostaClasseService repoRespostaClasse = new RespostaClasseService();
        Object dados = repoRespostaClasse.getAnotacao(idExercicio, idRespostaClasse, linha);

        return dados != null ? dados : "";
    }

    @RequestMapping(value = "/saveversionannotation", method = RequestMethod.POST)
    public ModelAndView saveversionannotation(
            @ModelAttribute("hdnAnotacaoIdExercicio") int id,
            @ModelAttribute("hdnAnotacaoIdRespostaClasse") int idRespostaClasse,
            @ModelAttribute("hdnAnotacaoLinha") int linha,
            HttpSession session,
            HttpServletRequest request,
            BindingResult result) {

        ModelAndView mav = new ModelAndView();

        Pessoa pessoa = (Pessoa) session.getAttribute("UsuarioLogado");
        Turma turma = (Turma) session.getAttribute("TurmaSelecionada");

        RespostaClasseService repoRespostaClasse = new RespostaClasseService();
        ExercicioService repoExercicio = new ExercicioService();
        Exercicio exercicio = repoExercicio.getMeuExercicio(turma.getId(), id);

        if (exercicio != null) {
            try {
                String anotacao = request.getParameter("anotacaoRespostaClasse") == null ? "" : request.getParameter("anotacaoRespostaClasse").toString();
                //boolean publico = request.getParameter("chkPublico") == null ? false : request.getParameter("chkPublico").toString().equals("1");

                if (!anotacao.isEmpty()) {
                    if (repoRespostaClasse.inserirAnotacao(id, pessoa.getId(), idRespostaClasse, linha, anotacao)) {
                        log(pessoa.getId(), "SUCESSO: ID EXERCICIO: " + exercicio.getId() + " ID RESPOSTACLASSE: " + idRespostaClasse + " LINHA: " + linha, ETipoLog.CODIGO_COMENTADO);
                    } else {
                        log(pessoa.getId(), "ERRO: ID EXERCICIO: " + exercicio.getId() + " ID RESPOSTACLASSE: " + idRespostaClasse + " LINHA: " + linha, ETipoLog.CODIGO_COMENTADO);
                    }
                }
            } catch (Exception e) {
                log(pessoa.getId(), "ERRO: ID EXERCICIO: " + exercicio.getId(), ETipoLog.CODIGO_COMENTADO);
            }

        }

        mav.setView(new RedirectView("/classes/showversionannotation/" + id + "/" + idRespostaClasse + "/" + linha, true, true, false));
        return mav;

    }

}
