<template>
  <a-form>
    <a-form-item name="comment"
                 :rules="[{ required: true, message: 'Please input comment' }]"
                 style="margin-top: 20px">
      <a-input v-model:value="comment"/>
    </a-form-item>
    <a-form-item>
      <a-button
          @click="addComment(bookId, comment)"
          html-type="submit"
          type="primary">
        Add comment
      </a-button>
    </a-form-item>
  </a-form>

  <a-list
      class="comment-list"
      :header="`${bookName}: ${comments.value.length} replies`"
      item-layout="horizontal"
      :data-source="comments.value"
  >
    <template #renderItem="{ item }">
      <a-list-item>
        <a-comment :avatar="'https://joeschmoe.io/api/v1/random'">
          <template #actions>
            <span @click="deleteComment(item.id)">Delete</span>
          </template>
          <template #content>
            <p>
              {{ item.comment }}
            </p>
          </template>
        </a-comment>
      </a-list-item>
    </template>
  </a-list>
</template>

<script>
import {api} from "../api/api.js";
import {reactive} from "vue";

export default {
  name: "BookComments",

  data() {
    return {
      comment: "",
      comments: reactive({value: []}),
      addCommentVisible: false
    }
  },

  props: {
    bookId: {
      type: Number,
      required: true
    },
    bookName: {
      type: String,
      required: true
    }
  },

  created() {
    this.getComments(this.bookId)
  },

  methods: {
    getComments(bookId) {
      api.getComments(bookId).then(response => {
        this.comments.value = response.data;
      });
    },

    deleteComment(commentId) {
      api.deleteComment(commentId).then(() => {
        this.getComments(this.bookId)
      })
    },

    addComment(bookId, comment) {
      api.addComment(bookId, comment).then(() => {
        this.comment = ""
        this.getComments(this.bookId)
      })
    }
  },

}
</script>

<style scoped>

</style>