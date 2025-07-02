package negocio.impl; // <-- CORRIGIDO

import domain.EntidadeDominio;
import negocio.IStrategy;
import java.util.Date;

public class ComplementarDtCadastro implements IStrategy {
    @Override
    public String processar(EntidadeDominio entidade) {
        if (entidade != null) {
            entidade.setDtCadastro(new Date());
        }
        return null;
    }
}