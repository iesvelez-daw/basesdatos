drop database if exists BD;
create database BD;
use BD;

create table personas (
  id     INTEGER primary key auto_increment,
  nombre VARCHAR(50),
  edad   INTEGER
);

insert into personas values (default, "Ana", 21);
insert into personas values (default, "Eva", 22);
insert into personas values (default, "Ian", 23);
