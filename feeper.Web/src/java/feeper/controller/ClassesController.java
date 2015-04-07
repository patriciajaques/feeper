package feeper.controller;

import feeper.Data.entity.Exercicio;
import feeper.Data.entity.ExercicioClasse;
import feeper.Data.entity.ExercicioSolucao;
import feeper.Data.entity.ExercicioSolucaoClasse;
import feeper.Data.entity.Pessoa;
import feeper.Data.entity.Turma;
import feeper.Data.model.EPerfil;
import feeper.Data.model.ETipoLog;
import feeper.Data.model.Util;
import feeper.Data.service.ExercicioClasseMarcacaoService;
import feeper.Data.service.ExercicioService;
import feeper.Data.service.ExercicioClasseService;
import feeper.Data.service.ExercicioSolucaoClasseMarcacaoService;
import feeper.Data.service.ExercicioSolucaoClasseService;
import feeper.Data.service.ExercicioSolucaoService;
import feeper.Data.service.PessoaService;
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

        PaginadorUtil<ExercicioClasse> paginador = new PaginadorUtil<ExercicioClasse>();

        String sql = "select * from ExercicioClasse where IdAluno = " + usuarioLogado.getId() + " and Favorito = 1";

        List<ExercicioClasse> lista = paginador.Execute(
                ExercicioClasse.class,
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

    @RequestMapping(value = "/show/{idExercicio}/{idExercicioClasse}/{linha}", method = RequestMethod.GET)
    public ModelAndView show(
            @PathVariable int idExercicio,
            @PathVariable int idExercicioClasse,
            @PathVariable int linha,
            HttpSession session,
            Model model) {

        ModelAndView mav = new ModelAndView();

        ExercicioClasseService repoClasse = new ExercicioClasseService();
        ExercicioClasse classe = repoClasse.getById(idExercicioClasse);

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
        mav.addObject("classe", classe);
        mav.addObject("idExercicio", idExercicio);
        mav.addObject("idExercicioClasse", idExercicioClasse);
        mav.addObject("linha", linha);

        ExercicioClasseMarcacaoService repoClasseMarcacao = new ExercicioClasseMarcacaoService();
        List<Object> listaDuvidas = repoClasseMarcacao.getLinhasDuvida(idExercicioClasse);

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

    @RequestMapping(value = "/savefavorite/{idExercicio}/{idExercicioClasse}", method = RequestMethod.GET)
    @ResponseBody
    public String savefavorite(
            @PathVariable int idExercicio,
            @PathVariable int idExercicioClasse,
            HttpSession session) {

        Pessoa pessoa = (Pessoa) session.getAttribute("UsuarioLogado");
        ExercicioClasseService repoClasse = new ExercicioClasseService();
        ExercicioClasse classe = repoClasse.getById(idExercicioClasse);

        if (classe == null) {
            return "erro";
        }

        classe.setFavorito(!classe.isFavorito());

        if (repoClasse.update(classe)) {
            log(pessoa.getId(), "SUCESSO: ID: " + idExercicioClasse, ETipoLog.CODIGO_FAVORITO);
            return "" + classe.isFavorito();
        } else {
            log(pessoa.getId(), "ERRO: ID: " + idExercicioClasse, ETipoLog.CODIGO_FAVORITO);
            return "erro";
        }

    }

    @RequestMapping(value = "/listsolucaoclasses/{idSolucao}/{idAluno}", method = RequestMethod.GET)
    public ModelAndView listsolucaoclasses(
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

        ExercicioSolucaoClasseService repoclasses = new ExercicioSolucaoClasseService();
        List<ExercicioSolucaoClasse> classes = repoclasses.getbyIdSolucao(idSolucao);

        mav.setViewName("classes/listsolucaoclasses");
        mav.addObject("classes", classes);
        mav.addObject("idAluno", idAluno);

        return mav;
    }

    @RequestMapping(value = "/showversion/{idExercicioSolucaoClasse}/{idAluno}", method = RequestMethod.GET)
    public ModelAndView showversion(
            @PathVariable int idExercicioSolucaoClasse,
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

        ExercicioSolucaoClasseService repoClasse = new ExercicioSolucaoClasseService();
        ExercicioSolucaoClasse classe = repoClasse.getById(idExercicioSolucaoClasse);

        ExercicioSolucaoService repoSolucao = new ExercicioSolucaoService();
        ExercicioSolucao solucao = repoSolucao.getById(classe.getIdSolucao());

        mav.setViewName("classes/showversion");
        mav.addObject("idExercicio", solucao.getIdExercicio());
        mav.addObject("idExercicioSolucaoClasse", classe.getId());
        mav.addObject("classe", classe);

        ExercicioSolucaoClasseMarcacaoService repoSolucaoClasseMarcacao = new ExercicioSolucaoClasseMarcacaoService();
        List<Object> listaDuvidas = repoSolucaoClasseMarcacao.getLinhasDuvida(classe.getId());

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

    @RequestMapping(value = "/downloadpkgversion/{idSolucao}/{idAluno}", method = RequestMethod.GET)
    public void downloadpkgversion(
            @PathVariable int idSolucao,
            @PathVariable int idAluno,
            HttpSession session,
            HttpServletRequest request,
            HttpServletResponse response) {

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

        ExercicioSolucaoClasseService repoSolucaoClasse = new ExercicioSolucaoClasseService();
        List<ExercicioSolucaoClasse> classes = repoSolucaoClasse.getbyIdSolucao(idSolucao);

        if (classes != null && classes.size() > 0) {
            try {

                ArrayList<byte[]> arquivos = new ArrayList<byte[]>();
                ArrayList<String> nomes = new ArrayList<String>();

                for (ExercicioSolucaoClasse classe : classes) {
                    arquivos.add(classe.getCodigo().getBytes("UTF-8"));
                    nomes.add(classe.getNomeClasse());
                }

                byte[] arquivoZip = Util.zipFiles(arquivos, nomes);
                InputStream stream = new ByteArrayInputStream(arquivoZip);
                IOUtils.copy(stream, response.getOutputStream());

                log(usuarioLogado.getId(), "SUCESSO: ID SOLUÇÃO: " + idSolucao, ETipoLog.CODIGO_BAIXADO);

                response.setContentType("application/force-download");
                response.setHeader("Content-Disposition", "attachment; filename=Solucao" + idSolucao + ".zip");
                response.flushBuffer();

            } catch (IOException ex) {
            }
        }
    }

    @RequestMapping(value = "/downloadfileversion/{idExercicioSolucaoClasse}/{idAluno}", method = RequestMethod.GET)
    public void downloadfileversion(
            @PathVariable int idExercicioSolucaoClasse,
            @PathVariable int idAluno,
            HttpSession session,
            HttpServletRequest request,
            HttpServletResponse response) {

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

        ExercicioSolucaoClasseService repoSolucaoClasse = new ExercicioSolucaoClasseService();
        ExercicioSolucaoClasse classe = repoSolucaoClasse.getById(idExercicioSolucaoClasse);

        if (classe != null) {
            try {

                ArrayList<byte[]> arquivos = new ArrayList<byte[]>();
                ArrayList<String> nomes = new ArrayList<String>();

                arquivos.add(classe.getCodigo().getBytes("UTF-8"));
                nomes.add(classe.getNomeClasse());

                byte[] arquivoZip = Util.zipFiles(arquivos, nomes);
                InputStream stream = new ByteArrayInputStream(arquivoZip);
                IOUtils.copy(stream, response.getOutputStream());

                log(usuarioLogado.getId(), "SUCESSO: ID SOLUÇÃO CLASSE: " + idExercicioSolucaoClasse, ETipoLog.CODIGO_BAIXADO);

                response.setContentType("application/force-download");
                response.setHeader("Content-Disposition", "attachment; filename=" + classe.getNomeClasse() + ".zip");
                response.flushBuffer();

            } catch (IOException ex) {
            }
        }
    }

    @RequestMapping(value = "/showversionquestion/{idExercicioSolucaoClasse}/{linha}", method = RequestMethod.GET)
    public String showversionquestion(
            @PathVariable int idExercicioSolucaoClasse,
            @PathVariable int linha,
            Model model,
            HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Pessoa pessoa = (Pessoa) session.getAttribute("UsuarioLogado");

        ExercicioSolucaoClasseMarcacaoService repoMarcacao = new ExercicioSolucaoClasseMarcacaoService();

        List<Object> dados;
        if (pessoa.getIdPerfil() == EPerfil.ALUNO) {
            dados = repoMarcacao.getMarcacaoAutor(pessoa.getId(), idExercicioSolucaoClasse, linha);
        } else {
            dados = repoMarcacao.getMarcacaoLeitor(idExercicioSolucaoClasse, linha);
        }

        model.addAttribute("lista", dados);
        model.addAttribute("idUsuarioLogado", pessoa.getId());

        return "exercicios/mensagens";
    }

    @RequestMapping(value = "/saveversionquestion", method = RequestMethod.POST)
    public ModelAndView saveversionquestion(
            @ModelAttribute("hdnQuestaoIdExercicio") int id,
            @ModelAttribute("hdnQuestaoIdExercicioSolucaoClasse") int idExercicioSolucaoClasse,
            @ModelAttribute("hdnQuestaoLinha") int linha,
            HttpSession session,
            HttpServletRequest request,
            BindingResult result) {

        ModelAndView mav = new ModelAndView();

        Pessoa pessoa = (Pessoa) session.getAttribute("UsuarioLogado");
        Turma turma = (Turma) session.getAttribute("TurmaSelecionada");

        ExercicioService repoExercicio = new ExercicioService();
        Exercicio exercicio = repoExercicio.getMeuExercicio(turma.getId(), id);

        if (exercicio != null) {
            try {
                String questao = request.getParameter("questaoExercicioSolucaoClasse") == null ? "" : request.getParameter("questaoExercicioSolucaoClasse").toString();

                if (!questao.isEmpty()) {
                    int idRemetente = pessoa.getId();
                    int idDestinatario = turma.getIdProfessor();

                    if (pessoa.getIdPerfil() == EPerfil.PROFESSOR) {
                        //Buscar o id do aluno
                        ExercicioSolucaoClasseService repoClasse = new ExercicioSolucaoClasseService();
                        ExercicioSolucaoClasse classe = repoClasse.getById(idExercicioSolucaoClasse);
                        ExercicioSolucaoService repoSolucao = new ExercicioSolucaoService();
                        ExercicioSolucao solucao = repoSolucao.getById(classe.getIdSolucao());
                        idDestinatario = solucao.getIdAluno();
                    }

                    ExercicioSolucaoClasseMarcacaoService repoMarcacao = new ExercicioSolucaoClasseMarcacaoService();
                    if (repoMarcacao.inserirPergunta(idRemetente, pessoa.getNome(), idExercicioSolucaoClasse, linha, false, idDestinatario, questao)) {
                        log(pessoa.getId(), "SUCESSO: ID EXERCICIO: " + exercicio.getId() + " ID SOLUÇÃOCLASSE: " + idExercicioSolucaoClasse + " LINHA: " + linha, ETipoLog.REALIZAR_PERGUNTA);
                    } else {
                        log(pessoa.getId(), "ERRO: ID EXERCICIO: " + exercicio.getId() + " ID SOLUÇÃOCLASSE: " + idExercicioSolucaoClasse + " LINHA: " + linha, ETipoLog.REALIZAR_PERGUNTA);
                    }
                }
            } catch (Exception e) {
                log(pessoa.getId(), "ERRO: ID EXERCICIO: " + exercicio.getId(), ETipoLog.REALIZAR_PERGUNTA);
            }

        }

        mav.setView(new RedirectView("/classes/showversionquestion/" + idExercicioSolucaoClasse + "/" + linha, true, true, false));
        return mav;

    }

    @RequestMapping(value = "/showversionannotation/{idExercicioSolucaoClasse}/{linha}", method = RequestMethod.GET)
    @ResponseBody
    public Object showversionannotation(
            @PathVariable int idExercicioSolucaoClasse,
            @PathVariable int linha,
            Model model,
            HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Pessoa pessoa = (Pessoa) session.getAttribute("UsuarioLogado");

        ExercicioSolucaoClasseMarcacaoService repoSolucaoClasse = new ExercicioSolucaoClasseMarcacaoService();
        Object dados = repoSolucaoClasse.getAnotacao(idExercicioSolucaoClasse, linha);

        return dados != null ? dados : "";
    }

    @RequestMapping(value = "/saveversionannotation", method = RequestMethod.POST)
    public ModelAndView saveversionannotation(
            @ModelAttribute("hdnAnotacaoIdExercicio") int id,
            @ModelAttribute("hdnAnotacaoIdExercicioSolucaoClasse") int idExercicioSolucaoClasse,
            @ModelAttribute("hdnAnotacaoLinha") int linha,
            HttpSession session,
            HttpServletRequest request,
            BindingResult result) {

        ModelAndView mav = new ModelAndView();

        Pessoa pessoa = (Pessoa) session.getAttribute("UsuarioLogado");
        Turma turma = (Turma) session.getAttribute("TurmaSelecionada");

        ExercicioService repoExercicio = new ExercicioService();
        Exercicio exercicio = repoExercicio.getMeuExercicio(turma.getId(), id);

        if (exercicio != null) {
            try {
                String anotacao = request.getParameter("anotacaoExercicioSolucaoClasse") == null ? "" : request.getParameter("anotacaoExercicioSolucaoClasse").toString();

                if (!anotacao.isEmpty()) {
                    ExercicioSolucaoClasseMarcacaoService repoMarcacao = new ExercicioSolucaoClasseMarcacaoService();
                    if (repoMarcacao.inserirAnotacao(pessoa.getId(), idExercicioSolucaoClasse, linha, anotacao)) {
                        log(pessoa.getId(), "SUCESSO: ID EXERCICIO: " + exercicio.getId() + " ID SOLUÇÃOCLASSE: " + idExercicioSolucaoClasse + " LINHA: " + linha, ETipoLog.CODIGO_COMENTADO);
                    } else {
                        log(pessoa.getId(), "ERRO: ID EXERCICIO: " + exercicio.getId() + " ID SOLUÇÃOCLASSE: " + idExercicioSolucaoClasse + " LINHA: " + linha, ETipoLog.CODIGO_COMENTADO);
                    }
                }
            } catch (Exception e) {
                log(pessoa.getId(), "ERRO: ID EXERCICIO: " + exercicio.getId(), ETipoLog.CODIGO_COMENTADO);
            }

        }

        mav.setView(new RedirectView("/classes/showversionannotation/" + idExercicioSolucaoClasse + "/" + linha, true, true, false));
        return mav;

    }

    @RequestMapping(value = "/diffclasses/{idClasseAluno}/{idClasseColega}", method = RequestMethod.GET)
    public ModelAndView diffclasses(
            @PathVariable int idClasseAluno,
            @PathVariable int idClasseColega,
            HttpSession session,
            Model model) {

        ModelAndView mav = new ModelAndView();

        ExercicioSolucaoClasseService repoClasse = new ExercicioSolucaoClasseService();
        ExercicioSolucaoClasse classeAluno = repoClasse.getById(idClasseAluno);
        ExercicioSolucaoClasse classeColega = repoClasse.getById(idClasseColega);

        ExercicioSolucaoService repoSolucao = new ExercicioSolucaoService();
        PessoaService repoPessoa = new PessoaService();

        ExercicioSolucao solucao = repoSolucao.getById(classeAluno.getIdSolucao());
        Pessoa aluno = repoPessoa.getById(solucao.getIdAluno());

        solucao = repoSolucao.getById(classeColega.getIdSolucao());
        Pessoa colega = repoPessoa.getById(solucao.getIdAluno());

        Pessoa usuarioLogado = (Pessoa) session.getAttribute("UsuarioLogado");
        if (usuarioLogado.getIdPerfil() == EPerfil.PROFESSOR) {
            //Validação para verificar se a pessoa logada é professor do autor do código fonte
            TurmaService repoTurma = new TurmaService();
            if (!repoTurma.verificaProfessorDoAluno(usuarioLogado.getId(), aluno.getId())) {
                mav.setView(new RedirectView("/closemodal", true, true, false));
                return mav;
            }
        }

        mav.setViewName("classes/diffclasses");
        mav.addObject("nomeAluno", aluno.getNome());
        mav.addObject("nomeColega", colega.getNome());
        mav.addObject("textoClasseAluno", classeAluno.getCodigo());
        mav.addObject("textoClasseColega", classeColega.getCodigo());

        return mav;
    }
}
