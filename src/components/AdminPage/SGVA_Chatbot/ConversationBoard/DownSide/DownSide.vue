<template>
<div>
  <ListHeader />
  <ListContents @open-init-modal="openInitModal" />
  <ListPage />
</div>
</template>

<script>
import ListHeader from "@/components/AdminPage/SGVA_Chatbot/ConversationBoard/DownSide/DownSideComponents/ListHeader.vue";
import ListContents from "@/components/AdminPage/SGVA_Chatbot/ConversationBoard/DownSide/DownSideComponents/ListContents.vue";
import ListPage from "@/components/AdminPage/SGVA_Chatbot/ConversationBoard/DownSide/DownSideComponents/ListPage.vue";

export default {
  name: "ContentDownSide",
  components: {
    ListHeader,
    ListContents,
    ListPage
  },
  methods: {
    openInitModal() {
      this.$emit("open-init-modal");
    }
  } 
}
</script>

<style>

</style>