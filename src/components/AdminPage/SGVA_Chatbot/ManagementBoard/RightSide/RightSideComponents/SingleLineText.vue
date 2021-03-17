<template>
  <tr>
    <th>
      <h4>항목별 질문</h4>
    </th>
    <td>
      <ul>
        <li>
          <input
           
            type="text"
            style="width:93.5%"
            title="내용"
            v-model="inventory.slotQuestions[indexKeeper.slotNameIndex]['examples'][0]['content']"
          />
        </li>
      </ul>
    </td>
  </tr>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "NextAction",
  props: ["templateType"],
  computed: {
    ...mapState(["managementBoardData", "inventory", "indexKeeper"]),
  },
};
</script>
