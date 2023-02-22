create table if not exists authors(
    id SERIAL primary key,
    name varchar(50) not null,
    gender char(1) not null,
    country varchar(50) not null
)