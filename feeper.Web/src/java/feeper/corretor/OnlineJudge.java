package feeper.corretor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oreilly.servlet.Base64Decoder;

public class OnlineJudge extends HttpServlet {
    
    Hashtable users = new Hashtable();
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        // Names and passwords are case sensitive!
        users.put("feeper:srv8f33p3r", "allowed");
      }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            
            int idResposta = Integer.parseInt(request.getParameter("r").toString());
            int timeout = 10000;
            
            Correcao c = new Correcao(idResposta, timeout);
            Thread t = new Thread(c);
            t.start();
            
        } catch (Exception e) {
            e.printStackTrace();
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
        
//        response.setContentType("text/plain");
//        PrintWriter out = response.getWriter();
//        
//        // Get Authorization header
//        String auth = request.getHeader("Authorization");
//
//        // Do we allow that user?
//        if (!allowUser(auth)) {
//            // Not allowed, so report he's unauthorized
//            response.setHeader("WWW-Authenticate", "BASIC realm=\"users\"");
//            response.sendError(response.SC_UNAUTHORIZED);
//            // Could offer to add him to the allowed user list
//        }
//        else {
//            // Allowed, so show him the secret stuff
//            processRequest(request, response);
//        }
        processRequest(request, response);
    }
    
    // This method checks the user information sent in the Authorization
    // header against the database of users maintained in the users Hashtable.
    protected boolean allowUser(String auth) throws IOException {
        if (auth == null) return false;  // no auth

        if (!auth.toUpperCase().startsWith("BASIC "))
            return false;  // we only do BASIC

        // Get encoded user and password, comes after "BASIC "
        String userpassEncoded = auth.substring(6);

        // Decode it, using any base 64 decoder (we use com.oreilly.servlet)
        String userpassDecoded = Base64Decoder.decode(userpassEncoded);

        // Check our user list to see if that user and password are "allowed"
        if ("allowed".equals(users.get(userpassDecoded)))
            return true;
        else
            return false;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "OnlineJudge 1.0";
    }// </editor-fold>

}
