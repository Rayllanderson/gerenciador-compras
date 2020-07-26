package model.util;

import java.util.Scanner;

import model.entities.User;
import model.exception.BackButtonException;
import model.exception.MyLoginException;
import model.service.UserService;

public class UserUtil {

    public static String formatarNome(String nome) {
	String[] name = nome.split(" ");
	return name[0].substring(0, 1).toUpperCase().concat(name[0].substring(1).toLowerCase());
    }

    /**
     * {@code} System.out.print(pedido + ": "); <br>
     * <br>
     * return scan.next();
     */
    public static String pedirAlgo(Scanner scan, String pedido) {
	System.out.print(pedido + ": ");
	return scan.next().trim();
    }

    public static boolean alterarNome(User user, UserService service, Scanner scan) throws BackButtonException {
	System.out.println("pressione 0 para cancelar");
	String nome = pedirAlgo(scan, "novo nome");
	ButtonUtil.botaoVoltar(nome);
	user.setName(nome);
	service.updateSenhaOuNome(user);
	return true;
    }

    public static boolean alterarSenha(User user, UserService service, Scanner scan) throws BackButtonException {
	String password = pedirAlgo(scan, "Para verificar que � voc�, digite sua senha atual");
	try {
	    if (service.verificarSenha(user, password)) {
		System.out.println("pressione 0 para cancelar");
		password = pedirAlgo(scan, "Nova Senha");
		ButtonUtil.botaoVoltar(password);
		user.setPassword(password);
		service.updateSenhaOuNome(user);
		return true;
	    }
	} catch (MyLoginException e) {
	    System.out.println(e.getMessage());
	}
	return false;
    }
    
    public static boolean alterarUsername(User user, UserService service, Scanner scan) throws BackButtonException {
	System.out.println("pressione 0 para cancelar");
	String nome = null;
	nome = pedirAlgo(scan, "novo username");
	ButtonUtil.botaoVoltar(nome);
	user.setUsername(nome);
	try{
	    service.alterarUsername(user);
	    return true;
	}catch (MyLoginException e) {
	   System.out.println(e.getMessage());
	   return false;
	}
    }

}
