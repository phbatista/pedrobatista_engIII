package domain; // <-- CORRIGIDO

public class Dependente extends Pessoa {
    private Parentesco parentesco;

    public Dependente(String nome, Parentesco parentesco) {
        super(nome);
        this.parentesco = parentesco;
    }

    public Parentesco getParentesco() {
        return parentesco;
    }

    public void setParentesco(Parentesco parentesco) {
        this.parentesco = parentesco;
    }
}