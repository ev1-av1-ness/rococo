create table if not exists `user`
(
    id                    binary(16)   unique not null default (UUID_TO_BIN(UUID(), true)),
    username              varchar(255) unique not null,
    firstname             varchar(255),
    lastname              varchar(255),
    avatar                longblob,
    primary key (id, username)
);