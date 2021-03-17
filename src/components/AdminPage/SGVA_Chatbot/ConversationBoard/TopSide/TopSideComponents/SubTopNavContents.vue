<template>
  <div class = "search_box detail_search" >
    <ul>
      <li>
        <select style="width:140px;" title="디바이스 선택" class="mr15">
          <option value="">디바이스 선택</option>
          <option value="">전체</option>
          <option value="">SGDefault</option>
        </select>
        <select style="width:140px;" title="Intent 선택" class="mr15">
          <option value="">Intent 선택</option>
          <option value="">전체</option>
          <option value="">SGDefault</option>
        </select>
        <input type="text" id="srchFr" style="width:140px;" placeholder="조회기간" title="조회일 시작일자" onClick="openCalendar(document.getElementById('srchFr'));">
        <a href="#" class="ico_cal" onClick="openCalendar(document.getElementById('srchFr'));">달력</a>
        <em>~</em>
        <input type="text" id="srchTo" style="width:140px;" placeholder="조회기간" title="조회일 종료일자" onClick="openCalendar(document.getElementById('srchTo'));">
        <a href="#" class="ico_cal" onClick="openCalendar(document.getElementById('srchTo'));">달력</a>
        <button type="button" class="btn_date ml13">당일</button>
        <button type="button" class="btn_date">1개월</button>
        <button type="button" class="btn_date">1년</button>
      </li>
    </ul>
    <ConvSearchButtons  />
    
  </div>
</template>

<script>
import ConvSearchButtons from "@/components/AdminPage/SGVA_Chatbot/Pieces/ConvSearchButtons.vue";

export default {
  name: "SubTopNavContents",
  components: {
    ConvSearchButtons,
  },
}
</script>

<style>

</style>