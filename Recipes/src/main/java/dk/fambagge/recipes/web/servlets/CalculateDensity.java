/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.fambagge.recipes.web.servlets;

import dk.fambagge.recipes.db.MeasureType;
import dk.fambagge.recipes.domain.Measure;
import dk.fambagge.recipes.web.servlets.beans.CalculateDensityData;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author Gof
 */
public class CalculateDensity extends HttpServlet {

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
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            
            CalculateDensityData data = new CalculateDensityData();
            
            try {
                BeanUtils.populate(data, request.getParameterMap());
            } catch (InvocationTargetException|IllegalAccessException ex) {
                Logger.getLogger("Recepies").log(Level.SEVERE, "Error populating bean with data", ex);
            }
           
            Measure weightMeassure = MeasureType.getTypeFromString(data.getCalculateDensityWeightUnit());
            Measure volumeMeassure = MeasureType.getTypeFromString(data.getCalculateDensityVolumeUnit());

            double grams = weightMeassure.convertTo(data.getCalculateDensityWeight(), Measure.Weight.GRAM);
            double liters = volumeMeassure.convertTo(data.getCalculateDensityVolume(), Measure.Volume.LITER);

            double density = grams / liters;

            out.println("{\"density\":"+density+"}");
        }
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Calculates density based on given values";
    }// </editor-fold>

}
