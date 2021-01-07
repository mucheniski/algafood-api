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
DELETE FROM item_pedido;
DELETE FROM pedido;

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

INSERT INTO cidade (id, nome, estado_id) VALUES(1, 'Londrina', 1);
INSERT INTO cidade (id, nome, estado_id) VALUES(2, 'Maringa', 1);
INSERT INTO cidade (id, nome, estado_id) VALUES(3, 'Campinas', 2);

insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, cidade_id, cep, logradouro, numero, bairro, ativo) values (1, 'Thai Gourmet', 10, 1, utc_timestamp, utc_timestamp, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro', true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo) values (2, 'Thai Delivery', 20, 1, utc_timestamp, utc_timestamp, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo) values (3, 'Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp, true);

INSERT INTO forma_pagamento (id, descricao) VALUES(1, 'Dinheiro');
INSERT INTO forma_pagamento (id, descricao) VALUES(2, 'Boleto');
INSERT INTO forma_pagamento (id, descricao) VALUES(3, 'Cartão');

INSERT INTO produto (id, ativo, descricao, nome, preco, restaurante_id) VALUES(1, false, 'Comida Tailandesa 1', 'Thai 1', 10.0, 1);
INSERT INTO produto (id, ativo, descricao, nome, preco, restaurante_id) VALUES(2, true, 'Comida Tailandesa 2', 'Thai 2', 15.0, 1);
INSERT INTO produto (id, ativo, descricao, nome, preco, restaurante_id) VALUES(3, true, 'Comida Indiana 1', 'Indi 1', 20.0, 3);

INSERT INTO permissao (id, nome, descricao) VALUES(1, 'CONSULTA_PRODUTOS', 'Permite apenas a consulta de produtos');
INSERT INTO permissao (id, nome, descricao) VALUES(2, 'GERENCIA_PRODUTOS', 'Permissão total sobre produtos');

INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES(1, 1), (1, 2), (1, 3), (2, 1), (2, 2);

INSERT INTO grupo (id, nome) VALUES(1, 'Administradores');
INSERT INTO grupo (id, nome) VALUES(2, 'Usuarios');

INSERT INTO grupo_permissao (grupo_id, permissao_id) VALUES(1, 1);
INSERT INTO grupo_permissao (grupo_id, permissao_id) VALUES(1, 2);
INSERT INTO grupo_permissao (grupo_id, permissao_id) VALUES(2, 1);

INSERT INTO usuario (id, data_cadastro, email, nome, senha) VALUES(1, utc_timestamp, 'usuario1@teste.com', 'Usuario1', '1234');
INSERT INTO usuario (id, data_cadastro, email, nome, senha) VALUES(2, utc_timestamp, 'usuario2@teste.com', 'Usuario2', '1234');

INSERT INTO usuario_grupo (usuario_id, grupo_id) VALUES(1, 1);
INSERT INTO usuario_grupo (usuario_id, grupo_id) VALUES(1, 2);
INSERT INTO usuario_grupo (usuario_id, grupo_id) VALUES(2, 2);

INSERT INTO restaurante_usuario_responsavel (restaurante_id, usuario_id) VALUES(1, 1);
INSERT INTO restaurante_usuario_responsavel (restaurante_id, usuario_id) VALUES(1, 2);
INSERT INTO restaurante_usuario_responsavel (restaurante_id, usuario_id) VALUES(2, 1);

INSERT INTO pedido (id, codigo, subtotal, taxa_frete, valor_total, data_criacao, data_confirmacao, data_cancelamento, data_entrega, restaurante_id, forma_pagamento_id, status, cidade_id, cep, logradouro, numero, complemento, bairro, usuario_cliente_id)
VALUES (1, '8e6588c2-b393-4e04-ba84-0d8a576977ae', 0, 5.0, 0, utc_timestamp, null, null, null, 1, 1, 'CRIADO', 1, 'cep1', 'logradouro1', 'numero1', 'complemento1', 'bairro1', 1);
INSERT INTO pedido (id, codigo, subtotal, taxa_frete, valor_total, data_criacao, data_confirmacao, data_cancelamento, data_entrega, restaurante_id, forma_pagamento_id, status, cidade_id, cep, logradouro, numero, complemento, bairro, usuario_cliente_id)
VALUES (2, '9789d953-cd06-44f9-9c6a-85babac394cf', 0, 5.0, 0, utc_timestamp, null, null, null, 2, 2, 'CRIADO', 2, 'cep2', 'logradouro2', 'numero2', 'complemento2', 'bairro2', 2);

INSERT INTO item_pedido (id, quantidade, preco_unitario, preco_total, observacao, produto_id, pedido_id)
VALUES(1, 1, 10.0, 0, 'Observacao1', 1, 1);
INSERT INTO item_pedido (id, quantidade, preco_unitario, preco_total, observacao, produto_id, pedido_id)
VALUES(2, 1, 10.0, 0, 'Observacao2', 2, 1);
INSERT INTO item_pedido (id, quantidade, preco_unitario, preco_total, observacao, produto_id, pedido_id)
VALUES(3, 2, 10.0, 0, 'Observacao3', 2, 2);