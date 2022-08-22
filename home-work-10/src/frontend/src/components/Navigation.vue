<template>
  <div>
    <a-layout>
      <a-layout-header style="padding-inline-start: 10px">
        <div>
          <h1 style="color: aliceblue">BookWorks</h1>
        </div>
      </a-layout-header>
      <a-layout-content>
        <router-view/>
      </a-layout-content>
    </a-layout>
  </div>
</template>

<script>
export default {
  name: "Navigation",
}
</script>

<style scoped>


</style>