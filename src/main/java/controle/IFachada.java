package controle;

import domain.EntidadeDominio;

import java.util.List;

public interface IFachada {

    public String salvar(EntidadeDominio entidade);

    public String alterar(EntidadeDominio entidade);

    public String excluir(EntidadeDominio entidade);

    public List<EntidadeDominio> consultar(EntidadeDominio entidade);
}