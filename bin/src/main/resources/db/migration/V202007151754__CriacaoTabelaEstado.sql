-- Criação da tabela estado
CREATE TABLE algafood2.estado (
	id BIGINT auto_increment NOT NULL,
	nome varchar(60) NOT NULL,
	CONSTRAINT estado_PK PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;

-- Inser dos nomes na tabela estado
INSERT INTO estado(nome) SELECT DISTINCT nome_estado FROM cidade;

-- Criação da coluna estado_id na tabela cidade
ALTER TABLE cidade ADD COLUMN estado_id BIGINT NOT NULL;

-- Insert dos id's do estado na tabela cidade
UPDATE cidade  
   SET cidade.estado_id = (SELECT estado.id 
   							 FROM estado 
   						    WHERE estado.nome = cidade.nome_estado);	 

 
-- Criação da constraint de ligação na tabela cidade
ALTER TABLE	cidade ADD CONSTRAINT fk_cidade_estado
FOREIGN KEY (estado_id) REFERENCES estado(id);


-- Deletando a coluna nome_estado da tabela cidade
ALTER TABLE cidade DROP COLUMN nome_estado;

-- Renomeando a coluna nome_cidade
ALTER TABLE cidade CHANGE nome_cidade nome VARCHAR(80) NOT NULL;

