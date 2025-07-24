
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './stores'

import './assets/scss/variables.scss'
import './assets/scss/bootstrap-overrides.scss'

const app = createApp(App)

app.use(router)
app.use(store)

app.mount('#app')
