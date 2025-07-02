package negocio.impl;

import domain.EntidadeDominio;
import domain.Log;
import negocio.IStrategy;

public class GerarLog implements IStrategy {

    @Override
    public String processar(EntidadeDominio entidade) {

        if (entidade != null) {
            String mensagem = "Operação realizada na entidade: " + entidade.getClass().getSimpleName()
                    + ", ID: " + entidade.getId();

            Log log = new Log(mensagem);

            System.out.println("LOG GERADO: " + mensagem);
        }

        return null;
    }
}