ALTER TABLE restaurante ADD aberto TINYINT(1) default false NOT NULL;
update restaurante set aberto = true;
