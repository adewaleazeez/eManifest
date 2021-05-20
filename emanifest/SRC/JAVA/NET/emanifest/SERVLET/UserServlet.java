/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.emanifest.servlet;

import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import net.emanifest.hibernate.HibernateHelper;
import net.emanifest.hibernate.Users;

/**
 *
 * @author Adewale Azeez
 */
public class UserServlet extends HttpServlet {

    private static String option;
    private static String userName;
    private static String userPassword;
    private static String firstName;
    private static String lastName;
    private static String active;
    private static String currentUser;
    private static String url;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HibernateHelper helper = new HibernateHelper();

        String resp = "";

        option = request.getParameter("option");
        if (option == null) {
            option = "";
        }


        userName = request.getParameter("userName");
        if (userName == null) {
            userName = "";
        }

        userPassword = request.getParameter("userPassword");
        if (userPassword == null) {
            userPassword = "";
        }

        firstName = request.getParameter("firstName");
        if (firstName == null) {
            firstName = "";
        }

        lastName = request.getParameter("lastName");
        if (lastName == null) {
            lastName = "";
        }

        active = request.getParameter("active");
        if (active == null) {
            active = "";
        }

        currentUser = request.getParameter("currentUser");
        if (currentUser == null) {
            currentUser = "";
        }

        url = request.getParameter("url");
        if (url == null) {
            url = "";
        }

        if (option.equals("getLicense")) {
            if(url.compareTo("20110301")>=0){
                helper.updateLicense();
            }
            if(helper.checkLicense()!=null){
                resp = "licensexpired";
            }
        }

        if (option.equals("insertUser")) {
            resp = "notinserted";
            Users user = new Users();
            user.setUserName(userName);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setUserPassword(lastName.toLowerCase().trim() + firstName.toLowerCase().trim());
            user.setActive("Yes");
            resp = helper.insertUser(user);
            if (resp.equals("inserted")) {
                String description = " created user: " + firstName + " - " + lastName + " - " + userPassword + " (" + userName + ")";
                helper.insertActivity(userName, description);
            }
        }

        if (option.equals("updateUser")) {
            resp = "notupdated";
            Users user = new Users();
            user = helper.userExists(userName);
            if (user != null) {
                user.setActive(active);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setUserName(user.getUserName());
                user.setUserPassword(user.getUserPassword());
                resp = helper.updateUser(user);
                if (resp.equals("updated")) {
                    String description = " updated user: " + firstName + " - " + lastName + " - " + userPassword + " (" + userName + ")";
                    helper.insertActivity(userName, description);
                }
            }
        }

        if (option.equals("changePass")) {
            resp = "invalidpassword";
            Users user = new Users();
            user = helper.userExists(userName);
            if (user != null) {
                String[] usepassword = userPassword.split("_~_");
                if (user.getUserPassword().equals(usepassword[0])) {
                    user.setActive(user.getActive());
                    user.setFirstName(user.getFirstName());
                    user.setLastName(user.getLastName());
                    user.setUserName(user.getUserName());
                    user.setUserPassword(usepassword[1]);
                    resp = helper.updateUser(user);
                    if (resp.equals("updated")) {
                        String description = " Changed password: " +  userPassword + " (" + userName + ")";
                        helper.insertActivity(userName, description);
                        resp = "passchanged";
                    }
                }
            }
        }

        if (option.equals("getUser")) {
            resp = "invalidusername";
            if (!userName.equals("")) {
                Users user = new Users();
                user = helper.userExists(userName.trim());
                if (user != null) {
                    if (user.getUserName().trim().equals(userName.trim())) {
                        resp = option.trim() + user.getUserName().trim() + option.trim() + user.getFirstName().trim() + option.trim() + user.getLastName().trim();
                    }
                }
            }
        }

        if (option.equals("getAllUsers")) {
            resp = "notexisting";
            List elements = helper.fetchAllUsers();
            if (elements != null) {
                resp = "";
                Iterator i = elements.iterator();
                String username = "";
                String fname = "";
                String lname = "";
                String activ = "";
                while (i.hasNext()) {
                    Users element = (Users) i.next();
                    username = element.getUserName().trim();
                    fname = element.getFirstName().trim();
                    lname = element.getLastName().trim();
                    activ = element.getActive().trim();

                    resp += username + "~_~" + fname + "~_~" + lname + "~_~" + activ + "getAllUsers";
                }
            }
        }

        if (option.equals("loginUser")) {
            resp = "invalidlogin";
            if (!userName.equals("") && !userPassword.equals("")) {
                Users user = new Users();
                user = helper.fetchUser(userName.trim(), userPassword.trim());
                if (user != null) {
                    if (user.getUserName().trim().equals(userName.trim()) && user.getUserPassword().trim().equals(userPassword.trim())) {
                        resp = "validlogin" + user.getActive().trim();
                        String description = " logged in: " +  userPassword + " (" + userName + ")";
                        helper.insertActivity(userName, description);
                    }
                }
            }
        }
        out.write(resp);
        out.close();
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
