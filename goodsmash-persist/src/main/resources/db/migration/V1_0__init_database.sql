create table confirm_details_dto
(
    id                   bigint not null
        constraint confirm_details_dto_pkey
            primary key,
    comment              varchar(255),
    kill_percent_rage0   integer,
    kill_percent_rage100 integer,
    kill_percent_rage150 integer,
    kill_percent_rage50  integer,
    target_id            bigint
);

alter table confirm_details_dto
    owner to postgres;

create table image
(
    id   bigint not null
        constraint image_pkey
            primary key,
    data oid
);

alter table image
    owner to postgres;

create table character
(
    id               bigint  not null
        constraint character_pkey
            primary key,
    character_weight varchar(255),
    is_floaty        boolean not null,
    name             varchar(255)
        constraint uk_tkj83ytk14o17nvecy96gjik0
            unique,
    skill_rating     integer default 0,
    tier             varchar(255),
    icon_id          bigint
        constraint fk7e8pv2qbu6ttr5u9xi83rxsp2
            references image,
    image_id         bigint
        constraint fk9y001ebso7y1ajuevjtjs7k4t
            references image
);

alter table character
    owner to postgres;

create table best_move_dto
(
    id           bigint not null
        constraint best_move_dto_pkey
            primary key,
    description  varchar(1200),
    name         varchar(255),
    video_url    varchar(255),
    character_id bigint
        constraint fkit7owibv7q364dpag7c5bdql9
            references character,
    constraint ukpsas9ywk1fpm6d8mtefev7nps
        unique (name, character_id)
);

alter table best_move_dto
    owner to postgres;

create table character_additional_filters
(
    character_id       bigint not null
        constraint fkaaukxk34muhb3tu2497l4ffxn
            references character,
    additional_filters varchar(255)
);

alter table character_additional_filters
    owner to postgres;

create table combo_dto
(
    id           bigint not null
        constraint combo_dto_pkey
            primary key,
    description  varchar(255),
    max_percent  integer,
    min_percent  integer,
    name         varchar(255),
    video_url    varchar(255),
    character_id bigint
        constraint fkppwm0evamioncy8w1uetuela5
            references character,
    constraint ukacag5k828wbpn98cxpaueoojt
        unique (name, character_id)
);

alter table combo_dto
    owner to postgres;

create table combo_dto_additional_filters
(
    combo_dto_id       bigint not null
        constraint fkh5wykny07l1tld8q0jcvdo8rn
            references combo_dto,
    additional_filters varchar(255)
);

alter table combo_dto_additional_filters
    owner to postgres;

create table confirm_dto
(
    id           bigint not null
        constraint confirm_dto_pkey
            primary key,
    comment      varchar(255),
    description  varchar(255),
    name         varchar(1200),
    character_id bigint
        constraint fkgq18nvpcqxop8siy97d906d7t
            references character,
    constraint uk2bcqxshojhjqx2va1x0r8167n
        unique (name, character_id)
);

alter table confirm_dto
    owner to postgres;

create table confirm_dto_confirm_details_dtos
(
    confirm_dto_id          bigint not null
        constraint fkqsbo0x51hvnpbnq8mrghwldmn
            references confirm_dto,
    confirm_details_dtos_id bigint not null
        constraint uk_ie22ll1emc0ndku1gu98bxpwc
            unique
        constraint fkku265rxp15q5h8t8yl3tenrme
            references confirm_details_dto
);

alter table confirm_dto_confirm_details_dtos
    owner to postgres;

create table information_dto
(
    id               bigint not null
        constraint information_dto_pkey
            primary key,
    description      varchar(1200),
    information_type varchar(255),
    target_id        bigint not null,
    character_id     bigint
        constraint fkg2595e3vc1h0a0s0c07a6awce
            references character
);

alter table information_dto
    owner to postgres;

create table move
(
    id bigint not null
        constraint move_pkey
            primary key
);

alter table move
    owner to postgres;

create table combo_dto_moves
(
    combo_dto_id bigint not null
        constraint fkq37eg9trt923n11txk497d7vu
            references combo_dto,
    moves_id     bigint not null
        constraint uk_r13qw9bsgrp4qpq6ybyvitgrb
            unique
        constraint fkhcpr7y6n0a49yh8g0qvysh3oh
            references move
);

alter table combo_dto_moves
    owner to postgres;

create table move_inputs
(
    move_id bigint not null
        constraint fkd8cp2lbmm10arfr2lsdpt5a0i
            references move,
    inputs  varchar(255)
);

alter table move_inputs
    owner to postgres;

create table playlist
(
    id            bigint not null
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

create table question
(
    id                   bigint  not null
        constraint question_pkey
            primary key,
    category             varchar(255),
    key                  varchar(255),
    nb_players           integer not null,
    parent_key           varchar(255),
    question             varchar(1200),
    question_from_picolo boolean not null,
    question_type        varchar(255),
    constraint ukkokebguw4srtl9vfexa5pg9jb
        unique (key, parent_key, question, category, question_type)
);

alter table question
    owner to postgres;

create table question_children
(
    question_id bigint not null
        constraint fk7qagmstsnsxf7o4y4m2q21uqe
            references question,
    children_id bigint not null
        constraint uk_q3xi9do5b38k3ply30wlh2umr
            unique
        constraint fkbtpy8jy2kj6akv9uit8r2fhlb
            references question
);

alter table question_children
    owner to postgres;

create table tier
(
    id    bigint not null
        constraint tier_pkey
            primary key,
    color varchar(10) default '#000000'::character varying,
    name  varchar(255)
        constraint uk_41thf79xhgrvjrsjgafbifysh
            unique,
    rank  integer
);

alter table tier
    owner to postgres;

create table tier_character_ids
(
    tier_id       bigint not null
        constraint fkkmct95wbeu26rhg7no4dcx6wv
            references tier,
    character_ids bigint
);

alter table tier_character_ids
    owner to postgres;

create table tierlist
(
    id           bigint not null
        constraint tierlist_pkey
            primary key,
    description  varchar(255),
    name         varchar(255),
    character_id bigint
        constraint fkrx7ksiasvej08xe04k0rsj6qq
            references character,
    constraint uknybo6jmqklc30574x7gjgd7f4
        unique (name, character_id)
);

alter table tierlist
    owner to postgres;

create table tierlist_tiers
(
    tierlist_id bigint not null
        constraint fkbi8sp9ce990b4fxpn69qvpb0k
            references tierlist,
    tiers_id    bigint not null
        constraint uk_i610g1p4tqq2pslc8rks8f9qi
            unique
        constraint fk4o1lhxe0p3kgglervqovdnjud
            references tier
);

alter table tierlist_tiers
    owner to postgres;

create table tierlist_unused_character_ids
(
    tierlist_id          bigint not null
        constraint fk3vae409nkrhih116mq59f52mb
            references tierlist,
    unused_character_ids bigint
);

alter table tierlist_unused_character_ids
    owner to postgres;

create table timecode
(
    id       bigint         not null
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

create table user_dto
(
    id          bigint  not null
        constraint user_dto_pkey
            primary key,
    email       varchar(255)
        constraint uk_5w5p18accndkchona47memqvo
            unique,
    is_approved boolean not null
);

alter table user_dto
    owner to postgres;

create table youtube_song
(
    id            bigint  not null
        constraint youtube_song_pkey
            primary key,
    artist_name   varchar(255),
    is_public     boolean not null,
    thumbnail_url varchar(255),
    title         varchar(255),
    uploader_name varchar(255),
    url           varchar(255)
        constraint uk_2500lxft1blwys68fo6xsyd1b
            unique
);

alter table youtube_song
    owner to postgres;

create table youtube_song_genres
(
    youtube_song_id bigint not null
        constraint fk2d2sfixx3ktjyvxi4nrfr6vq0
            references youtube_song,
    genres          varchar(255)
);

alter table youtube_song_genres
    owner to postgres;

create table youtube_song_timecodes
(
    youtube_song_id bigint not null
        constraint fkoyt0asc88oiesjnfcrkdy7am5
            references youtube_song,
    timecodes       integer
);

alter table youtube_song_timecodes
    owner to postgres;

