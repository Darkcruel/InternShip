<template>
  <div id="aside" @mouseover="activeSideBar" @mouseleave="inactiveSideBar" :class="{ open: isOpen, close: isClose }">
    <ul class="lnb">
      <li :class="{ on: isActive(menuActive.current) }">
        <a @click="slideMenu">현황</a>
        <div class="slider">
          <ul class="dep_menu">
            <li><a>대시보드</a></li>
            <li><a>통계</a></li>
          </ul>
        </div>
      </li>
      <li :class="{ on: isActive(menuActive.conversation) }">
        <a @click="slideMenu">대화관리</a>
        <div class="slider">
          <ul class="dep_menu">
            <li :class="{ on: isSubActive('ConversationHistoryView') }">
              <a @click="route('ConversationHistoryView', $event)">대화 이력 조회</a>
            </li>
            <li><a>비신뢰 답변 관리</a></li>
          </ul>
        </div>
      </li>
      <li :class="{ on: isActive(menuActive.faq) }">
        <a @click="slideMenu">자주 찾는 질문</a>
        <div class="slider">
          <ul class="dep_menu">
            <li :class="{ on: isSubActive('FAQView') }"><a @click="route('FAQView', $event)">FAQ</a></li>
          </ul>
        </div>
      </li>
      <li :class="{ on: isActive(menuActive.chatbot) }">
        <a @click="slideMenu">챗봇 관리</a>
        <div class="slider">
          <ul class="dep_menu">
            <li :class="{ on: isSubActive('ScenarioView') }">
              <a @click="route('ScenarioView', $event)">시나리오</a>
            </li>
            <li :class="{ on: isSubActive('FormsView') }" data-routename="FormsView">
              <a @click="route('FormsView', $event)">질문형 대화</a>
            </li>
            <li :class="{ on: isSubActive('ResponsesView') }" data-routename="ResponsesView"><a @click="route('ResponsesView', $event)">단순 답변</a></li>
            <li :class="{ on: isSubActive('IntentMappingView') }" data-routename="IntentMappingView">
              <a @click="route('IntentMappingView', $event)">의도 분석</a>
            </li>
          </ul>
        </div>
      </li>
      <li :class="{ on: isActive(menuActive.nlu) }">
        <a @click="slideMenu">용어 관리</a>
        <div class="slider">
          <ul class="dep_menu">
            <li><a>Regex & Lookup</a></li>
            <li><a>Synonyms</a></li>
            <li><a>불용어</a></li>
            <li :class="{ on: isSubActive('BanWordView') }"><a @click="route('BanWordView', $event)">금칙어</a></li>
          </ul>
        </div>
      </li>
      <li :class="{ on: isActive(menuActive.api) }"><a href="#">API 관리</a></li>
      <li :class="{ on: isActive(menuActive.learning) }"><a href="#">학습 관리</a></li>
      <li :class="{ on: isActive(menuActive.user) }">
        <a @click="slideMenu" href="#">사용자 관리</a>
        <div class="slider">
          <ul class="dep_menu">
            <li :class="{ on: isSubActive('UserAccountView') }" data-routename="UserAccountView" >
              <a @click="route('UserAccountView', $event)">계정 관리</a>
            </li>
            <li><a>접속 이력</a></li>
            <li><a>수행 이력</a></li>
          </ul>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "HomeSideBar",
  mounted() {
    this.setSideMenu();
    this.searchIsSubOn();
    this.getCurrentMenus();
  },
  computed: {
    ...mapState(["navObj", "scenarioDataKeeper", "inventory"]),
    
  },
  data() {
    return {
      isOpen: false,
      isClose: true,
      curMenu: "",
      menuActive: {
        current: [],
        conversation: ["ConversationHistoryView"],
        faq: ["FAQView"],
        chatbot: ["ScenarioView", "ResponsesView", "FormsView", "IntentMappingView"],
        nlu: ["BanWordView"],
        api: [],
        learning: [],
        user: ["UserAccountView"],

      },
    };
  },
  methods: {
    setSideMenu() {
      const menus = this.$el.querySelectorAll(".dep_menu");
      menus.forEach(menu => menu.parentElement.parentElement.classList.add("active"));

      const linkButtons = this.$el.querySelectorAll(".active a");
      linkButtons.forEach(button => button.classList.add("act"));

      const atc = this.$el.querySelectorAll(".lnb > li.active");
      atc.forEach(a => {
        a.classList.add("hidden");
      });
    },
    searchIsSubOn() {
      const subMenus = this.$el.querySelectorAll(".dep_menu li");
      for (let i = 0; i < subMenus.length; i++) {
        if (subMenus[i].innerText === this.navObj.selected) {
          subMenus[i].classList.add("on");
          break;
        }
      }
    },
    getCurrentMenus() {
      this.navObj.sub = [];
      this.navObj.title = this.$el.querySelector("li.on a").innerText;
      this.$el.querySelector("li.on div ul").children.forEach(l => {
        const arr = {
          title: "",
          route: "",
        };
        arr.title = l.innerText;
        arr.route = l.dataset.routename;
        this.navObj.sub.push(arr);
        if (l.classList.contains("on")) this.navObj.selected = l.innerText;
      });
    },
    isActive(menus) {
      return menus.find(m => m === this.$router.history.current.name);
    },
    isSubActive(view) {
      return this.$router.history.current.name === view;
    },
    activeSideBar() {
      this.isOpen = true;
      this.isClose = false;
    },
    inactiveSideBar() {
      this.isOpen = false;
      this.isClose = true;
    },
    slideMenu(event) {
      const accatc = event.target.nextSibling;
      const menus = document.querySelectorAll("li.active");
      const clickedMenu = event.target.parentElement;
      menus.forEach(m => m.classList.remove("on"));
      clickedMenu.classList.toggle("hidden");
      clickedMenu.classList.add("on");
      accatc.classList.toggle("open");
    },
    route(name, event) {
      this.navObj.title = "";
      this.navObj.sub = [];
      this.navObj.title = event.target.parentElement.parentElement.parentElement.previousSibling.innerText;
      this.navObj.selected = event.target.innerText;
      if (name === "ScenarioView") {
        // reconfigure scenarioDataKeeper materials
        this.scenarioDataKeeper.selectedTab = this.inventory.intentData;
        this.scenarioDataKeeper.selectedTabType = "";
      }
      const menus = event.target.parentElement.parentElement.children;
      menus.forEach(m => {
        this.navObj.sub.push({
          title: m.innerText,
          route: "",
        });
      });
      this.$router.push({ name: name }).catch(() => {});
    },
  },
};
</script>

<style scoped>
li {
  cursor: pointer;
}

.slider {
  overflow-y: hidden;
  max-height: 0;
  transition-property: all;
  transition-duration: 0.2s;
}

.slider.open {
  max-height: 500px;
}
</style>
