<template>
  <div>
    <TopNav />
    <div id="container">
      <div class="back_shadow"></div>
      <SideBar />
      <ConversationBoard :templateType="templateType" @open-init-modal="openmodal()"/>
      <Footer />
    </div>
    <ContentsModal @close-modal="closemodal()"/>
  </div>
</template>

<script>
import SideBar from "@/components/AdminPage/Common/SideBar/SideBar.vue";
import TopNav from "@/components/AdminPage/Common/TopNav/TopNav.vue";
import Footer from "@/components/AdminPage/Common/footer/Footer.vue";
import ConversationBoard from "@/components/AdminPage/SGVA_Chatbot/ConversationBoard/ConversationBoard.vue";
import ContentsModal from '@/components/AdminPage/SGVA_Chatbot/ConversationBoard/DownSide/DownSideComponents/ContentsModal.vue';
import initModal from "@/mixins/initModal";

export default {
  name: "AdminPage",
  components: {
    SideBar,
    TopNav,
    Footer,
    ConversationBoard,
    ContentsModal
  },
  mixins: [initModal],
  data() {
    return {
      templateType: "ConversationHistory",
      
    };
  },
  methods:{
    openmodal(){
      this.$el.querySelector(".layerwrap").style = "left: 63.5px;position: absolute;top: 0px;z-index: 9999;opacity: 1;display: block;";
    },
    closemodal(){
      document.documentElement.removeAttribute("style");
      this.$el.querySelector(".back_shadow").style = "";
      this.$el.querySelector(".layerwrap").style = "";
    }
  }
  
};
</script>
