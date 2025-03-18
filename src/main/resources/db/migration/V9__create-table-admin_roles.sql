create table admin_roles (

    admin_id integer not null,

    role_id integer not null,

    primary key (admin_id, role_id),

    foreign key (admin_id) references users (id),

    foreign key (role_id) references roles (id)

);