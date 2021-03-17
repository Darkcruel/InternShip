<template>
  <div>
    <TopNav />
    <div id="container">
      <SideBar />
      <ScenarioBoard :templateType="templateType" /> 
      <Footer />
    </div>
  </div>
</template>

<script>
import SideBar from "@/components/AdminPage/Common/SideBar/SideBar.vue";
import TopNav from "@/components/AdminPage/Common/TopNav/TopNav.vue";
import Footer from "@/components/AdminPage/Common/footer/Footer.vue";
import ScenarioBoard from "@/components/AdminPage/SGVA_Chatbot/ScenarioBoard/ScenarioBoard.vue";
export default {
  name: "ScenarioView",
  components: {
    SideBar,
    TopNav,
    Footer,
    ScenarioBoard,
  },
  data() {
    return {
      templateType: "Scenario",
    };
  },
};
</script>
