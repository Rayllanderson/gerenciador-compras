package com.ray.model.util;

import java.text.NumberFormat;

import com.ray.model.entities.Categoria;
import com.ray.model.entities.User;
import com.ray.model.exception.ListaVaziaException;
import com.ray.model.service.ProductService;

public class ProdutosUtil {

    private static NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
   
    // -------------------- M�TODOS FUN��ES �TEIS ----------------------//
    /**
     * @apiNote Exceptions tratadas nesse m�todo: NullPointerException,
     *          ListaVaziaException
     */
    public static String quantidadeGasta(ProductService service, Categoria cat) {
	try {
	    double orcamento = cat.getOrcamento();
	    double valorGasto = service.getValorRealGasto();
	    String complemento = ". ";
	    if (orcamento == 0) {
		complemento = " E voc� n�o tem um orcamento para essa lista";
	    } else {
		if (valorGasto > orcamento) {
		    complemento += "Voc� j� ultrapassou seu or�amento em R$" + (valorGasto - orcamento);
		} else {
		    complemento += "Voc� ainda tem " + currencyFormatter.format((orcamento - valorGasto))
			    + " para gastar de acordo com seu or�amento de " + currencyFormatter.format(orcamento);
		}
	    }
	    return "Voc� j� gastou " + currencyFormatter.format(valorGasto) + complemento;
	} catch (NullPointerException e) {
	    return "Voc� n�o tem or�amento para esta lista. Adicione um no menu principal";
	} catch (ListaVaziaException e) {
	    return "Nenhum produto comprado at� o momento, sendo assim, voc� n�o gastou nada.";
	}
    }

    /**
     * @apiNote Exceptions tratadas nesse m�todo: NullPointerException,
     *          ListaVaziaException
     */
    public static String disponivelParaComprar(ProductService service, Categoria cat) {
	try {
	    double disponivel = service.getValorDisponivelParaCompra(cat);
	    String complemento = "";
	    if (cat.getOrcamento() == 0.0 || cat.getOrcamento() == null) {
		throw new NullPointerException();
	    }
	    if (disponivel < 0) {
		complemento = "<font color=\"red\"> Ixi! Voc� passou do seu orcamento em " + currencyFormatter.format((-(disponivel)));
		complemento += ". Voc� n�o tem mais nada dispon�vel para gastar";
		complemento += ". Or�amento para lista " + cat.getName() + ": "
			+ currencyFormatter.format(cat.getOrcamento());
		complemento+="<font color=\"black\">";
	    } else {
		complemento = " <font color=#3CB371>Voc� tem dispon�vel " + currencyFormatter.format(disponivel)
			+ ", de acordo com seu or�amento para lista " + cat.getName();
		complemento+="<font color=\"black\">";
	    }
	    return complemento;
	} catch (ListaVaziaException e) {
	    if (cat.getOrcamento() == 0.0 || cat.getOrcamento() == null) {
		return "Voc� n�o tem or�amento para esta lista, portanto, imposs�vel saber quanto ainda tem dispon�vel para compra :( . Adicione um or�amento no menu principal";
	    } else 
		return e.getMessage().isEmpty() || e.getMessage().equals("Puxa, nenhum produto foi comprado at� o momento :(") ? "Voc� ainda n�o comprou nenhum produto da lista. Ent�o voc� ainda tem "
			+ currencyFormatter.format(cat.getOrcamento()) + " dispon�vel para gastar" : e.getMessage();
	} catch (NullPointerException e) {
	    return "Voc� n�o tem or�amento para esta lista. Por isso, infelizmente, n�o � poss�vel saber o quanto voc� ainda tem dispon�vel. Adicione um or�amento no menu principal.";
	}
    }

    /**
     * @apiNote Exceptions tratadas nesse m�todo: ListaVaziaException
     */
    public static String valorEconomizado(ProductService service) {
	try {
	    double valorEconomizado = service.getValorEconomizado();
	    if (valorEconomizado < 0) {
		return "<font color=\"red\">Eita! Voc� n�o economizou nada! Voc� gastou " + currencyFormatter.format((-(valorEconomizado)))
			+ " a mais do que planejava <font color=\"black\">";
	    }else if(valorEconomizado == 0) {
		return "At� o momento, voc� est� seguindo sua lista a risca! N�o economizou nada e tamb�m n�o gastou mais do que deveria. Est� indo bem!";
	    }
	    return "<font color=#3CB371>Voc� economizou " + currencyFormatter.format(valorEconomizado) + " Parab�ns! <font color=\"black\">";
	} catch (ListaVaziaException e) {
	    return e.getMessage().isEmpty() || e.getMessage().equals("Puxa, nenhum produto foi comprado at� o momento :(") ? "Voc� ainda n�o comprou nenhum produto da lista. No momento, imposs�vel saber valor economizado :(" : e.getMessage();
	}
    }

    /**
     * @apiNote Exceptions tratadas nesse m�todo: ListaVaziaException
     */
    public static void listarNaoConcluidos(ProductService service) {
	try {
	    service.listarNaoConcluidos();
	} catch (ListaVaziaException e) {
	    System.out.println("Todos os produtos da lista foram comprados :)");
	}
    }

    /**
     * @apiNote Exceptions tratadas nesse m�todo: ListaVaziaException
     */
    public static void listarConcluidos(ProductService service) {
	try {
	    service.listarConcluidos();
	} catch (ListaVaziaException e) {
	    System.out.println("Nenhum produto da lista foi comprado :(");
	}
    }

  
    public static void mostrarSomaTotal(ProductService service) {
	System.out.println("---------------------------------------");
	System.out.println("Valor Total Estipulado: " + currencyFormatter.format(service.getValorGastoEstipulado()));
	System.out.println("Valor Total: " + currencyFormatter.format(service.getValorTotalAtual()));
    }

    public static String mostrarInfosProdutos(User user, ProductService service, double orcamento) {
	int qntProdutos = 0, qntProdutosComprados = 0;
	double valorRealGasto = 0, valorEstipulado = service.getValorGastoEstipulado();
	double valorEstipuladoRestante = 0;
	try {
	    qntProdutos = service.findAllProduct().size();
	} catch (ListaVaziaException e) {
	    qntProdutos = 0;
	}
	try {
	    qntProdutosComprados = service.getProdutosConcluidos().size();
	} catch (ListaVaziaException e) {
	    qntProdutosComprados = 0;
	}
	try {
	    valorRealGasto = service.getValorRealGasto();
	} catch (ListaVaziaException e) {
	    valorRealGasto = 0;
	}
	try {
	    valorEstipuladoRestante = service.getValorEstipuladoRestante();
	} catch (ListaVaziaException e) {
	    valorEstipuladoRestante = 0;
	}
	
	StringBuilder infos = new StringBuilder();
	infos.append(!(qntProdutos == 1) ? "Voc� possui <strong>" + qntProdutos + "</strong> produtos na lista atual" : "Voc� possui <strong>" + qntProdutos + "</strong> produto na lista atual");
	infos.append("<br>");
	infos.append(!(qntProdutosComprados == 1) ? "Voc� j� comprou <strong>" + qntProdutosComprados + "</strong> produtos de um total de <strong>" + qntProdutos + "</strong>" : "Voc� j� comprou <strong>" + qntProdutosComprados + "</strong> produto de um total de <strong>" + qntProdutos + "</strong>" );
	infos.append("<br>");
	infos.append("Voc� j� gastou <strong>" + currencyFormatter.format(valorRealGasto)+ "</strong>");
	infos.append("<br>");
	infos.append("Falta gastar <strong>" + currencyFormatter.format(valorEstipuladoRestante) + "</strong>");
	infos.append("<br>");
	infos.append("O valor estipulado atual � de <strong>" + currencyFormatter.format(valorEstipulado) + "</strong>");
	infos.append("<br>");
	infos.append("O valor total atual � de <strong>" + currencyFormatter.format(service.getValorTotalAtual()) + "</strong>");
	infos.append("<br>");
	infos.append("Or�amento: <strong>" + currencyFormatter.format(orcamento) + "</strong>");
	infos.append("<br>");
	return infos.toString();
    }
}