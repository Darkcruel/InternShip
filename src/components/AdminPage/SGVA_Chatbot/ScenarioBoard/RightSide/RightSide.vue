<template>
  <div class="box_right">
    <div class="search">
      <div>
        <a href="#" class="ico_search">검색</a>
      </div>
      <input type="text" class="type5" id="srchFr" v-model="intentValue" autocomplete="off" placeholder="Search 8 stories" title="조회" />
    </div>
    <div class="title" style="padding:20px 20px;">
      <input type="text" style="width:93.5%" placeholder="제목을 입력해주세요." v-model="managementBoardData[templateType]['selected'].title" title="내용" />
      <a @click="deleteSelected" class="ico_del"><img src="../../../../../assets/Admin/images/ico_del.png" alt="삭제하기"/></a>
    </div>

    <div class="faq_write">
      <table class="tbl_view type2">
        <colgroup style="border: none">
          <col style="width:160px" />
          <col />
        </colgroup>
        <tbody>
          <!-- <DataValidation /> -->
          <DnD />
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import DnD from "@/components/AdminPage/SGVA_Chatbot/ScenarioBoard/RightSide/RightSideComponents/DnDTesting.vue";
// import DataValidation from "@/components/AdminPage/SGVA_Chatbot/ScenarioBoard/RightSide/RightSideComponents/DataValidation.vue";


export default {
  name: "ContentRightSide",
  components: {
  DnD
  // DataValidation,
  },
  props: ["templateType"],
  computed: {
    ...mapState(["managementBoardData", "inventory", "indexKeeper"]),
  },
  created() {
    if (this.templateType === "Forms") {
      this.setSlotIndex();
    }
  },
  data() {
    return {
      intentValue: "",
    };
  },
  methods: {
    deleteSelected() {
      let itemIndex = this.managementBoardData[this.templateType]["items"].findIndex(
        q => q.title === this.managementBoardData[this.templateType]["selected"].title,
      );
      let itemsLength = this.managementBoardData[this.templateType]["items"].length;
      let newSelectedIndex = "";

      this.managementBoardData[this.templateType]["items"].splice(itemIndex, 1);
      if (itemsLength > 1) {
        newSelectedIndex =
          itemIndex == 0
            ? this.managementBoardData[this.templateType]["items"][itemIndex]
            : this.managementBoardData[this.templateType]["items"][itemIndex - 1];
      }
      this.managementBoardData[this.templateType]["selected"] = newSelectedIndex;
    },
    setSlotIndex() {
      this.$store.commit("setSlotIndex", this.templateType);
    },
    // 검색 기능 (지우지 마세요)
    // findSearchRecommend(event) {
    //   if (event.keyCode === 13) return;
    //   let intentValue = this.intentValue;
    //   console.log(
    //     this.managementBoardData[this.templateType]["items"].filter(i => {
    //       return i.title.includes(intentValue);
    //     }),
    //   );
    // },
    // searchIntent() {
    //   if (this.managementBoardData[this.templateType]["items"].some(i => i.title === this.intentValue)) {
    //     this.managementBoardData[this.templateType]["selected"] = this.managementBoardData[this.templateType]["items"].find(i => i.title === this.intentValue);
    //   }
    // },
  },
};
</script>
