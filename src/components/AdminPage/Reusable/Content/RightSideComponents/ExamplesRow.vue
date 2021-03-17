<template>
  <tr>
    <th class="type2">
      <h4>{{ managementBoardData[templateType]["exampleTitle"] }}</h4>
    </th>
    <td>
      <ul>
        <li v-for="example in managementBoardData[templateType]['selected'].examples" :key="example.id">
          <input
            id="text"
            v-show="id === example.id"
            type="text"
            style="width:93%"
            title="내용"
            v-model="example.content"
            autocomplete="off"
            oncontextmenu="return false;"
            @mouseup="openContext"
            @keydown.enter="completeInput"
            @keydown="inputText"
          />
          <p id="test" v-show="id !== example.id" @click="changeInput(example.id, $event)">{{ example.content }}</p>
          <a class="ico_del" @click="deleteQuestion(example.id)">삭제</a>
        </li>
        <a class="ico_add" @click="addQuestion"><img src="../../../../../assets/Admin/images/ico_add.png" />질문 예제 추가</a>
      </ul>
    </td>
    <SlotSetModal v-show="slot" @close-modal="closeModal" @set-slot="setSlot" :text="text" />
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
    ...mapState(["managementBoardData"]),
  },
  mounted() {
    this.colorSlot();
  },
  data() {
    return {
      text: "",
      slot: false,
      start: 0,
      end: 0,
      curInput: 0,
      curP: "",
      id: "",
    };
  },
  methods: {
    colorSlot() {
      const responses = this.$el.querySelectorAll("#test");
      responses.forEach(r => {
        r.innerHTML = r.innerHTML.replaceAll("{", "<span style='background-color: yellow'>").replaceAll("}", "</span>");
      });
    },
    addQuestion() {
      this.managementBoardData[this.templateType]["selected"].examples.push({
        id: this.managementBoardData[this.templateType]["selected"].examples.length,
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
    openContext(e) {
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
      // this.$el.querySelector(".context__menu").style.left = posLeft;
      // this.$el.querySelector(".context__menu").style.top = posTop;
      // this.$el.querySelector(".context__menu").style.display = "block";
      this.$el.querySelector(".context__menu").setAttribute("style", `left: ${posLeft}; top: ${posTop}; display: block`);
      return false;
    },
    openSlot() {
      this.slot = true;
    },
    cancelSlot(text) {
      const input = this.curInput;
      const newText = text.replace("{", "").replace("}", "");
      const newValue = `${input.value.substring(0, this.start)}${newText}${input.value.substring(this.end, input.value.length)}`;
      this.managementBoardData[this.templateType]["selected"].examples.find(e => e.id === this.id).content = newValue;
    },
    closeModal(data) {
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
    inputText(e) {
      let handled = false;
      if ((e.keyCode === 219 || e.keyCode === 221) && e.shiftKey) handled = true;
      if (handled) e.preventDefault();
    },
  },
};
</script>

<style scoped>
#test {
  width: 93%;
  height: 40px;
  padding-top: 9px;
  padding-left: 12px;
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
