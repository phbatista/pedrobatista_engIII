package persistencia.impl;

import domain.*;
import persistencia.IDAO;
import utils.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements IDAO {

    @Override
    public void salvar(EntidadeDominio entidade) {
        if (!(entidade instanceof Cliente)) return;
        Cliente cliente = (Cliente) entidade;
        Endereco endereco = cliente.getEndereco();
        Cidade cidade = endereco.getCidade();
        Estado estado = cidade.getEstado();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Inicia a transação
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            // 1. Salva o Estado e recupera o ID
            String sqlEstado = "INSERT INTO estados (nome, uf) VALUES (?, ?)";
            pstmt = conn.prepareStatement(sqlEstado, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, estado.getDescricao());
            pstmt.setString(2, estado.getUf());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                estado.setId(rs.getInt(1));
            }
            rs.close();
            pstmt.close();

            // 2. Salva a Cidade e recupera o ID
            String sqlCidade = "INSERT INTO cidades (nome, estado_id) VALUES (?, ?)";
            pstmt = conn.prepareStatement(sqlCidade, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, cidade.getDescricao());
            pstmt.setInt(2, estado.getId());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                cidade.setId(rs.getInt(1));
            }
            rs.close();
            pstmt.close();

            // 3. Salva o Endereço e recupera o ID
            String sqlEndereco = "INSERT INTO enderecos (logradouro, cep, cidade_id) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sqlEndereco, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, endereco.getLogradouro());
            pstmt.setString(2, endereco.getCep());
            pstmt.setInt(3, cidade.getId());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                endereco.setId(rs.getInt(1));
            }
            rs.close();
            pstmt.close();

            // 4. Salva o Cliente e recupera o ID
            String sqlCliente = "INSERT INTO clientes (nome, cpf, credito, dt_cadastro, endereco_id) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sqlCliente, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getCpf());
            pstmt.setDouble(3, cliente.getCredito());
            pstmt.setTimestamp(4, new Timestamp(cliente.getDtCadastro().getTime()));
            pstmt.setInt(5, endereco.getId());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                cliente.setId(rs.getInt(1));
            }
            rs.close();
            pstmt.close();

            // 5. Salva os Dependentes
            if (cliente.getDeps() != null && !cliente.getDeps().isEmpty()) {
                String sqlDependente = "INSERT INTO dependentes (nome, cpf, dt_cadastro, cliente_id, parentesco_id) VALUES (?, ?, ?, ?, ?)";

                for (Dependente dep : cliente.getDeps()) {
                    Parentesco p = dep.getParentesco();
                    String sqlParentesco = "INSERT INTO parentescos (descricao) VALUES (?) ON CONFLICT (descricao) DO NOTHING";
                    pstmt = conn.prepareStatement(sqlParentesco, Statement.RETURN_GENERATED_KEYS);
                    pstmt.setString(1, p.getDescricao());
                    pstmt.executeUpdate();
                    pstmt.close();

                    String sqlSelectParentesco = "SELECT id FROM parentescos WHERE descricao = ?";
                    pstmt = conn.prepareStatement(sqlSelectParentesco);
                    pstmt.setString(1, p.getDescricao());
                    rs = pstmt.executeQuery();
                    if(rs.next()){
                        p.setId(rs.getInt("id"));
                    }
                    rs.close();
                    pstmt.close();

                    pstmt = conn.prepareStatement(sqlDependente);
                    pstmt.setString(1, dep.getNome());
                    pstmt.setString(2, dep.getCpf());
                    pstmt.setTimestamp(3, new Timestamp(dep.getDtCadastro().getTime()));
                    pstmt.setInt(4, cliente.getId());
                    pstmt.setInt(5, p.getId());
                    pstmt.executeUpdate();
                    pstmt.close();
                }
            }

            conn.commit();

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void alterar(EntidadeDominio entidade) {
        if (!(entidade instanceof Cliente)) return;
        Cliente cliente = (Cliente) entidade;
        String sql = "UPDATE clientes SET nome=?, cpf=?, credito=? WHERE id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getCpf());
            pstmt.setDouble(3, cliente.getCredito());
            pstmt.setInt(4, cliente.getId()); // Usa o ID para saber qual cliente alterar
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Dentro da classe persistencia.impl.ClienteDAO

    @Override
    public void excluir(EntidadeDominio entidade) {
        if (!(entidade instanceof Cliente)) return;
        Cliente cliente = (Cliente) entidade;

        // Graças ao "ON DELETE CASCADE" no banco, só precisamos apagar o cliente.
        // O banco se encarregará de apagar o endereço e os dependentes associados.
        String sql = "DELETE FROM clientes WHERE id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(true); // Para operações simples, autocommit pode ser usado.
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, cliente.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        // Este SQL complexo junta todas as tabelas para trazer os dados completos do cliente.
        String sql = "SELECT " +
                "cli.id as cliente_id, cli.nome as cliente_nome, cli.cpf, cli.credito, cli.dt_cadastro as cliente_dt_cadastro, " +
                "e.id as endereco_id, e.logradouro, e.cep, " +
                "c.id as cidade_id, c.nome as cidade_nome, " +
                "est.id as estado_id, est.nome as estado_nome, est.uf " +
                "FROM clientes cli " +
                "JOIN enderecos e ON cli.endereco_id = e.id " +
                "JOIN cidades c ON e.cidade_id = c.id " +
                "JOIN estados est ON c.estado_id = est.id " +
                "ORDER BY cli.id";

        List<EntidadeDominio> clientes = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                // Monta o objeto Estado
                Estado estado = new Estado(rs.getString("estado_nome"), rs.getString("uf"));
                estado.setId(rs.getInt("estado_id"));

                // Monta o objeto Cidade
                Cidade cidade = new Cidade(rs.getString("cidade_nome"), estado);
                cidade.setId(rs.getInt("cidade_id"));

                // Monta o objeto Endereco
                Endereco endereco = new Endereco(rs.getString("logradouro"), rs.getString("cep"), cidade);
                endereco.setId(rs.getInt("endereco_id"));

                // Monta o objeto Cliente
                Cliente cliente = new Cliente(
                        rs.getString("cliente_nome"),
                        rs.getString("cpf"),
                        rs.getDouble("credito"),
                        endereco,
                        null // A lista de dependentes será carregada em uma consulta separada
                );
                cliente.setId(rs.getInt("cliente_id"));
                cliente.setDtCadastro(rs.getTimestamp("cliente_dt_cadastro"));

                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Bloco finally para fechar os recursos
        }
        return clientes;
    }
}