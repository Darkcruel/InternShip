<template>
  <div id="pop_mesage" class="layerwrap type3">
    <h5 class="text">
      변경사항이 있습니다. 그래도 초기화 하시겠습니까?
    </h5>
    <input type="text" style="width:25%" title="내용" />
    <div class="btn_center">
      <a class="btn_blue" @click="clickOkay">예</a>
      <a class="btn_black b-close" @click="closeModal">아니오</a>
    </div>
    <a class="btn_close b-close" @click="closeModal">닫기</a>
  </div>
</template>

<script>
export default {
  methods: {
    clickOkay() {
      this.$emit("okay");
    },
    closeModal() {
      this.$emit("close-modal");
    },
  },
};
</script>
