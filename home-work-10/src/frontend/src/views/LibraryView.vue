<template>
  <div id="createBook">
    <a-button shape="round" type="primary"
              style="margin-bottom: 5px;"
              @click="createBookVisible=true">Create book
    </a-button>
    <a-modal v-model:visible="createBookVisible" @ok="createBook()">
      <create-book ref="createBook"/>
    </a-modal>
    <a-table :columns="columns" :data-source="books.value">
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'authors'">
          <span>
            <a-tag
                v-for="author in record.authors"
                :key="author.id"
                color="orange"
            >
              {{ author.firstName + ' ' + author.lastName }}
            </a-tag>
          </span>
        </template>
        <template v-else-if="column.key === 'genres'">
          <span>
            <a-tag
                v-for="genre in record.genres"
                :key="genre.id"
                color="green"
            >
              {{ genre.name }}
            </a-tag>
          </span>
        </template>
        <template v-else-if="column.key === 'actions'">
          <a-modal v-model:visible="commentsVisible[record.id]">
            <template #footer>
            </template>
            <book-comments :book-id="record.id" :book-name="record.name"/>
          </a-modal>
          <a-modal v-model:visible="editBooksVisible[record.id]" @ok="editBook(record.id)">
            <edit-book :ref="record.id + '_update'" :book-id="record.id" :book-name="record.name"/>
          </a-modal>
          <span>
            <a-space>
              <a-button
                  type="secondary"
                  size="small"
                  @click="commentsVisible[record.id]=true">
              Comments
            </a-button>
            <a-button
                type="warning"
                size="small"
                @click="editBooksVisible[record.id]=true">
              Edit</a-button>
            <a-button
                type="danger"
                ghost
                size="small"
                @click="deleteBook(record.id)">
              Delete
            </a-button>
            </a-space>
          </span>
        </template>
      </template>
    </a-table>
  </div>
</template>

<script>
import {api} from "../api/api.js";
import BookComments from "../components/BookComments.vue";
import CreateBook from "../components/CreateBook.vue";
import EditBook from "../components/EditBook.vue";
import {reactive, toRaw} from "vue";

export default {
  name: "LibraryView",

  components: {
    EditBook,
    BookComments,
    CreateBook
  },

  data() {
    return {
      commentsVisible: {},
      editBooksVisible: {},
      createBookVisible: false,
      books: reactive({value: []}),
      columns: [
        {
          title: 'ID',
          dataIndex: 'id',
          key: 'id',
        },
        {
          title: 'Name',
          dataIndex: 'name',
          key: 'name',
        },
        {
          title: 'Authors',
          dataIndex: 'authors',
          key: 'authors',
        },
        {
          title: 'Genres',
          dataIndex: 'genres',
          key: 'genres',
        },
        {
          title: 'Actions',
          key: 'actions',
        }
      ]
    }
  },

  created() {
    this.getBooks()
  },

  methods: {
    getBooks() {
      api.getBooks().then(response => {
        this.books.value = response.data;
        this.books.value.forEach(book => {
          this.commentsVisible[book.id] = false
          this.editBooksVisible[book.id] = false
        })
      });
    },

    deleteBook(bookId) {
      api.deleteBook(bookId).then(() => {
        this.getBooks()
      })
    },

    createBook() {
      const name = this.$refs.createBook.name
      const authors = toRaw(this.$refs.createBook.selectedAuthors)
      const genres = toRaw(this.$refs.createBook.selectedGenres)
      api.addBook(name, authors, genres).then(() => {
        this.createBookVisible = false
        this.$refs.createBook.name = ""
        this.$refs.createBook.selectedAuthors = []
        this.$refs.createBook.selectedGenres = []
        location.reload()
      })
    },

    editBook(bookId) {
      const name = this.$refs[bookId + "_update"].name
      const authors = toRaw(this.$refs[bookId + "_update"].selectedAuthors)
      const genres = toRaw(this.$refs[bookId + "_update"].selectedGenres)
      api.editBook(bookId, name, authors, genres).then(() => {
        this.editBooksVisible[bookId] = false
        this.$refs[bookId + "_update"].name = ""
        this.$refs[bookId + "_update"].selectedAuthors = []
        this.$refs[bookId + "_update"].selectedGenres = []
        location.reload()
      })
    }
  }
}
</script>

<style scoped>
#createBook {
  margin-top: 5px;
  margin-left: 5px;
  margin-right: 5px;
}
</style>