package model.util;

import java.util.Scanner;

import model.exception.BackButtonException;

public class ButtonUtil {
    
    private static Scanner scan = new Scanner (System.in);
    
    public static void botaoVoltar(Object opcao) throws BackButtonException{
	if (opcao.equals("0") || opcao.equals(0) || opcao.equals(-1)) {
	    throw new BackButtonException();
	}
    }
    
    /**
     * 
     * @param a��o: o que voc� deseja + acao. ex: acao = excluir; sendo assim: o que voc� deseja excluir?
     * @return true caso confirme a a��o
     * @throws NumberFormatException caso n�o digite uma das op��es
     */
    public static boolean confirmar (String acao) throws NumberFormatException {
	String choose = null;
	System.out.println("Voc� tem certeza que deseja " + acao + "?");
	System.out.println("[ 1 ] - sim");
	System.out.println("[ 2 ] - n�o");
	choose = scan.next();
	return Integer.parseInt(choose) == 1;
    }
}
