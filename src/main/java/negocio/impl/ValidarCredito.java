package negocio.impl;

import domain.Cliente;
import domain.EntidadeDominio;
import negocio.IStrategy;

public class ValidarCredito implements IStrategy {
    private static final double CREDITO_MINIMO = 1000.0;

    @Override
    public String processar(EntidadeDominio entidade) {
        if (entidade instanceof Cliente) {
            Cliente cliente = (Cliente) entidade;
            if (cliente.getCredito() < CREDITO_MINIMO) {
                return "Crédito deve ser de no mínimo R$ " + CREDITO_MINIMO;
            }
        }
        return null;
    }
}