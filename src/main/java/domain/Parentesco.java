package domain; // <-- CORRIGIDO

public class Parentesco extends EntidadeDominio {
    private String descricao;

    public Parentesco(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}