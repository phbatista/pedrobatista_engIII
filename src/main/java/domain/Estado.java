package domain;

public class Estado extends EntidadeDominio {
    private String descricao;
    private String uf;

    public Estado(String descricao, String uf) {
        this.descricao = descricao;
        this.uf = uf;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}