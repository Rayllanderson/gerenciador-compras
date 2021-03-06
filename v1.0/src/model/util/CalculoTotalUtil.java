package model.util;

import java.util.ArrayList;
import java.util.List;

import model.entities.Categoria;
import model.entities.Product;
import model.entities.User;
import model.exception.ListaVaziaException;
import model.service.CategoriaService;
import model.service.ProductService;

public class CalculoTotalUtil {
    private CategoriaService cService;

    public CalculoTotalUtil(User user) {
        this.cService = new CategoriaService(user);
    }

    private void instanciarTodosProdutos(Categoria cat) {
        ProductService pService = new ProductService(cat);
        List<Product> list = pService.findAllProduct();
        for (Product p : list) {
            cat.adicionarProduto(p);
        }
    }

    /**
     * @return uma lista contendo todos os produtos de um usu�rio
     */
    private List<Product> todosProdutos() {
        List<Categoria> listCategoria = cService.findAllCategorias();
        for (int i = 0; i < listCategoria.size(); i++) {
            instanciarTodosProdutos(listCategoria.get(i));
        }
        List<Product> list = new ArrayList<>();
        for (int i = 0; i < listCategoria.size(); i++) {
            list.addAll(listCategoria.get(i).getProductList());
        }
        return list;
    }

    public int numTotalProdutos() {
        return todosProdutos().size();
    }

    public int numTotalProdutosComprados() {
        int total = 0;
        for (Product p : this.todosProdutos()) {
            if (p.isComprado()) {
                total++;
            }
        }
        return total;
    }

    /**
     * @return quanto j� gastou de todas as listas
     */
    public double totalValorReal() {
        double total = 0;
        for (Product p : this.todosProdutos()) {
            total += p.getPrecoReal();
        }
        return total;
    }

    /**
     * @return total do valor que acha que vai pagar de todas as listas
     */
    public double totalEstipulado() {
        double total = 0;
        todosProdutos();
        for (Product p : this.todosProdutos()) {
            total += p.getPrecoEstipulado();
        }
        return total;
    }

    /**
     * @return list.size();
     *
     * @throws ListaVaziaException("Voc� n�o possui listas no momento");
     */
    public int numeroTotalCategorias() throws ListaVaziaException {
        List<Categoria> list = this.cService.findAllCategorias();
        return list.size();
    }
}
