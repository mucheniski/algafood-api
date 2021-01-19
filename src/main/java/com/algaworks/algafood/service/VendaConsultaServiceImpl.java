package com.algaworks.algafood.service;

import com.algaworks.algafood.dto.VendaDiariaDTO;
import com.algaworks.algafood.entity.Pedido;
import com.algaworks.algafood.filtro.VendaDiariaFiltro;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

/*
    Anotado como Repository porque é um componente que o spring traduz algumas exceptions do JPA para
    exceptions de persistencia do spring.
 */
@Repository
public class VendaConsultaServiceImpl implements VendaConsultasService {

    /*
        Consultas com criteria API
     */
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendaDiariaDTO> consultarVendasDiarias(VendaDiariaFiltro vendaDiariaFiltro) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<VendaDiariaDTO> query= builder.createQuery(VendaDiariaDTO.class);
        Root<Pedido> root = query.from(Pedido.class);

        /*
            Cria uma expressão para criar uma função de banco de dados no Java
         */
        Expression<Date> funcaoDateSQLDataCriacao = builder.function("date", Date.class, root.get("dataCriacao"));

        /*
            Criando o sql referente a esse em Criteria API
            select date(data_criacao) as data_criacao,
                   count(id) as total_vendas,
                   sum(valor_total) as total_faturado
            from pedido
            group by date(data_criacao);

            O builder do criteria vai fazer com que quando seja retornado um VendaDiariaDTO
            o construtor da classe seja chamado, por isso tem o @AllArgsConstructor do lombok na classe
            o construtor precisa estar nesta mesma ordem (dataCriacao, totalVendas, totalFaturado
         */
        CompoundSelection<VendaDiariaDTO> consulta =
                builder.construct(VendaDiariaDTO.class,
                  funcaoDateSQLDataCriacao,
                  builder.count(root.get("id")),
                  builder.sum(root.get("valorTotal"))
                );

        query.select(consulta);
        query.groupBy(funcaoDateSQLDataCriacao);

        return manager.createQuery(query).getResultList();

    }
}
