package com.ray.model.service;

import com.ray.model.dao.DaoFactory;
import com.ray.model.dao.UserDao;
import com.ray.model.entities.Arquivo;
import com.ray.model.entities.User;
import com.ray.model.exception.MyLoginException;
import com.ray.model.validacoes.UserValidation;

public class UserService {

    private UserDao dao;

    public UserService() {
	this.dao = DaoFactory.createUserDao();
    }

    public boolean alterarUsername(Long id, String newUsername) {
	User user = dao.findById(id);
	if (user != null) {
	    // Verificando se o username atual n�o � igual ao username dele mesmo
	    if (!newUsername.equals(user.getUsername())) {
		// se nao for, verificar se o username j� existe
		try {
		    UserValidation.validarUsername(newUsername, dao);
		    user.setUsername(newUsername);
		    dao.update(user);
		    return true;
		} catch (MyLoginException e) {
		    return false;
		}
	    }
	    return true; // caso o username for igual o atual n�o fazer nada, apenas retorna mensagem de
			 // sucesso
	}
	return false; // usu�rio n�o existe
    }

    /**
     * update name e username (verifica se o username ja existe)
     * 
     * @param id
     * @param newName
     * @param newUsername
     * @return true caso d� tudo ok, false se o usu�rio com id passado n�o exista
     * @throws MyLoginException caso login j� exista
     */
    public boolean update(Long id, String newName, String newUsername, String miniatura, String foto) throws MyLoginException {
	User user = dao.findById(id);
	if (user != null) {
	    if (miniatura.equals("") && user.getMiniatura()!= null) {
		miniatura = user.getMiniatura();
	    }
	    if (foto.equals("") && user.getFoto()!= null) {
		foto = user.getFoto();
	    }
	    alterarUsername(id, newUsername);
	    user.setName(newName);
	    user.setMiniatura(miniatura);
	    user.setFoto(new Arquivo(foto, null));
	    dao.update(user);
	    return true;
	}
	return false; // usu�rio n�o existe
    }


    /**
     * 
     * @param user novo usu�rio a se cadastrar
     * @return true caso ocorra tudo ok
     * @throws MyLoginException caso username j� exista (throws j� possui mensagem)
     */
    public boolean cadastrar(User user) throws MyLoginException {
	UserValidation.validarUsername(user.getName(), dao); // caso username j� exista, throw MyLoginException;
	dao.cadastrar(user);
	return true;
    }

    /**
     * m�todo verifica a senha antes de alterar
     * @param user
     * @param senhaAtual
     * @param newPassword
     * @return true caso ocorra tudo ok
     * @throws MyLoginException caso a senha n�o corresponda
     */
    public boolean changePassword(User user, String senhaAtual, String newPassword) throws MyLoginException {
	if (verificarSenha(user, senhaAtual)) { // verificar senha antes de poder alterar
	    user.setPassword(newPassword);
	    dao.update(user);
	    return true;
	} else {
	    throw new MyLoginException("Sua senha n�o corresponde a senha digitada. Digite novamente");
	}
    }

    public boolean verificarSenha(User user, String password) throws MyLoginException {
	if (user.getPassword().equals(password)) {
	    return true;
	}
	return false;
    }

    /**
     * update sem verifica��o, apenas atualiza
     * @param user
     */
    public void update(User user) {
	dao.update(user);
    }
}
