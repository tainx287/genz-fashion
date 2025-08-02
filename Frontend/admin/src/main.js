import { createApp } from 'vue';
import App from './App.vue';
import router from './components/router/index.js';
import "./global.css";

const app = createApp(App);
app.use(router);
app.mount('#app');
