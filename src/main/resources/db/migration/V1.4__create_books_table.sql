create table if not exists books(
    id SERIAL primary key,
    title varchar(100) not null,
    synopsis text not null,
    author_id integer not null,
    category_id integer not null,
    constraint fk_book_author foreign key (author_id) references authors(id),
    constraint fk_book_category foreign key (category_id) references categories(id),
    constraint unq_book_title unique(title)
)