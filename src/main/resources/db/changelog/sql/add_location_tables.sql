create table country
(
    id   bigint auto_increment
        primary key,
    name varchar(255) null
);

create table city
(
    country_id bigint       null,
    id         bigint auto_increment
        primary key,
    name       varchar(255) null,
    constraint FKrpd7j1p7yxr784adkx4pyepba
        foreign key (country_id) references country (id)
);

create table location
(
    city_id    bigint null,
    country_id bigint null,
    id         bigint auto_increment
        primary key,
    constraint FKn5m6ve3ryy2r25qvisdrg0aos
        foreign key (country_id) references country (id),
    constraint FKr2gdhhu8rhyrvslukhtfbg8v5
        foreign key (city_id) references city (id)
);