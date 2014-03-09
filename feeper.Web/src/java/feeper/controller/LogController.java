package feeper.controller;

import feeper.Data.entity.Log;
import feeper.Data.service.LogService;
import feeper.model.PaginadorUtil;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/log")
public class LogController extends ApplicationController {
    
    private LogService service;
    
    public LogController()
    {
        this.service = new LogService();
    }
    
    @RequestMapping(method=RequestMethod.GET)
    public String list(Model model) {
        
        return list("", 1, null, null, 1, 10, "id", "asc", "", model, null);
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public String list(
            @ModelAttribute("descricao") String descricao, 
            @ModelAttribute("idTipoLog") int idTipoLog, 
            @ModelAttribute("dataInicio") Date dataInicio, 
            @ModelAttribute("dataFim") Date dataFim, 
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
        if (sortField != null && !sortField.isEmpty()) sortField = "ID";
        if (sortDirection != null && !sortDirection.isEmpty()) sortDirection = "Asc";
        //-------------------------
        
        PaginadorUtil<Log> paginador = new PaginadorUtil<Log>();
        
        String sql = "select * from Log where Mensagem like :p0 and IdTipoLog = :p1 and DataCadastro >= :p2 and DataCadastro <= :p3 ";
        
        String[] params = new String[4];
        params[0] = "%"+ descricao + "%";
        params[1] = idTipoLog + "";
        params[2] = dataInicio.toString();
        params[3] = dataFim.toString();
        
        List<Log> lista = paginador.Execute(
                                        Log.class, 
                                        model, 
                                        sql, 
                                        params, 
                                        currentPage, 
                                        pageSize, 
                                        sortField, 
                                        sortDirection);
        
        model.addAttribute("listaLog", lista);
        model.addAttribute("descricao", descricao);
        model.addAttribute("idTipoLog", idTipoLog);
        model.addAttribute("dataInicio", dataInicio);
        model.addAttribute("dataFim", dataFim);
        
        return "log/list";
    }

}