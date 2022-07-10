create table if not exists book
(
    book_id bigserial primary key,
    name varchar(50) not null
);

create table if not exists author
(
    author_id bigserial primary key,
    first_name varchar(50) not null,
    last_name varchar(50) not null
);

create table if not exists book_author
(
    book_author_id bigserial primary key,
    book_id bigint not null,
    author_id bigint not null
);

alter table book_author
    add constraint FK_BookAuthor_BookId
        foreign key (book_id) references book (book_id) on delete cascade;

alter table book_author
    add constraint FK_BookAuthor_AuthorId
        foreign key (author_id) references author (author_id) on delete cascade;

create table if not exists genre
(
    genre_id bigserial primary key,
    name varchar(50) not null
);

create table if not exists book_genre
(
    book_genre_id bigserial primary key,
    book_id bigint not null,
    genre_id bigint not null
);

alter table book_genre
    add constraint FK_BookGenre_BookId
        foreign key (book_id) references book (book_id) on delete cascade;

alter table book_genre
    add constraint FK_BookGenre_GenreId
        foreign key (genre_id) references genre (genre_id) on delete cascade;

create table if not exists comment
(
    comment_id bigserial primary key,
    book_id bigint,
    comment varchar(50) not null
);

alter table comment
    add constraint FK_Comment_BookId
        foreign key (book_id) references book (book_id) on delete cascade;