package model.service;

import java.util.ArrayList;
import java.util.List;

import db.DbException;
import model.dao.CategoriaDao;
import model.dao.DaoFactory;
import model.entities.Categoria;
import model.entities.User;
import model.exception.CategoriaException;
import model.exception.ListaVaziaException;

public class CategoriaService {

    public CategoriaService(User user) {
	this.categoriaDao = DaoFactory.createCategoriaDao(user);
    }

    private CategoriaDao categoriaDao;

    public boolean criarCategoria(Categoria cat) {
	try {
	    categoriaDao.inserir(cat);
	    return true;
	} catch (DbException e) {
	    return false;
	}
    }

    public void ListarCategorias() throws ListaVaziaException{
	List<Categoria> list = new ArrayList<>();
	list = categoriaDao.findAll();
	if (!list.isEmpty()) {
	    for (int i = 0; i < list.size(); i++) {
		System.out.println("[ " + (i + 1) + " ] - " + list.get(i));
	    }
	} else {
	    throw new ListaVaziaException("Lista Vazia");
	}
    }
    /**
     * 
     * @param numero da categoria
     * @return a categoria escolhida
     * @throws CategoriaException se nao encontrar categoria com o n�mero digitado
     */
    public Categoria getCategoriaByNumber(int num) throws CategoriaException{
	List<Categoria> list = new ArrayList<>();
	list = categoriaDao.findAll();
	if (!list.isEmpty() && num <= list.size()) {
	    return list.get(num - 1);
	}
	throw new CategoriaException("N�o encontrado. Verifique se digitou corretamente");
    }

    public boolean deletarCategoria(Categoria cat) {
	try {
	    categoriaDao.deletById(cat.getId());
	    return true;
	} catch (DbException e) {
	    return false;
	}
    }

    public boolean atualizarCategoria(Categoria cat) {
	try {
	    categoriaDao.atualizar(cat);
	    return true;
	} catch (DbException e) {
	    return false;
	}
    }
    
    public void inserirOrcamento(Categoria categoria, double value) {
	categoria.setOrcamento(value);
	categoriaDao.atualizar(categoria);
    }
}
