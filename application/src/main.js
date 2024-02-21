import { createApp } from 'vue';
import App from './App.vue';
import router from '/src/main/resources/router/index.js'; // Adjust the path based on your directory structure

createApp(App).use(router).mount('#app');