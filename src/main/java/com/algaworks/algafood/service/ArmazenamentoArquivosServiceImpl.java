package com.algaworks.algafood.service;

import com.algaworks.algafood.exception.ArmazenamentoException;
import lombok.Builder;
import lombok.Getter;
import lombok.var;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ArmazenamentoArquivosServiceImpl implements ArmazenamentoArquivosService {

    /*
        O caminho vem do application.properties
     */
    @Value("${algafood.armazenamento.local.diretorioFotos}")
    private String diretorioFotos;

    @Override
    public void armazenarFotoLocal(NovaFoto novaFoto) {

        try {
            // Aqui concatenamos o caminho com o nome do arquivo ex. user/img/nomeArquivo,jpg
            Path caminhoDoArquivo = buscarCaminhoDoArquivo(novaFoto.getNomeArquivo());
            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(caminhoDoArquivo));
        } catch (Exception e) {
           throw new ArmazenamentoException("Erro ao armazenar arquivo", e);
        }

    }

    @Override
    public void removerFotoAnterior(String nomeFotoAnterior) {
        try {
            var caminhoArquivo = buscarCaminhoDoArquivo(nomeFotoAnterior);
            Files.deleteIfExists(caminhoArquivo);
        } catch (Exception e) {
            throw new ArmazenamentoException("Erro ao excluir arquivo", e);
        }
    }

    private Path buscarCaminhoDoArquivo(String nomeArquivo) {
        return FileSystems.getDefault().getPath(diretorioFotos, nomeArquivo);
    }

}
