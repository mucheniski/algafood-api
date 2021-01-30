CREATE TABLE foto_produto (
	produto_id BIGINT NOT NULL,
	nome_arquivo varchar(150) NOT NULL,
	descricao varchar(150),
	content_type varchar(80) not null,
	tamanho integer not null,
	primary key (produto_id),
	CONSTRAINT foto_produto_fk foreign key (produto_id) references produto (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;