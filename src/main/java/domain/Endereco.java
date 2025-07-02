package domain; // <-- CORRIGIDO

public class Endereco extends EntidadeDominio {
    private String logradouro;
    private String cep;
    private Cidade cidade;

    public Endereco(String logradouro, String cep, Cidade cidade) {
        this.logradouro = logradouro;
        this.cep = cep;
        this.cidade = cidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
}