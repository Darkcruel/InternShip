<template>
  <tr>
    <th class="type2">
      <h4>추천 답변</h4>
    </th>
    <td>
      <textarea v-model="managementBoardData[templateType]['selected']['answer']" style="width:100%; height:110px;"></textarea>
    </td>
  </tr>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "RecommendedAnswer",
  props: ["templateType"],
  computed: {
    ...mapState(["managementBoardData"]),
  },
};
</script>
