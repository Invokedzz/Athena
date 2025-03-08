create table user_roles (

    user_id integer not null,

    role_id integer not null,

    primary key (user_id, role_id),

    foreign key (user_id) references users (id),

    foreign key (role_id) references roles (id)

);