<template>
  <tr>
    <th>
      <h4>답변 처리 방식</h4>
    </th>
    <td>
      <br />
      <br />
      <ul>
        <div v-for="(content, cIndex) in managementBoardData[templateType]['selected']['selectedSlot']['content']" :key="cIndex">
          <li>
            <!-- Decide whether it is from text or intent -->
            <select 
            class="" style="min-width:300px; max-width:300px" title="추후 액션" placeholder="hello" v-model="content['type']">
              <template v-for="(value, index) in inventory['entryType']">
                <option :key="index" v-if="value === content['type']" selected>
                  {{ value }}
                </option>
                <option :key="index" v-else>
                  {{ value }}
                </option>
              </template>
            </select>

            <!-- Decide what intent -->
            <select 
            v-if="content['type'] == '의도별 값 지정'"
            class="ml13" style="min-width:300px; max-width:300px" title="추후 액션" placeholder="hello" v-model="content['intent']">
              <template v-for="(value, index) in inventory['intentData']">
                <option :key="index" v-if="value.title === content['intent']" selected>
                  {{ value.title }}
                </option>
                <option :key="index" v-else>
                  {{ value.title }}
                </option>
              </template>
            </select>
            
            <!-- What value to map the intent to  -->
            <input v-if="content['type'] == '의도별 값 지정'" type="text" class="ml13" style="width:25%" title="내용" placeholder="지정 값" v-model="content['value']" />
            <a @click="deleteByName(managementBoardData[templateType]['selected']['selectedSlot']['content'], content['value'], 'value')" class="ico_del">삭제</a>
          </li>
        </div>
        <a @click="addPushItem(managementBoardData[templateType]['selected']['selectedSlot']['content'], 'slotMapper')" class="ico_add">버튼 옵션 추가</a>
      </ul>
    </td>
  </tr>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import deleteByName from "@/mixins/deleteByName";
import addPushItem from "@/mixins/addPushItem";

export default {
  name: "Slot Value Mapper",
  mixins: [deleteByName, addPushItem],
  props: ["templateType"],
  computed: {
    ...mapState(["managementBoardData", "inventory", "mapperKeeper"]),
  },
  methods: {
    ...mapMutations(["addButton"]),
  },
};
</script>
