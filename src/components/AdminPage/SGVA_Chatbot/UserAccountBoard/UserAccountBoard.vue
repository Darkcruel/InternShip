<template>
  <div id="contents" @click="closeContext">
    <SubTopNav :templateType = "templateType" />
    <AccountButton />
    <div class = "cont_box2">
        <ContentLeftSide />
        <ContentRightSide />
    </div>
  </div>
</template>

<script>
import SubTopNav from "@/components/AdminPage/Common/TopNav/SubTopNav.vue";
import AccountButton from "@/components/AdminPage/SGVA_Chatbot/Pieces/AccountButton.vue";
import ContentLeftSide from "@/components/AdminPage/SGVA_Chatbot/UserAccountBoard/LeftSide/LeftSide.vue"
import ContentRightSide from "@/components/AdminPage/SGVA_Chatbot/UserAccountBoard/RightSide/RightSide.vue"
// import * as trainApi from "@/apis/admin.js";
import { mapState } from "vuex";


export default {
  name: "ManagementBoard",
  props: ["title", "subtitle", "templateType"],
  components: {
    SubTopNav,
    AccountButton,
    ContentLeftSide,
    ContentRightSide
  },
  computed: {
    ...mapState(["managementBoardData"]),
  },
  methods: {
    closeContext(e) {
      if (
        this.$el.querySelector(".example_text") === null ||
        e.target.getAttribute("class") === "example_text" ||
        e.target.parentElement.getAttribute("class") === "example_text"
      )
        return;
      this.$el.querySelector(".context__menu").style = "none";
    },
  },
};
</script>
