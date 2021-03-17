<template>
  <span class = "right">
    <a href="#" class="btn_search">조회</a>
    <a href="#" class="btn_detail"  @click = "openSearchModal">상세조회</a>
    <SearchboxModal @open-search-modal = "openSearchModal" @close-search-modal = "closeSearchModal" />
  </span>
</template>

<script>
import SearchboxModal from "@/components/AdminPage/SGVA_Chatbot/ConversationBoard/TopSide/TopSideComponents/SearchboxModal.vue";
import { mapState } from "vuex";

export default {
  name: "ConvSearchButtons",
  props: ["templateType"],
  components:{
    SearchboxModal
  },
  computed: {
    ...mapState(["managementBoardData"]),
  },
  methods: {
    openSearchModal() {
      document.documentElement.style.overflow = "hidden";
      this.$el.querySelector(".detail_view").style = "display: block";
    },
    closeSearchModal() {
      document.documentElement.removeAttribute("style");
      this.$el.querySelector(".detail_view").style = "";
    },
  }
}
</script>

<style>

</style>