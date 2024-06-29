create table replies(
    id bigint not null auto_increment,
    message varchar(500) not null,
    topic_id bigint not null,
    created_at timestamp not null default current_timestamp,
    user_id bigint not null,
    is_solution boolean not null default false,

    constraint fk_replies_user_id foreign key(user_id) references users(id),
    constraint fk_replies_topic_id foreign key(topic_id) references topics(id),
    primary key(id)
);
