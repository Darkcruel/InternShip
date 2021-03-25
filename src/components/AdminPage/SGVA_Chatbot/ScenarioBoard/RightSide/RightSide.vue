<template>
  <div class="box_right type2">
    <div class="search">
      <div>
        <a href="#" class="ico_search">검색</a>
      </div>
      <input
        type="text"
        class="type5 btn_edit"
        id="srchFr"
        placeholder="Search 8 stories"
        title="조회"
      />
    </div>
    <!-- 자동완성 -->
    <div class="title" style="padding: 20px 20px; background-color: white">
      <input
        type="text"
        style="width: 93.5%"
        placeholder="제목을 입력해주세요."
        v-model="managementBoardData[templateType]['selected'].title"
        title="내용"
      />
      <a @click="deleteSelected" class="ico_del"
        ><img
          src="../../../../../assets/Admin/images/ico_del.png"
          alt="삭제하기"
      /></a>
    </div>

    <div class="multi_txt">
      
    <TabSection /> 

    <ScenarioChart />
      
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import dragAndDrop from "@/mixins/dragAndDrop";
import TabSection from "@/components/AdminPage/SGVA_Chatbot/ScenarioBoard/RightSide/RightSideComponents/TabSection.vue";
import ScenarioChart from "@/components/AdminPage/SGVA_Chatbot/ScenarioBoard/RightSide/RightSideComponents/ScenarioChart.vue";

export default {
  name: "ContentRightSide",
  components: {
    TabSection,
    ScenarioChart,
  },
  props: ["templateType"],
  computed: {
    ...mapState(["managementBoardData", "inventory", "scenarioDataKeeper"]),
  },
  mixins: [dragAndDrop]
  ,
  created() {
    if (this.templateType === "Forms") {
      this.setSlotIndex();
    }
  },
  data() {
    return {
      isResponse: true,
      isForm: false,
      isAction: false,
    };
  },
  methods: {
    deleteSelected() {
      let itemIndex = this.managementBoardData[this.templateType][
        "items"
      ].findIndex(
        (q) =>
          q.title ===
          this.managementBoardData[this.templateType]["selected"].title
      );
      let itemsLength = this.managementBoardData[this.templateType]["items"]
        .length;
      let newSelectedIndex = "";

      this.managementBoardData[this.templateType]["items"].splice(itemIndex, 1);
      if (itemsLength > 1) {
        newSelectedIndex =
          itemIndex == 0
            ? this.managementBoardData[this.templateType]["items"][itemIndex]
            : this.managementBoardData[this.templateType]["items"][
                itemIndex - 1
              ];
      }
      this.managementBoardData[this.templateType][
        "selected"
      ] = newSelectedIndex;
    },
  },
};
</script>
