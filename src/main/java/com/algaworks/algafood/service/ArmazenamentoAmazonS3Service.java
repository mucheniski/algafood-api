package com.algaworks.algafood.service;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/*
    Deixei o service comentado porque não criei a conta da Amazon, apenas acompanhei
    as aulas
 */
// @Service
public class ArmazenamentoAmazonS3Service implements ArmazenamentoService{

    @Autowired
    private AmazonS3 amazonS3;

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
