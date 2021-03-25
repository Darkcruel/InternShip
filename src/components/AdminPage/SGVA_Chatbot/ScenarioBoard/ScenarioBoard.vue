<template>
  <div id="contents" @click="closeContext">
    <SubTopNav :templateType = "templateType" />
    
    <RasaButtons v-show="templateType != 'FAQ'" @open-init-modal="openInitModal" />
    <FAQButtons v-show="templateType == 'FAQ'" />

    <div class="cont_box2">
      <ContentLeftSide :templateType="templateType" @select-title="selectTitle" />
      <ContentRightSide :templateType="templateType" />
      <TriggerInputModal />
    </div>


  </div>
</template>

<script>
import TriggerInputModal from "@/components/AdminPage/SGVA_Chatbot/Pieces/ButtonModals/TriggerInputModal.vue";
import SubTopNav from "@/components/AdminPage/Common/TopNav/SubTopNav.vue";
import ContentLeftSide from "@/components/AdminPage/SGVA_Chatbot/ManagementBoard/LeftSide/LeftSide.vue";
import ContentRightSide from "@/components/AdminPage/SGVA_Chatbot/ScenarioBoard/RightSide/RightSide.vue";
import RasaButtons from "@/components/AdminPage/SGVA_Chatbot/Pieces/RasaButtons.vue";
import FAQButtons from "@/components/AdminPage/SGVA_Chatbot/Pieces/FAQButtons.vue";
// import * as trainApi from "@/apis/admin.js";
import initModal from "@/mixins/initModal";
import { mapState } from "vuex";

export default {
  name: "ManagementBoard",
  props: ["title", "subtitle", "templateType"],
  mixins: [initModal],
  components: {
    SubTopNav,
    ContentLeftSide,
    ContentRightSide,
    RasaButtons, 
    FAQButtons,
    TriggerInputModal,
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
    selectTitle(item) {
      const innerHTMLs = this.$el.querySelectorAll(".example_item");
      for (let i = 0; i < item.examples.length; i++) {
        innerHTMLs[i].innerHTML = item["examples"][i]["content"].replaceAll("{", "<span style='background-color: yellow'>").replaceAll("}", "</span>");
      }
    },
    openInitModal() {
      this.$emit("open-init-modal");
    },
  },
};
</script>
