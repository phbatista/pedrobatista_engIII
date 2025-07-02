<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Consulta de Clientes</title>
</head>
<body>
<div class="container">
    <h2>Clientes Cadastrados</h2>
    <p><a href="index.html">Cadastrar Novo Cliente</a></p>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>CPF</th>
            <th>Endereço</th>
            <th>Ações</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="cliente" items="${clientes}">
            <tr>
                <td>${cliente.id}</td>
                <td>${cliente.nome}</td>
                <td>${cliente.cpf}</td>
                <td>${cliente.endereco.logradouro}, ${cliente.endereco.cidade.descricao} - ${cliente.endereco.cidade.estado.uf}</td>
                <td>
                    <a href="#">Alterar</a>
                    |
                    <a href="CtrlCliente?operacao=excluir&id=${cliente.id}">Excluir</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>