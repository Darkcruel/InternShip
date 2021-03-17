<template>
  <tr>
    <th>
      <h4>항목명</h4>
    </th>
    <td>
      <ul>
        <li>
          <input
            id="slotName"
            type="text"
            style="width:25%"
            title="내용"
            v-model="managementBoardData[templateType]['selected']['selectedSlot'].slotName"
            @input="inputValue($event)"
          />

        </li>
      </ul>
    </td>
  </tr>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "SlotRow",
  props: ["templateType"],
  computed: {
    ...mapState(["managementBoardData", "inventory", "indexKeeper"]),
  },
  data() {
    return {
      inputField: "",
    };
  },
  methods: {
    inputValue(event) {
      this.inventory.slotQuestions[this.indexKeeper.slotNameIndex]["title"] = this.managementBoardData[this.templateType]["selected"]["selectedSlot"].slotName =
        event.target.value;
    },
  },
};
</script>
