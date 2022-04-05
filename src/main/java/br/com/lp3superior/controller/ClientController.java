package br.com.lp3superior.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.com.lp3superior.dao.ClientDAO;
import br.com.lp3superior.modelo.Client;

@WebServlet("/ClientController")
public class ClientController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final int QUANTITY_CLIENT_PER_PAGE = 3;
	
	public ClientController() {
		super();
	}

	private void toProcessTheRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");

		if (action == null) {
			throw new ServletException("No specified action.");
		} else if (action.equals("handle_client")) {
			String idClient = request.getParameter("idClient");
			if ((idClient == null ) || (idClient.equals(""))){
				addClient(request, response);
			}else {
				updateClient(request, response);
			}			
		} else if (action.equals("toManage")) {
			showClientList(request, response);
		} else if (action.equals("delete_client")) {
			deleteClient(request, response);
		} else if (action.equals("do_client_pagination")) {
			doClientPagination(request, response);
		} else if (action.equals("get_page_quantity")) {
			getPageQuantity(request, response);
		}
	}
	
	private void getPageQuantity(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ClientDAO clientDAO;
		try {
			clientDAO = new ClientDAO();
			int clientsQuantity = clientDAO.getClientsQuantity();
			int pagesQuantity = (int) Math.ceil((double) clientsQuantity / QUANTITY_CLIENT_PER_PAGE);
			String json = new Gson().toJson(pagesQuantity);
		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.setStatus(200);		    
		    response.getWriter().write(json);
			response.getWriter().flush();
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(500);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print("Error when trying to get pages quantity.");
			response.getWriter().flush();
		}
	}

	private void updateClient(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		long idClient = Long.parseLong(request.getParameter("idClient"));
		String name = request.getParameter("name");
		String cpfClient = request.getParameter("cpf");
		
		Client client = new Client();
		client.setIdClient(idClient);
		client.setName(name);
		client.setCpf(cpfClient);
				
		ClientDAO clientDAO;
		try {
			clientDAO = new ClientDAO();
			clientDAO.updateDatabase(client);
			String json = new Gson().toJson(client);
		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.setStatus(200);		    
		    response.getWriter().write(json);
			response.getWriter().flush();
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(500);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print("Problemas ao alterar o curso.");
			response.getWriter().flush();
		} 
		
	}

	private void showClientList(HttpServletRequest request, HttpServletResponse response) {
		
		String routePage = "/admin/clients_management.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(routePage);
		ClientDAO clientDAO;
		try {
			clientDAO = new ClientDAO();
			
			int clientsQuantity = clientDAO.getClientsQuantity();
			int pagesQuantity = (int) Math.ceil((double) clientsQuantity / QUANTITY_CLIENT_PER_PAGE);
			request.setAttribute("pagesQuantity", pagesQuantity);
			List<Client> clients = clientDAO.getClientWithPagination(QUANTITY_CLIENT_PER_PAGE, 0);
			//List<Client> clients = clientDAO.makeClientList();
			request.setAttribute("clients", clients);
			requestDispatcher.forward(request, response);
		} catch (SQLException | ServletException | IOException e) {			
			e.printStackTrace();
		}
	}
	
	private void deleteClient(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		long idClient = Long.parseLong(request.getParameter("idClient"));
		
		Client client = new Client();
		client.setIdClient(idClient);
				
		ClientDAO clientDAO;
		try {
			clientDAO = new ClientDAO();
			clientDAO.deleteDatabase(client);
			response.setStatus(200);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().flush();
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(500);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print("Problemas ao remover o curso.");
			response.getWriter().flush();
		} 
		
	    
	}

	private void addClient(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");
		String cpfClient = request.getParameter("cpf");
		
		Client client = new Client();
		client.setName(name);
		client.setCpf(cpfClient);
				
		ClientDAO clientDAO;
		try {
			clientDAO = new ClientDAO();
			clientDAO.addToDatabase(client);
			String json = new Gson().toJson(client);
		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.setStatus(200);		    
		    response.getWriter().write(json);
			response.getWriter().flush();
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(500);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print("Problemas ao salvar o curso.");
			response.getWriter().flush();
		} 
		
	}
	
	private int setOffset(String movementDirection, int currentPage) {
		int offset = 0;
		if (movementDirection.equals("previous")) {
			offset = ((currentPage - 1) * QUANTITY_CLIENT_PER_PAGE) - QUANTITY_CLIENT_PER_PAGE;
		} else if (movementDirection.equals("next") ) {
			offset = (currentPage * QUANTITY_CLIENT_PER_PAGE);
		} else {
			offset = (currentPage * QUANTITY_CLIENT_PER_PAGE) - QUANTITY_CLIENT_PER_PAGE;
		}
		return offset;
	}
	
	private void doClientPagination(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		String movementDirection = request.getParameter("movementDirection");
		int offset = setOffset(movementDirection, currentPage);
		ClientDAO clientDAO;
		try {
			clientDAO = new ClientDAO();
			List<Client> clients = clientDAO.getClientWithPagination(QUANTITY_CLIENT_PER_PAGE, offset);
			String json = new Gson().toJson(clients);
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.setStatus(200);		    
		    response.getWriter().write(json);
			response.getWriter().flush();
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(500);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print("Erro when trying to do client pagination.");
			response.getWriter().flush();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		toProcessTheRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		toProcessTheRequest(request, response);
	}

}
