<template>
  <div>
    <router-view />
  </div>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "App",
  created() {
    if (this.$router.history.current.name !== "ChatRoomView") {
      this.prepareVuexRasaData();
      this.getFAQData();
    }
  },
  mounted() {
    // window.addEventListener("beforeunload", this.unLoadEvent);
  },
  beforeUnmount() {
    // window.removeEventListener("beforeunload", this.unLoadEvent);
  },
  methods: {
    ...mapActions(["prepareVuexRasaData", "getFAQData"]),
    unLoadEvent: function(event) {
      if (this.canLeaveSite) return;
      event.preventDefault();
      event.returnValue = "";
    },
  },
};
</script>
