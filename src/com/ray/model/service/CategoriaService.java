package com.ray.model.service;

import java.util.ArrayList;
import java.util.List;

import com.ray.db.DbException;
import com.ray.model.dao.CategoriaDao;
import com.ray.model.dao.DaoFactory;
import com.ray.model.entities.Categoria;
import com.ray.model.entities.User;
import com.ray.model.exception.CategoriaException;
import com.ray.model.exception.ListaVaziaException;

public class CategoriaService {

    public CategoriaService(User user) {
	this.categoriaDao = DaoFactory.createCategoriaDao(user);
    }

    private CategoriaDao categoriaDao;

    public boolean salvar(Categoria cat) {
	try {
	    categoriaDao.save(cat);
	    return true;
	} catch (DbException e) {
	    return false;
	}
    }

    public List<Categoria> findAll() {
	return categoriaDao.findAll();
    }

    public List<Categoria> getCategoriaByName(String name) throws ListaVaziaException {
	List<Categoria> list = categoriaDao.findByName(name);
	if (list.isEmpty()) {
	    throw new ListaVaziaException(!(name.length() == 1) ? "N�o existe nenhuma lista com as letras '" + name +"'": "N�o existe nenhuma lista com a letra '" + name + "'");
	}
	return list;
    }

    /**
     * @throws ListaVaziaException ja com mensagem
     */
    public void ListarCategorias() throws ListaVaziaException {
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
     * @param numero da categoria
     * @return a categoria escolhida
     * @throws CategoriaException se nao encontrar categoria com o n�mero digitado.
     *                            Exception j� contem mensagem
     */
    public Categoria getCategoriaByNumber(int num) throws CategoriaException {
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

    public boolean deleteById(Long id) {
	try {
	    categoriaDao.deletById(id);
	    return true;
	} catch (DbException e) {
	    return false;
	}
    }

    public boolean update(Categoria cat) {
	try {
	    if (cat.getOrcamento() == null) {
		cat.setOrcamento(0.0);
	    }
	    categoriaDao.update(cat);
	    return true;
	} catch (DbException e) {
	    return false;
	}
    }

    public void inserirOrcamento(Categoria categoria, double value) {
	categoria.setOrcamento(value);
	categoriaDao.update(categoria);
    }

}