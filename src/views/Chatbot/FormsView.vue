<template>
  <div>
    <TopNav />
    <div id="container">
      <div class="back_shadow"></div>
      <SideBar />
      <ManagementBoard :templateType="templateType" @open-init-modal="openInitModal" />
      <Footer />
    </div>
    <InitWarningModal @close-modal="closeModal" @okay="initData" />
  </div>
</template>

<script>
import SideBar from "@/components/AdminPage/Common/SideBar/SideBar.vue";
import TopNav from "@/components/AdminPage/Common/TopNav/TopNav.vue";
import Footer from "@/components/AdminPage/Common/footer/Footer.vue";
import ManagementBoard from "@/components/AdminPage/SGVA_Chatbot/ManagementBoard/ManagementBoard.vue";
import InitWarningModal from "@/components/AdminPage/SGVA_Chatbot/ManagementBoard/RightSide/RightSideComponents/InitWarningModal.vue";
import initModal from "@/mixins/initModal";

export default {
  name: "FormsView",
  components: {
    SideBar,
    TopNav,
    Footer,
    ManagementBoard,
    InitWarningModal,
  },
  mixins: [initModal],
  data() {
    return {
      templateType: "Forms",
    };
  },
};
</script>
