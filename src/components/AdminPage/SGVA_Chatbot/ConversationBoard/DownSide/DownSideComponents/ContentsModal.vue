<template>
  <div id="detail_info" class="layerwrap type6">
    <h3>대화이력조회 상세보기 <!--<span>대화이력을 상세조회합니다.</span>--></h3>
    <div class="layer_cont">
      <div class="chatting_wrap left">
        <ul>
          <li class="right">
            <span class="date">00:03</span>
            <span class="coment">
            <p class="cont">안녕</p>
            </span>						
          </li>
          <li class="left">
            <span class="coment">
            <p class="cont">도움이 됐나요?</p>
            </span>						
            <span class="date">00:02</span>
          </li>
          <li class="right">
            <span class="date">00:03</span>
            <span class="coment">
            <p class="cont">남성 상의 보여줘</p>
            </span>						
          </li>
          <li class="left">
            <span class="coment">
            <p class="cont">남성 상의 목록입니다</p>
            </span>						
            <span class="date">00:02</span>
          </li>
          <li class="right">
            <span class="date">00:03</span>
            <span class="coment">
            <p class="cont">주소</p>
            </span>						
          </li>
          <li class="left">
            <span class="coment">
            <p class="cont">주소를 입력해 주세요</p>
            </span>						
            <span class="date">00:02</span>
          </li>
          <li class="right">
            <span class="date">00:03</span>
            <span class="coment">
            <p class="cont">남부순환로</p>
            </span>						
          </li>
        </ul>
      </div>
    </div>
    <div class="chatting_wrap right">
      <img :src= "sample" v-bind:style= "{width:'400px'}">
    </div>
    <div class="btn_center">
      <a href="#" class="btn_black b-close" @click="closeModal">닫기</a>
    </div>
    <a href="#" class="btn_close b-close" @click="closeModal">닫기</a>
  </div>  
</template>

<script>
import sample from "@/assets/Admin/images/chat_detail.jpg";
export default {
  name: "ContentsModal",
  data: function(){
    return{
      sample:sample
    }
  },
  methods:{
    closeModal() {
    this.$emit("close-modal");
    }
  }
}
</script>