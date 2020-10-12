package com.ray.model.validacoes;

import com.ray.model.exception.EntradaInvalidaException;

public class ValidacaoProduto {

    /**
     * Valida os campos Nome e pre�o estipulado
     * 
     * @param Nome
     * @throws EntradaInvalidaException lan�ada quando o campo Nome estiver em branco ou nulo
     */
    public static void validarNome(String nome) throws EntradaInvalidaException {
	if (nome.trim().isEmpty() || nome == null) {
	    throw new EntradaInvalidaException("O campo 'Nome' n�o pode ser nulo");
	}
    }
    
    /**
     * @param value
     * @return 0 caso o valor esteja nulo, senao, retorna o pr�prio valor
     */
    public static Double validarPreco(Double value) {
	return value == null ? 0.0 : value;
    }
}
