create table admins (

    id serial not null primary key,

    name character varying (255) unique not null,

    email character varying (255) unique not null,

    password character varying (255) not null

);