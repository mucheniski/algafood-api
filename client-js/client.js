function consultarRestaurantes() {
    $.ajax({
        url: "http://localhost:8080/restaurantes",
        type: "get",
        
        success: function(response) {
            $("#conteudo").text(JSON.stringify(response));
        }
    });
}

function fecharRestaurante() {
    $.ajax({
        url: "http://localhost:8080/restaurantes/1/desativar",
        type: "put",

        success: function(response) {
            alert("Restaurante desativado!")
        }
    });
}

$("#botao").click(consultarRestaurantes);
