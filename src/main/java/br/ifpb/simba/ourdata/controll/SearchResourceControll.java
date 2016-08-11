/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.controll;

import br.ifpb.simba.ourdata.entity.ResourceItemSearch;
import br.ifpb.simba.ourdata.services.QueryResourceItemSearchBo;
import com.vividsolutions.jts.geom.CoordinateSequenceFactory;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequenceFactory;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Wensttay
 */
@WebServlet( name = "SearchResources", urlPatterns = { "/SearchResources" } )
public class SearchResourceControll extends HttpServlet{

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        
        request.setCharacterEncoding("UTF-8");

        //String nameOfPlace = request.getParameter("nameOfPlace");
        //String typeOfPlace = request.getParameter("typeOfPlace");

        QueryResourceItemSearchBo bo = new QueryResourceItemSearchBo();

        //List<ResourceItemSearch> itensSearch = bo.getResourceItemSearchSortedByRank(nameOfPlace, typeOfPlace);
       
        //request.setAttribute("nameOfPlace",nameOfPlace);
        
        double maxx = Double.parseDouble(request.getParameter("maxx"));
        double minx = Double.parseDouble(request.getParameter("minx"));
        double maxy = Double.parseDouble(request.getParameter("maxy"));
        double miny = Double.parseDouble(request.getParameter("miny"));
        String placeName = request.getParameter("placeName");
        
        Envelope envelope = new Envelope(maxx,minx,maxy,miny);
        
        List<ResourceItemSearch> itensSearch = bo.getResourceItemSearchSortedByRank(envelope);
        
        
         //passando a lista de ResourceItemSearch para o JSP
        request.setAttribute("resourseList", itensSearch);
        request.setAttribute("size",itensSearch.size());
        request.setAttribute("nameOfPlace",placeName);
        
        RequestDispatcher rd = request.getRequestDispatcher("/result.jsp");
        rd.forward(request, response);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException{
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException{
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo(){
        return "Short description";
    }// </editor-fold>

}
