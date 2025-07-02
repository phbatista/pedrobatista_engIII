package negocio.impl;

import domain.Cliente;
import domain.Dependente;
import domain.EntidadeDominio;
import negocio.IStrategy;

public class ValidarDependentes implements IStrategy {
    @Override
    public String processar(EntidadeDominio entidade) {
        if (entidade instanceof Cliente) {
            Cliente cliente = (Cliente) entidade;
            if (cliente.getDeps() != null && !cliente.getDeps().isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (Dependente dep : cliente.getDeps()) {
                    if (dep.getNome() == null || dep.getNome().trim().isEmpty()) {
                        sb.append("O nome do dependente é obrigatório.\n");
                    }
                    if (dep.getParentesco() == null || dep.getParentesco().getDescricao() == null || dep.getParentesco().getDescricao().trim().isEmpty()) {
                        sb.append("O parentesco do dependente '").append(dep.getNome()).append("' é obrigatório.\n");
                    }
                }
                if (sb.length() > 0) {
                    return sb.toString();
                }
            }
        }
        return null;
    }
}