package feeper.controller;

import feeper.entity.CodigoFonte;
import feeper.entity.CodigoFonteResultado;
import feeper.entity.Exercicio;
import feeper.entity.ExercicioValidacao;
import feeper.entity.MeusExercicios;
import feeper.entity.Pessoa;
import feeper.entity.Turma;
import feeper.entity.UploadTemp;
import feeper.model.CodigoFonteService;
import feeper.model.EStatusCodigoFonte;
import feeper.model.ETipoLog;
import feeper.model.ExercicioService;
import feeper.model.ExercicioValidacaoService;
import feeper.model.HibernateUtil;
import feeper.model.PaginadorUtil;
import feeper.model.ScalarResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
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
                    "inner join NivelDificuldade as ND " +
                    "on ND.ID = E.IdNivelDificuldade " +
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
            HibernateUtil<UploadTemp> repo = new HibernateUtil<UploadTemp>(UploadTemp.class);
            exercicio.setDescricao(repo.getById(Integer.parseInt(idUploadTemp)).getArquivo());
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
        exercicioBanco.setIdNivelDificuldade(exercicio.getIdNivelDificuldade());
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
    public ModelAndView savevalidacao(HttpServletRequest request, HttpSession session) {
        
        ModelAndView mav = new ModelAndView();
        mav.setView(new RedirectView("/exercicios", true, true, false));
        
        int idExercicio = Integer.parseInt(request.getParameter("idExercicio").toString());
        int contador = Integer.parseInt(request.getParameter("contador").toString());
        ExercicioValidacaoService repo = new ExercicioValidacaoService();
        StringBuilder idsExistentes = new StringBuilder("0");
        
        for (int i = 1; i <= contador; i++) {
            
            int idValidacao = request.getParameter("idValidacao" + i) == null ? 0 : Integer.parseInt(request.getParameter("idValidacao" + i).toString());
            String entrada = request.getParameter("entrada" + i) == null ? "" : request.getParameter("entrada" + i).toString();
            String saida = request.getParameter("saida" + i) == null ? "" : request.getParameter("saida" + i).toString();
            String mensagem = request.getParameter("mensagem" + i) == null ? "" : request.getParameter("mensagem" + i).toString();
            
            if (entrada.isEmpty() && saida.isEmpty() && mensagem.isEmpty()) continue;
            
            idsExistentes.append(",").append(idValidacao);
            
            ExercicioValidacao entity = new ExercicioValidacao();
            
            entity.setId(idValidacao);
            entity.setIdExercicio(idExercicio);
            entity.setEntrada(entrada);
            entity.setSaida(saida);
            entity.setMensagem(mensagem);
            
            repo.insertOrUpdate(entity);
        }
        
        repo.deleteNotIn(idExercicio, idsExistentes.toString());
        
        Pessoa usuarioLogado = (Pessoa)session.getAttribute("UsuarioLogado");
        log(usuarioLogado.getId(), "SUCESSO: VALIDACAO: ID: " + idExercicio, ETipoLog.ALTERAR_EXERCICIO);
        
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
            uploadTemp.setArquivo(file.getBytes());
            HibernateUtil<UploadTemp> repo = new HibernateUtil<UploadTemp>(UploadTemp.class);
            if (repo.insert(uploadTemp))
            {
                log(usuarioLogado.getId(), "SUCESSO: ID: " + uploadTemp.getId() + " NOME: " + file.getOriginalFilename(), ETipoLog.UPLOAD_EXERCICIO);
                return uploadTemp.getId().toString();
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
                    "  E.DescricaoHtml, " +
                    "  ND.Nome AS NivelDificuldade, " +
                    "  T.Nome AS Turma, " +
                    "  IFNULL(CF.DataAlteracao, CF.DataCadastro) AS DataUltimaAlteracao " +
                    "from  " +
                    "  TurmaExercicio TE " +
                    "  inner join Exercicio E " +
                    "  on E.ID = TE.IdExercicio " +
                    "  inner join NivelDificuldade ND " +
                    "  on ND.ID = E.IdNivelDificuldade " +
                    "  inner join Turma T " +
                    "  on T.ID = TE.IdTurma " +
                    "  left join CodigoFonte CF " +
                    "  on CF.IdExercicio = E.ID " +
                    "  and CF.IdAutor = :p0 " +
                    "  and CF.Ativo = 1 " +
                    "where " +
                    "  TE.Visivel = 1 " +
                    "  and TE.IdTurma = :p1 " +
                    "  and E.IdNivelDificuldade <= :p2 " +
                    "  and T.Ativo = 1";
        
        Integer[] params = new Integer[3];
        params[0] = pessoa.getId();
        params[1] = turma.getId();
        params[2] = pessoa.getIdNivelDificuldade();
        
        ScalarResult[] scalarResult = new ScalarResult[7];
        scalarResult[0] = new ScalarResult("ID", Hibernate.INTEGER);
        scalarResult[1] = new ScalarResult("Nome", Hibernate.STRING);
        scalarResult[2] = new ScalarResult("Descricao", Hibernate.BINARY);
        scalarResult[3] = new ScalarResult("DescricaoHtml", Hibernate.STRING);
        scalarResult[4] = new ScalarResult("NivelDificuldade", Hibernate.STRING);
        scalarResult[5] = new ScalarResult("Turma", Hibernate.STRING);
        scalarResult[6] = new ScalarResult("DataUltimaAlteracao", Hibernate.TIMESTAMP);
        
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
        
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession(false);
        Pessoa pessoa = (Pessoa)session.getAttribute("UsuarioLogado");
        Turma turma = (Turma)session.getAttribute("TurmaSelecionada");
        
        ExercicioService repoExercicio = new ExercicioService();
        Exercicio exercicio = repoExercicio.getMeuExercicio(turma.getId(), pessoa.getIdNivelDificuldade(), id);
        
        if (exercicio != null)
        {
            CodigoFonteService repoCodigoFonte = new CodigoFonteService();
            CodigoFonte codigoFonte = repoCodigoFonte.getByIdExercicio(exercicio.getId(), pessoa.getId());
            
            if (codigoFonte != null)
            {
                CodigoFonteResultado codigoFonteResultado = repoCodigoFonte.getResultado(codigoFonte.getId());
                mav.addObject("CodigoFonteResultado", codigoFonteResultado);
            }
            mav.addObject("Exercicio", exercicio);
            mav.addObject("CodigoFonte", codigoFonte);
            
            mav.setViewName("exercicios/responder");
            return mav;
        }
        mav.setView(new RedirectView("/exercicios/meusexercicios", true, true, false));
        return mav;
    }
    
    @RequestMapping(value="/saveresponder", method=RequestMethod.POST)
    public ModelAndView saveresponder(
            Model model,
            HttpServletRequest request) {
        
        int id = Integer.parseInt(request.getParameter("hdnIdExercicio").toString());
        
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession(false);
        
        Pessoa pessoa = (Pessoa)session.getAttribute("UsuarioLogado");
        Turma turma = (Turma)session.getAttribute("TurmaSelecionada");
        
        ExercicioService repoExercicio = new ExercicioService();
        Exercicio exercicio = repoExercicio.getMeuExercicio(turma.getId(), pessoa.getIdNivelDificuldade(), id);
        
        if (exercicio != null)
        {
            CodigoFonteService repoCodigoFonte = new CodigoFonteService();
            CodigoFonte codigoFonte = repoCodigoFonte.getByIdExercicio(exercicio.getId(), pessoa.getId());
            
            if (codigoFonte != null)
            {
                codigoFonte.setFonteAnterior(codigoFonte.getFonte());
            }
            else if (codigoFonte == null)
            {
                codigoFonte = new CodigoFonte();
                codigoFonte.setDataCadastro(new Date());
                codigoFonte.setIdAutor(pessoa.getId());
                codigoFonte.setIdExercicio(exercicio.getId());
            }
            codigoFonte.setDataAlteracao(new Date());
            codigoFonte.setFonte(request.getParameter("hdnEditor").toString());
            codigoFonte.setAtivo(true);
            codigoFonte.setIdStatus(EStatusCodigoFonte.AGUARDANDO);
            
            if (codigoFonte.getId() == null)
            {
                repoCodigoFonte.insert(codigoFonte);
                log(pessoa.getId(), "SUCESSO: ID: " + codigoFonte.getId(), ETipoLog.CODIGO_ENVIADO);
            }
            else
            {
                repoCodigoFonte.update(codigoFonte);
                log(pessoa.getId(), "SUCESSO: ID: " + codigoFonte.getId(), ETipoLog.CODIGO_ENVIADO);
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
        
        ExercicioService repoExercicio = new ExercicioService();
        Exercicio exercicio = repoExercicio.getMeuExercicio(turma.getId(), pessoa.getIdNivelDificuldade(), id);
        
        if (exercicio != null)
        {
            CodigoFonteService repoCodigoFonte = new CodigoFonteService();
            CodigoFonte codigoFonte = repoCodigoFonte.getByIdExercicio(exercicio.getId(), pessoa.getId());
            
            if (codigoFonte != null)
            {
                try {
                    log(pessoa.getId(), "SUCESSO: ID: " + codigoFonte.getId(), ETipoLog.CODIGO_BAIXADO);
                    
                    InputStream stream = new ByteArrayInputStream(codigoFonte.getFonte().getBytes("UTF-8"));
                    IOUtils.copy(stream, response.getOutputStream());
                    
                    response.setContentType("application/force-download");
                    response.setHeader("Content-Disposition", "attachment; filename=Solution.java");
                    response.flushBuffer();
                    
                } catch (IOException ex) {
                }
            }
        }
    }
    
}