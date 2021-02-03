package com.algaworks.algafood.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;

public interface ArmazenamentoArquivosService {

    void armazenarFotoLocal(NovaFoto novaFoto);

    // Classe interna para pegar so dados da foto
    @Builder
    @Getter
    class NovaFoto {
        private String nomeArquivo;
        private InputStream inputStream;
    }

}
