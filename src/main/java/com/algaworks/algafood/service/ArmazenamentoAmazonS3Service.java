package com.algaworks.algafood.service;

import com.algaworks.algafood.exception.ArmazenamentoException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.var;
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

    @Autowired
    private ArmazenamentoProperties armazenamentoProperties;

    @Override
    public void armazenarFoto(NovaFoto novaFoto) {

        try {
            String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());

            var objectMetadata = new ObjectMetadata();

            var putObjectRequest = new PutObjectRequest(
                    armazenamentoProperties.getAmazonS3().getNomeBucket(),
                    caminhoArquivo, novaFoto.getInputStream(),
                    objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(putObjectRequest);
        }
        catch (Exception e) {
            throw new ArmazenamentoException("Não foi possível enviar o arquivo para a Amazon S3.", e);
        }

    }

    @Override
    public void removerFotoAnterior(String nomeFotoAnterior) {
        // Para remover é preciso usar um deleteObjetRequest
    }

    @Override
    public InputStream recuperarFoto(String nomeFoto) {
        return null;
    }

    private String getCaminhoArquivo(String nomeArquivo) {
        return String.format("%s/%s", armazenamentoProperties.getAmazonS3().getDiretorioFotos(), nomeArquivo);
    }
}
