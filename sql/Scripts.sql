select date(data_criacao) as data_criacao,
       count(id) as total_vendas,
       sum(valor_total) as total_faturado
from pedido
group by date(data_criacao);

select convert_tz('1986-12-27 09:00:00','+00:00','-03:00');

-- Com conversão de timezone
-- CONVERT_TZ (dt, from_tz,to_tz)
select date(convert_tz(p.data_criacao, '+00:00', '-03:00')) as data_criacao,
       count(p.id) as total_vendas,
       sum(p.valor_total) as total_faturado
from pedido p
group by date(convert_tz(p.data_criacao, '+00:00', '-03:00'));

select * from pedido;