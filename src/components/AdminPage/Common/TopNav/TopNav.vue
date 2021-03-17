<template>
  <div id="header">
    <h1>
      <a href="#"><img src="../../../../assets/Admin/images/logo.png" alt="SOLUGATE"/></a>
    </h1>
    <div class="nav_wrap">
      <h2>{{ navObj.title }}</h2>
      <ul class="nav">
        <li v-for="menu in navObj.sub" :key="menu.title">
          <a @click="route(menu.route, $event)">{{ menu.title }}</a>
        </li>
      </ul>
    </div>
    <div class="utility">
      <span class="name">솔루게이트님</span>
      <a href="#" class="logout"><img src="../../../../assets/Admin/images/ico_logout.png" alt="로그아웃"/></a>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  computed: {
    ...mapState(["navObj"]),
  },
  mounted() {
    this.checkNav();
  },
  updated() {
    this.checkNav();
  },
  methods: {
    checkNav() {
      const menus = this.$el.querySelector(".nav").children;
      menus.forEach(m => {
        if (m.innerText === this.navObj.selected) {
          m.classList.add("on");
          return false;
        }
      });
    },
    route(name, event) {
      this.navObj.selected = event.target.innerText;
      this.$router.push({ name: name }).catch(() => {});
    },
  },
};
</script>
