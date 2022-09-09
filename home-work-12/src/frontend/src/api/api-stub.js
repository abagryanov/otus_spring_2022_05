import books from "./stubs/books";
import comments from "./stubs/comments";

class Api {
    constructor() {
    }

    async getBooks() {
        return books;
    }

    async getComments(bookId) {
        if (bookId) {
            return comments;
        } else {
            throw new Error("Book id is not set.")
        }
    }
}

export const api = new Api()