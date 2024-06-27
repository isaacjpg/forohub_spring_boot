create table topics(
    id bigint not null auto_increment,
    title varchar(100) not null,
    message varchar(500) not null,
    created_at timestamp not null default current_timestamp,
    is_closed boolean not null default false,
    user_id bigint not null,
    constraint fk_user_id foreign key(user_id) references users(id),
    primary key(id)
);
