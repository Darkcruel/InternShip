<template>
  <span class = "right">
    <a href="#" class="btn_search">조회</a>
    <a href="#" class="btn_detail"  @click = "openSearchModal">상세조회</a>
    <SearchboxModal @open-search-modal = "openSearchModal" @close-modal = "closeModal" />
  </span>
</template>

<script>
import SearchboxModal from "@/components/AdminPage/SGVA_Chatbot/ConversationBoard/TopSide/TopSideComponents/SearchboxModal.vue";
import initModal from "@/mixins/initModal";
import { mapState } from "vuex";

export default {
  name: "ConvSearchButtons",
  props: ["templateType"],
  components:{
    SearchboxModal
  },
  mixins: [initModal],
  computed: {
    ...mapState(["managementBoardData"]),
  },
}
</script>

<style>

</style>