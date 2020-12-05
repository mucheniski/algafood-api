SET FOREIGN_KEY_CHECKS=0;

DELETE FROM cidade;
DELETE FROM cozinha;
DELETE FROM estado;
DELETE FROM forma_pagamento;
DELETE FROM grupo;
DELETE FROM grupo_permissao;
DELETE FROM permissao;
DELETE FROM produto;
DELETE FROM restaurante;
DELETE FROM restaurante_forma_pagamento;
DELETE FROM usuario;
DELETE FROM usuario_grupo;

SET FOREIGN_KEY_CHECKS=1;

alter table cidade auto_increment = 1;
alter table cozinha auto_increment = 1;
alter table estado auto_increment = 1;
alter table forma_pagamento auto_increment = 1;
alter table grupo auto_increment = 1;
alter table permissao  auto_increment = 1;
alter table produto auto_increment = 1;
alter table restaurante auto_increment = 1;
alter table usuario  auto_increment = 1;

INSERT INTO cozinha (id, nome) VALUES (1, 'Tailandesa');
INSERT INTO cozinha (id, nome) VALUES (2, 'Indiana');

INSERT INTO estado (id, nome) VALUES(1, 'Parana');
INSERT INTO estado (id, nome) VALUES(2, 'Sao Paulo');

INSERT INTO cidade (nome, estado_id) VALUES('Londrina', 1);
INSERT INTO cidade (nome, estado_id) VALUES('Maringa', 1);
INSERT INTO cidade (nome, estado_id) VALUES('Campinas', 2);

insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, cidade_id, cep, logradouro, numero, bairro, ativo) values (1, 'Thai Gourmet', 10, 1, utc_timestamp, utc_timestamp, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro', true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo) values (2, 'Thai Delivery', 9.50, 1, utc_timestamp, utc_timestamp, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo) values (3, 'Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp, true);

INSERT INTO forma_pagamento (descricao) VALUES('Dinheiro');
INSERT INTO forma_pagamento (descricao) VALUES('Boleto');
INSERT INTO forma_pagamento (descricao) VALUES('Cartão');

INSERT INTO produto (ativo, descricao, nome, preco, restaurante_id) VALUES(true, 'Comida Tailandesa 1', 'Thai 1', 10.0, 1);
INSERT INTO produto (ativo, descricao, nome, preco, restaurante_id) VALUES(true, 'Comida Tailandesa 2', 'Thai 2', 15.0, 1);
INSERT INTO produto (ativo, descricao, nome, preco, restaurante_id) VALUES(true, 'Comida Indiana 1', 'Indi 1', 20.0, 3);

INSERT INTO permissao (descricao, nome) VALUES('consulta produtos', 'Permite apenas a consulta de produtos');
INSERT INTO permissao (descricao, nome) VALUES('gerencia produtos', 'Permissão total sobre produtos');

INSERT INTO restaurante_forma_pagamento (rastaurante_id, forma_pagamento_id) VALUES(1, 1), (1, 2), (1, 3), (2, 1), (2, 2);

INSERT INTO grupo (nome) VALUES('Administradores');
INSERT INTO grupo (nome) VALUES('Usuarios');

INSERT INTO usuario (data_cadastro, email, nome, senha) VALUES(utc_timestamp, 'usuario1@teste.com', 'Usuario1', '1234');
INSERT INTO usuario (data_cadastro, email, nome, senha) VALUES(utc_timestamp, 'usuario2@teste.com', 'Usuario2', '1234');