<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Consulta de Clientes</title>
    <style>
        body { font-family: sans-serif; margin: 20px; }
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        a { color: #007bff; text-decoration: none; }
        a:hover { text-decoration: underline; }
        .container { max-width: 1000px; margin: auto; }
        .btn { display: inline-block; padding: 10px 15px; background-color: #007bff; color: white; border-radius: 4px; margin-top: 20px;}
    </style>
</head>
<body>
<div class="container">
    <h2>Clientes Cadastrados</h2>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>CPF</th>
            <th>Crédito (R$)</th>
            <th>Endereço</th>
            <th>Ações</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="cliente" items="${requestScope.clientes}">
            <tr>
                <td>${cliente.id}</td>
                <td>${cliente.nome}</td>
                <td>${cliente.cpf}</td>
                <td>${cliente.credito}</td>
                <td>
                        ${cliente.endereco.logradouro}, ${cliente.endereco.cidade.descricao} - ${cliente.endereco.cidade.estado.uf}
                </td>
                <td>
                    <a href="CtrlCliente?operacao=editar&id=${cliente.id}">Editar</a> |
                    <a href="CtrlCliente?operacao=excluir&id=${cliente.id}" onclick="return confirm('Tem certeza que deseja excluir este cliente?');">Excluir</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br>
    <a href="index.html" class="btn">Novo Cadastro</a>
</div>
</body>
</html>