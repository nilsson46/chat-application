import Vue from 'vue';
import VueRouter from 'vue-router';
import LandingPage from '@/main/resources/static/LandingPage.vue';
//import NotFound from '@/main/resources/static/NotFound.vue'; // Förutsatt att du har en NotFound-komponent

Vue.use(VueRouter);

const router = new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/home',
            name: 'Home',
            component: LandingPage
        },
        // Andra vägar här...
    ]
});

export default router;
