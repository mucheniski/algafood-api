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

$("#btn-consultar").click(consultarFormasPagamento());