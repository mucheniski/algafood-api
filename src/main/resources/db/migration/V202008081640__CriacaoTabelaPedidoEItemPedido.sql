CREATE TABLE pedido (
	id BIGINT auto_increment NOT NULL,
	sub_total DECIMAL(10,2) NOT NULL,
	taxa_frete DECIMAL(10,2) NOT NULL,
	valor_total DECIMAL(10,2) NOT NULL,
	data_criacao DATETIME NOT NULL,
	data_confirmacao DATETIME NULL,
	data_cancelamento DATETIME NULL,
	data_entrega DATETIME NULL,
	restaurante_id BIGINT NOT NULL,
	forma_pagamento_id BIGINT NOT NULL,
	status varchar(10) NULL,
	endereco_cidade_id BIGINT NOT NULL,
	endereco_cep varchar(9) NOT NULL,
	endereco_logradouro varchar(100) NOT NULL,
	endereco_numero varchar(20) NOT NULL,
	endereco_complemento varchar(100) NULL,
	endereco_bairro varchar(100) NOT NULL,
	usuario_cliente_id BIGINT NOT NULL,
	CONSTRAINT pedido_pK PRIMARY KEY (id),
	CONSTRAINT pedido_usuario_fk FOREIGN KEY (usuario_cliente_id) REFERENCES algafood2.usuario(id),
	CONSTRAINT pedido_restaurante_fk FOREIGN KEY (restaurante_id) REFERENCES algafood2.restaurante(id),
	CONSTRAINT pedido_forma_pagamento_fk FOREIGN KEY (forma_pagamento_id) REFERENCES algafood2.forma_pagamento(id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE item_pedido (
	id BIGINT auto_increment NOT NULL,
	quantidade SMALLINT NOT NULL,
	preco_unitario DECIMAL(10,2) NOT NULL,
	preco_total DECIMAL(10,2) NOT NULL,
	observacao varchar(100) NULL,
	produto_id BIGINT NOT NULL,
	pedido_id BIGINT NOT NULL,
	CONSTRAINT item_pedido_pk PRIMARY KEY (id),
    unique key uk_item_pedido_produto (pedido_id, produto_id),
	CONSTRAINT item_pedido_produto_fk FOREIGN KEY (produto_id) REFERENCES algafood2.produto(id),
	CONSTRAINT item_pedido_pedido_fk FOREIGN KEY (pedido_id) REFERENCES algafood2.pedido(id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;


