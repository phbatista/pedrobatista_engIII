package negocio.impl;

import domain.Cliente;
import domain.EntidadeDominio;
import negocio.IStrategy;

public class ValidarCpf implements IStrategy {
    @Override
    public String processar(EntidadeDominio entidade) {
        if (entidade instanceof Cliente) {
            Cliente cliente = (Cliente) entidade;
            if (cliente.getCpf() != null && cliente.getCpf().length() != 11) {
                return "CPF deve ter 11 digitos";
            }
        }
        return null;
    }
}