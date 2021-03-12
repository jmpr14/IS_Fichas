delete FROM desk_services.pedido;
delete FROM desk_services.exame;

ALTER TABLE doente AUTO_INCREMENT = 1;


insert into exame values (DEFAULT,"O paciente apresentou vários nodos suspeitos que precisam de análise.","TAC");
insert into pedido values (DEFAULT,NOW(),0,1,8,5,"TAC urgente");