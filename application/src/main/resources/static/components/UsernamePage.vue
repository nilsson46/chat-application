<template>
  <div class="username-page">
    <div class="username-page-container">
      <h1 class="title">Type your username to enter the Chatroom</h1>
      <form @submit.prevent="submitUsername">
        <div class="form-group">
          <input type="text" v-model="name" placeholder="Username" autocomplete="off" class="form-control">
        </div>
        <div class="form-group">
          <button type="submit" class="accent username-submit"> Start Chatting</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      name: ''
    };
  },
  methods: {
    submitUsername() {
      if (this.name.trim() !== '') {
        this.$emit('set-username', this.name.trim());
      }
    }
  }
}
</script>

<style scoped>
/* Scoped CSS för UsernamePage.vue */
.username-page {
  text-align: center;
}

.username-page-container {
  background: #fff;
  box-shadow: 0 1px 11px rgba(0, 0, 0, 0.27);
  border-radius: 2px;
  width: 100%;
  max-width: 500px;
  display: inline-block;
  margin-top: 42px;
  vertical-align: middle;
  position: relative;
  padding: 35px 55px 35px;
  min-height: 250px;
  position: absolute;
  top: 50%;
  left: 0;
  right: 0;
  margin: 0 auto;
  margin-top: -160px;
}

.title {
  font-size: 1.7em;
}

.form-group {
  margin-bottom: 15px;
}

.input-group input {
  padding-left: 10px;
  outline: none;
}

.username-submit {
  margin-top: 10px;
}
</style>