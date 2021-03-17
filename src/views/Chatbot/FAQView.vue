<template>
  <div>
    <TopNav />
    <div id="container">
      <SideBar />
      <ManagementBoard :templateType="templateType" @open-init-modal="openInitModal" />
      <Footer />
    </div>
  </div>
</template>

<script>
import SideBar from "@/components/AdminPage/Common/SideBar/SideBar.vue";
import TopNav from "@/components/AdminPage/Common/TopNav/TopNav.vue";
import Footer from "@/components/AdminPage/Common/footer/Footer.vue";
import ManagementBoard from "@/components/AdminPage/SGVA_Chatbot/ManagementBoard/ManagementBoard.vue";

export default {
  name: "AdminPage",
  components: {
    SideBar,
    TopNav,
    Footer,
    ManagementBoard,
  },
  data() {
    return {
      templateType: "FAQ",
    };
  },
};
</script>
