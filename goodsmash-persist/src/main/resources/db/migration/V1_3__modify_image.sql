alter table my_image add column character_id bigint;
alter table my_image add column type varchar(25);
alter table my_image drop column data;
alter table my_image add column data bytea;
