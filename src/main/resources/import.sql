INSERT INTO cozinha (id, nome) VALUES (1, 'Tailandesa');
INSERT INTO cozinha (id, nome) VALUES (2, 'Indiana');



INSERT INTO algafood.estado (id, nome) VALUES(1, 'Parana');
INSERT INTO algafood.estado (id, nome) VALUES(2, 'Sao Paulo');



INSERT INTO algafood.cidade (nome, estado_id) VALUES('Londrina', 1);
INSERT INTO algafood.cidade (nome, estado_id) VALUES('Maringa', 1);
INSERT INTO algafood.cidade (nome, estado_id) VALUES('Campinas', 2);



INSERT INTO algafood.restaurante (bairro, cep, complemento, logradouro, nome, taxa_frete, cozinha_id, cidade_id) VALUES('Centro', '8600000', 'Complemento', 'Logradouro', 'Tailandes', 10.0, 1, 2);
INSERT INTO algafood.restaurante (bairro, cep, complemento, logradouro, nome, taxa_frete, cozinha_id, cidade_id) VALUES('Bairro', '8600000', 'Complemento', 'Logradouro', 'Indiano', 11.0, 2, 3);



INSERT INTO algafood.forma_pagamento (descricao) VALUES('Dinheiro');
INSERT INTO algafood.forma_pagamento (descricao) VALUES('Boleto');
INSERT INTO algafood.forma_pagamento (descricao) VALUES('Cartão');



INSERT INTO algafood.permissao (descricao, nome) VALUES('consulta produtos', 'Permite apenas a consulta de produtos');
INSERT INTO algafood.permissao (descricao, nome) VALUES('gerencia produtos', 'Permissão total sobre produtos');



INSERT INTO algafood.restaurante_forma_pagamento (rastaurante_id, forma_pagamento_id) VALUES(1, 1), (1, 2), (1, 3), (2, 1), (2, 2);
