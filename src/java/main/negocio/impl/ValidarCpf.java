package java.main.negocio.impl;

import java.main.domain.Cliente;
import java.main.domain.EntidadeDominio;
import java.main.negocio.IStrategy;

public class ValidarCpf implements IStrategy {
    @Override
    public String processar(EntidadeDominio entidade) {
        Cliente cliente = (Cliente)entidade;
        if(cliente.getCpf().length() != 11)
            return "CPF deve ter 11 digitos"; // Mensagem de erro
        return null; // Sem erro
    }
}
