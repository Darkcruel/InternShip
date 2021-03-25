<template>
  <div class="box_left">
    <div class="list_header">
      <h3>{{ managementBoardData[templateType]["pageTitle"] }}</h3>
    </div>
    <div class="left_list">
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
      // 왼쪽 탭에서 새로운 Item / Object을 만들때 사용 됨 
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
          dataEntry = { examples: [{ slotName: "새 슬롯 #" + this.indexKeeper.newSlotIndex, id: 0, content: [] }], title: "" };
          this.inventory["slotQuestions"].push({ attachment: {}, buttons: [], examples: [{}], title: "새 슬롯 #" + this.indexKeeper.newSlotIndex });
          this.indexKeeper.newSlotIndex++;
          dataEntry["selectedSlot"] = dataEntry.examples[0];
        },
        Responses: () => {
          dataEntry = { attachment: {}, buttons: [], examples: [], title: "" };
        },
        Scenario: () => {
          dataEntry = {title: "", steps: [], trigger: {"object": {}, "type": "", "actionType": "", "id": -1} };
        },
      };

      templateTypeObj[this.templateType]();
      dataEntry["title"] = this.newItemInput;
      this.managementBoardData[this.templateType]["items"].push(dataEntry);
      this.newItemInput = "";
    },

    sendSelected(item, event) {
      // 왼쪽에서 아이템을 눌렀을때 그 정보가 모든 곳에 반영되게 하는 함수
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
