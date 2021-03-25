<template>
  <div class="inbox_right">
        <!-- <TriggerInputModal /> -->
        <!-- {{ managementBoardData.Scenario.selected.trigger}} -->
        <div
          class="flowgrid drop-zone scenario"
          @drop="
            onDrop(
              $event,
              scenarioDataKeeper,
              managementBoardData.Scenario.selected.steps
            )
          "
          @dragover.prevent
          @dragenter="onDragEnter($event, scenarioDataKeeper)"
        >

          <div type="button" class="trigger" @dblclick="handleDoubleClick" v-on-clickaway="handleClickAway" >
            <div  v-if="!triggerEditMode">
              <div class="trigger-type">{{ handleTriggerName(managementBoardData.Scenario.selected.trigger.actionType) }}</div>
              <div class="trigger-title">{{managementBoardData.Scenario.selected.trigger.object.title}}</div>
            </div>
            <div v-else>
            <div>
            <select class="trigger-input1 " title="추후 액션" placeholder="hello" v-model="managementBoardData.Scenario.selected.trigger.actionType">
              <template v-for="(value, index) in triggerType">
                <option :key="index" :value="value.payload" v-if="value.payload === managementBoardData.Scenario.selected.trigger.actionType" selected>
                  {{value.title}}
                </option>
                <option :key="index" :value="value.payload" v-else>
                  {{ value.title }}
                </option>
              </template>
            </select>
            </div>

            <div>
            <select class="trigger-input2" title="추후 액션" placeholder="hello" v-model="managementBoardData.Scenario.selected.trigger.object">
              <template v-for="(value, index) in listMapper(managementBoardData.Scenario.selected.trigger.actionType)">
                <option :key="index" :value="value" v-if="value.title === managementBoardData.Scenario.selected.trigger.object.title" selected>
                  {{ value.title }} 
                </option>
                <option :key="index" :value="value" v-else>
                  {{ value.title }}
                </option>
              </template>
            </select>
</div>
</div>
      
          
          </div>
          <button
            v-for="item in managementBoardData.Scenario.selected.steps"
            :key="item.id"
            :id="item.id"
            draggable
            :class="[
                        { btn_scenario04: (item.actionType === 'utter' && item.id != managementBoardData.Scenario.selected.steps.length-1)},
                        { btn_scenario05: (item.actionType === 'form' && item.id != managementBoardData.Scenario.selected.steps.length-1) },
                        { btn_scenario06: (item.actionType === 'action' && item.id != managementBoardData.Scenario.selected.steps.length-1) },
                        { btn_scenario08: (item.actionType === '' && item.id != managementBoardData.Scenario.selected.steps.length-1) },
                        { btn_scenario01: (item.actionType === 'utter' && item.id >= managementBoardData.Scenario.selected.steps.length-1)},
                        { btn_scenario02: (item.actionType === 'form' && item.id >= managementBoardData.Scenario.selected.steps.length-1) },
                        { btn_scenario03: (item.actionType === 'action' && item.id >= managementBoardData.Scenario.selected.steps.length-1) },
                        { btn_scenario07: (item.actionType === '' && item.id >= managementBoardData.Scenario.selected.steps.length-1) },
                      ]"
            @dragstart="handleDragStartScenario($event, item, scenarioDataKeeper)"
            @dragend="handleDragEndScenario($event)"
          >
            {{ item.object.title }}
            <img
          src="../../../../../../assets/Admin/images/ico_del.png"
          @click="handleDeleteStep(item, managementBoardData.Scenario.selected.steps)"
          alt="삭제하기"
          style="float:right; margin-right: 3px; background: white; border-radius: 30% "
      />
          </button>
          <div :id="managementBoardData.Scenario.selected.steps.length" class='ghost-button' > </div>
        </div>
        <!-- {{managementBoardData.Scenario.selected.steps}} -->
      </div>
</template>

<script>
import { mapState } from "vuex";
import dragAndDrop from "@/mixins/dragAndDrop";
import { mixin as clickaway } from 'vue-clickaway';
// import InputModal from "@/components/AdminPage/SGVA_Chatbot/Pieces/ButtonModals/TriggerInputModal.vue";
// import initModal from "@/mixins/initModal";

export default {
  name: "ScenarioChart",
  components: {
    // TriggerInputModal,
  },
    mixins: [dragAndDrop, clickaway],
  props: ["templateType"],
  computed: {
    ...mapState(["managementBoardData", "inventory", "scenarioDataKeeper"]),
  },
  created() {
    if (this.templateType === "Forms") {
      this.setSlotIndex();
    }
  },
  data() {
    return {
      triggerType: [{'title' : "의도", 'payload' : ""}, {'title' : "단순 답변", 'payload' : "utter"}, {'title' : "질문형 대화", 'payload' : "form"},{'title' : "커스텀 액션", 'payload' : "action"}],
      triggerEditMode: false, 
      }
  },
  methods: {
    handleClickAway() {
      if (this.triggerEditMode) {
        this.triggerEditMode = false;
      }
    },
    handleDoubleClick() {
      this.triggerEditMode = true; 
    },
    listMapper(type){
      switch (type) {
        case '':
          return this.inventory.intentData;
        case 'utter':
          return this.inventory.responseData;
        case 'form':
          return this.inventory.formData;
        case 'action':
          return this.inventory.actionData;
      }
    },
    handleTriggerName(actionType) {
      switch (actionType) {
        case "utter":
          return "단순 답변";
        case "form":
          return "질문형 대화";
        case "action":
          return "커스텀 액션";
        case "":
          return "의도";
      }
    }
  },
};
</script>
<style scoped>
.ghost-button{
  background: white;
  width: 100%;
  min-height: 120px;
}

.trigger-type, .trigger-title  {
  
  font-weight: bold;
}

.trigger-type {
  color: #484848;
  font-size: 14px;
  margin-bottom: 6px; 
  margin-top: 10px;  
}

.trigger-title {
  font-size: 18px;
  color: #1B2E59;
}

.trigger-input1, .trigger-input2 {
  min-width: 140px; 
  max-width: 140px;
  margin-bottom: 4px;
  height: 22px;
  font-size: 12px;
  border: 1.5px solid  #A0A0A0;
}

.trigger-input1 {
  margin-top: 17px;
}

.trigger{
  position: relative;
  display: block;
  box-sizing: border-box;
  width: 210px;
  height: 90px;
  left:145px;
  margin-bottom:60px;
  text-align: center;
  vertical-align: middle;
  color: #fff;
  border-radius: 50%;
  background:	white;
  /* background:	#BEBEBE; */
  text-align:center;
  align-items: middle;
  border: 5px solid #1B2E59;;
  
}

.trigger:before {
  position: absolute;
  z-index: 1;
  top: 85px;
  width: 2px;
  height: 60px;
  content: '';
  left:100px;
 background: url(../../../../../../assets/Admin/images/center_line.gif);
}

</style>