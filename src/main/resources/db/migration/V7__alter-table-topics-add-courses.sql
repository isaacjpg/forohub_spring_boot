alter table courses add course_id bigint;
alter table courses add constraint fk_course_id foreign key(course_id) references courses(id);