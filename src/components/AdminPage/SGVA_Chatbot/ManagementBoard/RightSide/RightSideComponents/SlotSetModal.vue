<template>
  <div class="detail_view">
    <h4>슬롯 설정</h4>
    <ul>
      <li>
        <input type="text" placeholder="단어" title="단어" class="mr7" :value="text" disabled />
        <a class="btn_blue" @click="submit">적용</a>
        <a class="btn_black btn_detail" @click="closeModal">취소</a>
        <a class="btn_search layer_close" @click="closeModal">닫기</a>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  props: ["text", "content", "start", "end"],
  computed: {
    ...mapState(["inventory"]),
  },
  methods: {
    closeModal() {
      this.$emit("close-modal", false);
    },
    submit() {
      for (let i = this.start; i < this.content.length; i++) {
        if (this.content[i] === "{" || this.content[i] === "}") {
          alert("슬롯 등록을 할 수 없습니다.");
          this.closeModal();
          return;
        }
      }
      if (this.inventory.slotQuestions[this.text] === undefined) {
        alert("존재하지 않는 슬롯입니다.");
        this.closeModal();
        return;
      }
      this.$emit("set-slot");
    },
  },
};
</script>
