ALTER TABLE pedido CHANGE endereco_cidade_id cidade_id bigint(20) NOT NULL;
ALTER TABLE pedido CHANGE endereco_cep cep varchar(9) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL;
ALTER TABLE pedido CHANGE endereco_logradouro logradouro varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL;
ALTER TABLE pedido CHANGE endereco_numero numero varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL;
ALTER TABLE pedido CHANGE endereco_complemento complemento varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL;
ALTER TABLE pedido CHANGE endereco_bairro bairro varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL;
ALTER TABLE pedido CHANGE sub_total subtotal decimal(10,2) NOT NULL;