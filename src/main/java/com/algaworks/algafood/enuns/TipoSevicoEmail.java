package com.algaworks.algafood.enuns;

import lombok.Getter;

@Getter
public enum TipoSevicoEmail {
    SMTP("smtp"),
    MOCK("mock");

    private String descricao;

    TipoSevicoEmail(String descricao) {
        this.descricao = descricao;
    }
}
