package controle;

import domain.*;
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
            List<EntidadeDominio> clientes = fachada.consultar(new Cliente(null, null, 0, null, null));
            request.setAttribute("clientes", clientes);
            request.getRequestDispatcher("/consultar.jsp").forward(request, response);

        } else if ("editar".equals(operacao)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Cliente clienteParaBuscar = new Cliente(null, null, 0, null, null);
            clienteParaBuscar.setId(id);

            List<EntidadeDominio> resultado = fachada.consultar(clienteParaBuscar);

            if (resultado != null && !resultado.isEmpty()) {
                request.setAttribute("cliente", resultado.get(0));
                request.getRequestDispatcher("/editar.jsp").forward(request, response);
            } else {
                response.sendRedirect("CtrlCliente?operacao=consultar&erro=ClienteNaoEncontrado");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operacao = request.getParameter("operacao");
        IFachada fachada = new Fachada();

        if ("salvar".equals(operacao)) {
            String nome = request.getParameter("txtNome");
            String cpf = request.getParameter("txtCpf");
            double credito = Double.parseDouble(request.getParameter("txtCredito"));

            String logradouro = request.getParameter("txtLogradouro");
            String cep = request.getParameter("txtCep");
            String nomeCidade = request.getParameter("txtCidade");
            String nomeEstado = request.getParameter("txtEstado");
            String uf = request.getParameter("txtUf");

            String[] nomesDep = request.getParameterValues("txtDepNome");
            String[] cpfsDep = request.getParameterValues("txtDepCpf");
            String[] parentescosDep = request.getParameterValues("txtDepParentesco");

            List<Dependente> dependentes = new ArrayList<>();

            if (nomesDep != null) {
                for (int i = 0; i < nomesDep.length; i++) {
                    Parentesco p = new Parentesco(parentescosDep[i]);
                    Dependente d = new Dependente(nomesDep[i], p);

                    if (cpfsDep[i] != null && !cpfsDep[i].isEmpty()) {
                        d.setCpf(cpfsDep[i]);
                    }
                    dependentes.add(d);
                }
            }

            Estado estado = new Estado(nomeEstado, uf);
            Cidade cidade = new Cidade(nomeCidade, estado);
            Endereco endereco = new Endereco(logradouro, cep, cidade);

            Cliente cliente = new Cliente(nome, cpf, credito, endereco, dependentes);

            fachada.salvar(cliente);
            response.sendRedirect("CtrlCliente?operacao=consultar");


        } else if ("alterar".equals(operacao)) {
            String nome = request.getParameter("txtNome");
            String cpf = request.getParameter("txtCpf");
            double credito = Double.parseDouble(request.getParameter("txtCredito"));

            String[] nomesDep = request.getParameterValues("txtDepNome");
            String[] cpfsDep = request.getParameterValues("txtDepCpf");
            String[] parentescosDep = request.getParameterValues("txtDepParentesco");

            List<Dependente> dependentes = new ArrayList<>();
            if (nomesDep != null) {
                for (int i = 0; i < nomesDep.length; i++) {
                    Parentesco p = new Parentesco(parentescosDep[i]);
                    Dependente d = new Dependente(nomesDep[i], p);
                    if (cpfsDep[i] != null && !cpfsDep[i].isEmpty()) {
                        d.setCpf(cpfsDep[i]);
                    }
                    dependentes.add(d);
                }
            }

            String logradouro = request.getParameter("txtLogradouro");
            String cep = request.getParameter("txtCep");
            String nomeCidade = request.getParameter("txtCidade");
            String nomeEstado = request.getParameter("txtEstado");
            String uf = request.getParameter("txtUf");

            int clienteId = Integer.parseInt(request.getParameter("id"));
            int enderecoId = Integer.parseInt(request.getParameter("enderecoId"));
            int cidadeId = Integer.parseInt(request.getParameter("cidadeId"));
            int estadoId = Integer.parseInt(request.getParameter("estadoId"));

            Estado estado = new Estado(nomeEstado, uf);
            estado.setId(estadoId);

            Cidade cidade = new Cidade(nomeCidade, estado);
            cidade.setId(cidadeId);

            Endereco endereco = new Endereco(logradouro, cep, cidade);
            endereco.setId(enderecoId);

            Cliente cliente = new Cliente(nome, cpf, credito, endereco, dependentes); // Dependentes não são editados neste formulário
            cliente.setId(clienteId);

            fachada.alterar(cliente);
            response.sendRedirect("CtrlCliente?operacao=consultar");

        } else {
            response.getWriter().append("Operação inválida ou não implementada.");
        }
    }
}