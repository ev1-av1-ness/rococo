create table if not exists painting
(
    id                      binary(16)  unique not null default (UUID_TO_BIN(UUID(), true)),
    title                   varchar(255) unique not null,
    description             text                not null,
    content                 longblob            not null,
    museum_id               binary(16)          not null,
    artist_id               binary(16)          not null,
    primary key (id),
    constraint fk_paintings_museums foreign key(museum_id) references `rococo-museum`.`museum` (museum_id),
    constraint fk_paintings_artists foreign key (artist_id) references `rococo-artist`.`artist` (artist_id)
);