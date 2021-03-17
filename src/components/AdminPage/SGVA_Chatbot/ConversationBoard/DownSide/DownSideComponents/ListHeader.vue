<template>
  <div class = "list_header">
    <h4>대화 이력 조회 내역</h4>
    <span>각 행을 클릭하여 상세정보를 조회할 수 있습니다.</span>
    <ListOption />
  </div>
</template>

<script>
import ListOption from "@/components/AdminPage/SGVA_Chatbot/ConversationBoard/DownSide/DownSideComponents/ListOption.vue";
export default {
  name: "ListHeader",
  components:{
    ListOption
  }
}
</script>