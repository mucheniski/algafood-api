package com.algaworks.algafood.storage;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface ArmazenamentoService {

    void armazenarFoto(NovaFoto novaFoto);

    void removerFotoAnterior(String nomeFotoAnterior);

    FotoRecuperada recuperarFoto(String nomeFoto);

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

    @Builder
    @Getter
    class FotoRecuperada {
        private InputStream inputStream;
        private String url;

        public boolean temUrl() {
            return url != null;
        }

        public boolean temInputstream() {
            return inputStream != null;
        }

    }

}
