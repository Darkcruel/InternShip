<template>
  <div id="contents">
      <SubTopNav :templateType="templateType" />
      <BanWordEnrollButtons v-show="templateType == 'BanWord'"/>
      <div class="cont_box2">
          <ContentLeftSide :templateType="templateType"/>
          <ContentRightSide :templateType="templateType" />
      </div>
  </div>
</template>

<script>
import SubTopNav from "@/components/AdminPage/Common/TopNav/SubTopNav.vue";
import BanWordEnrollButtons from "@/components/AdminPage/SGVA_Chatbot/Pieces/BanWordEnrollButtons.vue"
import ContentLeftSide from "@/components/AdminPage/SGVA_Chatbot/WordManagementBoard/LeftSide/LeftSide.vue";
import ContentRightSide from "@/components/AdminPage/SGVA_Chatbot/WordManagementBoard/RightSide/RightSide.vue";
import { mapState } from "vuex";
export default {
    name: "WordManagementBoard",
    props: ["title", "subtitle", "templateType"],
    components:{
        SubTopNav,
        BanWordEnrollButtons,
        ContentLeftSide,
        ContentRightSide
    },
    computed: {
    ...mapState(["managementBoardData"]),
    },
}
</script>

<style>

</style>