create table favorites (

    id integer not null,

    user_id integer not null,

    book_id integer not null,

    foreign key (user_id) references users (id) on delete cascade,

    foreign key (book_id) references stored_books (id) on delete cascade

);