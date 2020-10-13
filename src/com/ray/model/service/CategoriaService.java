package com.ray.model.service;

import java.util.List;

import com.ray.db.DbException;
import com.ray.model.dao.CategoriaDao;
import com.ray.model.dao.DaoFactory;
import com.ray.model.entities.Categoria;
import com.ray.model.entities.User;
import com.ray.model.exception.CategoriaException;
import com.ray.model.exception.CategoriaInexistenteException;
import com.ray.model.exception.ListaVaziaException;

public class CategoriaService {

    public CategoriaService(User user) {
	this.categoriaDao = DaoFactory.createCategoriaDao(user);
    }

    protected CategoriaDao categoriaDao;

    // --------------------------- CRUD ----------------------------------
    /**
     * 
     * @param cat
     * @return a categoria salva no banco de dados, com id
     * @throws CategoriaException caso o nome da categoria esteja vazio
     */
    public Categoria save(Categoria cat) throws CategoriaException {
	try {
	   if (cat.getOrcamento() == null) {
		cat.setOrcamento(0.0);
	    }
	   validarNome(cat);
	   return categoriaDao.save(cat);
	} catch (DbException e) {
	    return null;
	}
    }
   
    public List<Categoria> findAll() {
	return categoriaDao.findAll();
    }
    
    public boolean deleteById(Long id) {
	try {
	    categoriaDao.deletById(id);
	    return true;
	} catch (DbException e) {
	    return false;
	}
    }

    /**
     * @param cat
     * @return true caso d� tudo ok
     * @throws CategoriaException caso o nome da categoria esteja nulo
     * @throws CategoriaInexistenteException - caso a categoria editada n�o exista (quest�o de seguran�a apenas, para n�o permitir mudar a categoria editando o html, por exemplo)
     */
    public boolean update(Categoria cat) throws CategoriaException, CategoriaInexistenteException {
	try {
	    if (cat.getOrcamento() == null) {
		cat.setOrcamento(0.0);
	    }
	    validarCategoria(cat);
	    validarNome(cat);
	    categoriaDao.update(cat);
	    return true;
	} catch (DbException e) {
	    return false;
	}
    }
    
    // -------------------------------- outros -------------------------------//
    
    public List<Categoria> findCategoriaByName(String name) throws ListaVaziaException {
	List<Categoria> list = categoriaDao.findByName(name);
	if (list.isEmpty()) {
	    throw new ListaVaziaException(!(name.length() == 1) ? "N�o existe nenhuma lista com as letras '" + name +"'": "N�o existe nenhuma lista com a letra '" + name + "'");
	}
	return list;
    }

    //----------------------- valida�oes ----------------------------//
    
    private void validarNome(Categoria cat) throws CategoriaException {
	String nome = cat.getName();
	if (nome.trim().isEmpty() || nome == null) {
	    throw new CategoriaException("O campo 'Nome' n�o pode ser nulo");
	}
    }
    
    /**
     * m�todo para verifica se a categoria atual percente ao usu�rio
     * 
     * @throws CategoriaInexistenteException caso a categoria escolhida n�o exista para o
     *                           usu�rio atual
     */
    public static void validarCategoria(Categoria cat) throws CategoriaInexistenteException {
	// H� uma possibilidade de editar o html e mudar a categoria, portanto, antes de
	// atualizar,
	// procura no banco de dados para verificar se o usu�rio � mesmo o dono da
	// categoria, se nao for, retorna nulo.
	CategoriaDao catRespotitory = DaoFactory.createCategoriaDao(cat.getUser());
	List<Categoria> list = catRespotitory.findAll();
	if (!list.contains(cat)) {
	    throw new CategoriaInexistenteException("Essa categoria n�o existe.");
	}
    }
}
