<template>
  <div class="content_header type2">
    <h3>{{ managementBoardData[templateType]["pageTitle"] }}</h3>
    <span>{{ managementBoardData[templateType]["pageSubtitle"] }}</span>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "SubTopNav",
  props: ["templateType"],
  computed: {
    ...mapState(["managementBoardData"]),
  },
};
</script>
