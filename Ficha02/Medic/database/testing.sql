delete FROM pedido;
delete FROM exame;
delete FROM doente;
delete FROM worklist;

ALTER TABLE pedido AUTO_INCREMENT = 1;
ALTER TABLE exame AUTO_INCREMENT = 1;
ALTER TABLE doente AUTO_INCREMENT = 1;
ALTER TABLE worklist AUTO_INCREMENT = 1;


insert into doente values (DEFAULT,"123456789","Gervásio","917722772","Braga");
insert into exame values (DEFAULT,"O paciente apresentou vários nodos suspeitos que precisam de análise.","TAC","2021-04-29","10:30");
insert into exame values (DEFAULT,"","TAC","2021-04-30","10:30");
insert into pedido values (DEFAULT,NOW(),0,1,1,1,"TAC urgente");
insert into pedido values (DEFAULT,NOW(),0,0,2,1,"TAC urgente 2");

/*
INSERT INTO doente VALUES(DEFAULT,"123456789","Jose Ferreira","911111111","Rua da Universidade");
INSERT INTO doente VALUES(DEFAULT,"12345","Antonio Silva","999999999","Rua dos Chaos, n8");

insert into exame values (DEFAULT,"","CARDIO","2021-04-29","10:00");
insert into exame values (DEFAULT,"","RADIO","2021-04-30","10:30");
insert into exame values (DEFAULT,"","QUIMIO","2021-04-23","16:00");

insert into pedido values (DEFAULT,NOW(),0,0,1,1,"ecocardiograma e prova de esforço");
insert into pedido values (DEFAULT,NOW(),0,0,2,1,"radiografia urgente!");
insert into pedido values (DEFAULT,NOW(),0,2,3,2,"");
*/