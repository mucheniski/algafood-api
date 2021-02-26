package com.algaworks.algafood.service;

import org.springframework.stereotype.Service;

import java.io.InputStream;

/*
    Deixei o service comentado porque não criei a conta da Amazon, apenas acompanhei
    as aulas
 */
// @Service
public class ArmazenamentoAmazonS3Service implements ArmazenamentoService{
    @Override
    public void armazenarFotoLocal(NovaFoto novaFoto) {

    }

    @Override
    public void removerFotoAnterior(String nomeFotoAnterior) {

    }

    @Override
    public InputStream recuperarFoto(String nomeFoto) {
        return null;
    }
}
