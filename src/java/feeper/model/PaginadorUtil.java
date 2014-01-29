/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.model;

import java.math.BigInteger;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.ui.Model;

/**
 *
 * @author
 * fabioalves
 */
public class PaginadorUtil<T> {
    
    
    public List<T> Execute(Class objClass
                            , Model model
                            , String query
                            , String[] params
                            , int currentPage
                            , int pageSize
                            , String sortField
                            , String sortDirection)
    {
        IntegerResult totalRows = new IntegerResult();
        int totalPages = 0;
        List<T> list = null;
        
        if (sortField != null && !sortField.isEmpty())
            query = AddQuerySorting(query, sortField, sortDirection);
        
        list = ExecutePagedQuery(objClass, query, params, pageSize, currentPage, totalRows);
        
        totalPages = (int)Math.ceil((double)totalRows.getResult() / (double)pageSize);

        if (currentPage > totalPages)
            currentPage = 1;

        UpdateModel(model, totalPages, totalRows.getResult(), currentPage, pageSize, sortField, sortDirection);
        
        return list;
    }
    
    private String AddQuerySorting(String query, String sortField, String sortDirection)
    {
        String[] fields = sortField.split(",");
        String[] directions = sortDirection.split(",");
        StringBuilder sortExpression = new StringBuilder();

        for (int i = 0; i < fields.length; i++)
        {
            if (i == 0)
                sortExpression.append(fields[i]).append(" ").append(directions[i]);
            else
                sortExpression.append(", ").append(fields[i]).append(" ").append(directions[i]);
        }

        query += " ORDER BY " + sortExpression.toString();
        
        return query;
    }
    
    private List<T> ExecutePagedQuery(Class objClass, String query, String[] params, int pageSize, int currentPage, IntegerResult count)
    {
        String command = "SELECT SQL_CALC_FOUND_ROWS";
        String commandCount = "SELECT FOUND_ROWS()";
        command += query.substring(6) + " LIMIT " + ((currentPage - 1) * pageSize) + ", " + pageSize;
        
        HibernateUtil<T> repo = new HibernateUtil<T>();
        SQLQuery exec = repo.query(command).addEntity(objClass);
        
        for (int i = 0; i < params.length; i++)
        {
            exec.setString("p" + i, params[i]);
        }
        
        List<T> list = exec.list();
        count.setResult(((BigInteger)repo.query(commandCount).uniqueResult()).intValue());
        
        return list;
    }
    
    private void UpdateModel(Model model, int totalPages, int totalRows, int currentPage, int pageSize, String sortField, String sortDirection)
    {
        UpdatePagerViewData(model, totalPages, totalRows, currentPage, pageSize);
        UpdateSorterViewData(model, sortField, sortDirection);
        model.addAttribute("GetPages", GetPages(currentPage, totalPages));
    }
    
    private void UpdatePagerViewData(Model model, int totalPages, int totalRows, int currentPage, int pageSize)
    {
        //model.addAttribute("pagerStats", GenPagerStats(viewData, totalRows, currentPage, pageSize));
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageSize", pageSize);

        if (totalPages <= 1)
        {
            model.addAttribute("isPagerVisible", false);
            return;
        }

        int lastPage = totalPages;

        model.addAttribute("isPagerVisible", true);
        model.addAttribute("totalPages", totalPages);

        if (currentPage == 1)
            model.addAttribute("isFirstPage", true);
        else
        {
            model.addAttribute("isFirstPage", false);
            model.addAttribute("previousPage", currentPage - 1);
        }

        if (currentPage == lastPage)
            model.addAttribute("isLastPage", true);
        else
        {
            model.addAttribute("isLastPage", false);

            model.addAttribute("nextPage", currentPage + 1);
            model.addAttribute("lastPage", lastPage);
        }
    }
    
    private void UpdateSorterViewData(Model model, String sortField, String sortDirection)
    {
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
    }
    
    private String GetPages(int currentPage, int totalPages)
    {
        StringBuilder html = new StringBuilder();
        int previous;
        int next;
        int maxPages = 4;
        int maxPagesShow = 2;

        if (totalPages > 1)
        {
            if ((currentPage - maxPages) < 0)
                previous = 1;
            else
                previous = currentPage - maxPagesShow;

            if ((currentPage + maxPages) > totalPages + 1)
                next = totalPages;
            else
                next = currentPage + maxPagesShow;

            if (currentPage - previous < maxPagesShow) next += maxPagesShow - (currentPage - previous);
            if (next - currentPage < maxPagesShow) previous -= maxPagesShow - (next - currentPage);

            if (previous < 1) previous = 1;
            if (next > totalPages) next = totalPages;

            for (int i = previous; i <= next; i++)
            {
                if (i != currentPage)
                    html.append("<li><a href='#' onclick='goToPage(").append(i).append(")'>").append(i).append("</a></li>");
                else
                    html.append("<li class='active'><a href='#' onclick='goToPage(").append(i).append(")'>").append(i).append("</a></li>");
            }
        }

        return html.toString();
    }
    
}
