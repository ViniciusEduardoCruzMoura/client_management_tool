package br.com.lp3superior.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import br.com.lp3superior.dao.UserDAO;
import br.com.lp3superior.modelo.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@WebServlet("/UserController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public UserController() {
        super();
    }
    
    private void toProcessTheRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		if (action == null) {
			throw new ServletException("No specified action.");
		} else if (action.equals("new_account" )) {
			newAccount(request, response);
		} else if (action.equals("create_new_account")) {
			createNewAccount(request, response);
		} else if (action.equals("go_to_login_page")) {
			goToLoginPage(request, response);
		} else if (action.equals("login")) {
			login(request, response);
		} else if (action.equals("logout") ) {
			logout(request, response);
		}
		
	}
    
    private String md5(String str) {
    	String strMd5 = null;		
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(str.getBytes(),0,str.length());
		    strMd5 = new BigInteger(1,m.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	    return strMd5;
    }
    
    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	HttpSession session = request.getSession(false);
    	if(session != null){
    		session.invalidate();
    	}
    	response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
    
    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		User user = new User();
		user.setEmail(email);
		String strMd5 = md5(password);
		user.setPassword(strMd5);
		
		UserDAO userDAO;
		try {
			userDAO = new UserDAO();
			String json = null;
			if (userDAO.login(user)) {
				HttpSession oldSession = request.getSession(false);
				if (oldSession != null) {
					oldSession.invalidate();
				}
				HttpSession newSession = request.getSession(true);
				newSession.setAttribute("user", user);
				newSession.setMaxInactiveInterval(8*60);
				json = new Gson().toJson("OK");
			} else {
				json = new Gson().toJson("NO");
			}
		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.setStatus(200);		    
		    response.getWriter().write(json);
			response.getWriter().flush();
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(500);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print("Error when trying to login.");
			response.getWriter().flush();
		}
    }
    
    private void  goToLoginPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String routePage =  "index.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(routePage);		
		try {			
			requestDispatcher.forward(request, response);
		} catch (ServletException | IOException e) {			
			e.printStackTrace();
		}	
    }
    
    private void newAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String routePage =  "sign_up.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(routePage);
		try {			
			requestDispatcher.forward(request, response);
		} catch (ServletException | IOException e) {			
			e.printStackTrace();
		}
	}
	
	private void createNewAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");	
		String password = request.getParameter("password");
		
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		String strMd5 = md5(password);
		user.setPassword(strMd5);
		
		UserDAO userDAO;
		try {
			userDAO = new UserDAO();
			userDAO.addUser(user);
			String json = new Gson().toJson("OK");
		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.setStatus(200);		    
		    response.getWriter().write(json);
			response.getWriter().flush();
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(500);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print("Error when trying to create a new account.");
			response.getWriter().flush();
		}
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		toProcessTheRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		toProcessTheRequest(request, response);
	}

}
