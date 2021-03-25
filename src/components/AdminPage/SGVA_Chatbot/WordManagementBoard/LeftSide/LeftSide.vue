<template>
    <div class="box_left">
        <div class="list_header">
            <h3>{{ managementBoardData[templateType]["pageTitle"] }}</h3>
        </div>
        <BanWordList v-show="templateType == 'BanWord'" />
    </div>
</template>

<script>
import { mapState } from "vuex";
import BanWordList from "@/components/AdminPage/SGVA_Chatbot/WordManagementBoard/LeftSide/LeftSideComponents/BanWordList.vue";
export default {
    name: "ContentLeftSide",
    props: ["title", "templateType"],
    computed: {
    ...mapState(["managementBoardData"]),
    },
    components:{
        BanWordList
    }
    
}
</script>

<style>

</style>