<template>
  <tr>
    <th class="last">
      <h4>첨부파일</h4>
    </th>
    <td class="last">
      <ul>
        <li class="file_attach">
          <select
            class=""
            style="min-width:300px; max-width:300px"
            title="추후 액션"
            placeholder="첨부파일 타입"
            v-model="managementBoardData[templateType]['selected']['attachment']['type']"
          >
            <template v-for="(value, index) in inventory['attachmentType']">
              <option :key="index" v-if="value === managementBoardData[templateType]['selected']['attachment']['type']" selected>
                <!-- {{ managementBoardData[templateType]['selected']['attachment']['type'] === value }} -->
                {{ value }}
              </option>
              <option :key="index" v-else>
                {{ value }}
              </option>
            </template>
          </select>

          <span v-if="managementBoardData[templateType]['selected']['attachment']['type'] === 'URL'">
            <input v-model="managementBoardData[templateType]['selected']['attachment']['payload']" class="ml13" style="width:50%" />
          </span>

          <span v-else-if="managementBoardData[templateType]['selected']['attachment']['type']">
            <div id="selectedFile" @click="clickInputFile"> {{ selectedFile }}</div>

            <input ref="file" type="file" class="ml13 form_file" style="display: none" @change="selectFile" />
            <button type="button" class="ml13 btn_white btn_file form_file" @click="clickInputFile">파일 선택</button>            
          </span>
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
    ...mapState(["managementBoardData", "inventory"]),
  },
  data() {
    return {
      selectedFile: "",
      editing: false,
      editingInput: "",
    };
  },
  methods: {

    clickEditable(input){
      this.editingInput = input;
      this.editing = true;
    },

    editableBlurAway() {
      if (this.editingInput == "") {
        this.editing = false;
      }
      this.editingInput = "";
    },

    clickInputFile() {
      this.$refs.file.click();
    },
    selectFile(event) {
      this.selectedFile = event.target.files[0].name;
    },
  },
};
</script>

<style scoped>
#selectedFile {
  width: 300px;
  height: 40px;
  border: 1px solid #c9c9c9;
  display: inline-block;
  vertical-align: middle;
  margin-left: 10px;
  padding-bottom: 9px;
  padding-top: 9px;
  padding-left: 12px;
}
</style>
