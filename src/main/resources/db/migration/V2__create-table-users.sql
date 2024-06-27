create table users(
    id bigint not null auto_increment,
    name varchar(100) not null,
    email varchar(100) not null unique,
    password varchar(100) not null,
    profile_id bigint not null,
    constraint fk_users_profile_id foreign key(profile_id) references profiles(id),
    primary key(id)
);