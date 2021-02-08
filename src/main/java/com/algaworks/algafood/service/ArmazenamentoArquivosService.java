package com.algaworks.algafood.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface ArmazenamentoArquivosService {

    void armazenarFotoLocal(NovaFoto novaFoto);

    void removerFotoAnterior(String nomeFotoAnterior);

    /*
        Default significa que não é só a assinatura do método e sim a implementação também
     */
    default String gerarNovoNome(String nomeOriginal) {
        return UUID.randomUUID().toString() + "_" + nomeOriginal;
    }

    // Classe interna para pegar so dados da foto
    @Builder
    @Getter
    class NovaFoto {
        private String nomeArquivo;
        private InputStream inputStream;
    }

}
