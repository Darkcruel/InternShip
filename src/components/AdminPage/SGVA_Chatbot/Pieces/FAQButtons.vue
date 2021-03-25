<template>
  <div class="btn_right">
    <a class="btn_white" @click="downloadBertFile">다운로드</a>
    <a class="btn_white" @click="openUploadFAQ">업로드</a>
    <input
      type="file"
      style="display: none"
      ref="fileInput"
      @change="uploadFAQ"
    />
    <a class="btn_white" @click="saveFAQData">저장하기</a>
    <a class="btn_refresh">FAQ학습</a>
  </div>
</template>

<script>
import { mapState } from "vuex";
import * as trainApi from "@/apis/admin.js";
import axios from "axios";

export default {
  name: "FAQButtons",
  props: ["templateType"],
  computed: {
    ...mapState(["managementBoardData", "inventory"]),
  },
  methods: {
    // clickDownload
    saveFAQData() {
      trainApi
        .saveFAQData(this.managementBoardData.FAQ.items)
        .then((res) => {
          console.log("FAQ Data Sent");
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    },

    downloadBertFile() {
      axios({
        url: "http://192.168.0.240:9090/bert/getFile",
        method: "GET",
        responseType: "blob",
      }).then((response) => {
        var fileURL = window.URL.createObjectURL(new Blob([response.data]));
        var fileLink = document.createElement("a");

        fileLink.href = fileURL;
        fileLink.setAttribute("download", "FAQ.xlsx");
        document.body.appendChild(fileLink);

        fileLink.click();
      });
    },

    openUploadFAQ() {
      this.$refs.fileInput.click();
    },

    uploadFAQ(event) {
      const files = event.target.files;
      const file = files[0];
      let formData = new FormData();
      formData.append('file', file);

      axios({
        url: "http://192.168.0.240:9090/bert/setFile",
        method: "Post",
        data: formData,
        headers: { "Content-Type": "multipart/form-data"},
      })
        .then((response) => {
          console.log(response);
        })
        .catch((err) => console.log(err));
    },
  },
};
</script>
