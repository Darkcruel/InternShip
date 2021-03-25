<template>
    <div class="box_right">
        <Search @auto-complete = "autoComplete" />
        <AutoComplete />
        <span class="edit_mask" @click = "CloseAutoComplete"></span>
        <WriteWord/>   
    </div>
</template>

<script>
import AutoComplete from "@/components/AdminPage/SGVA_Chatbot/WordManagementBoard/RightSide/RightSideComponents/AutoComplete.vue";
import WriteWord from "@/components/AdminPage/SGVA_Chatbot/WordManagementBoard/RightSide/RightSideComponents/WriteWord.vue";
import Search from "@/components/AdminPage/SGVA_Chatbot/WordManagementBoard/RightSide/RightSideComponents/Search.vue";
export default {
    name : "ContentRightSide",
    props: ["title", "templateType"],
    components:{
      AutoComplete,
      WriteWord,
      Search
    },
    methods:{
        autoComplete() {
            document.documentElement.style.overflow = "hidden";
            this.$el.querySelector(".edit_view").style = "display: block";
            this.$el.querySelector(".edit_mask").style = "display: block";
        },
        CloseAutoComplete(){
            document.documentElement.removeAttribute("style");
            this.$el.querySelector(".edit_view").style = "";
            this.$el.querySelector(".edit_mask").style = "";
        }
    }
}
</script>
