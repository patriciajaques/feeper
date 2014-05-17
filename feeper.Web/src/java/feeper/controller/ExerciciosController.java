package feeper.controller;

import com.oreilly.servlet.Base64Encoder;
import feeper.Data.entity.CodigoFonte;
import feeper.Data.entity.Exercicio;
import feeper.Data.entity.ExercicioClasseValidacao;
import feeper.Data.entity.ExercicioValidacao;
import feeper.Data.entity.MeusExercicios;
import feeper.Data.entity.Pessoa;
import feeper.Data.entity.Resposta;
import feeper.Data.entity.RespostaCodigoFonte;
import feeper.Data.entity.Turma;
import feeper.Data.entity.UploadTemp;
import feeper.Data.model.EPerfil;
import feeper.Data.model.ETipoLog;
import feeper.Data.model.IntegerResult;
import feeper.Data.model.ScalarResult;
import feeper.Data.model.Util;
import feeper.Data.service.CodigoFonteMarcacaoService;
import feeper.Data.service.CodigoFonteService;
import feeper.Data.service.ExercicioClasseValidacaoService;
import feeper.Data.service.ExercicioService;
import feeper.Data.service.ExercicioValidacaoService;
import feeper.Data.service.RespostaService;
import feeper.Data.service.UploadTempService;
import feeper.model.PaginadorUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value="/exercicios")
public class ExerciciosController extends ApplicationController {
    
    ExercicioService service;
    
    public ExerciciosController()
    {
        this.service = new ExercicioService();
    }
    
    @RequestMapping(method=RequestMethod.GET)
    public String list(Model model) {
        return list("", "", 1, 10, "id", "asc", "", model, null);
    }
    
    @RequestMapping(method=RequestMethod.POST)
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
        if (gridAction.equals("PageSizeChanged") || gridAction.equals("Sorted") || gridAction.equals("Searched"))
            currentPage = 1;

        if (currentPage == 0) currentPage = 1;
        if (pageSize == 0) pageSize = 10;
        if (sortField != null && !sortField.isEmpty()) sortField = "E.ID";
        if (sortDirection != null && !sortDirection.isEmpty()) sortDirection = "Asc";
        //-------------------------
        
        PaginadorUtil<Exercicio> paginador = new PaginadorUtil<Exercicio>();
        
        String sql = "select * from Exercicio as E " +
                    "inner join Pessoa as P " +
                    "on P.ID = E.IdAutor " +
                    "where E.nome like :p0 " +
                    "and P.nome like :p1 ";
        
        String[] params = new String[2];
        params[0] = "%"+ nome + "%";
        params[1] = "%"+ autor + "%";
        
        List<Exercicio> lista = paginador.Execute(
                                        Exercicio.class, 
                                        model, 
                                        sql, 
                                        params, 
                                        currentPage, 
                                        pageSize, 
                                        sortField, 
                                        sortDirection);
        
        model.addAttribute("listaExercicios", lista);
        model.addAttribute("nome", nome);
        model.addAttribute("autor", autor);
        
        return "exercicios/list";
    }
    
    @RequestMapping(value="/add", method=RequestMethod.GET)
    public String add(Model model) {
        
        model.addAttribute(new Exercicio());
        model.addAttribute("IsAdd", true);
        
        return "exercicios/edit";
    }
    
    @RequestMapping(value="/saveadd", method=RequestMethod.POST)
    public ModelAndView saveadd(
            @ModelAttribute("exercicio") Exercicio exercicio, 
            @ModelAttribute("htmlcontent") String html,
            @ModelAttribute("idUploadTemp") String idUploadTemp,
            HttpSession session,
            BindingResult result,
            final RedirectAttributes flash) {
        
        ModelAndView mav = new ModelAndView();
        mav.setView(new RedirectView("/exercicios", true, true, false));
        
        Pessoa usuarioLogado = (Pessoa)session.getAttribute("UsuarioLogado");
        
        exercicio.setDataCadastro(new Date());
        exercicio.setIdAutor(usuarioLogado.getId());
        
        if (idUploadTemp == null || idUploadTemp.isEmpty())
        {
            exercicio.setDescricaoHtml(html);
        }
        else
        {
            UploadTempService repoUploadTemp = new UploadTempService();
            UploadTemp uploadTemp = repoUploadTemp.getByGuid(idUploadTemp);
            exercicio.setDescricao(uploadTemp.getArquivo());
        }
        
        if (service.insert(exercicio))
        {
            log(usuarioLogado.getId(), "SUCESSO: ID: " + exercicio.getId(), ETipoLog.CADASTRAR_EXERCICIO);
            flash.addFlashAttribute("MSG_SUCESSO", "Registro inserido com sucesso");
        }
        else
        {
            log(usuarioLogado.getId(), "ERRO: ID: " + exercicio.getNome(), ETipoLog.CADASTRAR_EXERCICIO);
            flash.addFlashAttribute("MSG_ERRO", "Ocorreu um erro ao inserir o registro");
        }
        
        return mav;
    }
    
    @RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
    public String edit(@PathVariable int id, Model model) {
        
        Exercicio exercicio = service.getById(id);
        exercicio.setValidacoes(service.getValidacoes(id));
        exercicio.setClassesValidacao(service.getClassesValidacao(id));
        
        model.addAttribute(exercicio);
        model.addAttribute("IsAdd", false);
        
        return "exercicios/edit";
    }
    
    @RequestMapping(value="/saveedit", method=RequestMethod.POST)
    public ModelAndView saveedit(
            @ModelAttribute("exercicio") Exercicio exercicio, 
            @ModelAttribute("htmlcontent") String html,
            HttpSession session,
            BindingResult result,
            final RedirectAttributes flash) {
        
        ModelAndView mav = new ModelAndView();
        mav.setView(new RedirectView("/exercicios", true, true, false));
        
        Exercicio exercicioBanco = service.getById(exercicio.getId());
        
        exercicioBanco.setAtivo(exercicio.isAtivo());
        exercicioBanco.setDescricaoHtml(html);
        exercicioBanco.setNome(exercicio.getNome());
        
        Pessoa usuarioLogado = (Pessoa)session.getAttribute("UsuarioLogado");
        
        if (service.update(exercicioBanco))
        {
            log(usuarioLogado.getId(), "SUCESSO: ID: " + exercicioBanco.getId(), ETipoLog.ALTERAR_EXERCICIO);
            flash.addFlashAttribute("MSG_SUCESSO", "Registro alterado com sucesso");
        }
        else
        {
            log(usuarioLogado.getId(), "ERRO: ID: " + exercicioBanco.getId(), ETipoLog.ALTERAR_EXERCICIO);
            flash.addFlashAttribute("MSG_ERRO", "Ocorreu um erro ao alterar o registro");
        }
        
        return mav;
    }
    
    @RequestMapping(value="/savevalidacao", method=RequestMethod.POST)
    public ModelAndView savevalidacao(
            HttpServletRequest request, 
            HttpSession session,
            final RedirectAttributes flash) {
        
        Pessoa usuarioLogado = (Pessoa)session.getAttribute("UsuarioLogado");
        int idExercicio = Integer.parseInt(request.getParameter("idExercicio").toString());
        int contador = Integer.parseInt(request.getParameter("contador").toString());
        ExercicioValidacaoService repo = new ExercicioValidacaoService();
        StringBuilder idsExistentes = new StringBuilder("0");
        
        ModelAndView mav = new ModelAndView();
        mav.setView(new RedirectView("/exercicios/edit/" + idExercicio, true, true, false));
        
        try {
            
            for (int i = 1; i <= contador; i++) {

                int idValidacao = request.getParameter("idValidacao" + i) == null ? 0 : Integer.parseInt(request.getParameter("idValidacao" + i).toString());
                String entrada = request.getParameter("entrada" + i) == null ? "" : request.getParameter("entrada" + i).toString();
                String saida = request.getParameter("saida" + i) == null ? "" : request.getParameter("saida" + i).toString();
                String mensagem = request.getParameter("mensagem" + i) == null ? "" : request.getParameter("mensagem" + i).toString();

                if (entrada.isEmpty() && saida.isEmpty() && mensagem.isEmpty()) continue;

                ExercicioValidacao entity = new ExercicioValidacao();

                if (idValidacao != 0)
                    entity.setId(idValidacao);
                entity.setIdExercicio(idExercicio);
                entity.setEntrada(entrada);
                entity.setSaida(saida);
                entity.setMensagem(mensagem);
                entity.setDataCadastro(new Date());
                entity.setAtivo(true);

                repo.insertOrUpdate(entity);

                idsExistentes.append(",").append(entity.getId());
            }

            repo.deleteNotIn(idExercicio, idsExistentes.toString());

            log(usuarioLogado.getId(), "SUCESSO: VALIDACAO: ID: " + idExercicio, ETipoLog.ALTERAR_EXERCICIO);
            flash.addFlashAttribute("MSG_SUCESSO", "Validações salvas com sucesso!");
        
        } catch (Exception e) {
            log(usuarioLogado.getId(), "ERRO: VALIDACAO: ID: " + idExercicio, ETipoLog.ALTERAR_EXERCICIO);
            flash.addFlashAttribute("MSG_ERRO", "Ocorreu um erro ao salvar as validações");
        }
        
        return mav;
    }
    
    @RequestMapping(value="/saveclassevalidacao", method=RequestMethod.POST)
    public ModelAndView saveclassevalidacao(
            HttpServletRequest request, 
            HttpSession session,
            final RedirectAttributes flash) {
        
        Pessoa usuarioLogado = (Pessoa)session.getAttribute("UsuarioLogado");
        int idExercicio = Integer.parseInt(request.getParameter("idExercicio").toString());
        int contador = Integer.parseInt(request.getParameter("contador").toString());
        ExercicioClasseValidacaoService repo = new ExercicioClasseValidacaoService();
        StringBuilder idsExistentes = new StringBuilder("0");
        
        ModelAndView mav = new ModelAndView();
        mav.setView(new RedirectView("/exercicios/edit/" + idExercicio, true, true, false));
        
        try {
            
            for (int i = 1; i <= contador; i++) {

                int idClasseValidacao = request.getParameter("idClasseValidacao" + i) == null ? 0 : Integer.parseInt(request.getParameter("idClasseValidacao" + i).toString());
                String fonte = request.getParameter("fonte" + i) == null ? "" : request.getParameter("fonte" + i).toString();
                String saida = request.getParameter("saida" + i) == null ? "" : request.getParameter("saida" + i).toString();
                String mensagem = request.getParameter("mensagem" + i) == null ? "" : request.getParameter("mensagem" + i).toString();
                String mensagemCompilacao = request.getParameter("mensagemCompilacao" + i) == null ? "" : request.getParameter("mensagemCompilacao" + i).toString();

                if (fonte.isEmpty()) continue;

                ExercicioClasseValidacao entity = new ExercicioClasseValidacao();

                if (idClasseValidacao != 0)
                    entity.setId(idClasseValidacao);
                entity.setIdExercicio(idExercicio);
                entity.setFonte(fonte);
                entity.setSaida(saida);
                entity.setMensagem(mensagem);
                entity.setMensagemCompilacao(mensagemCompilacao);
                entity.setDataCadastro(new Date());
                entity.setAtivo(true);

                repo.insertOrUpdate(entity);

                idsExistentes.append(",").append(entity.getId());
            }

            repo.deleteNotIn(idExercicio, idsExistentes.toString());

            log(usuarioLogado.getId(), "SUCESSO: CLASSE VALIDACAO: ID: " + idExercicio, ETipoLog.ALTERAR_EXERCICIO);
            flash.addFlashAttribute("MSG_SUCESSO", "Classes de validação salvas com sucesso!");
        
        } catch (Exception e) {
            log(usuarioLogado.getId(), "ERRO: CLASSE VALIDACAO: ID: " + idExercicio, ETipoLog.ALTERAR_EXERCICIO);
            flash.addFlashAttribute("MSG_ERRO", "Ocorreu um erro ao salvar as classes de validação");
        }
        return mav;
    }
    
    @RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
    public ModelAndView delete(
            @PathVariable int id, 
            Model model,
            HttpSession session,
            final RedirectAttributes flash) {
        
        ModelAndView mav = new ModelAndView();
        mav.setView(new RedirectView("/exercicios", true, true, false));
        
        Exercicio exercicio = service.getById(id);
        
        Pessoa usuarioLogado = (Pessoa)session.getAttribute("UsuarioLogado");
        if (service.delete(exercicio))
        {
            log(usuarioLogado.getId(), "SUCESSO: ID: " + id, ETipoLog.EXCLUIR_EXERCICIO);
            flash.addFlashAttribute("MSG_SUCESSO", "Registro excluído com sucesso");
        }
        else
        {
            log(usuarioLogado.getId(), "ERRO: ID: " + id, ETipoLog.EXCLUIR_EXERCICIO);
            flash.addFlashAttribute("MSG_ERRO", "Ocorreu um erro ao excluir o registro");
        }
        
        return mav;
    }
    
    @RequestMapping(value="/upload", method=RequestMethod.POST, produces="application/json")
    @ResponseBody
    public String upload(MultipartHttpServletRequest request) {
        
        MultipartFile file = request.getFile("filedata");
        HttpSession session = request.getSession();
        Pessoa usuarioLogado = (Pessoa)session.getAttribute("UsuarioLogado");
        
        try {
            UploadTemp uploadTemp = new UploadTemp();
            uploadTemp.setGuid(UUID.randomUUID().toString());
            uploadTemp.setArquivo(file.getBytes());
            UploadTempService repoUploadTemp = new UploadTempService();
            if (repoUploadTemp.insert(uploadTemp))
            {
                log(usuarioLogado.getId(), "SUCESSO: ID: " + uploadTemp.getId() + " NOME: " + file.getOriginalFilename(), ETipoLog.UPLOAD_EXERCICIO);
                return uploadTemp.getGuid();
            }
            else
            {
                log(usuarioLogado.getId(), "ERRO: NOME: " + file.getOriginalFilename(), ETipoLog.UPLOAD_EXERCICIO);
                return "erro";
            }
            
        } catch (IOException ex) {
            return "";
        }
    }
    
    @RequestMapping(value="/meusexercicios", method=RequestMethod.GET)
    public String meusexercicios(Model model, HttpServletRequest request) {
        
        return meusexercicios(1, 10, "Nome", "asc", "", model, null, request);
    }
    
    @RequestMapping(value="/meusexercicios", method=RequestMethod.POST)
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
        if (gridAction.equals("PageSizeChanged") || gridAction.equals("Sorted") || gridAction.equals("Searched"))
            currentPage = 1;

        if (currentPage == 0) currentPage = 1;
        if (pageSize == 0) pageSize = 10;
        if (sortField != null && !sortField.isEmpty()) sortField = "E.Nome";
        if (sortDirection != null && !sortDirection.isEmpty()) sortDirection = "Asc";
        //-------------------------
        
        PaginadorUtil<MeusExercicios> paginador = new PaginadorUtil<MeusExercicios>();
        
        HttpSession session = request.getSession(false);
        Pessoa pessoa = (Pessoa)session.getAttribute("UsuarioLogado");
        Turma turma = (Turma)session.getAttribute("TurmaSelecionada");
        
        String sql = "select " +
                    "  E.ID,  " +
                    "  E.Nome, " +
                    "  E.Descricao, " +
                    "  TRUNCATE_TEXT(REMOVE_HTML_TAGS(E.DescricaoHtml), 100) AS DescricaoHtml, " +
                    "  T.Nome AS Turma, " +
                    "  (select MAX(DataCadastro) from Resposta R " +
                    "  where R.IdExercicio = E.ID and R.IdAutor = :p0 ) AS DataUltimaAlteracao " +
                    "from  " +
                    "  TurmaExercicio TE " +
                    "  inner join Exercicio E " +
                    "  on E.ID = TE.IdExercicio " +
                    "  inner join Turma T " +
                    "  on T.ID = TE.IdTurma " +
                    "where " +
                    "  TE.Visivel = 1 " +
                    "  and TE.IdTurma = :p1 " +
                    "  and T.Ativo = 1";
        
        Integer[] params = new Integer[2];
        params[0] = pessoa.getId();
        params[1] = turma.getId();
        
        ScalarResult[] scalarResult = new ScalarResult[6];
        scalarResult[0] = new ScalarResult("ID", Hibernate.INTEGER);
        scalarResult[1] = new ScalarResult("Nome", Hibernate.STRING);
        scalarResult[2] = new ScalarResult("Descricao", Hibernate.BINARY);
        scalarResult[3] = new ScalarResult("DescricaoHtml", Hibernate.STRING);
        scalarResult[4] = new ScalarResult("Turma", Hibernate.STRING);
        scalarResult[5] = new ScalarResult("DataUltimaAlteracao", Hibernate.TIMESTAMP);
        
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
    
    @RequestMapping(value="/responder/{id}", method=RequestMethod.GET)
    public ModelAndView responder(
            @PathVariable int id, 
            Model model,
            HttpServletRequest request) {
        
        //service.closeSession();
        
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession(false);
        Pessoa pessoa = (Pessoa)session.getAttribute("UsuarioLogado");
        Turma turma = (Turma)session.getAttribute("TurmaSelecionada");
        
        Exercicio exercicio = service.getMeuExercicio(turma.getId(), id);
        
        if (exercicio != null)
        {
            CodigoFonteService repoCodigoFonte = new CodigoFonteService();
            List<CodigoFonte> listaCodigoFonte = repoCodigoFonte.getAllByIdExercicio(exercicio.getId(), pessoa.getId());
            
            if (listaCodigoFonte != null && listaCodigoFonte.size() > 0)
            {
                RespostaService repoResposta = new RespostaService();
                Resposta resposta = repoResposta.getLastByIdExercicio(exercicio.getId(), pessoa.getId());
                mav.addObject("Resposta", resposta);
            }
            else
            {
                if (service.getValidacoes(id).size() > 0)
                {
                    CodigoFonte codigoFontePrincipal = new CodigoFonte();
                    codigoFontePrincipal.setClasse("Solution.java");
                    codigoFontePrincipal.setId(0);
                    listaCodigoFonte = new ArrayList<CodigoFonte>();
                    listaCodigoFonte.add(codigoFontePrincipal);
                }
            }
            mav.addObject("Exercicio", exercicio);
            mav.addObject("ListaCodigoFonte", listaCodigoFonte);
            mav.addObject("CodigoFontePadraoClasse", CodigoFonteService.CODIGO_PADRAO_CLASSE.replaceAll("\n", "#n#"));
            
            mav.setViewName("exercicios/responder");
            return mav;
        }
        mav.setView(new RedirectView("/exercicios/meusexercicios", true, true, false));
        return mav;
    }
    
    @RequestMapping(value="/saveresponder", method=RequestMethod.POST)
    public ModelAndView saveresponder(
            Model model,
            HttpServletRequest request,
            final RedirectAttributes flash) {
        
        int id = Integer.parseInt(request.getParameter("hdnIdExercicio").toString());
        
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession(false);
        
        Pessoa pessoa = (Pessoa)session.getAttribute("UsuarioLogado");
        Turma turma = (Turma)session.getAttribute("TurmaSelecionada");
        
        CodigoFonteService repoCodigoFonte = new CodigoFonteService();
        Exercicio exercicio = service.getMeuExercicio(turma.getId(), id);
        
        if (exercicio != null)
        {
            List<CodigoFonte> listaCodigoFonte = repoCodigoFonte.getAllByIdExercicio(exercicio.getId(), pessoa.getId());
            if (listaCodigoFonte != null && listaCodigoFonte.size() > 0)
            {
                RespostaService repoResposta = new RespostaService();
                IntegerResult idResposta = new IntegerResult();
                if (repoResposta.salvarResposta(pessoa.getId(), exercicio.getId(), idResposta))
                {
                    log(pessoa.getId(), "SUCESSO: ID EXERCICIO: " + exercicio.getId(), ETipoLog.CODIGO_ENVIADO);
                    
                    if (idResposta.getResult() > 0)
                    {
                        try {
//                            CredentialsProvider credsProvider = new BasicCredentialsProvider();
//                            credsProvider.setCredentials(
//                                AuthScope.ANY,
//                                new UsernamePasswordCredentials("feeper", Base64Encoder.encode("srv8f33p3r")));
//                            CloseableHttpClient httpclient = HttpClients.custom()
//                                .setDefaultCredentialsProvider(credsProvider)
//                                .build();
//                            HttpGet httpget = new HttpGet("http://feeper.jelasticlw.com.br/OnlineJudge?r=" + idResposta.getResult());
//                            CloseableHttpResponse response = httpclient.execute(httpget);
                            
                            String idRespostaEncoded = Base64Encoder.encode(Integer.toString(idResposta.getResult()));
                            log(pessoa.getId(), "ONLINEJUDGE ID RESPOSTA: " + idResposta.getResult() + " ENCODED: " + idRespostaEncoded, ETipoLog.CODIGO_ENVIADO);
                            
//                            URL urlServlet = new URL("http://feeper.jelasticlw.com.br/OnlineJudge?r=" + idRespostaEncoded);
//                            HttpURLConnection servletConnection = (HttpURLConnection) urlServlet.openConnection();
//                            servletConnection.setRequestMethod("GET");
//                            servletConnection.setDoOutput(true);
//                            InputStream response = servletConnection.getInputStream();
                            
                            flash.addFlashAttribute("idRespostaEncoded", idRespostaEncoded);
                            
                            //HttpGet httpget = new HttpGet("http://feeper.jelasticlw.com.br/OnlineJudge?r=" + idRespostaEncoded);
                            //CloseableHttpResponse response = HttpClients.createDefault().execute(httpget);
                            
                        } catch (Exception e) {
                            log(pessoa.getId(), "ERRO: ONLINEJUDGE ID RESPOSTA: " + idResposta.getResult() + " MESSAGE: " + e.getMessage(), ETipoLog.CODIGO_ENVIADO);
                        }
                    }
                }
                else
                {
                    log(pessoa.getId(), "ERRO: ID EXERCICIO: " + exercicio.getId(), ETipoLog.CODIGO_ENVIADO);
                }
            }
        }
        mav.setView(new RedirectView("/exercicios/responder/" + id, true, true, false));
        return mav;
    }
    
    @RequestMapping(value="/download/{id}", method=RequestMethod.GET)
    public void download(
        @PathVariable int id, 
        HttpServletRequest request,
        HttpServletResponse response) {
        
        HttpSession session = request.getSession(false);
        Pessoa pessoa = (Pessoa)session.getAttribute("UsuarioLogado");
        Turma turma = (Turma)session.getAttribute("TurmaSelecionada");
        
        Exercicio exercicio = service.getMeuExercicio(turma.getId(), id);
        
        if (exercicio != null)
        {
            CodigoFonteService repoCodigoFonte = new CodigoFonteService();
            List<CodigoFonte> codigosFonte = repoCodigoFonte.getAllByIdExercicio(exercicio.getId(), pessoa.getId());
            
            if (codigosFonte != null && codigosFonte.size() > 0)
            {
                try {
                    
                    ArrayList<byte[]> arquivos = new ArrayList<byte[]>();
                    ArrayList<String> nomes = new ArrayList<String>();
                    
                    for (CodigoFonte codigoFonte : codigosFonte) {
                        arquivos.add(codigoFonte.getFonte().getBytes("UTF-8"));
                        nomes.add(codigoFonte.getClasse());
                    }
                    
                    byte[] arquivoZip = Util.zipFiles(arquivos, nomes);
                    InputStream stream = new ByteArrayInputStream(arquivoZip);
                    IOUtils.copy(stream, response.getOutputStream());
                    
                    log(pessoa.getId(), "SUCESSO: ID EXERCICIO: " + id, ETipoLog.CODIGO_BAIXADO);
                    
                    response.setContentType("application/force-download");
                    response.setHeader("Content-Disposition", "attachment; filename=Exercicio"+id+".zip");
                    response.flushBuffer();
                    
                } catch (IOException ex) {
                }
            }
        }
    }
    
    @RequestMapping(value="/showcode/{idExercicio}/{idCodigoFonte}", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object showcode(
            @PathVariable int idExercicio, 
            @PathVariable int idCodigoFonte, 
            Model model, 
            HttpServletRequest request) {
        
        if (idCodigoFonte == 0)
        {
            Object[] dados = new Object[7];
            dados[0] = 0;
            dados[1] = CodigoFonteService.CODIGO_PADRAO_PRINCIPAL;
            dados[2] = "Solution.java";
            dados[3] = true;
            dados[4] = "";
            dados[5] = "";
            dados[6] = false;
            
            return dados;
        }
        
        HttpSession session = request.getSession(false);
        Pessoa pessoa = (Pessoa)session.getAttribute("UsuarioLogado");
        
        CodigoFonteService repoCodigoFonte = new CodigoFonteService();
        CodigoFonteMarcacaoService repoCodigoFonteMarcacao = new CodigoFonteMarcacaoService();
        Object[] dados = (Object[])repoCodigoFonte.getByIdExercicio(idExercicio, pessoa.getId(), idCodigoFonte);
        
        dados[1] = dados[1].toString().replaceAll("\"", "#'#");
        dados[4] = repoCodigoFonteMarcacao.getLinhasDuvida(idCodigoFonte);
        dados[5] = repoCodigoFonteMarcacao.getLinhasAnotacao(idCodigoFonte);
        
        return dados;
    }
    
    @RequestMapping(value="/savecodigofonte", method=RequestMethod.POST)
    public ModelAndView savecodigofonte(
            @ModelAttribute("hdnIdExercicio") int id, 
            HttpSession session,
            HttpServletRequest request,
            BindingResult result) {
        
        ModelAndView mav = new ModelAndView();
        
        Pessoa pessoa = (Pessoa)session.getAttribute("UsuarioLogado");
        Turma turma = (Turma)session.getAttribute("TurmaSelecionada");
        
        CodigoFonteService repoCodigoFonte = new CodigoFonteService();
        Exercicio exercicio = service.getMeuExercicio(turma.getId(), id);
        
        if (exercicio != null)
        {
            try
            {
                int idCodigoFonte = request.getParameter("hdnIdCodigoFonte").isEmpty() ? 0 : Integer.parseInt(request.getParameter("hdnIdCodigoFonte").toString());
                boolean principal = request.getParameter("hdnPrincipal") == null ? false : request.getParameter("hdnPrincipal").toString().equals("true") || request.getParameter("hdnPrincipal").toString().equals("1");
                String classe = request.getParameter("hdnNomeCodigoFonte") == null ? "" : request.getParameter("hdnNomeCodigoFonte").toString();
                String fonte = request.getParameter("hdnEditor") == null ? "" : request.getParameter("hdnEditor").toString();

                if (!fonte.isEmpty())
                {
                    CodigoFonte entity = new CodigoFonte();

                    if (idCodigoFonte > 0)
                    {
                        entity = repoCodigoFonte.getById(idCodigoFonte);
                        entity.setFonteAnterior(entity.getFonte());
                    }
                    else
                    {
                        if (!principal && service.getValidacoes(id).size() > 0)
                            repoCodigoFonte.inserirCodigoPrincipal(exercicio.getId(), pessoa.getId());
                        entity.setIdAutor(pessoa.getId());
                        entity.setIdExercicio(exercicio.getId());
                    }
                    entity.setClasse(Util.removeAccent(classe));
                    entity.setPrincipal(principal);
                    entity.setDataCadastro(new Date());
                    entity.setFonte(fonte);

                    repoCodigoFonte.insertOrUpdate(entity);
                    
                    log(pessoa.getId(), "SUCESSO: CLASSE: " + classe, ETipoLog.CODIGO_SALVO);
                }
            }
            catch(Exception e) {
                log(pessoa.getId(), "SUCESSO: ID EXERCICIO: " + exercicio.getId(), ETipoLog.CODIGO_SALVO);
            }
        
        }
        
        mav.setView(new RedirectView("/exercicios/responder/" + id, true, true, false));
        return mav;
        
    }
    
    @RequestMapping(value="/deletecode/{idExercicio}/{idCodigoFonte}", method=RequestMethod.GET)
    public ModelAndView deletecode(
            @PathVariable int idExercicio, 
            @PathVariable int idCodigoFonte, 
            Model model, 
            HttpServletRequest request) {
        
        HttpSession session = request.getSession(false);
        Pessoa pessoa = (Pessoa)session.getAttribute("UsuarioLogado");
        
        try
        {
            CodigoFonteService repoCodigoFonte = new CodigoFonteService();
            CodigoFonte codigoFonte = repoCodigoFonte.getById(idExercicio, pessoa.getId(), idCodigoFonte);
            
            if (codigoFonte != null && !codigoFonte.isPrincipal())
            {
                repoCodigoFonte.delete(codigoFonte);
                log(pessoa.getId(), "SUCESSO: ID: " + idCodigoFonte, ETipoLog.CODIGO_EXCLUIDO);
            }
        }
        catch (Exception e) {
            log(pessoa.getId(), "ERRO: ID: " + idCodigoFonte, ETipoLog.CODIGO_EXCLUIDO);
        }
        
        ModelAndView mav = new ModelAndView();
        mav.setView(new RedirectView("/exercicios/responder/" + idExercicio, true, true, false));
        return mav;
        
    }
    
    @RequestMapping(value="/showquestion/{idExercicio}/{idCodigoFonte}/{linha}", method=RequestMethod.GET)
    public String showquestion(
            @PathVariable int idExercicio, 
            @PathVariable int idCodigoFonte, 
            @PathVariable int linha, 
            Model model, 
            HttpServletRequest request) {
        
        HttpSession session = request.getSession(false);
        Pessoa pessoa = (Pessoa)session.getAttribute("UsuarioLogado");
        
        CodigoFonteService repoCodigoFonte = new CodigoFonteService();
        
        List<Object> dados;
        if (pessoa.getIdPerfil() == EPerfil.ALUNO)
            dados = repoCodigoFonte.getMarcacaoAutor(idExercicio, pessoa.getId(), idCodigoFonte, linha);
        else
            dados = repoCodigoFonte.getMarcacaoLeitor(idExercicio, idCodigoFonte, linha);
        
        model.addAttribute("lista", dados);
        model.addAttribute("idUsuarioLogado", pessoa.getId());
        
        return "exercicios/mensagens";
    }
    
    @RequestMapping(value="/savequestion", method=RequestMethod.POST)
    public ModelAndView savequestion(
            @ModelAttribute("hdnQuestaoIdExercicio") int id, 
            @ModelAttribute("hdnQuestaoIdCodigoFonte") int idCodigoFonte, 
            @ModelAttribute("hdnQuestaoLinha") int linha, 
            HttpSession session,
            HttpServletRequest request,
            BindingResult result) {
        
        ModelAndView mav = new ModelAndView();
        
        Pessoa pessoa = (Pessoa)session.getAttribute("UsuarioLogado");
        Turma turma = (Turma)session.getAttribute("TurmaSelecionada");
        
        CodigoFonteService repoCodigoFonte = new CodigoFonteService();
        Exercicio exercicio = service.getMeuExercicio(turma.getId(), id);
        
        if (exercicio != null)
        {
            try
            {
                String questao = request.getParameter("questaoCodigoFonte") == null ? "" : request.getParameter("questaoCodigoFonte").toString();
                //boolean publico = request.getParameter("chkPublico") == null ? false : request.getParameter("chkPublico").toString().equals("1");

                if (!questao.isEmpty())
                {
                    int idRemetente = pessoa.getId();
                    int idDestinatario = turma.getIdProfessor();
                    
                    if (pessoa.getIdPerfil() == EPerfil.PROFESSOR)
                    {
                        //Buscar o id do aluno
                        CodigoFonte codigoFonte = repoCodigoFonte.getById(id, idCodigoFonte);
                        idDestinatario = codigoFonte.getIdAutor();
                    }
                    
                    if (repoCodigoFonte.inserirPergunta(id, idRemetente, idCodigoFonte, linha, false, idDestinatario, questao))
                        log(pessoa.getId(), "SUCESSO: ID EXERCICIO: " + exercicio.getId() + " ID CODIGOFONTE: " + idCodigoFonte + " LINHA: " + linha, ETipoLog.REALIZAR_PERGUNTA);
                    else
                        log(pessoa.getId(), "ERRO: ID EXERCICIO: " + exercicio.getId() + " ID CODIGOFONTE: " + idCodigoFonte + " LINHA: " + linha, ETipoLog.REALIZAR_PERGUNTA);
                }
            }
            catch(Exception e) {
                log(pessoa.getId(), "ERRO: ID EXERCICIO: " + exercicio.getId(), ETipoLog.REALIZAR_PERGUNTA);
            }
        
        }
        
        mav.setView(new RedirectView("/exercicios/showquestion/" + id + "/" + idCodigoFonte + "/" + linha, true, true, false));
        return mav;
        
    }
    
    @RequestMapping(value="/showannotation/{idExercicio}/{idCodigoFonte}/{linha}", method=RequestMethod.GET)
    @ResponseBody
    public Object showannotation(
            @PathVariable int idExercicio, 
            @PathVariable int idCodigoFonte, 
            @PathVariable int linha, 
            Model model, 
            HttpServletRequest request) {
        
        HttpSession session = request.getSession(false);
        Pessoa pessoa = (Pessoa)session.getAttribute("UsuarioLogado");
        
        CodigoFonteService repoCodigoFonte = new CodigoFonteService();
        Object dados = repoCodigoFonte.getAnotacao(idExercicio, idCodigoFonte, linha);
        
        return dados != null ? dados : "";
    }
    
    @RequestMapping(value="/saveannotation", method=RequestMethod.POST)
    public ModelAndView saveannotation(
            @ModelAttribute("hdnAnotacaoIdExercicio") int id, 
            @ModelAttribute("hdnAnotacaoIdCodigoFonte") int idCodigoFonte, 
            @ModelAttribute("hdnAnotacaoLinha") int linha, 
            HttpSession session,
            HttpServletRequest request,
            BindingResult result) {
        
        ModelAndView mav = new ModelAndView();
        
        Pessoa pessoa = (Pessoa)session.getAttribute("UsuarioLogado");
        Turma turma = (Turma)session.getAttribute("TurmaSelecionada");
        
        CodigoFonteService repoCodigoFonte = new CodigoFonteService();
        Exercicio exercicio = service.getMeuExercicio(turma.getId(), id);
        
        if (exercicio != null)
        {
            try
            {
                String anotacao = request.getParameter("anotacaoCodigoFonte") == null ? "" : request.getParameter("anotacaoCodigoFonte").toString();
                //boolean publico = request.getParameter("chkPublico") == null ? false : request.getParameter("chkPublico").toString().equals("1");

                if (!anotacao.isEmpty())
                {
                    if (repoCodigoFonte.inserirAnotacao(id, pessoa.getId(), idCodigoFonte, linha, false, anotacao))
                        log(pessoa.getId(), "SUCESSO: ID EXERCICIO: " + exercicio.getId() + " ID CODIGOFONTE: " + idCodigoFonte + " LINHA: " + linha, ETipoLog.CODIGO_COMENTADO);
                    else
                        log(pessoa.getId(), "ERRO: ID EXERCICIO: " + exercicio.getId() + " ID CODIGOFONTE: " + idCodigoFonte + " LINHA: " + linha, ETipoLog.CODIGO_COMENTADO);
                }
            }
            catch(Exception e) {
                log(pessoa.getId(), "ERRO: ID EXERCICIO: " + exercicio.getId(), ETipoLog.CODIGO_COMENTADO);
            }
        
        }
        
        mav.setView(new RedirectView("/exercicios/showannotation/" + id + "/" + idCodigoFonte + "/" + linha, true, true, false));
        return mav;
        
    }
    
    @RequestMapping(value="/uploadclass", method=RequestMethod.POST)
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
    
    @RequestMapping(value="/getstatus/{idExercicio}", method=RequestMethod.GET)
    @ResponseBody
    public int getstatus(
            @PathVariable int idExercicio,
            HttpSession session) {
        
        Pessoa pessoa = (Pessoa)session.getAttribute("UsuarioLogado");
        Turma turma = (Turma)session.getAttribute("TurmaSelecionada");
        
        CodigoFonteService repoCodigoFonte = new CodigoFonteService();
        Exercicio exercicio = service.getMeuExercicio(turma.getId(), idExercicio);
        
        if (exercicio != null)
        {
            RespostaService repoResposta = new RespostaService();
            //repoResposta.closeSession();
            
            Resposta resposta = repoResposta.getLastByIdExercicio(exercicio.getId(), pessoa.getId());
                       
            return resposta.getIdStatus();
        }
        
        return 0;
    }
    
}