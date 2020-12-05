CREATE TABLE cidade (
	id BIGINT auto_increment NOT NULL,
	nome_cidade varchar(80) NOT NULL,
	nome_estado varchar(80) NOT NULL,
	CONSTRAINT cidade_PK PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;

