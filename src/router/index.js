import Vue from "vue";
import VueRouter from "vue-router";
import ChatRoomView from "@/views/Chatbot/ChatRoomView";
import AdminPageView from "@/views/AdminPageView";
import LoginView from "@/views/LoginView";

Vue.use(VueRouter);

const routes = [
  {
    path: "/chatroom",
    name: "ChatRoomView",
    component: ChatRoomView,
  },
  {
    path: "/admin",
    name: "AdminPageView",
    component: AdminPageView,
  },
  {
    path: "/admin/scenario",
    name: "ScenarioView",
    component: () => import("@/views/Chatbot/ScenarioView"),
  },
  {
    path: "/admin/intent",
    name: "IntentMappingView",
    component: () => import("@/views/Chatbot/IntentMappingView"),
  },
  {
    path: "/admin/forms",
    name: "FormsView",
    component: () => import("@/views/Chatbot/FormsView"),
  },
  {
    path: "/admin/faq",
    name: "FAQView",
    component: () => import("@/views/Chatbot/FAQView"),
  },
  {
    path: "/admin/responses",
    name: "ResponsesView",
    component: () => import("@/views/Chatbot/ResponsesView"),
  },
  {
    path: "/admin/history",
    name: "ConversationHistoryView",
    component: () => import("@/views/Chatbot/ConversationHistoryView"),
  },
  {
    path: "/admin/account",
    name: "UserAccountView",
    component: () => import("@/views/Chatbot/UserAccountView"),
  },
  {
    path: "/admin/banword",
    name: "BanWordView",
    component: () => import("@/views/Chatbot/BanWordView"),
  },
  {
    path: "/login",
    name: "LoginView",
    component: LoginView,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
