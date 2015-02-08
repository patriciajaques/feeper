package feeper.controller;

import feeper.Data.entity.Exercicio;
import feeper.Data.entity.ExercicioCasoTeste;
import feeper.Data.entity.ExercicioClasse;
import feeper.Data.entity.ExercicioInterface;
import feeper.Data.entity.ExercicioInterfaceMembro;
import feeper.Data.entity.ExercicioInterfaceMembroParametro;
import feeper.Data.entity.ExercicioSolucao;
import feeper.Data.entity.MeusExercicios;
import feeper.Data.entity.Pessoa;
import feeper.Data.entity.Turma;
import feeper.Data.entity.UploadTemp;
import feeper.Data.model.EPerfil;
import feeper.Data.model.ETipoLog;
import feeper.Data.model.EmemberType;
import feeper.Data.model.IntegerResult;
import feeper.Data.model.ScalarResult;
import feeper.Data.model.Util;
import feeper.Data.service.ExercicioClasseMarcacaoService;
import feeper.Data.service.ExercicioService;
import feeper.Data.service.ExercicioClasseService;
import feeper.Data.service.ExercicioSolucaoService;
import feeper.Data.service.ExercicioCasoTesteService;
import feeper.Data.service.ExercicioInterfaceService;
import feeper.Data.service.UploadTempService;
import feeper.model.InterfaceHandler;
import feeper.model.PaginadorUtil;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    public ExerciciosController() {
        this.service = new ExercicioService();
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

    @RequestMapping(value = "/getJson", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Exercicio getJson(@RequestParam(value = "exercicioId", defaultValue = "0") String exercicioId, HttpServletRequest request) {

        int exercId = Integer.parseInt(exercicioId);

        ExercicioService service = new ExercicioService();
        Exercicio exercicio = service.getById(exercId);

        if (exercicio == null) {
            //é novo
            exercicio = new Exercicio();
            exercicio.setId(0);
            exercicio.setDataCadastro(new Date());
            exercicio.setAtivo(true);

            HttpSession session = request.getSession();
            Pessoa usuarioLogado = (Pessoa) session.getAttribute("UsuarioLogado");

            exercicio.setAutor(usuarioLogado);
            exercicio.setIdAutor(usuarioLogado.getId());

        } else {

            //esvazia para diminuir o tráfego de dados
            exercicio.setDescricao(null);

            ExercicioInterfaceService repoInterface = new ExercicioInterfaceService();
            exercicio.setInterfaceSolucao(repoInterface.getbyIdExercicio(exercId));

            ExercicioCasoTesteService repoCasoTeste = new ExercicioCasoTesteService();
            exercicio.setCasosTeste(repoCasoTeste.getByIdExercicio(exercId, false));
        }
        return exercicio;
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

        ExercicioInterfaceService repoInterface = new ExercicioInterfaceService();

        if (exercicioId != null && exercicioId > 0) {
            service.update(exercicio);

            ExercicioInterface interFace = repoInterface.getbyIdExercicio(exercicioId);

            Integer dbInterfaceID = interFace != null ? interFace.getId() : 0;
            Integer newInterfaceID = exercicio.getInterfaceSolucao() != null ? exercicio.getInterfaceSolucao().getId() : 0;

            if (newInterfaceID != dbInterfaceID) {
                repoInterface.delete(interFace);
            }

        } else {
            service.insert(exercicio);
        }

        if (exercicio.getInterfaceSolucao() != null) {
            repoInterface.salvarInterface(exercicio.getId(), exercicio.getInterfaceSolucao());
        }

        ExercicioCasoTesteService repoCasoTeste = new ExercicioCasoTesteService();
        List<ExercicioCasoTeste> casosTeste = exercicio.getCasosTeste();

        return repoCasoTeste.SaveCasos(exercicio.getId(), casosTeste);
    }

    @RequestMapping(value = "/uploaddescricao", method = RequestMethod.POST)
    @ResponseBody
    public int uploaddescricao(MultipartHttpServletRequest request) throws IOException {

        MultipartFile file = request.getFile("filedata");
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

    @RequestMapping(value = "/uploadinterfacesolucao", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ExercicioInterface uploadinterfacesolucao(MultipartHttpServletRequest request) throws IOException {

        MultipartFile file = request.getFile("filedata");
        HttpSession session = request.getSession();
        Pessoa usuarioLogado = (Pessoa) session.getAttribute("UsuarioLogado");

        File f = new File("tmp/" + file.getOriginalFilename());
        f.mkdirs();
        file.transferTo(f);

        String className = FilenameUtils.removeExtension(file.getOriginalFilename());
        InterfaceHandler handler = new InterfaceHandler();
        Class<?> classe = handler.GetInterface(f.getPath(), className);

        ExercicioInterface inter = new ExercicioInterface();
        inter.setNomeClasse(className);

        List<ExercicioInterfaceMembro> membros = new ArrayList();

        //fields pega todos, para testar getters e setters
        for (Field field : classe.getDeclaredFields()) {

            ExercicioInterfaceMembro membro = new ExercicioInterfaceMembro();
            membro.setMemberType(EmemberType.FIELD);
            membro.setModifier(field.getModifiers());
            membro.setName(field.getName());
            membro.setType(field.getType().getSimpleName());

            membros.add(membro);
        }

        //construtores, somente públicos
        for (Constructor constructor : classe.getConstructors()) {

            ExercicioInterfaceMembro membro = new ExercicioInterfaceMembro();
            membro.setMemberType(EmemberType.CONSTRUCTOR);
            membro.setModifier(constructor.getModifiers());
            membro.setName(constructor.getName());

            Parameter[] parameters = constructor.getParameters();
            List<ExercicioInterfaceMembroParametro> parametros = new ArrayList();
            for (Parameter parameter : parameters) {
                ExercicioInterfaceMembroParametro parametro = new ExercicioInterfaceMembroParametro();
                parametro.setOrdem(parametros.size() + 1);
                parametro.setName(parameter.getName());
                parametro.setType(parameter.getType().getSimpleName());
                parametros.add(parametro);
            }
            membro.setParametros(parametros);

            membros.add(membro);
        }

        //membros, somente públicos
        for (Method method : classe.getMethods()) {

            ExercicioInterfaceMembro membro = new ExercicioInterfaceMembro();
            membro.setMemberType(EmemberType.METHOD);
            membro.setModifier(method.getModifiers());
            membro.setType(method.getReturnType().getSimpleName());
            membro.setName(method.getName());

            Parameter[] parameters = method.getParameters();
            List<ExercicioInterfaceMembroParametro> parametros = new ArrayList();
            for (Parameter parameter : parameters) {
                ExercicioInterfaceMembroParametro parametro = new ExercicioInterfaceMembroParametro();
                parametro.setName(parameter.getName());
                parametro.setType(parameter.getType().getSimpleName());
                parametros.add(parametro);
            }
            membro.setParametros(parametros);

            membros.add(membro);
        }

        inter.setMembros(membros);

        return inter;
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

        if (exercicio != null) {

            ExercicioSolucaoService repoSolucao = new ExercicioSolucaoService();
            ExercicioSolucao solucao = repoSolucao.getLastByIdExercicio(exercicio.getId(), pessoa.getId());

            ExercicioClasseService repoClasses = new ExercicioClasseService();
            List<ExercicioClasse> listaClasses = repoClasses.getAllByIdExercicio(exercicio.getId(), pessoa.getId());

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

                ExercicioSolucao solucao = repoSolucao.enviarCorrecao(idSolucao.getResult());

                log(pessoa.getId(), "CORRETOR ID SOLUÇÃO: " + idSolucao.getResult() + " RESULTADO: " + solucao.getIdStatus(), ETipoLog.CODIGO_ENVIADO);

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
            ExercicioClasseService repoSolucaoClasses = new ExercicioClasseService();
            List<ExercicioClasse> classes = repoSolucaoClasses.getAllByIdExercicio(exercicio.getId(), pessoa.getId());

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
    public Object showcode(
            @PathVariable int idExercicio,
            @PathVariable int idClasse,
            Model model,
            HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Pessoa pessoa = (Pessoa) session.getAttribute("UsuarioLogado");

        ExercicioClasseService repoSolucaoClasses = new ExercicioClasseService();

        ExercicioClasseMarcacaoService repoClasseMarcacao = new ExercicioClasseMarcacaoService();
        Object[] dados = new Object[7];
        dados[0] = idClasse;
        ExercicioClasse classe = repoSolucaoClasses.getById(idClasse);
        dados[1] = classe.getNomeClasse();
        dados[2] = classe.getCodigo().replaceAll("\"", "#'#");
        dados[3] = repoClasseMarcacao.getLinhasDuvida(idClasse);
        dados[4] = repoClasseMarcacao.getLinhasAnotacao(idClasse);
        dados[5] = classe.isFavorito();

        return dados;
    }

    @RequestMapping(value = "/savesolucaoclasse", method = RequestMethod.POST)
    public ModelAndView savesolucaoclasse(
            @ModelAttribute("hdnIdExercicio") int id,
            HttpSession session,
            HttpServletRequest request,
            BindingResult result) {

        ModelAndView mav = new ModelAndView();

        Pessoa pessoa = (Pessoa) session.getAttribute("UsuarioLogado");
        Turma turma = (Turma) session.getAttribute("TurmaSelecionada");

        ExercicioClasseService repoSolucaoClasses = new ExercicioClasseService();
        Exercicio exercicio = service.getMeuExercicio(turma.getId(), id);

        if (exercicio != null) {
            try {
                int idclasse = request.getParameter("hdnIdClasse").isEmpty() ? 0 : Integer.parseInt(request.getParameter("hdnIdClasse").toString());
                String nomeClasse = request.getParameter("hdnNomeClasse") == null ? "" : request.getParameter("hdnNomeClasse").toString();
                String fonte = request.getParameter("hdnEditor") == null ? "" : request.getParameter("hdnEditor").toString();

                if (!fonte.isEmpty()) {
                    ExercicioClasse entity = new ExercicioClasse();

                    if (idclasse > 0) {
                        entity = repoSolucaoClasses.getById(idclasse);
                        entity.setCodigoAnterior(entity.getCodigo());
                    } else {
                        entity.setIdAluno(pessoa.getId());
                        entity.setIdExercicio(exercicio.getId());
                    }
                    entity.setNomeClasse(Util.removeAccent(nomeClasse));
                    entity.setDataCadastro(new Date());
                    entity.setCodigo(fonte);

                    if (entity.getId() > 0) {
                        repoSolucaoClasses.update(entity);
                    } else {
                        repoSolucaoClasses.insert(entity);
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
            @ModelAttribute("hdnQuestaoIdClasse") int idClasse,
            @ModelAttribute("hdnQuestaoLinha") int linha,
            HttpSession session,
            HttpServletRequest request,
            BindingResult result) {

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
            MultipartFile file = request.getFile("filedata");
            String fileName = Util.prepareStringForSave(Util.removeExtension(file.getOriginalFilename()));
            return fileName + "#@#" + IOUtils.toString(file.getInputStream(), "UTF-8");
        } catch (IOException ex) {
            return "";
        }
    }

    @RequestMapping(value = "/getstatus/{idExercicio}", method = RequestMethod.GET)
    @ResponseBody
    public int getstatus(
            @PathVariable int idExercicio,
            HttpSession session) {

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

}
