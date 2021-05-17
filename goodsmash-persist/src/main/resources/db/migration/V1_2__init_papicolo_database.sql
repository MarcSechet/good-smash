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
