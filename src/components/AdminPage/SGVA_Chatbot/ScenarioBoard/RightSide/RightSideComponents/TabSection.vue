<template>
<div class="inbox_left">
        <div class="tab_menu">
          <ul class="tab tabs">
            <!-- TAB HEADS -->
            <li :class="{ on: isIntent }" @click="selectTab('')">
              <a>의도</a>
            </li>
            <li :class="{ on: isResponse }" @click="selectTab('utter')">
              <a>답변</a>
            </li>
            <li :class="{ on: isForm }" @click="selectTab('form')">
              <a>대화</a>
            </li>
            <li :class="{ on: isAction }" @click="selectTab('action')">
              <a>액션</a>
            </li>
            

            <!-- INPUT -->
            <input
              type="text"
              style="width: 87%"
              placeholder="입력해주세요."
              title="아이디"
            />
            <a href="#" class="ico_search02"></a>
          </ul>

          <div class="tab_container inventory">
            <div class="cont">
              <!-- tab01 -->
              <div>
                <ul>
                  <li
                    v-for="item in scenarioDataKeeper.selectedTab"
                    :key="item.id"
                  >
                    <button
                      type="button"
                      :class="[
                        { btn_scenario01: isResponse },
                        { btn_scenario02: isForm },
                        { btn_scenario03: isAction },
                        { btn_scenario07: isIntent },
                      ]"
                      draggable
                      @dragstart="
                        handleDragStartInventory(
                          $event,
                          item,
                          scenarioDataKeeper
                        )
                      "
                      @dragend="
                        handleDragEndInventory($event, scenarioDataKeeper)
                      "
                    >
                      {{ item.title }}
                    </button>
                  </li>
                </ul>
              </div>
              <!-- //tab01 -->
            </div>
          </div>
        </div>
      </div>
</template>

<script>
import { mapState } from "vuex";
import dragAndDrop from "@/mixins/dragAndDrop";


export default {
  name: "TabSection",
  components: {
  },
  mixins: [dragAndDrop],
  data() {
    return {
      isIntent: true,
      isResponse: false,
      isForm: false,
      isAction: false,
    };
  },
  props: ["templateType"],
  computed: {
    ...mapState(["managementBoardData", "inventory", "scenarioDataKeeper"]),
  },
  methods: {
      selectTab(selectIndex) {
      this.scenarioDataKeeper.selectedTabType = selectIndex;
      switch (selectIndex) {
        case "utter":
          this.scenarioDataKeeper.selectedTab = this.inventory.responseData;
          this.isResponse = true;
          this.isForm = false;
          this.isAction = false;
          this.isIntent = false;
          break;

        case "form":
          this.scenarioDataKeeper.selectedTab = this.inventory.formData;
          this.isResponse = false;
          this.isForm = true;
          this.isAction = false;
          this.isIntent = false;
          break;

        case "action":
          this.scenarioDataKeeper.selectedTab = this.inventory.actionData;
          this.isResponse = false;
          this.isForm = false;
          this.isAction = true;
          this.isIntent = false;
          break;
        
        case "":
          this.scenarioDataKeeper.selectedTab = this.inventory.intentData;
          this.isResponse = false;
          this.isForm = false;
          this.isAction = false;
          this.isIntent = true;
          break;
      }
    },
    
  },
};
</script>
