package controle; // <-- CORRIGIDO

// Imports Corrigidos
import domain.Cliente;
import domain.EntidadeDominio;
import negocio.IStrategy;
import negocio.impl.ComplementarDtCadastro;
import negocio.impl.ValidarCpf;
import negocio.impl.ValidarCredito;
import persistencia.IDAO;
import persistencia.impl.ClienteDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fachada implements IFachada {

    private Map<String, List<IStrategy>> rns;
    private Map<String, IDAO> daos;

    public Fachada() {
        rns = new HashMap<>();
        daos = new HashMap<>();

        // Configuração para a entidade Cliente
        List<IStrategy> regrasCliente = new ArrayList<>();
        regrasCliente.add(new ValidarCpf());
        regrasCliente.add(new ValidarCredito());
        regrasCliente.add(new ComplementarDtCadastro());

        rns.put(Cliente.class.getName(), regrasCliente);
        daos.put(Cliente.class.getName(), new ClienteDAO());

        // Aqui você poderia adicionar regras e DAOs para outras entidades...
    }

    @Override
    public String salvar(EntidadeDominio entidade) {
        String nmClasse = entidade.getClass().getName();
        List<IStrategy> regras = rns.get(nmClasse);
        StringBuilder sb = new StringBuilder();

        for (IStrategy s : regras) {
            String msg = s.processar(entidade);
            if (msg != null) {
                sb.append(msg);
                sb.append("\n");
            }
        }

        if (sb.length() == 0) {
            IDAO dao = daos.get(nmClasse);
            dao.salvar(entidade);
            return "SALVO COM SUCESSO!";
        } else {
            return sb.toString();
        }
    }

    @Override
    public String alterar(EntidadeDominio entidade) {
        return "Operação Alterar ainda não implementada.";
    }

    @Override
    public String excluir(EntidadeDominio entidade) {
        String nmClasse = entidade.getClass().getName();
        IDAO dao = daos.get(nmClasse);
        dao.excluir(entidade);
        return "EXCLUÍDO COM SUCESSO!";
    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        String nmClasse = entidade.getClass().getName();
        IDAO dao = daos.get(nmClasse);
        return dao.consultar(entidade);
    }
}