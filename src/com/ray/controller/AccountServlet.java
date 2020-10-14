package com.ray.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ray.informacoes.InformacoesUsuario;
import com.ray.model.dao.DaoFactory;
import com.ray.model.dao.UserDao;
import com.ray.model.entities.User;
import com.ray.model.exception.MyLoginException;
import com.ray.model.service.UserService;
import com.ray.model.validacoes.UserValidation;

@WebServlet("/my-account")
public class AccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDao repository = DaoFactory.createUserDao();
    private UserService service = new UserService();

    public AccountServlet() {
	super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	String action = request.getParameter("action");
	if (action != null) {
	    setInformacoes(request);
	    if (action.equals("view")) {
		RequestDispatcher dispatcher = null;
		dispatcher = request.getRequestDispatcher("account.jsp");
		dispatcher.forward(request, response);
	    }
	}

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	String action = request.getParameter("action");
	System.out.println(action);
	if (action != null) {
	    response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");
	    String id = request.getParameter("id");
	    String name = request.getParameter("nome");
	    String username = request.getParameter("username");
	    if (action.equals("editar")) {
		if (UserValidation.idIsValid(request, id)) { // verificando se o id � de fato o id do user logado
		    if (!UserValidation.fieldsAreValids(name, username)) { // validando os campos
			response.setStatus(400);
			response.getWriter().write("Um ou mais campos est�os vazios");
		    } else {
			if (UserValidation.userIsModified(name, username, request)) { //verificando se modificou pra evitar requisi��o desnecess�ria
			    try {
				if (service.update(Long.valueOf(id), name, username)) {
				    response.setStatus(200);
				    response.getWriter().write("Editado com sucesso!");
				    request.getSession().setAttribute("user", repository.findById(Long.valueOf(id)));
				    setInformacoes(request);
				} else {
				    response.setStatus(500);
				    response.getWriter().write("Ocorreu um erro inesperado");
				}
			    } catch (MyLoginException e) {
				response.setStatus(409);
				response.getWriter().write("O username escolhido j� est� em uso. Tente usar outro");
			    }
			} else { //usuario nao mudou nenhum campo
			    response.setStatus(200);
			    response.getWriter().write("Nenhuma altera��o foi detectada.");
			}
		    }
		} else { // id n�o � o mesmo
		    response.setStatus(400);
		    response.getWriter().write("Ocorreu um erro. Por favor, atualize a p�gina e se o problema persistir, fa�a login novamente.");
		}
	    }else if(action.equals("base64")) {
		String fileUpload = request.getParameter("fileUpload");
		System.out.println("UE CARALGO...");
		System.out.println("alo..." + fileUpload);
	    } else {//se mudar o parametro de action
		response.setStatus(400);
		response.getWriter().write("Ocorreu um erro. Por favor, atualize a p�gina.");
	    }
	}
    }

    private void setInformacoes(HttpServletRequest request) {
	User user = (User) request.getSession().getAttribute("user");
	InformacoesUsuario infos = new InformacoesUsuario(user);
	request.getSession().setAttribute("tListas", infos.getNumeroTotalCategorias());
	request.getSession().setAttribute("tProdutos", infos.getNumTotalProdutos());
	request.getSession().setAttribute("nProdutosComprados", infos.getNumTotalProdutosComprados());
	request.getSession().setAttribute("tEstipulado", infos.getTotalEstipulado());
	request.getSession().setAttribute("tGasto", infos.getTotalReal());
    }

}