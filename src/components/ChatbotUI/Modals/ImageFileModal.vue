<template>
  <div id="modalApp">
    <div class="modal__container">
      <div>
        <label for="">파일명</label>
        <div class="file__title">
          <span v-if="files">{{ files.name }}</span>
        </div>
      </div>
      <div class="button__item">
        <v-btn depressed color="primary" @click="submitImage" class="submit__file">
          전송
        </v-btn>
        <v-btn depressed @click="closeModal">
          취소
        </v-btn>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ["files"],
  methods: {
    submitImage: function() {
      this.$emit("submit", this.files);
    },
    closeModal: function() {
      this.$emit("close-modal", null);
    },
  },
};
</script>

<style>
#modalApp {
  width: 488px;
  height: 200px;
  padding: 20px 20px;
  background-color: white;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translateY(-50%) translateX(-50%);
  box-shadow: 10px 5px 5px rgba(0, 0, 0, 0.1);
}

.file__title {
  border: 0.3px rgba(0, 0, 0, 0.2) solid;
  height: 100px;
  margin-top: 3px;
  padding: 5px 5px;
}

.button__item {
  margin-top: 10px;
  text-align: right;
}

.submit__file {
  margin-right: 10px;
}
</style>
