package feeper.controller;

import feeper.Data.model.ScalarResult;
import feeper.Data.model.Util;
import feeper.Data.service.LogService;
import feeper.model.PaginadorUtil;
import java.util.List;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/log")
public class LogController extends ApplicationController {

    private LogService service;

    public LogController() {
        this.service = new LogService();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {

        return list("", 1, "", "", 1, 10, "L.DataCadastro", "Desc", "", model, null);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String list(
            @ModelAttribute("descricao") String descricao,
            @ModelAttribute("idTipoLog") Integer idTipoLog,
            @ModelAttribute("dataInicio") String dataInicio,
            @ModelAttribute("dataFim") String dataFim,
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
            sortField = "L.DataCadastro";
        }
        if (sortDirection != null && !sortDirection.isEmpty()) {
            sortDirection = "Desc";
        }
        //-------------------------

        //int idExercicio = Integer.parseInt(request.getParameter("idExercicio").toString());
//        String descricao = request.getParameter("descricao").toString();
//        Integer idTipoLog = request.getParameter("idTipoLog").toString();
//        Date dataInicio = request.getParameter("dataInicio").toString();
//        Date dataFim = request.getParameter("dataFim").toString();
        PaginadorUtil<Object> paginador = new PaginadorUtil<Object>();

        //String sql = "select * from Log where Mensagem like :p0 and IdTipoLog = :p1 and DataCadastro >= :p2 and DataCadastro <= :p3 ";
        String sql = "select "
                + "  P.Nome, "
                + "  L.Mensagem, "
                + "  TL.Nome as TipoLog, "
                + "  L.DataCadastro "
                + "from "
                + "  Log L "
                + "  inner join TipoLog TL "
                + "  on TL.ID = L.IdTipoLog "
                + "  inner join Pessoa P "
                + "  on P.ID = L.IdPessoa "
                + "where 1=1 ";

        if (!descricao.isEmpty()) {
            sql += " and L.Mensagem like '%" + descricao + "%'";
        }
        if (!dataInicio.isEmpty()) {
            sql += " and L.DataCadastro >= '" + Util.formatDate(Util.stringToData(dataInicio), "yyyy-MM-dd") + " 00:00'";
        }
        if (!dataFim.isEmpty()) {
            sql += " and L.DataCadastro <= '" + Util.formatDate(Util.stringToData(dataFim), "yyyy-MM-dd") + " 23:59'";
        }
        if (idTipoLog != null) {
            sql += " and L.IdTipoLog = " + idTipoLog;
        }

        ScalarResult[] scalar = new ScalarResult[4];
        scalar[0] = new ScalarResult("Nome", StringType.INSTANCE);
        scalar[1] = new ScalarResult("Mensagem", StringType.INSTANCE);
        scalar[2] = new ScalarResult("TipoLog", StringType.INSTANCE);
        scalar[3] = new ScalarResult("DataCadastro", TimestampType.INSTANCE);

        List<Object> lista = paginador.Execute(
                model,
                sql,
                scalar,
                currentPage,
                pageSize,
                sortField,
                sortDirection);

        model.addAttribute("listaLog", lista);
        model.addAttribute("descricao", descricao);
        model.addAttribute("idTipoLog", idTipoLog);
        model.addAttribute("dataInicio", dataInicio);
        model.addAttribute("dataFim", dataFim);

        LogService repoLog = new LogService();
        model.addAttribute("listaTipoLog", repoLog.getTipoLog());

        return "log/list";
    }

}
