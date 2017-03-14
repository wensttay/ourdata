package br.ifpb.simba.ourdata.controll;

import br.ifpb.simba.ourdata.entity.Resource;
import br.ifpb.simba.ourdata.entity.ResourceItemSearch;
import br.ifpb.simba.ourdata.entity.ResourceTimeSearch;
import br.ifpb.simba.ourdata.services.QueryResourceItemSearchBo;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @version 1.0
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 07/01/2017 - 12:01:31
 */
@WebServlet(name = "SearchResourcesByTime", urlPatterns = {"/SearchResourcesByTime"})
public class SearchResourcesByTimeControll extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        QueryResourceItemSearchBo itemSearchBo = new QueryResourceItemSearchBo();

        String startTime = request.getParameter("startInput");
        String endTime = request.getParameter("endInput");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

        Date startDate = null;
        Date endDate = null;
        try {
            startDate = sdf.parse(startTime);
            endDate = sdf.parse(endTime);
        } catch (ParseException ex) {
            Logger.getLogger(SearchResourcesByTimeControll.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<ResourceItemSearch> itensSearch = new ArrayList<>();

        if (startDate != null && endDate != null) {
            List<ResourceTimeSearch> rtses = itemSearchBo.getResourceItemSearchByTime(startDate, endDate);
            for (ResourceTimeSearch rtse : rtses) {
                Resource r = new Resource();
                r.setDescricao(rtse.getDescription());
                r.setUrl(rtse.getResourceUrl());
                r.setFormato("CSV");

                itensSearch.add(
                        new ResourceItemSearch(r, rtse.getIntervelTimes()));
            }
        }

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        request.setAttribute("resourseList", itensSearch);
        request.setAttribute("size", itensSearch.size());

        String formatedStart = df.format(startDate);
        String formatedEnd = df.format(endDate);

        request.setAttribute("nameOfPlace", "From " + formatedStart + " to " + formatedEnd);

        RequestDispatcher rd = request.getRequestDispatcher("/result.jsp");
        rd.forward(request, response);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
