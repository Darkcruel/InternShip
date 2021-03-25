<template>
    <div class="search">
      <div>
        <a href="#" class="ico_search">검색</a>
      </div>
      <input type="text" class="type5 btn_edit" id="srchFr" placeholder="Search 8 stories" title="조회" @click = "autoComplete">
    </div> 
</template>

<script>
export default {
    name: "Search",
    methods:{
        autoComplete() {
            this.$emit("auto-complete");
        },
    }
}
</script>
