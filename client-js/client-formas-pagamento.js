function altertPage() {
    alert("Page is loaded!");
}

function consultar() {
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

        var linkAcao = $("<a href='#'>")
                            .text("Excluir")
                            .click(function(event) {
                                event.preventDefault();
                                excluir(formaPagemento);
                            });

        linha.append(
            $("<td>").text(formaPagemento.id),
            $("<td>").text(formaPagemento.descricao),
            $("<td>").append(linkAcao)
        );

        linha.appendTo("#tabela");
    });
}

function cadastrar() {
    var formaPagamentoJSON = JSON.stringify({
        "descricao": $("#campo-descricao").val()
    });

    console.log(formaPagamentoJSON);

    $.ajax({
        url: "http://localhost:8080/formas-pagamento",
        type: "post",
        data: formaPagamentoJSON,
        contentType: "application/json",

        success: function(response) {
            alert("Cadastrado com sucesso!");
            consultar();
        },

        error: function(error) {
            if (error.status == 400) {
                var problema = JSON.parse(error.responseText);
                alert(problema.mensagemParaUsuario);
            } else {
                alert("Erro ao cadastrar!");
            }
        }
    });
}

function excluir(formaPagemento) {

    $.ajax({
        url: "http://localhost:8080/formas-pagamento/" + formaPagemento.id,
        type: "delete",

        success: function(response) {
            alert("Excluído com sucesso!");
            consultar();
        },

        error: function(error) {
            var problema = JSON.parse(error.responseText);
            alert(problema.mensagemParaUsuario);
        }
    });

}


//$("#btn-consultar").click(consultar());
//$("#btn-cadastrar").click(cadastrar());
//$("#btn-excluir").click(excluir())

