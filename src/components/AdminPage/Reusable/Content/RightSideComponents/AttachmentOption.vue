<template>
  <tr>
    <th class="last">
      <h4>첨부파일</h4>
    </th>
    <td class="last">
      <ul>
        <li class="file_attach">
          <input type="file" id="file" value="file" style="display: none" />
          <input type="text" disabled class="form_file" style="width:50%" />
          <button type="button" class="btn_white btn_file form_file" @click="openFileSelection">
            파일 선택
          </button>
        </li>
      </ul>
    </td>
  </tr>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "AttachmentOption",
  props: ["templateType"],
  computed: {
    ...mapState(["managementBoardData"]),
  },
  data() {
    return {
      fileName: "",
    };
  },
  methods: {
    openFileSelection() {
      this.$el.querySelector("#file").click();
      // console.log(this.$el.querySelector("#file"));
    },
  },
};
</script>
