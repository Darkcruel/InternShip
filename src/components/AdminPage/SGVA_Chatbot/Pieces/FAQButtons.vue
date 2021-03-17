<template>
  <div class="btn_right">
    <a class="btn_white">다운로드</a>
    <a class="btn_white">업로드</a>
    <a class="btn_refresh">FAQ학습</a>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "FAQButtons",
  props: ["templateType"],
  computed: {
    ...mapState(["managementBoardData", "inventory"]),
  },
};
</script>
