<template>
    <div>
        <TopNav />
        <div id="container">
            <SideBar />
            <WordManagementBoard :templateType="templateType"/>
            <Footer />
        </div>
    </div>
</template>

<script>
import TopNav from "@/components/AdminPage/Common/TopNav/TopNav.vue";
import SideBar from "@/components/AdminPage/Common/SideBar/SideBar.vue";
import Footer from "@/components/AdminPage/Common/footer/Footer.vue";
import WordManagementBoard from "@/components/AdminPage/SGVA_Chatbot/WordManagementBoard/WordManagementBoard.vue";
export default {
    name: "BanWordView",
    components:{
        TopNav,
        SideBar,
        Footer,
        WordManagementBoard
    },
    data() {
        return {
        templateType: "BanWord",
        };
    },
}
</script>

<style>

</style>