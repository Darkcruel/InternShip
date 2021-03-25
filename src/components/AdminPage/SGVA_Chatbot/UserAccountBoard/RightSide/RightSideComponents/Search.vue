<template>
  <div class="search">
    <select title="선택">
      <option value="">ID</option>
      <option value="">이름</option>
    </select>
    <a href="#" class="ico_search type2">검색</a>
    <input type="text" class="type5 btn_edit" id="srchFr" placeholder="Search 8 stories" title="조회" @click = "autoCompleteModal">
  </div>
</template>

<script>


export default {
  name : "Search",
  components: { 
    //AutoCompleteModal },
  },
  methods: {
    autoCompleteModal() {
      this.$emit("auto-complete-modal");
    },
  }
}
</script>

<style scoped>
select {
  width:100px;
  margin-right:20px
}
</style>