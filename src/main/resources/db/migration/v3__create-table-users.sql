create table users (

    id integer not null,

    username character varying (255) not null,

    email character varying (255) unique not null,

    password character varying (255) not null,

    birth_date date not null,

    primary key (id)

);