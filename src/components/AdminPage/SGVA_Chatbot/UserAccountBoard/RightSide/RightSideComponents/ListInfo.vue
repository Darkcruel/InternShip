<template>
  <div class="write">
      <!-- list -->
    <table class="tbl_list">
      <caption>사용자 등록</caption>
      <colgroup>
        <col style="width:12%">
        <col>
      </colgroup>
      <thead>
          <tr>
              <th scope="col">ID</th>
              <td class="tleft"><input type="text" style="width:98%" title="ID"></td>
          </tr>
      </thead>
      <tbody>
        <tr>
          <th scope="col">비밀번호</th>
          <td class="tleft"><input type="password" style="width:98%" title="비밀번호"></td>
        </tr>
        <tr>
          <th scope="col">비밀번호 확인</th>
          <td class="tleft"><input type="password" style="width:98%" title="비밀번호 확인"></td>
        </tr>
        <tr>
          <th scope="col">닉네임</th>
          <td class="tleft"><input type="text" style="width:98%" title="닉네임"></td>
        </tr>
        <tr>
          <th scope="col">이메일</th>
          <td class="tleft"><input type="text" style="width:98%" title="이메일"></td>
        </tr>
        <tr>
          <th scope="col">전화번호</th>
          <td class="tleft"><input type="text" style="width:98%" title="전화번호"></td>
        </tr>
        <tr>
          <th scope="col">등록일자</th>
          <td class="tleft">2020-03-16</td>
        </tr>
        <tr>
          <th scope="col">등록자 ID</th>
          <td class="tleft">Admin</td>
        </tr>
      </tbody>
    </table>
    <div class="btn_right type4">        
      <a href="#" class="btn_black">삭제하기</a>
      <a href="#" class="btn_save">저장하기</a>
    </div>
              <!-- //list -->                		
  </div>
</template>

<script>
export default {
  name : "ListInfo"
}
</script>

<style>

</style>