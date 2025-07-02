package domain;

public class Log extends EntidadeDominio {
    private String descricao;

    public Log() {
    }

    public Log(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}