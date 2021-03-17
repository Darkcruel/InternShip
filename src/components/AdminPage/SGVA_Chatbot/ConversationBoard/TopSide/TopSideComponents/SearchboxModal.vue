<template>
   <div class="detail_view">
    <h3 style="text-align : left">상세조회</h3>
    <ul class="detail1">
      <li>
        <input type="text" placeholder="아이디" title="아이디">
      </li>
      <li>
        <select title="신뢰도 여부">
          <option value="">신뢰도 여부</option>
          <option value="">신뢰</option>
          <option value="">비신뢰</option>
        </select>
      </li>
    </ul>
    <div class="btn"><a href="#" class="btn_search">조회</a></div>
    <button type="button" class="layer_close" @click="closeSearchModal" >닫기</button>
    </div>
</template>

<script>
export default {
  methods: {
    closeSearchModal() {
      this.$emit("close-search-modal");
    },
  },
};
</script>

<style scoped>

h3{
  text-align : left
}

</style>