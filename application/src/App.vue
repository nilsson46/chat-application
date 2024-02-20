<template>
  <div id="app">
    <router-view /> <!-- Visa router-vy för att hantera olika vägar -->
  </div>
</template>

<script>
export default {
  name: 'App',
}
</script>

<style>
/* Global CSS för hela applikationen */
* {
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
}

html, body {
  height: 100%;
  overflow: hidden;
}

body {
  margin: 0;
  padding: 0;
  font-weight: 400;
  font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 1rem;
  line-height: 1.58;
  color: #333;
  background-color: #f4f4f4;
}
</style>