import axios from "axios";

class Api {
    _client = axios.create()

    constructor() {
    }

    async getBook(bookId) {
        if (!bookId) {
            throw new Error("Book id is not set")
        }
        try {
            const {data} = await this._client("/api/books/" + bookId)
            return data
        } catch (error) {
            console.error(error)
        }
    }

    async getBooks() {
        try {
            const {data} = await this._client("/api/books")
            return data
        } catch (error) {
            console.error(error)
        }
    }

    async deleteBook(bookId) {
        if (!bookId) {
            throw new Error("Book id is not set")
        }
        try {
            await this._client("/api/books/" + bookId, {
                method: "DELETE"
            })
        } catch (error) {
            console.error(error)
        }
    }

    async editBook(bookId, name, authors, genres) {
        if (!bookId || !name || name.length === 0 || genres.length === 0) {
            throw new Error("Book id, name, authors and genres should be set")
        }
        try {
            await this._client("/api/books/edit", {
                method: "POST",
                data: {
                    id: bookId,
                    name: name,
                    authors: authors,
                    genres: genres
                }
            })
        } catch (error) {
            console.error(error)
        }
    }

    async addBook(name, authors, genres) {
        try {
            await this._client("/api/books/create", {
                method: "POST",
                data: {
                    name: name,
                    authors: authors,
                    genres: genres
                }
            })
        } catch (error) {
            console.error(error)
        }
    }

    async getComments(bookId) {
        if (!bookId) {
            throw new Error("Book id is not set")
        }
        try {
            const {data} = await this._client("/api/comments/" + bookId)
            return data
        } catch (error) {
            console.error(error)
        }
    }

    async deleteComment(commentId) {
        if (!commentId) {
            throw new Error("Comment id is not set")
        }
        try {
            await this._client("/api/comments/" + commentId, {
                method: "DELETE"
            })
        } catch (error) {
            console.error(error)
        }
    }

    async addComment(bookId, comment) {
        if (!bookId) {
            throw new Error("Book id is not set")
        }
        if (!comment) {
            throw new Error("Comment is not set")
        }
        try {
            await this._client("/api/comments/create", {
                method: "POST",
                data: {
                    bookId: bookId,
                    comment: comment
                }
            })
        } catch (error) {
            console.error(error)
        }
    }

    async getAuthors() {
        try {
            const {data} = await this._client("/api/authors")
            return data
        } catch (error) {
            console.error(error)
        }
    }

    async getGenres() {
        try {
            const {data} = await this._client("/api/genres")
            return data
        } catch (error) {
            console.error(error)
        }
    }
}

export const api = new Api()