<template>
  <div class="btn_right">
    <a class="btn_white" @click="openInitModal">초기화</a>
    <a class="btn_white" @click="saveRASAData">저장하기</a>
    <a class="btn_refresh">챗봇학습</a>
  </div>
</template>

<script>
import { mapState } from "vuex";
import * as trainApi from "@/apis/admin.js";

export default {
  name: "RasaButtons",
  props: ["templateType"],
  computed: {
    ...mapState(["managementBoardData", "inventory"]),
  },
  methods: {
    openInitModal() {
      this.$emit("open-init-modal");
    },

    saveRASAData() {
      console.log(this.inventory);
      trainApi.saveRasaData(this.inventory)
      .then(res => {
        console.log(res);
        })
      .catch(err => {
        console.log(err);
        });
    },
    // train() {
    //   const data = this.managementBoardData[this.templateType]["items"];
    //   if (this.templateType === "FAQ") {
    //     trainApi
    //       .saveFAQData(data)
    //       .then(res => console.log(res))
    //       .catch(err => console.log(err));
    //   } else {
    //     data.forEach(d => (d.examples = d.examples.filter(e => e.content)));
    //     trainApi
    //       .saveRasaData(data)
    //       .then(res => console.log(res))
    //       .catch(err => console.log(err));
    //   }
    // },
  },
};
</script>
