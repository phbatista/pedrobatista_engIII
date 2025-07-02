package java.main.domain;

import java.util.List;

public class Cliente extends Pessoa {
    private double credito;
    private Endereco endereco;
    private List<Dependente> deps; // Lista de dependentes

    public Cliente(double credito, Endereco endereco, List<Dependente> deps) {
        this.credito = credito;
        this.endereco = endereco;
        this.deps = deps;
    }

    public double getCredito() {
        return credito;
    }

    public void setCredito(double credito) {
        this.credito = credito;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Dependente> getDeps() {
        return deps;
    }

    public void setDeps(List<Dependente> deps) {
        this.deps = deps;
    }
}