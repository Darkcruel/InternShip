<template>
  <div class="box_left">
    <div class="list_header">
      <h3>{{ managementBoardData[templateType]["pageTitle"] }}</h3>
    </div>
    <div>
      <ul>
        <input type="text" style="width:87%;" placeholder="카테고리명을 입력해주세요." title="아이디" v-model="newItemInput" />
        <a @click="addNewItem" class="ico_add">버튼 옵션 추가</a>
      </ul>
      <ul>
        <li v-for="item in managementBoardData[templateType]['items']" :key="item.id">
          <a @click="sendSelected(item, $event)">{{ item.title }}</a>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "ContentLeftSide",
  props: ["title", "templateType"],
  data() {
    return {
      newItemInput: "",
    };
  },
  mounted() {
    this.$el.querySelector("ul > li").classList.add("on");
  },
  computed: {
    ...mapState(["managementBoardData", "inventory", "indexKeeper"]),
  },
  methods: {
    addNewItem() {
      if (this.newItemInput == "") return;

      let dataEntry = "";
      const templateTypeObj = {
        IntentMapping: () => {
          dataEntry = { examples: [], title: "" };
        },
        FAQ: () => {
          dataEntry = { answer: "", examples: [], selectedAction: "", title: "" };
        },
        Forms: () => {
          dataEntry = { examples: [{ slotName: "새 슬롯 #" + this.indexKeeper.newSlotIndex, id: 0, content: {} }], title: "" };
          this.inventory["slotQuestions"].push({ attachment: {}, buttons: [], examples: [{}], title: "새 슬롯 #" + this.indexKeeper.newSlotIndex });
          this.indexKeeper.newSlotIndex++;
          dataEntry["selectedSlot"] = dataEntry.examples[0];
        },
        Responses: () => {
          dataEntry = { attachment: {}, buttons: [], examples: [], title: "" };
        },
      };

      templateTypeObj[this.templateType]();
      dataEntry["title"] = this.newItemInput;
      this.managementBoardData[this.templateType]["items"].push(dataEntry);
      this.newItemInput = "";

      // switch (this.templateType) {
      //   case "IntentMapping":
      //     dataEntry = { examples: [], title: "" };
      //     break;
      //   case "FAQ":
      //     dataEntry = { answer: "", examples: [], selectedAction: "", title: "" };
      //     break;
      //   case "Forms":
      //     dataEntry = { examples: [{ slotName: "", id: 0, content: {} }], title: "", response: {} };
      //     this.inventory["slotQuestions"][""] = { attachment: {}, buttons: [], examples: [{}], title: "" };
      //     dataEntry["response"] = this.inventory["slotQuestions"][""];
      //     dataEntry["selectedSlot"] = dataEntry.examples[0];
      //     break;

      //   case "Responses":
      //     dataEntry = { attachment: {}, buttons: [], examples: [], title: "" };
      //     break;
      // }
    },

    sendSelected(item, event) {
      const titles = this.$el.querySelectorAll("ul > li");
      titles.forEach(i => i.classList.remove("on"));
      event.target.parentElement.classList.add("on");
      this.managementBoardData[this.templateType]["selected"] = item;
      if (this.templateType === "Responses") {
        this.$emit("select-title", item);
      } else if (this.templateType === "Forms") {
        this.$store.commit("setSlotIndex", this.templateType);
      }
    },
  },
};
</script>
