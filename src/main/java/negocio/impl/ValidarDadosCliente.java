package negocio.impl;

import domain.Cliente;
import domain.EntidadeDominio;
import negocio.IStrategy;

public class ValidarDadosCliente implements IStrategy {
    @Override
    public String processar(EntidadeDominio entidade) {
        if (entidade instanceof Cliente) {
            Cliente cliente = (Cliente) entidade;
            StringBuilder sb = new StringBuilder();

            if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
                sb.append("O nome do cliente é obrigatório.\n");
            }
            if (cliente.getEndereco() == null) {
                sb.append("O endereço do cliente é obrigatório.\n");
            }

            if (sb.length() > 0) {
                return sb.toString();
            }
        }
        return null;
    }
}