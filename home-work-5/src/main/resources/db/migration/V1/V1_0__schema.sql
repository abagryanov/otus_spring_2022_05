create table if not exists book
(
    book_id bigint primary key auto_increment,
    name varchar(50) not null
);

create table if not exists author
(
    author_id bigint primary key auto_increment,
    first_name varchar(50) not null,
    last_name varchar(50) not null
);

create table if not exists book_author
(
    book_author_id bigint primary key auto_increment,
    book_id bigint not null,
    author_id bigint not null,
    constraint FK_BookAuthor_BookId foreign key (book_id) references book (book_id),
    constraint FK_BookAuthor_AuthorId foreign key (author_id) references author (author_id)
);

create table if not exists genre
(
    genre_id bigint primary key auto_increment,
    name varchar(50) not null
);

create table if not exists book_genre
(
    book_genre_id bigint primary key auto_increment,
    book_id bigint not null,
    genre_id bigint not null,
    constraint FK_BookGenre_BookId foreign key (book_id) references book (book_id),
    constraint FK_BookGenre_GenreId foreign key (genre_id) references genre (genre_id)
);