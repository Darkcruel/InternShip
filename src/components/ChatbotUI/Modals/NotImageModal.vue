<template>
  <div id="modalApp__error">
    <div class="modal__container">
      <div>
        이미지 파일이 아닙니다.
      </div>
      <div class="button__item">
        <v-btn depressed color="primary" @click="clickConfirm">
          확인
        </v-btn>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    clickConfirm: function() {
      this.$emit("confirm");
    },
  },
};
</script>

<style>
#modalApp__error {
  width: 488px;
  height: 150px;
  padding: 20px 20px;
  background-color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translateY(-50%) translateX(-50%);
  box-shadow: 10px 5px 5px rgba(0, 0, 0, 0.1);
}
</style>
