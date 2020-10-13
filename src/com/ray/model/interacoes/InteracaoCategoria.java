package com.ray.model.interacoes;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.ray.model.entities.Categoria;
import com.ray.model.entities.User;
import com.ray.model.exception.BackButtonException;
import com.ray.model.exception.CategoriaException;
import com.ray.model.exception.CategoriaInexistenteException;
import com.ray.model.exception.ConfirmException;
import com.ray.model.exception.EntradaInvalidaException;
import com.ray.model.exception.ListaVaziaException;
import com.ray.model.exception.OpcaoInvalidaException;
import com.ray.model.service.CategoriaServiceConsole;
import com.ray.model.util.ButtonUtil;

public class InteracaoCategoria {
    
    /**
     * @return categoria selecionada
     * @throws BackButtonException
     * @throws NumberFormatException
     * @throws OpcaoInvalidaException
     * @apiNote Exceptions tratadas nesse m�todo: CategoriaException,
     *          ListaVaziaException
     */
    public static Categoria selecionarCategoria(CategoriaServiceConsole service, Scanner scan)
	    throws BackButtonException, NumberFormatException, OpcaoInvalidaException {
	System.out.println("Pressione 0 para voltar");
	try {
	    service.listarCategoriasConsole();
	    String num = scan.next();
	    ButtonUtil.botaoVoltar(num);
	    return service.getCategoriaByNumber(Integer.parseInt(num));
	} catch (ListaVaziaException e) {
	    throw new OpcaoInvalidaException(e.getMessage());
	} catch (CategoriaException e) {
	    throw new OpcaoInvalidaException(e.getMessage());
	}
    }

    /**
     * Exceptions tratados nesse m�todo: EntradaInvalidaException
     * 
     * @throws BackButtonException
     * @throws NumberFormatException
     */
    public static void adicionarCategoria(CategoriaServiceConsole service, User user)
	    throws BackButtonException, NumberFormatException {
	Scanner scan = new Scanner(System.in);
	scan.useDelimiter(System.lineSeparator());
	try {
	    Categoria novaCategoria = new Categoria();
	    System.out.println("*-*-*-*- Criando nova Lista *-*-*-*-");
	    String nome = getNome(scan);
	    novaCategoria.setName(nome);
	    novaCategoria.setUser(user);
	    if (service.save(novaCategoria) != null) {
		System.out.println("Deseja adicionar um or�amento para essa Lista?");
		System.out.println("[ 1 ] - sim");
		System.out.println("[ 2 ] - n�o");
		String op = scan.next();
		if (Integer.parseInt(op) == 1) {
		    inserirOrcamento(service, novaCategoria);
		}
		System.out.println("Nova lista criada! Selecione a lista no menu principal");
	    }
	} catch (EntradaInvalidaException e) {
	    System.out.println(e.getMessage());
	}
    }

    /**
     * {@summary M�todo pra perguntar o nome e receber nome de volta<br>
     * <br>
     * }
     * 
     * @param scan
     * @return nome digitado
     * @throws BackButtonException
     */
    private static String getNome(Scanner scan) throws BackButtonException {
	System.out.println("Pressione 0 para voltar");
	System.out.print("Nome: ");
	String nome = scan.next();
	ButtonUtil.botaoVoltar(nome);
	return nome;
    }

    /**
     * @throws NumberFormatException
     * @throws BackButtonException
     * @apiNote Exceptions tratadas nesse m�todo: ListaVaziaException,
     *          CategoriaException, ConfirmException
     */
    public static void deletarCategoria(CategoriaServiceConsole service) throws NumberFormatException, BackButtonException {
	@SuppressWarnings("resource")
	Scanner scan = new Scanner(System.in);
	scan.useDelimiter(System.lineSeparator());
	try {
	    System.out.println("Selecione a lista que deseja Excluir: ");
	    System.out.println("Pressione 0 para voltar");
	    service.listarCategoriasConsole();
	    String num = scan.next();
	    ButtonUtil.botaoVoltar(num);
	    Categoria cat1 = service.getCategoriaByNumber(Integer.parseInt(num));
	    ButtonUtil.confirmar("deletar a lista " + cat1.getName());
	    if (service.deletarCategoria(cat1))
		System.out.println("Lista deletada com sucesso!");
	} catch (ListaVaziaException e) {
	    System.out.println(e.getMessage());
	} catch (CategoriaException e) {
	    System.out.println(e.getMessage());
	} catch (ConfirmException e) {
	    System.out.println("Lista n�o deletada.");
	}
    }

    /**
     * @throws EntradaInvalidaException j� com mensagem pronta
     * @apiNote Exceptions tratadas nesse m�todo: ListaVaziaException,
     *          CategoriaException, ConfirmException
     */
    private static void inserirOrcamento(CategoriaServiceConsole service, Categoria cat) throws EntradaInvalidaException {
	@SuppressWarnings("resource")
	Scanner scan = new Scanner(System.in);
	try {
	    System.out.print("Valor do or�amento para a lista " + cat.getName() + ": ");
	    double value = scan.nextDouble();
	    service.inserirOrcamento(cat, value);
	} catch (InputMismatchException e) {
	    throw new EntradaInvalidaException("Digite um valor v�lido");
	}
    }

    /**
     * @param oqVaiSerEditado exemplo: "nome" editar� o nome. <br>
     *                        <br>
     *                        Op��es dispo�veis para editar: <Strong>nome,
     *                        orcamento, tudo<Strong><br>
     *                        <br>
     * @throws BackButtonException
     * @throws NumberFormatException
     * @apiNote Exceptions tratadas nesse m�todo: EntradaInvalidaException,
     *          ConfirmException
     */
    public static void editarTudoCategoria(CategoriaServiceConsole service, String oqVaiSerEditado, Categoria cat)
	    throws BackButtonException, NumberFormatException {
	@SuppressWarnings("resource")
	Scanner scan = new Scanner(System.in);
	try {
	    if (oqVaiSerEditado.equalsIgnoreCase("nome")) {
		String name;
		System.out.print("Novo nome: ");
		name = scan.next();
		ButtonUtil.botaoVoltar(name);
		ButtonUtil.confirmar("renomear");
		cat.setName(name);
	    }

	    if (oqVaiSerEditado.equalsIgnoreCase("orcamento")) {
		inserirOrcamento(service, cat);
	    }
	    if (oqVaiSerEditado.equalsIgnoreCase("tudo")) {
		String name;
		System.out.print("Novo nome: ");
		name = scan.next();
		ButtonUtil.botaoVoltar(name);
		ButtonUtil.confirmar("alterar os valores");
		cat.setName(name);
		inserirOrcamento(service, cat);
	    }
	    if (service.update(cat))
		System.out.println("Lista editada com sucesso!");
	    else
		System.out.println("Ocorreu um erro ao editar. Tente novamente");
	} catch (ConfirmException e) {
	    System.out.println("Lista n�o atualizada.");
	} catch (EntradaInvalidaException e) {
	    System.out.println(e.getMessage());
	} catch (CategoriaInexistenteException e) {
	    e.printStackTrace();
	}
    }

    /**
     * @param cat
     * @apiNote Exceptions tratadas nesse m�todo: EntradaInvalidaException
     */
    public static void adicionarOrcamento(CategoriaServiceConsole service, Categoria cat) {
	try {
	    inserirOrcamento(service, cat);
	} catch (EntradaInvalidaException e) {
	    System.out.println(e.getMessage());
	}
    }

}
