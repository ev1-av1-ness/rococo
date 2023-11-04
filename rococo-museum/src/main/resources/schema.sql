create table if not exists museum
(
    museum_id                      binary(16)  unique not null default (UUID_TO_BIN(UUID(), true)),
    title                   varchar(255) unique not null,
    description             text                not null,
    photo                   longblob            not null,
    geo_id                  binary(16)          not null,
    primary key (museum_id),
    constraint fk_museums_geo foreign key (geo_id) references `rococo-geo`.`geo`(geo_id)
);