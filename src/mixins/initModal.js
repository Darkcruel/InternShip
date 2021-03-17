export default {
  methods: {
    openInitModal() {
      document.documentElement.style.overflow = "hidden";
      this.$el.querySelector(".back_shadow").style = "display: block";
      this.$el.querySelector(".layerwrap").style = "display: block";
    },
    initData() {
      // 초기화 시키는 기능을 넣어야함.
      alert("초기화가 완료되었습니다.");
      this.closeModal();
    },
    closeModal() {
      document.documentElement.removeAttribute("style");
      this.$el.querySelector(".back_shadow").style = "";
      this.$el.querySelector(".layerwrap").style = "";
    },
  },
};
