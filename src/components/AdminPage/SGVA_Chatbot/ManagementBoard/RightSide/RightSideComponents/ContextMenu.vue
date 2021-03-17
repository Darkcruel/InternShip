<template>
  <div class="context__menu">
    <ul>
      <li @click="openSlotSet">슬롯 설정</li>
      <li v-show="text.indexOf('{') !== -1 && text.indexOf('}') !== -1" @click="cancelSlotSet">슬롯 해제</li>
    </ul>
  </div>
</template>

<script>
export default {
  props: ["text"],
  methods: {
    openSlotSet() {
      this.$emit("open-slot");
    },
    cancelSlotSet() {
      this.$emit("cancel-slot", this.text);
    },
  },
};
</script>

<style scoped>
.context__menu {
  display: none;
  width: 120px;
  text-align: center;
  border: 1px solid black;
  position: absolute;
  background-color: white;
  border-radius: 5px;
}

.context__menu li {
  cursor: pointer;
}

.context__menu li:hover {
  background-color: rgb(240, 240, 240);
}
</style>
