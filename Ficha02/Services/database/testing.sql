delete FROM desk_services.worklist;
delete FROM desk_services.pedido;
delete FROM desk_services.exame;
delete FROM desk_services.doente;

ALTER TABLE desk_services.worklist AUTO_INCREMENT = 1;
ALTER TABLE desk_services.pedido AUTO_INCREMENT = 1;
ALTER TABLE desk_services.exame AUTO_INCREMENT = 1;
ALTER TABLE desk_services.doente AUTO_INCREMENT = 1;

insert into doente values (DEFAULT,"123456789","Gervásio","917722772","Braga");
insert into exame values (DEFAULT,"O paciente apresentou vários nodos suspeitos que precisam de análise.","TAC","2021-04-29","10:30");
insert into exame values (DEFAULT,"","TAC","2021-04-30","10:30");
insert into pedido values (DEFAULT,NOW(),0,1,1,1,"TAC urgente");
insert into pedido values (DEFAULT,NOW(),0,0,2,1,"TAC urgente 2");


select id_pedido, num_ep, estado, Exame_id_exame, Doente_id_doente, data, hora, descricao from Pedido, Exame WHERE Pedido.Exame_id_exame = Exame.id_exame;
SELECT id_pedido, num_ep, estado, Exame_id_exame, Doente_id_doente, data, hora, descricao FROM Pedido, Exame WHERE Pedido.Exame_id_exame = Exame.id_exame AND Exame.data = "2021-04-29";