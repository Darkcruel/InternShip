<template>
  <tr>
    <th class="type2">
      <h4>{{ managementBoardData[templateType]["exampleTitle"] }}</h4>
    </th>
    <td>
      <ul v-if="templateType === 'Responses'">
        <li v-for="example in managementBoardData[templateType]['selected'].examples" :key="example.id">
          <input
            class="example_text"
            v-show="id === example.id"
            type="text"
            style="width:93%"
            title="내용"
            v-model="example.content"
            autocomplete="off"
            oncontextmenu="return false;"
            @mouseup="openContext(example.content, $event)"
            @keydown.enter="completeInput"
            @keydown="inputResponse"
          />
          <p class="example_item" ref="example_item" v-show="id !== example.id" @click="changeInput(example.id, $event)">{{ example.content }}</p>
          <a class="ico_del" @click="deleteQuestion(example.id)">삭제</a>
        </li>
        <a class="ico_add" @click="addQuestion"><img src="../../../../../../assets/Admin/images/ico_add.png" />질문 예제 추가</a>
      </ul>
      <ul v-else>
        <li v-for="example in managementBoardData[templateType]['selected'].examples" :key="example.id">
          <input
            class="example_text"
            ref="example_item"
            type="text"
            style="width:93%"
            title="내용"
            v-model="example.content"
            autocomplete="off"
            oncontextmenu="return false;"
          />
          <a class="ico_del" @click="deleteQuestion(example.id)">삭제</a>
        </li>
        <a class="ico_add" @click="addQuestion"><img src="../../../../../../assets/Admin/images/ico_add.png" />질문 예제 추가</a>
      </ul>
    </td>
    <SlotSetModal v-show="slot" @close-modal="closeModal" @set-slot="setSlot" :text="text" :start="start" :end="end" :content="content" />
    <ContextMenu @open-slot="openSlot" @cancel-slot="cancelSlot" :text="text" />
  </tr>
</template>

<script>
import ContextMenu from "@/components/AdminPage/SGVA_Chatbot/ManagementBoard/RightSide/RightSideComponents/ContextMenu.vue";
import SlotSetModal from "@/components/AdminPage/SGVA_Chatbot/ManagementBoard/RightSide/RightSideComponents/SlotSetModal.vue";
import { mapState } from "vuex";

export default {
  name: "ExamplesRow",
  components: {
    ContextMenu,
    SlotSetModal,
  },
  props: ["templateType"],
  computed: {
    ...mapState(["managementBoardData", "inventory"]),
  },
  mounted() {
    this.colorSlot();
  },
  data() {
    return {
      num: 0,
      text: "",
      content: "",
      slot: false,
      start: 0,
      end: 0,
      curInput: 0,
      curP: "",
      id: "",
    };
  },
  methods: {
    addQuestion() {
      let lastElementIndex = this.managementBoardData[this.templateType]["selected"].examples.length - 1;
      let newId = 0;
      if (lastElementIndex >= 0) {
        let lastElementId = this.managementBoardData[this.templateType]["selected"].examples[lastElementIndex].id;
        newId = lastElementId + 1; 
      }

      this.managementBoardData[this.templateType]["selected"].examples.push({
        id: newId,
        content: "",
      });
    },
    deleteQuestion(id) {
      this.managementBoardData[this.templateType]["selected"].examples.splice(
        this.managementBoardData[this.templateType]["selected"].examples.findIndex(q => q.id === id),
        1,
      );
    },
    saveSelection(containerEl) {
      const range = window.getSelection().getRangeAt(0);
      const preSelectionRange = range.cloneRange();
      preSelectionRange.selectNodeContents(containerEl);
      preSelectionRange.setEnd(range.startContainer, range.startOffset);
      const start = preSelectionRange.toString().length;

      return {
        start: start,
        end: start + range.toString().length,
      };
    },
    openContext(content, e) {
      if (e.which === 3) {
        window.getSelection().removeAllRanges();
        this.$el.querySelector(".context__menu").style.display = "none";
        this.text = "";
        return;
      }
      if (this.$el.querySelector(".context__menu").style.display === "block") {
        window.getSelection().removeAllRanges();
        this.$el.querySelector(".context__menu").style.display = "none";
        this.text = "";
        return;
      }
      const selectedText = window.getSelection().toString();
      this.curInput = e.target;
      this.start = e.target.selectionStart;
      this.end = e.target.selectionEnd;
      this.text = selectedText;
      this.content = content;
      this.$emit("openContext", this.text);
      if (!this.text) {
        this.$el.querySelector(".context__menu").style.display = "none";
        return;
      }

      const posX = e.pageX;
      const posY = e.pageY;
      const secMargin = 10;
      const posLeft = `${posX + secMargin}px`;
      const posTop = `${posY + secMargin}px`;
      this.$el.querySelector(".context__menu").setAttribute("style", `left: ${posLeft}; top: ${posTop}; display: block`);
      return false;
    },
    openSlot() {
      document.documentElement.style.overflow = "hidden";
      this.slot = true;
    },
    cancelSlot(text) {
      const input = this.curInput;
      const newText = text.replace("{", "").replace("}", "");
      const newValue = `${input.value.substring(0, this.start)}${newText}${input.value.substring(this.end, input.value.length)}`;
      this.managementBoardData[this.templateType]["selected"].examples.find(e => e.id === this.id).content = newValue;
    },
    colorSlot() {
      for (let i = 0; i < this.managementBoardData[this.templateType]["selected"]["examples"].length; i++) {
        this.$refs.example_item[i].innerHTML = this.managementBoardData[this.templateType]["selected"]["examples"][i]["content"]
          .replaceAll("{", "<span style='background-color: yellow'>")
          .replaceAll("}", "</span>");
      }
    },
    closeModal(data) {
      document.documentElement.removeAttribute("style");
      this.slot = data;
    },
    setSlot() {
      const input = this.curInput;
      const newValue = `${input.value.substring(0, this.start)}{${input.value.substring(this.start, this.end)}}${input.value.substring(
        this.end,
        input.value.length,
      )}`;
      this.managementBoardData[this.templateType]["selected"].examples.find(e => e.id === this.id).content = newValue;
      this.closeModal(false);
    },
    changeInput(id, e) {
      this.id = id;
      this.curP = e.target;
    },
    completeInput(e) {
      const text = e.target.value.replaceAll("{", "<span style='background-color: yellow'>").replaceAll("}", "</span>");
      this.curP.innerHTML = text;
      this.id = "";
    },
    inputResponse(e) {
      let handled = false;
      if ((e.keyCode === 219 || e.keyCode === 221) && e.shiftKey) handled = true;
      if (handled) e.preventDefault();
    },
  },
};
</script>

<style scoped>
.example_item {
  width: 93%;
  height: 40px;
  padding-bottom: 9px;
  padding-top: 9px;
  padding-left: 12px;
  overflow-y: scroll;
  border: 1px solid #c9c9c9;
  display: inline-block;
  font-size: 15px;
  font-family: "Noto Sans KR", "Malgun Gothic", sans-serif;
  color: #333;
  border-radius: 3px;
  vertical-align: middle;
}

li {
  height: 40px;
}
</style>
