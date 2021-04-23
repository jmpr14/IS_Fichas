delete FROM desk_services.worklist;
delete FROM desk_services.pedido;
delete FROM desk_services.exame;
delete FROM desk_services.doente;

ALTER TABLE desk_services.worklist AUTO_INCREMENT = 1;
ALTER TABLE desk_services.pedido AUTO_INCREMENT = 1;
ALTER TABLE desk_services.exame AUTO_INCREMENT = 1;
ALTER TABLE desk_services.doente AUTO_INCREMENT = 1;


insert into exame values (DEFAULT,"O paciente apresentou vários nodos suspeitos que precisam de análise.","TAC");
insert into pedido values (DEFAULT,NOW(),0,1,8,5,"TAC urgente");