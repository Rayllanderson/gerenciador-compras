package model.service;

import java.util.List;

import db.DbException;
import model.dao.DaoFactory;
import model.dao.ProductDao;
import model.entities.Categoria;
import model.entities.Product;
import model.exception.ListaVaziaException;
import model.exception.ProductoException;
import model.util.FormatarTabela;

public class ProductService {

    private ProductDao dao;

    public ProductService(Categoria cat) {
	this.dao = DaoFactory.createProductDao(cat);
    }

    public boolean inserir(Product p) {
	try {
	    dao.inserir(p);
	    return true;
	} catch (DbException e) {
	    return false;
	}
    }

    public void listarPordutos() {
	List<Product> list = dao.findAll();
	if (list.isEmpty()) {
	    throw new ListaVaziaException("Ops, parece que voc� n�o tem nenhum produto na lista.");
	}
	int maxLenName = FormatarTabela.maxLenghtName(list) + 2;
	FormatarTabela.printInvoiceHeader(maxLenName + 2, 5);
	for (int i = 0; i < list.size(); i++) {
	    FormatarTabela.printInvoice(list.get(i), maxLenName + 2, 5, (i + 1));
	}
    }

    public Product getProdutoByNumer(int num) {
	List<Product> list = dao.findAll();
	if (list.isEmpty()) {
	    throw new ListaVaziaException("Ops, parece que voc� n�o tem nenhum produto na lista.");
	}
	if (num > list.size()) {
	    throw new ProductoException(
		    "Parece n�o existe nenhum produto com n�mero " + num + ". Verifique a tabela e tente novamente");
	}
	return list.get(num - 1);
    }

    public boolean atualizar(Product p) {
	try {
	    dao.atualizar(p);
	    return true;
	} catch (DbException e) {
	    return false;
	}
    }

    public boolean deletar(Product p) {
	try {
	    dao.deletById(p.getId());
	    return true;
	} catch (DbException e) {
	    return false;
	}
    }

    public void editarNome(Product p, String nome) {
	p.setNome(nome);
	dao.atualizar(p);
    }

    public void editarPrecoEstipulado(Product p, double value) {
	p.setPrecoEstipulado(value);
	dao.atualizar(p);
    }

    public void editarPrecoReal(Product p, double value) {
	p.setPrecoReal(value);
	dao.atualizar(p);
    }

    public void marcarComoConcluido(Product p, double value) {
	if (p.isCompraro()) {
	    throw new ProductoException("Produto estava marcado como comprado");
	}
	p.setPrecoReal(value);
	if (!(p.getId() == null)) {
	    p.setCompraro(true);
	    dao.atualizar(p);
	} else {
	    p.setCompraro(true);
	}
    }

    public void marcarComoNaoConcluido(Product p, double value) {
	if (!(p.isCompraro())) {
	    throw new ProductoException("Produto estava marcado como n�o comprado");
	}
	p.setPrecoReal(value);
	if (!(p.getId() == null)) {
	    p.setCompraro(false);
	    dao.atualizar(p);
	} else {
	    p.setPrecoReal(value);
	}
    }

    public void listarNaoConcluidos() {
	List<Product> list = dao.findAll();
	if (list.isEmpty()) {
	    throw new ListaVaziaException("Ops, sem produtos. Parece que todos os seus produtos j� foram comprados.");
	}
	int maxLenName = FormatarTabela.maxLenghtName(list) + 2;
	FormatarTabela.printInvoiceHeader(maxLenName + 2, 5);
	for (int i = 0; i < list.size(); i++) {
	    if (!(list.get(i).isCompraro())) {
		FormatarTabela.printInvoice(list.get(i), maxLenName + 2, 5, (i + 1));
	    }
	}
    }

    public void listarConcluidos() {
	List<Product> list = dao.findAll();
	if (list.isEmpty()) {
	    throw new ListaVaziaException("Ops, sem produtos. Parece que voc� ainda n�o comprou nenhum produto.");
	}
	int maxLenName = FormatarTabela.maxLenghtName(list) + 2;
	FormatarTabela.printInvoiceHeader(maxLenName + 2, 5);
	for (int i = 0; i < list.size(); i++) {
	    if (list.get(i).isCompraro()) {
		FormatarTabela.printInvoice(list.get(i), maxLenName + 2, 5, (i + 1));
	    }
	}
    }

    public List<Product> produtosNaoConcluidos() {
	List<Product> list = dao.findAll();
	if (list.isEmpty()) {
	    throw new ListaVaziaException("Todos os produtos da lista foram comprados :)");
	}
	for (int i = 0; i < list.size(); i++) {
	    if (list.get(i).isCompraro()) {
		list.remove(i);
	    }
	}
	return list;
    }

    public List<Product> produtosConcluidos() {
	List<Product> list = dao.findAll();
	if (list.isEmpty()) {
	    throw new ListaVaziaException("Nenhum produto foi comprado ainda :(");
	}
	for (int i = 0; i < list.size(); i++) {
	    if (!(list.get(i).isCompraro())) {
		list.remove(i);
	    }
	}
	return list;
    }

    public double quantidadeGasta() {
	List<Product> list = this.produtosConcluidos();
	double sum = 0;
	for (Product p : list) {
	    sum += p.getPrecoReal();
	}
	return sum;
    }

    public double valorEconomizado() {
	List<Product> list = this.produtosConcluidos();
	double total = 0;
	for (Product p : list) {
	    total += p.getPrecoEstipulado() - p.getPrecoReal();
	}
	return total;
    }
    
    public double valorDisponivelParaCompra(Categoria cat) {
	double total = 0;
	double orcamento = cat.getOrcamento();
	total = orcamento - this.quantidadeGasta();
	return total;
    }
 
    // talvez editar categoria futuramente...
}
