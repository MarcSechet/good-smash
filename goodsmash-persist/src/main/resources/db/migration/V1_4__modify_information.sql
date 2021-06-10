alter table information drop column target_id;
alter table information add column target_id bigint;
alter table information add constraint uk_1 unique (description, information_type, target_id);
alter table information add constraint uk_2 unique (character_id,description, information_type);
