package negocio.impl;

import domain.Cliente;
import domain.EntidadeDominio;
import domain.Endereco;
import negocio.IStrategy;

public class ValidarEndereco implements IStrategy {
    @Override
    public String processar(EntidadeDominio entidade) {
        // A validação do endereço ocorre no contexto de um Cliente
        if (entidade instanceof Cliente) {
            Endereco endereco = ((Cliente) entidade).getEndereco();
            if (endereco != null) {
                StringBuilder sb = new StringBuilder();
                if (endereco.getLogradouro() == null || endereco.getLogradouro().trim().isEmpty()) {
                    sb.append("O logradouro é obrigatório.\n");
                }
                if (endereco.getCep() == null || endereco.getCep().trim().isEmpty()) {
                    sb.append("O CEP é obrigatório.\n");
                }
                if (endereco.getCidade() == null || endereco.getCidade().getDescricao() == null || endereco.getCidade().getDescricao().trim().isEmpty()) {
                    sb.append("A cidade é obrigatória.\n");
                }
                if (endereco.getCidade() != null && (endereco.getCidade().getEstado() == null || endereco.getCidade().getEstado().getUf() == null || endereco.getCidade().getEstado().getUf().trim().isEmpty())) {
                    sb.append("O estado (UF) é obrigatório.\n");
                }

                if (sb.length() > 0) {
                    return sb.toString();
                }
            }
        }
        return null; // Sem erros
    }
}