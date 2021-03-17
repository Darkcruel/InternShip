<template>
<div>
  <div class="content_header type2">
    <h3>{{ managementBoardData[templateType]["pageTitle"] }}</h3>
    <span>{{ managementBoardData[templateType]["pageSubtitle"] }}</span>
    <a href="#" v-on:click = "isActive" class = "right btn_detail_search"><img src="../../../../assets/Admin/images/ico_arrow.png" alt="검색조건"></a>
  </div>
  <transition name = "slide-fade">
    <SubTopNavContents :templateType="templateType" v-show = "show" />
  </transition>
  
</div>
  
</template>

<script>
import SubTopNavContents from '@/components/AdminPage/SGVA_Chatbot/ConversationBoard/TopSide/TopSideComponents/SubTopNavContents.vue';
import { mapState } from "vuex";

export default {
  name: "SubTopNavConv",
  props: ["templateType"],
  components: {
    SubTopNavContents,
  },
  data : function() {
    return {
      show : true,
    }
  },
  computed: {
    ...mapState(["managementBoardData"]),
  },
  methods: {
    isActive : function() {
      this.show = !this.show;
    },
  },
};
</script>

<style>
.slide-fade-enter-active {
  transition: all .3s ease;
}
.slide-fade-leave-active {
  transition: all .3s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}
.slide-fade-enter, .slide-fade-leave-to
/* .slide-fade-leave-active below version 2.1.8 */ {
  transform: translateY(-20px);
  opacity: 0;
}

</style>