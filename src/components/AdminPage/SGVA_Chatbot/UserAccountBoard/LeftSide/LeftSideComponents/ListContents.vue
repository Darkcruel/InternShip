<template>
  <div class="list">
    <!-- list -->
    <table class="tbl_list">
      <caption>사용자 리스트</caption>
      <colgroup>
        <col style="width:60%">
        <col>
      </colgroup>
      <thead>
        <tr>
          <th scope="col">ID</th>
          <th scope="col">이름</th>
        </tr>
      </thead>
      <tbody>
        <tr class="link" v-for = "item in lists" v-bind:key = "item.id" v-on:click = "listClick">
          <td>{{ item.memberId }}</td>
          <td>{{ item.memberName }}</td>
        </tr>
        
      </tbody>  
    </table>
    <!-- //list -->
      <!-- paginate 
      <div class="paginate">
          <a href="#" class="pre_end">처음</a>
          <a href="#" class="pre">이전</a>
          <a href="#" class="active">1</a>
          <a href="#">2</a>
          <a href="#" class="next">다음</a>
          <a href="#" class="next_end">마지막</a>
      </div>
        //paginate -->
  </div>
</template>

<script>
import axios from "axios";

export default {
  name : "ListContents",
  data : function () {
    return {
      lists: []
    }
  },
  created() {
    this.showList()
  },

  methods : {
    showList: function () {
      axios
        .post("/admin/account/leftlist")
        .then(response => {
          var content = response.data.leftList;
          this.lists = content;
          console.log(content);
        });
    },
    listClick: function() {
      alert("ddd");
    }
  },
}
</script>

<style>

</style>