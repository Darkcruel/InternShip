<template>
  <div class="faq_write type2 title">
    <ul class="header">
      <h4>새로운 항목</h4>
      <li>
        <input type="text" style="width:400px" placeholder="새 항목명을 입력해주세요." title="항목명" autocomplete="off" v-model="slotNameInput" />
        <a @click="addSlot" class="ico_add">추가</a>
      </li>

      <li>
        <span v-for="item in managementBoardData[templateType]['selected']['examples']" :key="item.id">
          <button type="button" class="btn_white type4" @click="sendSelected(item)">{{ item["slotName"] }}</button>
          <a @click="deleteSlot(item)" class="ico_del">삭제</a>
        </span>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapState } from "vuex";
import deleteById from "@/mixins/deleteById";
import deleteByName from "@/mixins/deleteByName";

export default {
  name: "SubSelectionTable",
  mixins: [deleteById, deleteByName],
  data() {
    return {
      slotNameInput: "",
    };
  },
  props: ["templateType"],
  computed: {
    ...mapState(["managementBoardData", "inventory"]),
  },
  methods: {
    sendSelected(item) {
      this.managementBoardData[this.templateType]["selected"]["selectedSlot"] = item;
      this.$store.commit("setSlotIndex", this.templateType);
    },
    deleteSlot(item) {
      this.deleteById(this.managementBoardData[this.templateType].selected.examples, item.id);
      this.deleteByName(this.inventory.slotQuestions, item.slotName, 'title');
    },

    addSlot() {
      if (this.slotNameInput == "") return;

      // Adding new slot
      const newSlot = { content: {}, id: this.managementBoardData[this.templateType]["selected"]["examples"].length, slotName: this.slotNameInput };
      this.managementBoardData[this.templateType]["selected"]["examples"].push(newSlot);

      // Adding new response for the slot
      const newResponseObj = { buttons: [], attachment: "", title: this.slotNameInput, examples: [{}] };
      this.inventory["slotQuestions"].push(newResponseObj);
      

      // Format value
      this.slotNameInput = "";
    },
  },
};
</script>
