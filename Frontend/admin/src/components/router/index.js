import { createRouter, createWebHistory } from "vue-router";
import POSOrder from "@/components/order/orderForm.vue";

const routes = [
    { path: "/order", component: POSOrder}
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});


export default router;
