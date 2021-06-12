alter table confirm_details add constraint fk_character foreign key (target_id) references character (id);
