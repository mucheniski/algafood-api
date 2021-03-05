package com.algaworks.algafood.storage;

import com.algaworks.algafood.exception.ArmazenamentoException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;

/*
    Deixei o service comentado porque não criei a conta da Amazon, apenas acompanhei as aulas
    O que define qual serviço é chamado é qual @Service está ativo no momento do build do projeto, o local ou o S3
    também pode ser difinido no eu uma classe de configuração
 */
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
        String caminhoArquivo = getCaminhoArquivo(nomeFotoAnterior);
        var objectMetadata = new ObjectMetadata();
        var deleteObjectRequest = new DeleteObjectRequest(caminhoArquivo, armazenamentoProperties.getAmazonS3().getIdChaveAcesso());
        amazonS3.deleteObject(deleteObjectRequest);
    }

    @Override
    public FotoRecuperada recuperarFoto(String nomeFoto) {
        String caminhoArquivo = getCaminhoArquivo(nomeFoto);

        // getUrl(bucketName, key)
        URL url = amazonS3.getUrl(armazenamentoProperties.getAmazonS3().getNomeBucket(), caminhoArquivo);

        return FotoRecuperada.builder()
                .url(url.toString())
                .build();
    }

    private String getCaminhoArquivo(String nomeArquivo) {
        return String.format("%s/%s", armazenamentoProperties.getAmazonS3().getDiretorioFotos(), nomeArquivo);
    }
}
