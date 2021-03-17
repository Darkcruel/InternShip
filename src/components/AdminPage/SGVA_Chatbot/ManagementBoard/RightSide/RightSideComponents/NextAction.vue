<template>
  <tr>
    <th class="type2">
      <h4>추후 액션</h4>
    </th>
    <td>
      <select 
            class="" style="min-width:350px; max-width:350px" title="추후 액션" placeholder="hello" v-model="managementBoardData[templateType]['selected']['selectedActionType']">
              <template v-for="(value, index) in inventory['actionType']">
                <option :key="index" v-if="value.title === managementBoardData[templateType]['selected']['selectedActionType']" selected>
                  {{ value }}
                </option>
                <option :key="index" v-else>
                  {{ value }}
                </option>
              </template>
      </select>
      
      <select class="ml13" style="min-width:350px; max-width:350px" title="추후 액션" v-model="managementBoardData[templateType]['selected']['selectedAction']">
        <template v-for="(value, index) in (managementBoardData[templateType]['selected']['selectedActionType'] == '질문형 대화') ? inventory['formData'] : inventory['responseData']">
          <option :key="index" v-if="value.title === managementBoardData[templateType]['selected']['selectedAction']" selected>
            {{ value.title }}
          </option>
          <option :key="index" v-else>
            {{ value.title }}
          </option>
        </template>
      </select>
    </td>
  </tr>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "NextAction",
  props: ["templateType"],
  computed: {
    ...mapState(["managementBoardData"]),
    ...mapState(["inventory"]),
  },
};
</script>
