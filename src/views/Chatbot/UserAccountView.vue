<template>
  <div>
    <TopNav />
    <div id="container">
      <SideBar />
      <UserAccountBoard :templateType = "templateType"/>
      <Footer />
    </div>
  </div>
</template>

<script>
import SideBar from "@/components/AdminPage/Common/SideBar/SideBar.vue";
import TopNav from "@/components/AdminPage/Common/TopNav/TopNav.vue";
import Footer from "@/components/AdminPage/Common/footer/Footer.vue";
import UserAccountBoard from '@/components/AdminPage/SGVA_Chatbot/UserAccountBoard/UserAccountBoard.vue';

export default {
  name: "UserAccountView",
  components: {
    SideBar,
    TopNav,
    Footer,
    UserAccountBoard,
  },
  data() {
    return {
      templateType: "UserAccount",
    };
  },
};
</script>
