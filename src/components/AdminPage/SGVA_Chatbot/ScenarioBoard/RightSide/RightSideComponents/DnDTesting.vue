<template>
  <div>
    <!-- {{ managementBoardData.Scenario.selected}} -->
    <!-- {{ scenarioDataKeeper }} -->
    <!-- {{ inventory }} -->

    <div
      class="drop-zone scenario"
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
      <div
        :id="item.id"
        v-for="item in managementBoardData.Scenario.selected.steps"
        :key="item.id"
        class="drag-el"
        draggable
        @dragstart="handleDragStartScenario($event, item, scenarioDataKeeper)"
        @dragend="handleDragEndScenario($event, scenarioDataKeeper)"
      >
        <!-- {{ item.object.title }} -->
        {{ item.object.title }}
      </div>
    </div>
    <div class="drop-zone inventory">
      <div
        class="drag-el"
        v-for="item in inventory.intentData"
        :key="item.id"
        draggable
        @dragstart="handleDragStartInventory($event, item, scenarioDataKeeper)"
        @dragend="handleDragEndInventory($event, scenarioDataKeeper)"
        @dragleave="onDragLeave($event, scenarioDataKeeper)"
      >
        {{ item.title }}
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "DnD",
  components: {},
  props: ["templateType"],
  data() {
    return {};
  },

  computed: {
    ...mapState([
      "managementBoardData",
      "inventory",
      "indexKeeper",
      "scenarioDataKeeper",
    ]),
    listOne() {
      return this.items.filter((item) => item.list === 1);
    },
    listTwo() {
      return this.items.filter((item) => item.list === 2);
    },
  },

  methods: {
    handleDragStartScenario: (evt, item, stateManager) => {
      stateManager.selectedStepObjectStart = [item, "rearrange step"];
      stateManager.selectedStepObjectIndex = item.id;
      evt.dataTransfer.dropEffect = "move";
      evt.dataTransfer.effectAllowed = "move";
    },
    handleDragEndScenario: (evt, stateManager) => {
      stateManager.selectedStepObjectStart = "";
    },

    handleDragStartInventory: (evt, item, stateManager) => {
      stateManager.selectedStepObjectStart = [item, "new step"];
      evt.dataTransfer.dropEffect = "move";
      evt.dataTransfer.effectAllowed = "move";
    },
    handleDragEndInventory: (evt, stateManager) => {
      stateManager.selectedStepObjectStart = "";
    },
    onDrop(evt, stateManager, refList) {
      evt.preventDefault();
      let newStepObject = stateManager.selectedStepObjectEnd;
      let stepType = newStepObject[1];
      let step = newStepObject[0];
      stateManager.selectedStepObjectEnd = "";

      let targetId = evt.target.id;
      
      if (targetId == "") { 
        // Notify the user that, the operation didnt work
        return ; 
      }
      let newStepObj = { type: "", id: targetId };
      newStepObj["object"] = step;

      if (stepType === "new step") {
        for (let i = 0; i < refList.length; i++) {
          if (i >= targetId) {
            refList[i].id = Number(refList[i].id) + 1;
          }
        }
        refList.splice(targetId, 0, newStepObj);
      } else {
        let temp = refList[stateManager.selectedStepObjectIndex];
        let selectedIndex = Number(stateManager.selectedStepObjectIndex);

        temp.id = targetId;
        let start = 0;
        let end = 0;

        if (selectedIndex < targetId) {
          end = targetId;
          start = selectedIndex + 1;

          for (start; start <= end; start++) {
            refList[start].id = Number(refList[start].id) - 1;
            refList[start - 1] = refList[start];
            
          }
        } else {
          start = selectedIndex - 1;
          end = targetId;
          for (start; start >= end; start--) {
            refList[start].id = Number(refList[start].id) + 1;
            refList[start + 1] = refList[start];
          }
        }

        refList[targetId] = temp;
      }
    },

    onDragEnter(evt, stateManager) {
      evt.preventDefault();
      stateManager.selectedStepObjectEnd = stateManager.selectedStepObjectStart;
    },

    onDragLeave(evt, stateManager) {
      evt.preventDefault();
      stateManager.selectedStepObjectEnd = "";
    },
  },

  created() {},
};
</script>
<style scoped>
.drop-zone {
  background-color: #eee;
  margin-bottom: 10px;
  padding: 10px;
}

.drag-el {
  background-color: #fff;
  margin-bottom: 10px;
  padding: 5px;
}
</style>
