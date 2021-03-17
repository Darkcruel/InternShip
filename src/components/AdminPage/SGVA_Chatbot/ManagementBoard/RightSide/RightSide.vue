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

    <!-- Forms Only -->
    <SubSelectionTable v-show="templateType == 'Forms'" :templateType="templateType" />

    <div class="faq_write">
      <table class="tbl_view type2">
        <colgroup style="border: none">
          <col style="width:160px" />
          <col />
        </colgroup>
        <tbody>
          <!-- Forms Only -->
          <SlotRow v-show="templateType == 'Forms'" :templateType="templateType" />
          <SingleLineText v-show="templateType == 'Forms'" :templateType="templateType" />
          <SlotValueMapper v-if="templateType == 'Forms'" :templateType="templateType" />
          <ButtonOption
            v-if="templateType == 'Forms'"
            :templateType="templateType"
            :buttonProp="inventory.slotQuestions[indexKeeper.slotNameIndex]['buttons']"
          />

          <!-- FAQ Only -->
          <RecommendedAnswer v-if="templateType == 'FAQ'" :templateType="templateType" />
          <NextAction v-if="templateType == 'FAQ'" :templateType="templateType" />

          <!-- Apply to All except Forms -->
          <ExamplesRow v-if="templateType != 'Forms'" :templateType="templateType" />

          <!-- Responses Only -->
          <ButtonOption
            v-if="templateType == 'Responses'"
            :templateType="templateType"
            :buttonProp="managementBoardData[templateType]['selected']['buttons']"
          />
          <AttachmentOption v-show="templateType == 'Responses'" :templateType="templateType" />
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import ExamplesRow from "@/components/AdminPage/SGVA_Chatbot/ManagementBoard/RightSide/RightSideComponents/ExamplesRow.vue";
import RecommendedAnswer from "@/components/AdminPage/SGVA_Chatbot/ManagementBoard/RightSide/RightSideComponents/RecommendedAnswer.vue";
import NextAction from "@/components/AdminPage/SGVA_Chatbot/ManagementBoard/RightSide/RightSideComponents/NextAction.vue";
import AttachmentOption from "@/components/AdminPage/SGVA_Chatbot/ManagementBoard/RightSide/RightSideComponents/AttachmentOption.vue";
import ButtonOption from "@/components/AdminPage/SGVA_Chatbot/ManagementBoard/RightSide/RightSideComponents/ButtonOption.vue";
import SlotValueMapper from "@/components/AdminPage/SGVA_Chatbot/ManagementBoard/RightSide/RightSideComponents/SlotValueMapper.vue";
import SlotRow from "@/components/AdminPage/SGVA_Chatbot/ManagementBoard/RightSide/RightSideComponents/SlotRow.vue";
import SubSelectionTable from "@/components/AdminPage/SGVA_Chatbot/ManagementBoard/RightSide/RightSideComponents/SubSelectionTable.vue";
import SingleLineText from "@/components/AdminPage/SGVA_Chatbot/ManagementBoard/RightSide/RightSideComponents/SingleLineText.vue";

export default {
  name: "ContentRightSide",
  components: {
    ExamplesRow,
    RecommendedAnswer,
    NextAction,
    ButtonOption,
    AttachmentOption,
    SlotValueMapper,
    SlotRow,
    SubSelectionTable,
    SingleLineText,
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
