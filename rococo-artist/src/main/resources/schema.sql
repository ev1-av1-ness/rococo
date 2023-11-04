create table if not exists artist
(
    artist_id               binary(16)  unique not null default (UUID_TO_BIN(UUID(), true)),
    name                    varchar(255) unique not null,
    biography               text                not null,
    photo                   longblob            not null,
    primary key (artist_id)
);