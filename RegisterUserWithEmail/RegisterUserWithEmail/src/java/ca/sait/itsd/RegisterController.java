/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.sait.itsd;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author john
 */
@WebServlet(name = "RegisterController", urlPatterns = {"/RegisterController"})
public class RegisterController extends HttpServlet {

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
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        String email = request.getParameter("email");
        
        String register = request.getParameter("register");
        
        String confirmEmail = request.getParameter("confirmEmail");
        String confirmID = request.getParameter("confirmID");        
        
        String logout = request.getParameter("logout");
        
        
        //If user wants to go to registration page
        if (register!=null) {
            getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);            
        }
        //User is confirming their registration via email
        else if (confirmEmail!=null && confirmID!=null) {
            
            RegisterUser ru = new RegisterUser();
            if (ru.confirmRegistration(confirmEmail, confirmID)) {
                request.setAttribute("message", "Registration confirmed successfully, please log in");
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);                
            }
            else {
                request.setAttribute("message", "Error confirming registration!");
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            }
            
        }
        //No operations, just go to login page
        else if (username==null && password==null) {
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
        //Logging out
        else if (logout!=null) {
            HttpSession session = request.getSession();
            
            session.invalidate();
            
            request.setAttribute("message", "Logged out");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
        //Registering a new user
        else if (username!=null && password!=null && email!=null && !username.isEmpty() && !password.isEmpty() && !email.isEmpty()) {
            RegisterUser ru = new RegisterUser();
            
            if (ru.registerNewUser(username, password, email)) {
                
                String confirmationID = ru.getConfirmIDforUsername(username);
                
                SendEmail sendEmail = new SendEmail();
                
                String emailBody="Click on this link to confirm your registration: <a href='http://localhost:8080/RegisterUserWithEmail/RegisterController?confirmEmail=" + email
                        + "&confirmID=" + confirmationID + "'>Confirm</a>";
                
                try {
                    sendEmail.sendMail(email, "Confirm registration", emailBody, true);
                } catch (MessagingException ex) {
                    Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NamingException ex) {
                    Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                request.setAttribute("message", "New user registered - confirm via email before attempting to log in");
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            }
            else {
                request.setAttribute("message", "Error registering new user!");
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);               
            }
        }
        //Logging in
        else if (username!=null && password!=null && !username.isEmpty() && !password.isEmpty() ) {
            Validate v = new Validate();
            
            if (v.validateLogin(username, password)) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                getServletContext().getRequestDispatcher("/WEB-INF/main.jsp").forward(request, response);
            }
            else {
                request.setAttribute("message", "Invalid username or password!");
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            }
                    
        }
        //Nothing else happening, so just go to login page
        else if (username!=null && password!=null ) {
            
            if (username.isEmpty() || password.isEmpty()) {
                request.setAttribute("message", "Both username and password are required!");
            }
            
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
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
        return "Short description";
    }// </editor-fold>

}
