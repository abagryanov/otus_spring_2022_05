<template>
  <a-form>
    <a-form-item name="name"
                 label="Name"
                 :rules="[{ required: true, message: 'Please input name' }]"
                 style="margin-top: 20px">
      <a-input v-model:value="name"/>
    </a-form-item>
    <a-form-item name="authors"
                 label="Authors"
                 :rules="[{ required: true, message: 'Please select authors' }]">
      <a-select v-model:value="selectedAuthors" mode="multiple">
        <a-select-option v-for="author in selectorData.availableAuthors" :value="author.id">
          {{ author.firstName + ' ' + author.lastName }}
        </a-select-option>
      </a-select>
    </a-form-item>
    <a-form-item name="genres"
                 label="Genres"
                 :rules="[{ required: true, message: 'Please select genres' }]">
      <a-select v-model:value="selectedGenres" mode="multiple">
        <a-select-option v-for="genre in selectorData.availableGenres" :value="genre.id">
          {{ genre.name }}
        </a-select-option>
      </a-select>
    </a-form-item>
  </a-form>
</template>

<script>
import {api} from "../api/api.js";
import {reactive} from "vue";

export default {
  name: "CreateBook",

  data() {
    return {
      name: "",
      selectorData: reactive({
        availableAuthors: [],
        availableGenres: []
      }),
      selectedAuthors: [],
      selectedGenres: []
    }
  },

  created() {
    this.getAvailableAuthors()
    this.getAvailableGenres()
  },

  methods: {
    getAvailableAuthors() {
      api.getAuthors().then(response => {
        this.selectorData.availableAuthors = response
      })
    },

    getAvailableGenres() {
      api.getGenres().then(response => {
        this.selectorData.availableGenres = response
      })
    },
  }
}
</script>

<style scoped>

</style>