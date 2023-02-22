create table if not exists categories(
    id SERIAL primary key,
    name varchar(50) not null,
    description varchar(100) not null,
    constraint unq_name unique(name)
)