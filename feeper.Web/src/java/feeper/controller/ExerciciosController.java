package feeper.controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import feeper.Data.entity.ConfiguracaoSistema;
import feeper.Data.entity.Exercicio;
import feeper.Data.entity.ExercicioCasoTeste;
import feeper.Data.entity.ExercicioClasse;
import feeper.Data.entity.ExercicioClasseAuxiliar;
import feeper.Data.entity.ExercicioPontos;
import feeper.Data.entity.ExercicioSolucao;
import feeper.Data.entity.ExercicioSolucaoClasse;
import feeper.Data.entity.ExercicioSolucaoErro;
import feeper.Data.entity.MeusExercicios;
import feeper.Data.entity.Pessoa;
import feeper.Data.entity.Turma;
import feeper.Data.entity.UploadTemp;
import feeper.Data.model.EErrorType;
import feeper.Data.model.EPerfil;
import feeper.Data.model.EStatusSolucao;
import feeper.Data.model.ETipoLog;
import feeper.Data.model.FileUtils;
import feeper.Data.model.IntegerResult;
import feeper.Data.model.ScalarResult;
import feeper.Data.model.Util;
import feeper.Data.service.ConfiguracaoService;
import feeper.Data.service.ExercicioClasseMarcacaoService;
import feeper.Data.service.ExercicioService;
import feeper.Data.service.ExercicioClasseService;
import feeper.Data.service.ExercicioSolucaoService;
import feeper.Data.service.ExercicioCasoTesteService;
import feeper.Data.service.ExercicioClasseAuxiliarService;
import feeper.Data.service.ExercicioPontosService;
import feeper.Data.service.ExercicioSolucaoClasseService;
import feeper.Data.service.ExercicioSolucaoErroService;
import feeper.Data.service.MedalhaPessoaService;
import feeper.Data.service.UploadTempService;
import feeper.model.AssinaturaLoader;
import feeper.model.PaginadorUtil;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.hibernate.type.BinaryType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = "/exercicios")
public class ExerciciosController extends ApplicationController {

    ExercicioService service;
    ExercicioPontosService exercicioPontosService;

    public ExerciciosController() {
        this.service = new ExercicioService();
        this.exercicioPontosService = new ExercicioPontosService();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        return list("", "", 1, 10, "id", "asc", "", model, null);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String list(
            @ModelAttribute("nome") String nome,
            @ModelAttribute("autor") String autor,
            @ModelAttribute("currentPage") int currentPage,
            @ModelAttribute("pageSize") int pageSize,
            @ModelAttribute("sortField") String sortField,
            @ModelAttribute("sortDirection") String sortDirection,
            @ModelAttribute("gridAction") String gridAction,
            Model model,
            BindingResult result) {

        //Configurações do Paginador
        if (gridAction.equals("PageSizeChanged") || gridAction.equals("Sorted") || gridAction.equals("Searched")) {
            currentPage = 1;
        }

        if (currentPage == 0) {
            currentPage = 1;
        }
        if (pageSize == 0) {
            pageSize = 10;
        }
        if (sortField != null && !sortField.isEmpty()) {
            sortField = "E.ID";
        }
        if (sortDirection != null && !sortDirection.isEmpty()) {
            sortDirection = "Asc";
        }
        //-------------------------

        PaginadorUtil<Exercicio> paginador = new PaginadorUtil<Exercicio>();

        String sql = "select * from Exercicio as E "
                + "inner join Pessoa as P "
                + "on P.ID = E.IdAutor "
                + "where E.nome like :p0 "
                + "and P.nome like :p1 ";

        String[] params = new String[2];
        params[0] = "%" + nome + "%";
        params[1] = "%" + autor + "%";

        List<Exercicio> lista = paginador.Execute(
                Exercicio.class,
                model,
                sql,
                params,
                currentPage,
                pageSize,
                sortField,
                sortDirection);
        for (Exercicio exercicio : lista) {
            exercicio.setAutor(service.getAutor(exercicio.getId()));
        }

        model.addAttribute("listaExercicios", lista);
        model.addAttribute("nome", nome);
        model.addAttribute("autor", autor);

        return "exercicios/list";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Model model) {

        return "exercicios/edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable int id, Model model) {

        return "exercicios/edit";
    }

    @RequestMapping(value = "/getJson", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public List<Object> getJson(@RequestParam(value = "exercicioId", defaultValue = "0") String exercicioId, HttpServletRequest request) {

        int exercId = Integer.parseInt(exercicioId);

        HttpSession session = request.getSession();
        Pessoa usuarioLogado = (Pessoa) session.getAttribute("UsuarioLogado");

        ExercicioService service = new ExercicioService();
        Exercicio exercicio = service.getById(exercId);

        if (exercicio == null) {
            //é novo
            exercicio = new Exercicio();
            exercicio.setId(0);
            exercicio.setDataCadastro(new Date());
            exercicio.setAtivo(true);
            exercicio.setAutor(usuarioLogado);
            exercicio.setIdAutor(usuarioLogado.getId());

        } else {
            //esvazia para diminuir o tráfego de dados
            exercicio.setDescricao(null);

            ExercicioClasseAuxiliarService repoClasses = new ExercicioClasseAuxiliarService();
            List<ExercicioClasseAuxiliar> classes = repoClasses.getAllByIdExercicio(exercId);
            try {
                classes = this.carregaAssinaturas(classes, request);
            } catch (Exception ex) {
            }
            exercicio.setClassesAuxiliares(classes);

            ExercicioCasoTesteService repoCasoTeste = new ExercicioCasoTesteService();
            List<ExercicioCasoTeste> casosTeste = repoCasoTeste.getByIdExercicio(exercId, false);
            exercicio.setCasosTeste(casosTeste);
        }
        List<Object> resultados = new ArrayList<Object>();
        resultados.add(exercicio);
        resultados.add(ExercicioSolucaoService.CODIGO_PADRAO_CLASSE);
        return resultados;
    }

    @RequestMapping(value = "/saveJson", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public boolean saveJson(@RequestBody Exercicio exercicio) {

        ExercicioService service = new ExercicioService();

        Integer exercicioId = exercicio.getId();

        if (exercicio.getIdUploadTemp() > 0) {
            UploadTempService uploadService = new UploadTempService();
            UploadTemp upload = uploadService.getById(exercicio.getIdUploadTemp());

            exercicio.setDescricao(upload.getArquivo());
            exercicio.setIdUploadTemp(0);

            uploadService.delete(upload);
        } else if (exercicio.getId() > 0) {
            //carrega dados esvaziados no get
            Exercicio dbExercicio = service.getById(exercicio.getId());

            exercicio.setDescricao(dbExercicio.getDescricao());
        }

        if (exercicioId != null && exercicioId > 0) {
            service.update(exercicio);
        } else {
            service.insert(exercicio);
        }

        ExercicioClasseAuxiliarService repoClasses = new ExercicioClasseAuxiliarService();
        List<ExercicioClasseAuxiliar> classes = exercicio.getClassesAuxiliares();
        Boolean sucess = repoClasses.SaveClasses(exercicio.getId(), classes);
        if (sucess == false) {
            return false;
        }

        ExercicioCasoTesteService repoCasoTeste = new ExercicioCasoTesteService();
        List<ExercicioCasoTeste> casosTeste = exercicio.getCasosTeste();

        return repoCasoTeste.SaveCasos(exercicio.getId(), casosTeste);
    }

    @RequestMapping(value = "/carregaAssinaturas", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public List<ExercicioClasseAuxiliar> carregaAssinaturas(@RequestBody List<ExercicioClasseAuxiliar> classes, HttpServletRequest request) throws Exception {

        HttpSession session = request.getSession();
        Pessoa usuarioLogado = (Pessoa) session.getAttribute("UsuarioLogado");

        AssinaturaLoader handler = new AssinaturaLoader();
        return handler.CarregaAssinaturas(classes, usuarioLogado.getId());
    }

    @RequestMapping(value = "/uploaddescricao", method = RequestMethod.POST)
    @ResponseBody
    public int uploaddescricao(MultipartHttpServletRequest request) throws IOException {

        MultipartFile file = request.getFile("file");
        HttpSession session = request.getSession();
        Pessoa usuarioLogado = (Pessoa) session.getAttribute("UsuarioLogado");

        UploadTemp uploadTemp = new UploadTemp();
        uploadTemp.setGuid(UUID.randomUUID().toString());
        uploadTemp.setArquivo(file.getBytes());
        UploadTempService repoUploadTemp = new UploadTempService();
        if (repoUploadTemp.insert(uploadTemp)) {
            log(usuarioLogado.getId(), "SUCESSO: ID: " + uploadTemp.getId() + " NOME: " + file.getOriginalFilename(), ETipoLog.UPLOAD_EXERCICIO);
            return uploadTemp.getId();
        } else {
            log(usuarioLogado.getId(), "ERRO: NOME: " + file.getOriginalFilename(), ETipoLog.UPLOAD_EXERCICIO);
        }

        return 0;
    }

    @RequestMapping(value = "/verdescricao", method = RequestMethod.GET)
    @ResponseBody
    public void verdescricao(@RequestParam(value = "exercicioId", defaultValue = "0") int exercicioId,
            @RequestParam(value = "uploadTempId", defaultValue = "0") int uploadTempId,
            HttpServletResponse response) throws IOException {

        byte[] data = null;
        if (exercicioId > 0) {
            Exercicio exercicio = service.getById(exercicioId);
            data = exercicio.getDescricao();
        } else if (uploadTempId > 0) {
            UploadTempService repoUploadTemp = new UploadTempService();
            UploadTemp upload = repoUploadTemp.getById(uploadTempId);
            data = upload.getArquivo();
        }

        if (data != null) {
            InputStream stream = new ByteArrayInputStream(data);
            IOUtils.copy(stream, response.getOutputStream());

            response.setHeader("Content-Disposition", "inline; filename=Exercicio.pdf");
            response.flushBuffer();
        }
    }

    @RequestMapping(value = "/uploadClasseAuxiliar", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ExercicioClasseAuxiliar uploadClasseAuxiliar(MultipartHttpServletRequest request) throws IOException {

        MultipartFile file = request.getFile("file");
        HttpSession session = request.getSession();
        Pessoa usuarioLogado = (Pessoa) session.getAttribute("UsuarioLogado");

        String className = FilenameUtils.removeExtension(file.getOriginalFilename());
        ExercicioClasseAuxiliar classeAuxiliar = new ExercicioClasseAuxiliar();
        classeAuxiliar.setNomeClasse(className);

        String codigo = FileUtils.toString(file.getInputStream());
        classeAuxiliar.setCodigo(codigo);
        classeAuxiliar.setMostrarParaAluno(false);

        return classeAuxiliar;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView delete(
            @PathVariable int id,
            Model model,
            HttpSession session,
            final RedirectAttributes flash) {

        ModelAndView mav = new ModelAndView();
        mav.setView(new RedirectView("/exercicios", true, true, false));

        Exercicio exercicio = service.getById(id);

        Pessoa usuarioLogado = (Pessoa) session.getAttribute("UsuarioLogado");
        if (service.delete(exercicio)) {
            log(usuarioLogado.getId(), "SUCESSO: ID: " + id, ETipoLog.EXCLUIR_EXERCICIO);
            flash.addFlashAttribute("MSG_SUCESSO", "Registro excluído com sucesso");
        } else {
            log(usuarioLogado.getId(), "ERRO: ID: " + id, ETipoLog.EXCLUIR_EXERCICIO);
            flash.addFlashAttribute("MSG_ERRO", "Ocorreu um erro ao excluir o registro");
        }

        return mav;
    }

    @RequestMapping(value = "/meusexercicios", method = RequestMethod.GET)
    public String meusexercicios(Model model, HttpServletRequest request) {

        return meusexercicios(1, 10, "Nome", "asc", "", model, null, request);
    }

    @RequestMapping(value = "/meusexercicios", method = RequestMethod.POST)
    public String meusexercicios(
            @ModelAttribute("currentPage") int currentPage,
            @ModelAttribute("pageSize") int pageSize,
            @ModelAttribute("sortField") String sortField,
            @ModelAttribute("sortDirection") String sortDirection,
            @ModelAttribute("gridAction") String gridAction,
            Model model,
            BindingResult result,
            HttpServletRequest request) {

        //Configurações do Paginador
        if (gridAction.equals("PageSizeChanged") || gridAction.equals("Sorted") || gridAction.equals("Searched")) {
            currentPage = 1;
        }

        if (currentPage == 0) {
            currentPage = 1;
        }
        if (pageSize == 0) {
            pageSize = 10;
        }
        if (sortField != null && !sortField.isEmpty()) {
            sortField = "E.Nome";
        }
        if (sortDirection != null && !sortDirection.isEmpty()) {
            sortDirection = "Asc";
        }
        //-------------------------

        PaginadorUtil<MeusExercicios> paginador = new PaginadorUtil<MeusExercicios>();

        HttpSession session = request.getSession(false);
        Pessoa pessoa = (Pessoa) session.getAttribute("UsuarioLogado");
        Turma turma = (Turma) session.getAttribute("TurmaSelecionada");

        String sql = "select "
                + "  E.ID,  "
                + "  E.Nome, "
                + "  E.Descricao, "
                + "  TRUNCATE_TEXT(REMOVE_HTML_TAGS(E.DescricaoHtml), 100) AS DescricaoHtml, "
                + "  T.Nome AS Turma, "
                + "  (select MAX(DataCadastro) from ExercicioSolucao ES "
                + "  where ES.IdExercicio = E.ID and ES.IdAluno = :p0 ) AS DataUltimaAlteracao "
                + "from  "
                + "  TurmaExercicio TE "
                + "  inner join Exercicio E "
                + "  on E.ID = TE.IdExercicio "
                + "  inner join Turma T "
                + "  on T.ID = TE.IdTurma "
                + "where "
                + "  TE.Visivel = 1 "
                + "  and TE.IdTurma = :p1 "
                + "  and T.Ativo = 1";

        Integer[] params = new Integer[2];
        params[0] = pessoa.getId();
        params[1] = turma.getId();

        ScalarResult[] scalarResult = new ScalarResult[6];
        scalarResult[0] = new ScalarResult("ID", IntegerType.INSTANCE);
        scalarResult[1] = new ScalarResult("Nome", StringType.INSTANCE);
        scalarResult[2] = new ScalarResult("Descricao", BinaryType.INSTANCE);
        scalarResult[3] = new ScalarResult("DescricaoHtml", StringType.INSTANCE);
        scalarResult[4] = new ScalarResult("Turma", StringType.INSTANCE);
        scalarResult[5] = new ScalarResult("DataUltimaAlteracao", TimestampType.INSTANCE);

        List lista = paginador.Execute(model,
                sql,
                params,
                scalarResult,
                currentPage,
                pageSize,
                sortField,
                sortDirection);

        model.addAttribute("listaExercicios", lista);

        return "exercicios/meusexercicios";
    }

    @RequestMapping(value = "/solucionar/{id}", method = RequestMethod.GET)
    public ModelAndView solucionar(
            @PathVariable int id,
            Model model,
            HttpServletRequest request) {

        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession(false);
        Pessoa pessoa = (Pessoa) session.getAttribute("UsuarioLogado");
        Turma turma = (Turma) session.getAttribute("TurmaSelecionada");
        
        Exercicio exercicio = service.getMeuExercicio(turma.getId(), id);
        exercicio.setPontosAcerto(exercicioPontosService.exercicioPontuacaoIdPessoa(pessoa.getId(), id));
        
        pessoa.setPontos(exercicioPontosService.getPointsByIdPessoa(pessoa.getId()));
        if (exercicio != null) {

            ExercicioSolucaoService repoSolucao = new ExercicioSolucaoService();
            ExercicioSolucao solucao = repoSolucao.getLastByIdExercicio(exercicio.getId(), pessoa.getId());

            ExercicioClasseService repoClasses = new ExercicioClasseService();
            List<ExercicioClasse> listaClasses = repoClasses.getAllByIdExercicio(exercicio.getId(), pessoa.getId());

            for (ExercicioClasse classe : listaClasses) {

                classe.setCodigo(classe.getCodigo().replaceAll("#n#", System.getProperty("line.separator")));
            }

            //carrega classes auxiliares
            ExercicioClasseAuxiliarService repoClassesAuxiliares = new ExercicioClasseAuxiliarService();
            List<ExercicioClasseAuxiliar> listaClassesAuxiliares = repoClassesAuxiliares.getAllByIdExercicio(exercicio.getId());

            for (ExercicioClasseAuxiliar classeAuxiliar : listaClassesAuxiliares) {

                if (classeAuxiliar.getMostrarParaAluno() != true) {
                    continue;
                }

                Boolean finded = false;
                for (ExercicioClasse classe : listaClasses) {
                    if (classe.getNomeClasse().equals(classeAuxiliar.getNomeClasse())) {
                        finded = true;
                        break;
                    }
                }
                if (finded == false) {
                    ExercicioClasse classe = new ExercicioClasse();
                    classe.setIdExercicio(exercicio.getId());
                    classe.setIdAluno(pessoa.getId());
                    classe.setDataCadastro(new Date());
                    classe.setNomeClasse(classeAuxiliar.getNomeClasse());
                    classe.setCodigo(classeAuxiliar.getCodigo());
                    repoClasses.insert(classe);
                    listaClasses.add(classe);
                }
            }
            
            mav.addObject("Pessoa", pessoa);
            mav.addObject("Exercicio", exercicio);
            mav.addObject("Solucao", solucao);
            mav.addObject("Classes", listaClasses);
            mav.addObject("CodigoFontePadraoClasse", ExercicioSolucaoService.CODIGO_PADRAO_CLASSE.replaceAll("\n", "#n#"));

            mav.setViewName("exercicios/solucionar");
            return mav;
        }
        mav.setView(new RedirectView("/exercicios/meusexercicios", true, true, false));
        return mav;
    }

    @RequestMapping(value = "/enviarcorrecao", method = RequestMethod.POST)
    public ModelAndView enviarcorrecao(
            Model model,
            HttpServletRequest request,
            final RedirectAttributes flash) {

        int id = Integer.parseInt(request.getParameter("hdnIdExercicio").toString());

        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession(false);

        Pessoa pessoa = (Pessoa) session.getAttribute("UsuarioLogado");
        Turma turma = (Turma) session.getAttribute("TurmaSelecionada");

        Exercicio exercicio = service.getMeuExercicio(turma.getId(), id);

        if (exercicio != null) {
            ExercicioClasseService repoClasses = new ExercicioClasseService();

            List<ExercicioClasse> listaClasses = repoClasses.getAllByIdExercicio(exercicio.getId(), pessoa.getId());

            if (listaClasses != null && listaClasses.size() > 0) {

                ExercicioSolucaoService repoSolucao = new ExercicioSolucaoService();
                IntegerResult idSolucao = new IntegerResult();
                repoSolucao.salvarSolucao(pessoa.getId(), exercicio.getId(), idSolucao);

                //enviar para correção
                log(pessoa.getId(), "SUCESSO: ID EXERCICIO: " + exercicio.getId(), ETipoLog.CODIGO_ENVIADO);
                this.enviarCorrecao(idSolucao.getResult());
            }
        }

        mav.setView(new RedirectView("/exercicios/solucionar/" + id, true, true, false));
        return mav;
    }

    @RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
    public void download(
            @PathVariable int id,
            HttpServletRequest request,
            HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        Pessoa pessoa = (Pessoa) session.getAttribute("UsuarioLogado");
        Turma turma = (Turma) session.getAttribute("TurmaSelecionada");

        Exercicio exercicio = service.getMeuExercicio(turma.getId(), id);

        if (exercicio != null) {
            ExercicioClasseService repoClasses = new ExercicioClasseService();
            List<ExercicioClasse> classes = repoClasses.getAllByIdExercicio(exercicio.getId(), pessoa.getId());

            if (classes != null && classes.size() > 0) {
                try {

                    ArrayList<byte[]> arquivos = new ArrayList<byte[]>();
                    ArrayList<String> nomes = new ArrayList<String>();

                    for (ExercicioClasse classe : classes) {
                        arquivos.add(classe.getCodigo().getBytes("UTF-8"));
                        nomes.add(classe.getNomeClasse() + ".java");
                    }

                    byte[] arquivoZip = Util.zipFiles(arquivos, nomes);
                    InputStream stream = new ByteArrayInputStream(arquivoZip);
                    IOUtils.copy(stream, response.getOutputStream());

                    log(pessoa.getId(), "SUCESSO: ID EXERCICIO: " + id, ETipoLog.CODIGO_BAIXADO);

                    response.setContentType("application/force-download");
                    response.setHeader("Content-Disposition", "attachment; filename=Exercicio" + id + ".zip");
                    response.flushBuffer();

                } catch (IOException ex) {
                }
            }
        }
    }

    @RequestMapping(value = "/showclassecode/{idExercicio}/{idClasse}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object showclassecode(
            @PathVariable int idExercicio,
            @PathVariable int idClasse,
            Model model,
            HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Pessoa pessoa = (Pessoa) session.getAttribute("UsuarioLogado");

        ExercicioClasseService repoClasses = new ExercicioClasseService();

        ExercicioClasseMarcacaoService repoClasseMarcacao = new ExercicioClasseMarcacaoService();
        Object[] dados = new Object[7];
        dados[0] = idClasse;
        ExercicioClasse classe = repoClasses.getById(idClasse);
        dados[1] = classe.getNomeClasse();

        dados[2] = classe.getCodigo();

        dados[3] = repoClasseMarcacao.getLinhasDuvida(idClasse);
        dados[4] = repoClasseMarcacao.getLinhasAnotacao(idClasse);
        dados[5] = classe.isFavorito();

        return dados;
    }

    @RequestMapping(value = "/saveclasse", method = RequestMethod.POST)
    public ModelAndView saveclasse(
            @ModelAttribute("hdnIdExercicio") int id,
            HttpSession session,
            HttpServletRequest request,
            BindingResult result) {

        ModelAndView mav = new ModelAndView();

        Pessoa pessoa = (Pessoa) session.getAttribute("UsuarioLogado");
        Turma turma = (Turma) session.getAttribute("TurmaSelecionada");

        ExercicioClasseService repoClasses = new ExercicioClasseService();
        Exercicio exercicio = service.getMeuExercicio(turma.getId(), id);

        if (exercicio != null) {
            try {
                int idclasse = request.getParameter("hdnIdClasse").isEmpty() ? 0 : Integer.parseInt(request.getParameter("hdnIdClasse").toString());
                String nomeClasse = request.getParameter("hdnNomeClasse") == null ? "" : request.getParameter("hdnNomeClasse").toString();
                String fonte = request.getParameter("hdnEditor") == null ? "" : request.getParameter("hdnEditor").toString();

                if (!fonte.isEmpty()) {
                    ExercicioClasse entity = new ExercicioClasse();

                    if (idclasse > 0) {
                        entity = repoClasses.getById(idclasse);
                        entity.setCodigoAnterior(entity.getCodigo());
                    } else {
                        entity.setIdAluno(pessoa.getId());
                        entity.setIdExercicio(exercicio.getId());
                    }
                    entity.setNomeClasse(Util.removeAccent(nomeClasse));
                    entity.setDataCadastro(new Date());

                    entity.setCodigo(fonte);

                    if (entity.getId() > 0) {
                        repoClasses.update(entity);
                    } else {
                        repoClasses.insert(entity);
                    }

                    log(pessoa.getId(), "SUCESSO: CLASSE: " + nomeClasse, ETipoLog.CODIGO_SALVO);
                }
            } catch (Exception e) {
                log(pessoa.getId(), "SUCESSO: ID EXERCICIO: " + exercicio.getId(), ETipoLog.CODIGO_SALVO);
            }

        }

        mav.setView(new RedirectView("/exercicios/solucionar/" + id, true, true, false));
        return mav;

    }

    @RequestMapping(value = "/deleteclasse/{idExercicio}/{idClasse}", method = RequestMethod.GET)
    public ModelAndView deleteClasse(
            @PathVariable int idExercicio,
            @PathVariable int idClasse,
            Model model,
            HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Pessoa pessoa = (Pessoa) session.getAttribute("UsuarioLogado");

        try {
            ExercicioClasseService repoExercicioClasses = new ExercicioClasseService();
            ExercicioClasse classe = repoExercicioClasses.getById(idClasse);

            if (classe != null) {
                repoExercicioClasses.delete(classe);
                log(pessoa.getId(), "SUCESSO: ID: " + idClasse, ETipoLog.CODIGO_EXCLUIDO);
            }
        } catch (Exception e) {
            log(pessoa.getId(), "ERRO: ID: " + idClasse, ETipoLog.CODIGO_EXCLUIDO);
        }

        ModelAndView mav = new ModelAndView();
        mav.setView(new RedirectView("/exercicios/solucionar/" + idExercicio, true, true, false));
        return mav;

    }

    @RequestMapping(value = "/showquestion/{idExercicio}/{idClasse}/{linha}", method = RequestMethod.GET)
    public String showquestion(
            @PathVariable int idExercicio,
            @PathVariable int idClasse,
            @PathVariable int linha,
            Model model,
            HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Pessoa pessoa = (Pessoa) session.getAttribute("UsuarioLogado");

        ExercicioClasseMarcacaoService repoMarcacao = new ExercicioClasseMarcacaoService();

        List<Object> dados;
        if (pessoa.getIdPerfil() == EPerfil.ALUNO) {
            dados = repoMarcacao.getMarcacaoAutor(idExercicio, pessoa.getId(), idClasse, linha);
        } else {
            dados = repoMarcacao.getMarcacaoLeitor(idExercicio, idClasse, linha);
        }

        model.addAttribute("lista", dados);
        model.addAttribute("idUsuarioLogado", pessoa.getId());

        return "exercicios/mensagens";
    }

    @RequestMapping(value = "/savequestion", method = RequestMethod.POST)
    public ModelAndView savequestion(
            @ModelAttribute("hdnQuestaoIdExercicio") int id,
            @ModelAttribute("hdnQuestaoIdExercicioClasse") int idClasse,
            @ModelAttribute("hdnQuestaoLinha") int linha,
            HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        ModelAndView mav = new ModelAndView();

        Pessoa pessoa = (Pessoa) session.getAttribute("UsuarioLogado");
        Turma turma = (Turma) session.getAttribute("TurmaSelecionada");

        ExercicioClasseService repoClasses = new ExercicioClasseService();
        ExercicioClasseMarcacaoService repoMarcacoes = new ExercicioClasseMarcacaoService();
        Exercicio exercicio = service.getMeuExercicio(turma.getId(), id);

        if (exercicio != null) {
            try {
                String questao = request.getParameter("questaoClasse") == null ? "" : request.getParameter("questaoClasse").toString();

                if (!questao.isEmpty()) {
                    int idRemetente = pessoa.getId();
                    int idDestinatario = turma.getIdProfessor();

                    if (pessoa.getIdPerfil() == EPerfil.PROFESSOR) {
                        //Buscar o id do aluno
                        ExercicioClasse classe = repoClasses.getById(idClasse);
                        idDestinatario = classe.getIdAluno();
                    }

                    if (repoMarcacoes.inserirPergunta(id, idRemetente, pessoa.getNome(), idClasse, linha, false, idDestinatario, questao)) {
                        log(pessoa.getId(), "SUCESSO: ID EXERCICIO: " + exercicio.getId() + " ID CLASSE: " + idClasse + " LINHA: " + linha, ETipoLog.REALIZAR_PERGUNTA);
                    } else {
                        log(pessoa.getId(), "ERRO: ID EXERCICIO: " + exercicio.getId() + " ID CLASSE: " + idClasse + " LINHA: " + linha, ETipoLog.REALIZAR_PERGUNTA);
                    }
                }
            } catch (Exception e) {
                log(pessoa.getId(), "ERRO: ID EXERCICIO: " + exercicio.getId(), ETipoLog.REALIZAR_PERGUNTA);
            }

        }

        mav.setView(new RedirectView("/exercicios/showquestion/" + id + "/" + idClasse + "/" + linha, true, true, false));
        return mav;

    }

    @RequestMapping(value = "/showannotation/{idExercicio}/{idClasse}/{linha}", method = RequestMethod.GET)
    @ResponseBody
    public Object showannotation(
            @PathVariable int idExercicio,
            @PathVariable int idClasse,
            @PathVariable int linha,
            Model model,
            HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Pessoa pessoa = (Pessoa) session.getAttribute("UsuarioLogado");

        ExercicioClasseMarcacaoService repoMarcacoes = new ExercicioClasseMarcacaoService();
        Object dados = repoMarcacoes.getAnotacao(idExercicio, idClasse, linha);

        return dados != null ? dados : "";
    }

    @RequestMapping(value = "/saveannotation", method = RequestMethod.POST)
    public ModelAndView saveannotation(
            @ModelAttribute("hdnAnotacaoIdExercicio") int id,
            @ModelAttribute("hdnAnotacaoIdClasse") int idClasse,
            @ModelAttribute("hdnAnotacaoLinha") int linha,
            HttpSession session,
            HttpServletRequest request,
            BindingResult result) {

        ModelAndView mav = new ModelAndView();

        Pessoa pessoa = (Pessoa) session.getAttribute("UsuarioLogado");
        Turma turma = (Turma) session.getAttribute("TurmaSelecionada");

        ExercicioClasseMarcacaoService repoMarcacoes = new ExercicioClasseMarcacaoService();
        Exercicio exercicio = service.getMeuExercicio(turma.getId(), id);

        if (exercicio != null) {
            try {
                String anotacao = request.getParameter("anotacaoClasse") == null ? "" : request.getParameter("anotacaoClasse").toString();

                if (!anotacao.isEmpty()) {
                    if (repoMarcacoes.inserirAnotacao(id, pessoa.getId(), idClasse, linha, false, anotacao)) {
                        log(pessoa.getId(), "SUCESSO: ID EXERCICIO: " + exercicio.getId() + " ID CLASSE: " + idClasse + " LINHA: " + linha, ETipoLog.CODIGO_COMENTADO);
                    } else {
                        log(pessoa.getId(), "ERRO: ID EXERCICIO: " + exercicio.getId() + " ID CLASSE: " + idClasse + " LINHA: " + linha, ETipoLog.CODIGO_COMENTADO);
                    }
                }
            } catch (Exception e) {
                log(pessoa.getId(), "ERRO: ID EXERCICIO: " + exercicio.getId(), ETipoLog.CODIGO_COMENTADO);
            }

        }

        mav.setView(new RedirectView("/exercicios/showannotation/" + id + "/" + idClasse + "/" + linha, true, true, false));
        return mav;

    }

    @RequestMapping(value = "/uploadclass", method = RequestMethod.POST)
    @ResponseBody
    public String uploadclass(MultipartHttpServletRequest request) {

        try {
            MultipartFile file = request.getFile("file");
            String fileName = Util.prepareStringForSave(Util.removeExtension(file.getOriginalFilename()));
            return fileName + "#@#" + IOUtils.toString(file.getInputStream(), "UTF-8");
        } catch (IOException ex) {
            return "";
        }
    }

    @RequestMapping(value = "/getstatus/{idExercicio}", method = RequestMethod.GET)
    @ResponseBody
    public int getstatus(@PathVariable int idExercicio, HttpSession session) {

        Pessoa pessoa = (Pessoa) session.getAttribute("UsuarioLogado");
        Turma turma = (Turma) session.getAttribute("TurmaSelecionada");

        Exercicio exercicio = service.getMeuExercicio(turma.getId(), idExercicio);

        if (exercicio != null) {

            ExercicioSolucaoService repoSolucao = new ExercicioSolucaoService();
            ExercicioSolucao solucao = repoSolucao.getLastByIdExercicio(exercicio.getId(), pessoa.getId());

            return solucao.getIdStatus();
        }

        return 0;
    }

    public void enviarCorrecao(int idSolucao) {

        ExercicioSolucaoService repoSolucao = new ExercicioSolucaoService();
        ExercicioSolucao solucao = repoSolucao.getById(idSolucao);
        int idExercicio = solucao.getIdExercicio();

        ExercicioSolucaoClasseService repoClassesSolucao = new ExercicioSolucaoClasseService();
        solucao.setClasses(repoClassesSolucao.getbyIdSolucao(solucao.getId()));

        ExercicioCasoTesteService repoTestes = new ExercicioCasoTesteService();
        solucao.setTestes(repoTestes.getByIdExercicio(idExercicio, true));

        for (int i = 0; i < solucao.getTestes().size(); i++) {
            ExercicioCasoTeste teste = solucao.getTestes().get(i);
            if (teste.getMensagemCompilacao() != null) {
                teste.setMensagemCompilacao(teste.getMensagemCompilacao().replace("\r\n", "<br/>").replace("\n", "<br/>"));
            }
            if (teste.getMensagemPersonalizada() != null) {
                teste.setMensagemPersonalizada(teste.getMensagemPersonalizada().replace("\r\n", "<br/>").replace("\n", "<br/>"));
            }
        }

        try {
            ConfiguracaoService confService = new ConfiguracaoService();
            ConfiguracaoSistema conf = confService.getConfiguracao();
            String serverURL = conf.getEnderecoSistemaCorretorJava();

            String xmlData = this.solucaoToXML(solucao);

            Unirest.setTimeouts(600000, 600000);

            Future<HttpResponse<String>> future = Unirest.post(serverURL)
                    .header("accept", "application/xml")
                    .header("Accept-Charset", "UTF-8")
                    .header("Content-Type", "application/xml")
                    .body(xmlData)
                    .asStringAsync(new Callback<String>() {

                        public void failed(UnirestException ex) {
                            ExerciciosController.setaErroSolucao(solucao, ex.getMessage());
                        }

                        public void completed(HttpResponse<String> response) {
                            try {
                                Charset charset = Charset.forName("UTF8");
                                BufferedReader in = new BufferedReader(new InputStreamReader(response.getRawBody(), charset));
                                StringBuilder builder = new StringBuilder();
                                String line = null;

                                while ((line = in.readLine()) != null) {
                                    builder.append(line);
                                }
                                ExerciciosController.setaResultadoSolucao(builder.toString());
                            } catch (Exception ex) {
                                ExerciciosController.setaErroSolucao(solucao, ex.getMessage());
                            }
                        }

                        public void cancelled() {
                            ExerciciosController.setaErroSolucao(solucao, "The request has been cancelled");
                        }
                    });

        } catch (Exception ex) {
            ExerciciosController.setaErroSolucao(solucao, ex.getMessage());
            log(solucao.getIdAluno(), "ERRO ID SOLUÇÃO: " + solucao.getId(), ETipoLog.CODIGO_ENVIADO);
        }
    }

    public static void setaErroSolucao(ExercicioSolucao solucao, String mensagem) {

        ExercicioSolucaoService repoSolucao = new ExercicioSolucaoService();

        solucao.setIdStatus(EStatusSolucao.ERRO_COMPILACAO);
        solucao.setErrosCount(1);
        repoSolucao.update(solucao);

        ExercicioSolucaoErro erro = new ExercicioSolucaoErro();
        erro.setIdSolucao(solucao.getId());
        erro.setIdCasoTeste(-1);
        erro.setErrorType(EErrorType.COMPILACAO);
        erro.setLinhaErro(-1);
        erro.setMensagemErro(mensagem);

        ExercicioSolucaoErroService repoErrosSolucao = new ExercicioSolucaoErroService();
        repoErrosSolucao.insert(erro);
    }

    public static void setaResultadoSolucao(String responseText) throws JAXBException {

        ExercicioSolucaoService repoSolucao = new ExercicioSolucaoService();
        ExercicioPontosService exercicioPontosService = new ExercicioPontosService();
        ExercicioSolucao solucao = ExerciciosController.solucaoFromXML(responseText);

        if (solucao.getErros() != null) {
            ExercicioSolucaoErroService repoErrosSolucao = new ExercicioSolucaoErroService();

            for (ExercicioSolucaoErro erro : solucao.getErros()) {

                repoErrosSolucao.insert(erro);
            }
        }
        
        Integer pontos = exercicioPontosService.exercicioPontuacaoIdPessoa(solucao.getIdAluno(), solucao.getIdExercicio());
        repoSolucao.update(solucao);
        
        // Se for 4 exercicio correto
        if(solucao.getIdStatus() == EStatusSolucao.RESOLVIDO){ // Pontuacao
            ExercicioPontos exercicioPontos = new ExercicioPontos();
            exercicioPontos.setIdAluno(solucao.getIdAluno());
            exercicioPontos.setIdExercicio(solucao.getIdExercicio());
            exercicioPontos.setPontos(pontos);
            //exercicioPontos.setPontos(100);
            exercicioPontosService.insert(exercicioPontos);
        }
        
        MedalhaPessoaService medalhaPessoaService = new MedalhaPessoaService();
        medalhaPessoaService.notifyEnvioExercicio(solucao.getIdAluno());
    }

    private static String solucaoToXML(ExercicioSolucao solucao) throws JAXBException {

        for (ExercicioCasoTeste teste : solucao.getTestes()) {

            teste.setCodigo(teste.getCodigo().replace(System.getProperty("line.separator"), "#n#"));
            teste.setCodigo(teste.getCodigo().replace("\r\n", "#n#"));
            teste.setCodigo(teste.getCodigo().replace("\n", "#n#"));
        }
        
        for (ExercicioSolucaoClasse classe : solucao.getClasses()) {

            classe.setCodigo(classe.getCodigo().replace(System.getProperty("line.separator"), "#n#"));
            classe.setCodigo(classe.getCodigo().replace("\r\n", "#n#"));
            classe.setCodigo(classe.getCodigo().replace("\n", "#n#"));
        }

        JAXBContext context = JAXBContext.newInstance(ExercicioSolucao.class);
        Marshaller m = context.createMarshaller();
        StringWriter sw = new StringWriter();

        m.marshal(solucao, sw);

        return sw.toString();
    }

    private static ExercicioSolucao solucaoFromXML(String xmlString) throws JAXBException {

        StringReader reader = new StringReader(xmlString);
        JAXBContext jaxbContext = JAXBContext.newInstance(ExercicioSolucao.class
        );
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (ExercicioSolucao) jaxbUnmarshaller.unmarshal(reader);
    }

}
