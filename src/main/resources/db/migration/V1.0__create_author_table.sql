create table author(
    id bigint(20) AUTO_INCREMENT,
    first_name varchar(20),
    last_name varchar(20),
    primary key (id)
);

create table book(
    id bigint(20) AUTO_INCREMENT,
    title varchar(20),
    isbn varchar(20),
    page_count int,
    author_id bigint(20),
    primary key (id)
)

