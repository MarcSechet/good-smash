create table playlist
(
    id            bigserial
        constraint playlist_pkey
            primary key,
    is_public     boolean,
    playlist_id   varchar(255)
        constraint uk_2cbvjl7skkh8n70jnh3k1ojb6
            unique,
    uploader_name varchar(255)
);

alter table playlist
    owner to postgres;

create table timecode
(
    id       bigserial
        constraint timecode_pkey
            primary key,
    song_id  varchar(255),
    timecode numeric(19, 2) not null
);

alter table timecode
    owner to postgres;

create table playlist_timecodes
(
    playlist_id  bigint not null
        constraint fk57siukrd69r44tlmmk40rcf6f
            references playlist,
    timecodes_id bigint not null
        constraint uk_cdkmexq656cnfpenudcc6cbpg
            unique
        constraint fk4isu5fwcv30o0chjip2y30sn1
            references timecode
);

alter table playlist_timecodes
    owner to postgres;
