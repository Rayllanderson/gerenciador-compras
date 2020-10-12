package com.ray.model.view;

import java.util.Scanner;

import com.ray.informacoes.InformacoesProdutos;
import com.ray.model.entities.Categoria;
import com.ray.model.entities.Product;
import com.ray.model.exception.BackButtonException;
import com.ray.model.exception.EntradaInvalidaException;
import com.ray.model.exception.OpcaoInvalidaException;
import com.ray.model.exception.ProductoException;
import com.ray.model.interacoes.InteracaoProduto;
import com.ray.model.service.ProductServiceConsole;
import com.ray.model.util.ButtonUtil;
import com.ray.model.util.ProdutosUtilConsole;

public class MenuProduto {

    // --------------------- MENUS EDITAR PRODUTO ----------------------------
    /**
     * Exceptions tratadas: EntradaInvalidaException, NumberFormatException, OpcaoInvalidaException
     */
    
    @SuppressWarnings("resource")
    public static boolean menuEditarProduto(ProductServiceConsole service) {
	String opcaoEditarProduto;
	Scanner scan = new Scanner(System.in);
	while (true) {
	    try {
		Product p = InteracaoProduto.selecionarProduto(service, new ProdutosUtilConsole(service.getCat()), "Editar");
		ButtonUtil.botaoVoltar(p);
		System.out.println("Escolha o que deseja editar");
		Menu.menuEditarProduto();
		opcaoEditarProduto = scan.next();
		if (opcaoEditarProduto.equals("0"))
		    return false;
		switch (Integer.parseInt(opcaoEditarProduto)) {
		case 1:
		    return eliminarVerbose((InteracaoProduto.editarTudoProduto(service, p)));
		case 2:
		    return eliminarVerbose(InteracaoProduto.editarNomeProduto(service, p));
		case 3:
		    return eliminarVerbose(InteracaoProduto.editarValorEstipulado(service, p));
		case 4:
		    return eliminarVerbose(InteracaoProduto.editarValorReal(service, p));
		case 5:
		    return eliminarVerbose(InteracaoProduto.marcarComoConcluido(service, p));
		case 6:
		    return eliminarVerbose(InteracaoProduto.marcarComoNaoConcluido(service, p));
		case 7:
		    return eliminarVerbose(InteracaoProduto.editarCategoria(p));
		default:
		    throw new OpcaoInvalidaException("Op��o inv�lida");
		}
	    } catch (NumberFormatException e) {
		System.out.println("Entrada inv�lida! Digite apenas n�meros");
	    } catch (BackButtonException e) {
		return false;
	    } catch (OpcaoInvalidaException e) {
		System.out.println(e.getMessage());
	    }catch (ProductoException e) {
		System.out.println(e.getMessage());
	    }
	}
    }

    // ------------------------- MENU FUNCOES UTEIS -------------------------
    /**
     * Exceptions tratadas: EntradaInvalidaException, NumberFormatException
     */
    public static boolean funcoesUteis(ProdutosUtilConsole service, Categoria cat) {
	String opcaoEditarProduto;
	@SuppressWarnings("resource")
	Scanner scan = new Scanner(System.in);
	InformacoesProdutos.mostrarInfosProdutos(cat.getUser(), service, cat.getOrcamento());
	while (true) {
	    try {
		Menu.menuFinanceiro();
		opcaoEditarProduto = scan.next();
		if (opcaoEditarProduto.equals("0")) {
		    return false;
		}
		switch (Integer.parseInt(opcaoEditarProduto)) {
		case 1:
		    InformacoesProdutos.quantidadeGasta(service, cat);
		    break;
		case 2:
		    System.out.println("Produtos comprados: ");
		    InformacoesProdutos.listarConcluidosConsole(service);
		    break;
		case 3:
		    System.out.println("Produdos que voc� ainda n�o comprou: ");
		    InformacoesProdutos.listarNaoConcluidosConsole(service);
		    break;
		case 4:
		    InformacoesProdutos.disponivelParaComprar(service, cat);
		    break;
		case 5:
		    InformacoesProdutos.valorEconomizado(service);
		    break;
		default:
		    throw new EntradaInvalidaException("Op��o inv�lida! Tente novamente.");
		}
	    } catch (EntradaInvalidaException e) {
		System.out.println(e.getMessage());
	    } catch (NumberFormatException e) {
		System.out.println("Digite apenas n�meros");
	    }
	}
    }

    private static boolean eliminarVerbose(boolean funcao) {
	if (funcao) {
	    return Menu.continuarEditando();
	} else {
	    return false;
	}
    }

}
