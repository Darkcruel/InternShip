<template>
  <div class="box_left">
    <div class="list_header">
      <h3>계정관리</h3>
    </div>                	
    <ListContents />
  </div>
</template>

<script>
import ListContents from  "@/components/AdminPage/SGVA_Chatbot/UserAccountBoard/LeftSide/LeftSideComponents/ListContents.vue"

export default {
  name : "ContentLeftSide",
  components: { 
    ListContents },
}
</script>

<style>

</style>