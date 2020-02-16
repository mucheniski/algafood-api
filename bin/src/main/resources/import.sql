INSERT INTO cozinha (id, nome) VALUES (1, 'Tailandesa');
INSERT INTO cozinha (id, nome) VALUES (2, 'Indiana');


INSERT INTO restaurante (nome, taxa_frete, cozinha_id) VALUES('Restaurante Tailandes', 10.0, 1);
INSERT INTO restaurante (nome, taxa_frete, cozinha_id) VALUES('Restaurante Indiano', 20.0, 2);


INSERT INTO algafood.forma_pagamento (descricao) VALUES('Dinheiro');
INSERT INTO algafood.forma_pagamento (descricao) VALUES('Boleto');
INSERT INTO algafood.forma_pagamento (descricao) VALUES('Cartão');


INSERT INTO algafood.permissao (descricao, nome) VALUES('consulta produtos', 'Permite apenas a consulta de produtos');
INSERT INTO algafood.permissao (descricao, nome) VALUES('gerencia produtos', 'Permissão total sobre produtos');

INSERT INTO algafood.estado (id, nome) VALUES(1, 'Parana');
INSERT INTO algafood.estado (id, nome) VALUES(2, 'Sao Paulo');


INSERT INTO algafood.cidade (nome, estado_id) VALUES('Londrina', 1);
INSERT INTO algafood.cidade (nome, estado_id) VALUES('Maringa', 1);
INSERT INTO algafood.cidade (nome, estado_id) VALUES('Campinas', 2);