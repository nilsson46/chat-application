<template>
  <div class="chat-page">
    <div class="chat-container">
      <div class="chat-header">
        <h2>ChatZilla</h2>
      </div>
      <div class="connecting" v-if="!connected">
        Connecting...
      </div>
      <ul id="messageArea">
        <!-- Message items will go here -->
      </ul>
      <form @submit.prevent="sendMessage">
        <div class="form-group">
          <div class="input-group clearfix">
            <input type="text" v-model="message" placeholder="Type a message..." autocomplete="off" class="form-control"/>
            <button type="submit" class="primary">Send</button>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      message: '',
      connected: false
    };
  },
  methods: {
    sendMessage() {
      if (this.message.trim() !== '') {
        // Skicka meddelande via WebSocket eller hantera det här
        this.message = '';
      }
    }
  }
}
</script>
<style scoped>
/* Scoped CSS för ChatPage.vue */
.chat-page {
  position: relative;
  height: 100%;
}

.chat-container {
  max-width: 700px;
  margin-left: auto;
  margin-right: auto;
  background-color: #fff;
  box-shadow: 0 1px 11px rgba(0, 0, 0, 0.27);
  margin-top: 30px;
  height: calc(100% - 60px);
  max-height: 600px;
  position: relative;
}

.chat-header {
  text-align: center;
  padding: 15px;
  border-bottom: 1px solid #ececec;
}

.connecting {
  padding-top: 5px;
  text-align: center;
  color: #777;
  position: absolute;
  top: 65px;
  width: 100%;
}

#messageArea {
  list-style-type: none;
  background-color: #FFF;
  margin: 0;
  overflow: auto;
  overflow-y: scroll;
  padding: 0 20px 0px 20px;
  height: calc(100% - 150px);
}

.input-group input {
  float: left;
  width: calc(100% - 85px);
}

.primary {
  float: left;
  width: 80px;
  height: 38px;
  margin-left: 5px;
}
</style>