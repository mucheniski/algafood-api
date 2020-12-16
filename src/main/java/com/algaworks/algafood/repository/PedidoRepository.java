package com.algaworks.algafood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.entity.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
