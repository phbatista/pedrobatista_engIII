<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Editar Cliente</title>
    <style>
        body { font-family: sans-serif; background-color: #f4f7f6; margin: 0; padding: 20px; }
        .container { max-width: 600px; margin: 40px auto; padding: 30px; background-color: white; border: 1px solid #e0e0e0; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.05); }
        h2, h3 { color: #333; border-bottom: 2px solid #007bff; padding-bottom: 10px; margin-bottom: 20px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: 600; color: #555; }
        input[type="text"], input[type="number"] { width: 100%; padding: 10px; box-sizing: border-box; border: 1px solid #ccc; border-radius: 4px; }
        button { padding: 12px 20px; background-color: #28a745; color: white; border: none; cursor: pointer; border-radius: 4px; font-size: 16px; margin-top: 10px; }
        button:hover { background-color: #218838; }
    </style>
</head>
<body>

<div class="container">
    <h2>Editar Cliente</h2>
    <%-- O formulário agora aponta para a operação 'alterar' --%>
    <form action="CtrlCliente" method="post">
        <input type="hidden" name="operacao" value="alterar">

        <%-- Campos escondidos para guardar os IDs --%>
        <input type="hidden" name="id" value="${cliente.id}">
        <input type="hidden" name="enderecoId" value="${cliente.endereco.id}">
        <input type="hidden" name="cidadeId" value="${cliente.endereco.cidade.id}">
        <input type="hidden" name="estadoId" value="${cliente.endereco.cidade.estado.id}">

        <h3>Dados Pessoais</h3>
        <div class="form-group">
            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="txtNome" value="${cliente.nome}" required>
        </div>
        <div class="form-group">
            <label for="cpf">CPF:</label>
            <input type="text" id="cpf" name="txtCpf" value="${cliente.cpf}" required>
        </div>
        <div class="form-group">
            <label for="credito">Crédito:</label>
            <input type="number" step="0.01" id="credito" name="txtCredito" value="${cliente.credito}" required>
        </div>

        <h3>Endereço</h3>
        <div class="form-group">
            <label for="logradouro">Logradouro:</label>
            <input type="text" id="logradouro" name="txtLogradouro" value="${cliente.endereco.logradouro}" required>
        </div>
        <div class="form-group">
            <label for="cep">CEP:</label>
            <input type="text" id="cep" name="txtCep" value="${cliente.endereco.cep}" required>
        </div>
        <div class="form-group">
            <label for="cidade">Cidade:</label>
            <input type="text" id="cidade" name="txtCidade" value="${cliente.endereco.cidade.descricao}" required>
        </div>
        <div class="form-group">
            <label for="estado">Estado:</label>
            <input type="text" id="estado" name="txtEstado" value="${cliente.endereco.cidade.estado.descricao}" required>
        </div>
        <div class="form-group">
            <label for="uf">UF:</label>
            <input type="text" id="uf" name="txtUf" value="${cliente.endereco.cidade.estado.uf}" required maxlength="2">
        </div>

        <hr>
        <button type="submit">Salvar Alterações</button>
    </form>
</div>

</body>
</html>