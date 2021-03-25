<template>
  <div id="loginView">
    <div class="align-center">
      <img alt="Vue logo" src="@/assets/Admin/images/login/sg_logo.png" />

      <table class="table">
        <tr>
          <td>아이디</td>
          <td><input id="id" v-model="id" type="text" /></td>
        </tr>
        <tr>
          <td>패스워드</td>
          <td><input id="pw" v-model="pw" type="text" /></td>
        </tr>
      </table>

      <button @click="loginClick" type="button">로그인</button>
    </div>
    <div>
      <h1>test</h1>
      <p>{{ test }}</p>
    </div>
  </div>
</template>

<script>
import axios from "axios";

// proxy 처리를 위한
var protocol = location.protocol;
var hostName = location.hostname;
var port = location.port;
var url = protocol + "//" + hostName + ":" + port;
var test = "";

console.log("### login test");
console.log(url);

export default {
  name: "loginView",
  data: function() {
    return {
      id: "",
      pw: "",
      test
    };
  },
  methods: {
    loginClick: function() {
      console.log("### id : " + this.id);
      console.log("### pw : " + this.pw);

      axios
        .post("/admin/login", {
          id: this.id,
          pw: this.pw,
        })
        .then(response => {
          if (response.data.result) {
            // admin page로 이동
            console.log(response);
            this.$router.push("/admin");
          } else {
            console.log(response);
            this.test = response.data;
          }
      });
    },
  },

};
</script>

<style scoped>
#loginView .align-center {
  text-align: center;
}

#loginView table {
  border: 1px solid #444444;
  margin-left: auto;
  margin-right: auto;
}

#loginView th,
td {
  border: 1px solid #444444;
}
</style>
