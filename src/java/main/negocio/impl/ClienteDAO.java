package java.main.negocio.impl;

import java.main.domain.EntidadeDominio;
import java.main.persistencia.IDAO;

public class ClienteDAO implements IDAO {
    // ATENÇÃO: É aqui que você vai escrever o código JDBC para
    // conectar ao banco, criar os comandos SQL (INSERT, UPDATE, etc)
    // e executá-los. O projeto do professor deixou isso em branco
    // para VOCÊ preencher!

    @Override
    public void salvar(EntidadeDominio entidade) {
        System.out.println("SQL INSERT para o cliente seria executado aqui.");
    }
    // Implementar os outros métodos...
}
