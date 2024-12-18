alter table subscriber
    add location_id bigint null;

alter table subscriber
    add constraint subscriber_location_id_fk
        foreign key (location_id) references location (id);