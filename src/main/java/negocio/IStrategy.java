package negocio; // <-- CORRIGIDO

import domain.EntidadeDominio;

public interface IStrategy {
    public String processar(EntidadeDominio entidade);
}