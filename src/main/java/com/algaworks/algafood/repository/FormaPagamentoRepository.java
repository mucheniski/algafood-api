package com.algaworks.algafood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.entity.FormaPagamento;

import java.time.OffsetDateTime;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

    @Query("select max(dataAtualizacao) from FormaPagamento")
    OffsetDateTime getDataUltimaAtualizacao();

}