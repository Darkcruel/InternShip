<template>
  <tr>
    <th>
      <h4>버튼 옵션</h4>
    </th>
    <td>
      <!-- {{ managementBoardData[templateType]["selected"]["selectedSlot"] }}
      <br />
      <br />
      {{ inventory["slotQuestions"] }}  -->
      <ul>
        <div v-for="(buttonItem, bIndex) in buttonProp" :key="bIndex">
          <li>
            <input type="text" style="width:25%" title="내용" v-model="buttonItem['content']['title']" />
            <select class="ml13" style="min-width:400px; max-width:400px" title="추후 액션" placeholder="hello" v-model="buttonItem['content']['payload']">
              <template v-for="(value, index) in inventory['intentData']">
                <option :key="index" v-if="value.title === buttonItem['content']['payload']" selected>
                  {{ value.title }}
                </option>
                <option :key="index" v-else>
                  {{ value.title }}
                </option>
              </template>
            </select>
            <a @click="deleteById(buttonProp, buttonItem.id)" class="ico_del">삭제</a>
          </li>
        </div>
        <a @click="addExample(buttonProp, true)" class="ico_add">버튼 옵션 추가</a>
      </ul>
    </td>
  </tr>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import deleteById from "@/mixins/deleteById";
import addExample from "@/mixins/addExample";

export default {
  name: "ButtonOption",
  mixins: [deleteById, addExample],
  props: ["templateType", "buttonProp"],
  computed: {
    ...mapState(["managementBoardData", "inventory"]),
  },
  methods: {
    ...mapMutations(["addButton"]),
  },
};
</script>
