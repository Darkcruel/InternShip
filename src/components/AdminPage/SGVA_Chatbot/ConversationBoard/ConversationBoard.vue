<template>
  <div id = "contents" @click="closeContext">
    <SubTopNavConv :templateType = "templateType" />
    <ConversationButtons />
    <div class = "cont_box">
      <ContentDownSide :templateType="templateType" @open-init-modal="openInitModal"/>
    </div>
  </div>
</template>

<script>
import SubTopNavConv from "@/components/AdminPage/Common/TopNav/SubTopNavConv.vue";
import ConversationButtons from "@/components/AdminPage/SGVA_Chatbot/Pieces/ConversationButtons.vue";
import ContentDownSide from "@/components/AdminPage/SGVA_Chatbot/ConversationBoard/DownSide/DownSide.vue"

import { mapState } from "vuex";
// import { mapGetters, mapActions} from 'vuex'

export default {
    name: "ConversationBoard",
    props: ["title", "subtitle", "templateType"],
    components: {
        SubTopNavConv,
        ConversationButtons,
        ContentDownSide
    },
    data : function() {
        return {
            visible : false
        }
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
      handleClickButton(){
        this.visible = !this.visible
      },
      openInitModal() {
        this.$emit("open-init-modal");
      }
  }
}
</script>

<style>
    
</style>