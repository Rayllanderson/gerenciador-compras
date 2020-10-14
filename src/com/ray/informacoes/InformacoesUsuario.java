package com.ray.informacoes;

import java.text.NumberFormat;

import com.ray.model.entities.User;
import com.ray.model.util.CalculoTotal;
import com.ray.model.util.UserUtil;


public class InformacoesUsuario {
    
    private UserUtil util;
    
    public InformacoesUsuario(User user) {
	util = new UserUtil(user);
    }
    
    private static NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
    
    // ---------------------------------------------------------------------//
    public static void mostrarInfosConsole(User user) {
	NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
	CalculoTotal ct = new CalculoTotal(user);
	System.out.println("Ol�, " + UserUtil.formatarNome(user.getName()) + "!");
	System.out.println("Voc� possui " + ct.numeroTotalCategorias() + " listas no total");
	System.out.println("Voc� j� comprou " + ct.numTotalProdutosComprados() + " produtos de um total de " + ct.numTotalProdutos());
	System.out.println("Voc� j� gastou "+ currencyFormatter.format(ct.totalValorReal()));
	System.out.println("Voc� pretendia gastar " + currencyFormatter.format( ct.totalEstipulado()));
    }

    
    public int getNumeroTotalCategorias() {
	return util.getNumeroTotalCategorias();
    }
    
    public int getNumTotalProdutosComprados() {
	return util.getNumTotalProdutosComprados();
    }
    
    public int getNumTotalProdutos() {
	return util.getNumTotalProdutos();
    }
    
    public String getTotalReal() {
	return currencyFormatter.format(util.getTotalReal());
    }

    public String getTotalEstipulado() {
	return currencyFormatter.format(util.getTotalEstipulado());
    }
}
