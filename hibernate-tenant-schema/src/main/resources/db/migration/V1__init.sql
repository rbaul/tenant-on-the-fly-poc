create sequence hibernate_sequence start with 1 increment by 1;
create table customer (id bigint not null, name varchar(255), address varchar(255), family_name varchar(255), version bigint, primary key (id));
