<template>
  <div id="contents" @click="closeContext">
    <SubTopNav :templateType = "templateType" />
    
    <RasaButtons v-show="templateType != 'FAQ'" @open-init-modal="openInitModal" />
    <FAQButtons v-show="templateType == 'FAQ'" />

    <div class="cont_box2">
      <ContentLeftSide :templateType="templateType" @select-title="selectTitle" />
      <ContentRightSide :templateType="templateType" />
    </div>
  </div>
</template>

<script>
import SubTopNav from "@/components/AdminPage/Common/TopNav/SubTopNav.vue";
import ContentLeftSide from "@/components/AdminPage/SGVA_Chatbot/ManagementBoard/LeftSide/LeftSide.vue";
import ContentRightSide from "@/components/AdminPage/SGVA_Chatbot/ScenarioBoard/RightSide/RightSide.vue";
import RasaButtons from "@/components/AdminPage/SGVA_Chatbot/Pieces/RasaButtons.vue";
import FAQButtons from "@/components/AdminPage/SGVA_Chatbot/Pieces/FAQButtons.vue";
// import * as trainApi from "@/apis/admin.js";
import { mapState } from "vuex";

export default {
  name: "ManagementBoard",
  props: ["title", "subtitle", "templateType"],
  components: {
    SubTopNav,
    ContentLeftSide,
    ContentRightSide,
    RasaButtons, 
    FAQButtons,
  },
  computed: {
    ...mapState(["managementBoardData"]),
  },
  methods: {
    // train() {
    //   const data = this.managementBoardData[this.templateType]["items"];
    //   if (this.templateType === "FAQ") {
    //     trainApi
    //       .saveFAQData(data)
    //       .then(res => console.log(res))
    //       .catch(err => console.log(err));
    //   } else {
    //     data.forEach(d => (d.examples = d.examples.filter(e => e.content)));
    //     trainApi
    //       .saveRasaData(data)
    //       .then(res => console.log(res))
    //       .catch(err => console.log(err));
    //   }
    // },
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
    // 나중에 쓸 것입니다. 지우지 마세요.
    // trainNLU() {
    //   this.items.forEach(i => {
    //     if (!i.sentence || !i.selectedIntent) return true;
    //     if (!this.json.nlu.find(n => n.intent === i.selectedIntent)) {
    //       this.json.nlu.push({
    //         intent: i.selectedIntent,
    //         examples: [i.sentence],
    //       });
    //     } else {
    //       this.json.nlu[this.json.nlu.findIndex(n => n.intent === i.selectedIntent)].examples.push(i.sentence);
    //     }
    //   });
    // },
  },
};
</script>
