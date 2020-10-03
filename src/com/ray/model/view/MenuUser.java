package com.ray.model.view;

import java.util.Scanner;

import com.ray.model.entities.User;
import com.ray.model.exception.BackButtonException;
import com.ray.model.exception.OpcaoInvalidaException;
import com.ray.model.service.UserService;
import com.ray.model.interacoes.InteracaoUser;

public class MenuUser {

    public static boolean opcoesAccount(User user) {
	UserService service = new UserService();
	String opcao = null;
	Scanner scan = new Scanner(System.in);
	scan.useDelimiter(System.lineSeparator());
	while (true) {
	    System.out.println("Seu atual username �: " + user.getUsername());
	    System.out.println("Seu atual nome �: " + user.getName());
	    try {
		System.out.println("Deseja alterar algo? ");
		Menu.menuConfiguracoesConta();
		opcao = scan.next();
		switch (Integer.parseInt(opcao)) {
		case 0:
		    return false;
		case 1:
		    return encurtarVerbose(InteracaoUser.alterarNome(user, service, scan), "nome");
		case 2:
		    return encurtarVerbose(InteracaoUser.alterarUsername(user, service, scan), "username");
		case 3:
		    return encurtarVerbose(InteracaoUser.alterarSenha(user, service, scan), "senha");
		default:
		    throw new OpcaoInvalidaException("Op��o inv�lida");
		}
	    } catch (NumberFormatException e) {
		System.out.println("Entrada inv�lida! Digite apenas n�meros");
	    } catch (BackButtonException e) {
		return false;
	    } catch (OpcaoInvalidaException e) {
		System.out.println(e.getMessage());
	    }
	}
    }

    private static boolean encurtarVerbose(boolean funcao, String alteracao) {
	if (alteracao.equalsIgnoreCase("senha"))
	    alteracao = "senha alterada";
	if (funcao) {
	    System.out.println(alteracao + " alterado com sucesso!");
	    return true;
	}
	return false;
    }

}
