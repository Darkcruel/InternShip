<template>
  <div class="btn_right">
    <a href="#" class="btn_write">신규등록</a>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "AccountButton",
  props: ["templateType"],
  computed: {
    ...mapState(["managementBoardData"]),
  },
}
</script>

<style>

</style>