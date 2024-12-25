create table weather_entry
(
    id                       bigint auto_increment
        primary key,
    city_name                varchar(255) null,
    weather_response_data_id bigint       null,
    constraint weather_response_data_fk
        foreign key (weather_response_data_id) references weather_response_data (id)
            on delete cascade
);