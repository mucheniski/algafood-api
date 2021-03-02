package com.algaworks.algafood.service;

import com.algaworks.algafood.exception.ArmazenamentoException;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ArmazenamentoLocalServiceImpl implements ArmazenamentoService {

    @Autowired
    ArmazenamentoProperties armazenamentoProperties;

    @Override
    public void armazenarFoto(NovaFoto novaFoto) {

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

    @Override
    public FotoRecuperada recuperarFoto(String nomeFoto) {
        try {
            Path caminhoDoArquivo = buscarCaminhoDoArquivo(nomeFoto);

            FotoRecuperada fotoRecuperada =
                    FotoRecuperada.builder()
                            .inputStream(Files.newInputStream(caminhoDoArquivo))
                            .build();

            return fotoRecuperada;
        } catch (Exception e) {
            throw new ArmazenamentoException("Não foi possível recuperar arquivo.", e);
        }
    }

    private Path buscarCaminhoDoArquivo(String nomeArquivo) {
        String diretorioFotos = armazenamentoProperties.getLocal().getDiretorioFotos().toString();
        return FileSystems.getDefault().getPath(diretorioFotos, nomeArquivo);
    }

}
