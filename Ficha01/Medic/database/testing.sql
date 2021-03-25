delete FROM pedido;
delete FROM exame;
delete FROM doente;

ALTER TABLE pedido AUTO_INCREMENT = 1;
ALTER TABLE exame AUTO_INCREMENT = 1;
ALTER TABLE doente AUTO_INCREMENT = 1;

INSERT INTO doente VALUES(DEFAULT,"123456789","Jose Ferreira","911111111","Rua da Universidade");
INSERT INTO doente VALUES(DEFAULT,"12345","Antonio Silva","999999999","Rua dos Chaos, n8");

insert into exame values (DEFAULT,"","CARDIO");
insert into exame values (DEFAULT,"","RADIO");
insert into exame values (DEFAULT,"","QUIMIO");

insert into pedido values (DEFAULT,NOW(),0,0,1,1,"ecocardiograma e prova de esforço");
insert into pedido values (DEFAULT,NOW(),0,0,2,1,"radiografia urgente!");
insert into pedido values (DEFAULT,NOW(),0,2,3,2,"");