<template>
<div class="box_right" >
  <Search @auto-complete-modal = "autoCompleteModal" />
      <!-- 자동완성 -->
  <AutoCompleteModal/>
  <span class="edit_mask" @click = "closeModal" ></span>
  <!-- //자동완성 -->
  <ListInfo />
</div>
</template>

<script>
import Search from "@/components/AdminPage/SGVA_Chatbot/UserAccountBoard/RightSide/RightSideComponents/Search.vue"
import AutoCompleteModal from "@/components/AdminPage/SGVA_Chatbot/UserAccountBoard/RightSide/RightSideComponents/AutoCompleteModal.vue"
import ListInfo from "@/components/AdminPage/SGVA_Chatbot/UserAccountBoard/RightSide/RightSideComponents/ListInfo.vue"

export default {
  name : "ContentRightSide",
  components: { 
    Search ,
    AutoCompleteModal,
    ListInfo
  },
  methods: {
    autoCompleteModal() {
      document.documentElement.style.overflow = "hidden";
      this.$el.querySelector(".edit_view").style = "display: block";
      this.$el.querySelector(".edit_mask").style = "display: block";
    },
    closeModal() {
      document.documentElement.removeAttribute("style");
      this.$el.querySelector(".edit_view").style = "";
      this.$el.querySelector(".edit_mask").style = "";
    }
  }
}
</script>

<style>

</style>