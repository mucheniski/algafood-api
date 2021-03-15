function consultarFormasPagamento() {
    $.ajax({
        url: "http://localhost:8080/formas-pagamento",
        type: "get",

        success: function(response) {
            preencherTabela(response);
        }
    });
}

function preencherTabela(formasPagemento) {
    $("#tabela tbody tr").remove();

    $.each(formasPagemento, function(i, formaPagemento){
        var linha = $("<tr>");

        linha.append(
            $("<td>").text(formaPagemento.id),
            $("<td>").text(formaPagemento.descricao)
        );

        linha.appendTo("#tabela");
    });

}

function cadastrarFormaPagamento() {
    var formaPagamentoJSON = JSON.stringify({
        "descricao": $("campo-descricao").val()
    });

    console.log(formaPagamentoJSON);

    $.ajax({
        url: "http://localhost:8080/formas-pagamento",
        type: "post",
        data: formaPagamentoJSON,
        contentType: "application/json",

        success: function(response) { 
            alert("Cadastrado com sucesso!");
            consultarFormasPagamento();
        },


        error: function(error) {
            if (error.status == 400) {
                var problema = JSON.parse(error.responseText);
                alert(problema.mensagemParaUsuario);
            } else {
                alert("Erro ao cadastrar!")
            }
        }

    });

}

$("#btn-consultar").click(consultarFormasPagamento());
$("#btn-cadastrar").click(cadastrarFormaPagamento());