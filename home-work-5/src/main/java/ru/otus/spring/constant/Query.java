package ru.otus.spring.constant;

public class Query {
    public  static final String ALL_BOOKS_AUTHORS_QUERY = "select " +
            "b.book_id, " +
            "b.name as book_name, " +
            "a.author_id, " +
            "a.first_name, " +
            "a.last_name " +
            "from book as b " +
            "left join book_author as ba on b.book_id = ba.book_id " +
            "left join author as a on ba.author_id = a.author_id";

    public static final String ALL_BOOKS_GENRES_QUERY = "select " +
            "b.book_id, " +
            "g.genre_id, " +
            "g.name as genre_name " +
            "from book as b " +
            "left join book_genre as bg on b.book_id = bg.book_id " +
            "left join genre as g on bg.genre_id = g.genre_id";

    public static final String CURRENT_BOOK_AUTHORS_QUERY = "select " +
            "b.book_id, " +
            "b.name as book_name, " +
            "a.author_id, " +
            "a.first_name, " +
            "a.last_name " +
            "from book as b " +
            "left join book_author as ba on b.book_id = ba.book_id " +
            "left join author as a on ba.author_id = a.author_id " +
            "where b.book_id = :id";

    public static final String CURRENT_BOOK_GENRES_QUERY = "select " +
            "b.book_id, " +
            "g.genre_id, " +
            "g.name as genre_name " +
            "from book as b " +
            "left join book_genre as bg on b.book_id = bg.book_id " +
            "left join genre as g on bg.genre_id = g.genre_id " +
            "where b.book_id = :id";
}
