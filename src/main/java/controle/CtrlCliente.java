package controle;

import domain.*; // Importa todas as classes do pacote domain
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CtrlCliente extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CtrlCliente() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operacao = request.getParameter("operacao");
        IFachada fachada = new Fachada();

        if ("excluir".equals(operacao)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Cliente cliente = new Cliente(null, null, 0, null, null);
            cliente.setId(id);
            fachada.excluir(cliente);
            response.sendRedirect("CtrlCliente?operacao=consultar");

        } else if ("consultar".equals(operacao)) {
            // A consulta foi movida para o doGet para facilitar o redirecionamento
            List<EntidadeDominio> clientes = fachada.consultar(new Cliente(null, null, 0, null, null));
            request.setAttribute("clientes", clientes);
            request.getRequestDispatcher("/consultar.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operacao = request.getParameter("operacao");

        // CORREÇÃO APLICADA AQUI:
        IFachada fachada = new Fachada();

        if ("salvar".equals(operacao)) {
            // Dados do Cliente
            String nome = request.getParameter("txtNome");
            String cpf = request.getParameter("txtCpf");
            double credito = Double.parseDouble(request.getParameter("txtCredito"));

            // Dados do Endereço
            String logradouro = request.getParameter("txtLogradouro");
            String cep = request.getParameter("txtCep");
            String nomeCidade = request.getParameter("txtCidade");
            String nomeEstado = request.getParameter("txtEstado");
            String uf = request.getParameter("txtUf");

            // Dados dos Dependentes
            String[] nomesDep = request.getParameterValues("txtDepNome");
            String[] cpfsDep = request.getParameterValues("txtDepCpf");
            String[] parentescosDep = request.getParameterValues("txtDepParentesco");

            List<Dependente> dependentes = new ArrayList<>();
            if (nomesDep != null) {
                for (int i = 0; i < nomesDep.length; i++) {
                    Parentesco p = new Parentesco(parentescosDep[i]);
                    Dependente d = new Dependente(nomesDep[i], p);
                    d.setCpf(cpfsDep[i]);
                    dependentes.add(d);
                }
            }

            // Montando os objetos
            Estado estado = new Estado(nomeEstado, uf);
            Cidade cidade = new Cidade(nomeCidade, estado);
            Endereco endereco = new Endereco(logradouro, cep, cidade);

            Cliente cliente = new Cliente(nome, cpf, credito, endereco, dependentes);

            // Salva o cliente e redireciona para a tela de consulta
            fachada.salvar(cliente);
            response.sendRedirect("CtrlCliente?operacao=consultar");

        } else {
            response.getWriter().append("Operação inválida ou não implementada.");
        }
    }
}