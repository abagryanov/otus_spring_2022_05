<template>
    <div>
      <a-card :title="name" style="width: 600px">
        <p v-text="formattedAuthors"></p>
        <p v-text="formattedGenres"></p>
        <template #extra>
          <a-button type="link">Edit</a-button>
          <a-button type="link">Delete</a-button>
        </template>
      </a-card>
    </div>
</template>
<script>
export default {
  name: "Book",

  props: {
    id: {
      type: Number,
      required: true
    },

    name: {
      type: String,
      required: true
    },

    authors: {
      type: Array,
      required: true
    },

    genres: {
      type: Array,
      required: true
    }
  },

  computed: {
    formattedAuthors() {
      return "Authors: " + this.authors.map(author => author.firstName + " " + author.lastName)
          .join(', ')
    },

    formattedGenres() {
      return "Genres: " + this.genres.map(genre => genre.name)
          .join(', ')
    }
  }
}
</script>

<style scoped>

</style>