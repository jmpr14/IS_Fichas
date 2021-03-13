delete FROM pedido;
delete FROM exame;
delete FROM doente;

ALTER TABLE pedido AUTO_INCREMENT = 1;

INSERT INTO doente VALUES(DEFAULT,"123456789","Antonio Silva","927777777","Rua da Universidade, n10");
INSERT INTO doente VALUES(DEFAULT,"12345","Maria Ferreira","914444444","Rua dos Chaos, n8 - Braga");

insert into exame values (DEFAULT,"","TAC");
insert into exame values (DEFAULT,"","BIOPSIA");
insert into exame values (DEFAULT,"","CARDIO");
insert into exame values (DEFAULT,"","CARDIO");

insert into pedido values (DEFAULT,NOW(),0,0,1,1,"TAC urgente.");
insert into pedido values (DEFAULT,NOW(),0,0,2,1,"Biopsia nodulo estomago.");
insert into pedido values (DEFAULT,NOW(),0,0,3,2,"Ecocardiograma urgente.");
insert into pedido values (DEFAULT,NOW(),0,0,4,2,"Prova de esforço de rotina.");