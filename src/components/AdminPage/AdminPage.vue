<template>
  <div>
    <TopNav />
    <div id="container">
      <SideBar />
      <Footer />
    </div>
  </div>
</template>

<script>
import SideBar from "@/components/AdminPage/Common/SideBar/SideBar.vue";
import TopNav from "@/components/AdminPage/Common/TopNav/TopNav.vue";
import Footer from "@/components/AdminPage/Common/footer/Footer.vue";

export default {
  name: "AdminPage",
  components: {
    SideBar,
    TopNav,
    Footer,
  },
};
</script>
