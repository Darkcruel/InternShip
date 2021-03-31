const path = require('path');

// 통신을 위한 설정
module.exports = {
   // VUE Build 경로 설정
    outputDir : path.resolve(__dirname, "../SGVA_CHATBOT/src/main/resources/static"),

   // 시작 페이지 설정
    //indexPath: "../static/index.html",
    
    devServer : {
      // port: 9090,      // vue 실행 port 설정
      // proxy 설정 (URL별 설정도 가능)
      proxy : //'http://localhost:9090'
      { 
         '/admin/login' : {
            target : 'http://localhost:8888',      // backend 서버 설정
            //ws : true,
            changeOrigin : true
         },
         '/admin/banword/wordlist' : {
            target : 'http://localhost:8888',      // backend 서버 설정
            //ws : true,
            changeOrigin : true
         },
         '/admin/banword/saveword' : {
            target : 'http://localhost:8888',      // backend 서버 설정
            //ws : true,
            changeOrigin : true
         },
         '/admin/banword/delete' : {
            target : 'http://localhost:8888',      // backend 서버 설정
            //ws : true,
            changeOrigin : true
         },
         '/admin/banword/search' : {
            target : 'http://localhost:8888',      // backend 서버 설정
            //ws : true,
            changeOrigin : true
         },
         '/admin/account/leftlist' : {
            target : 'http://localhost:8888',      // backend 서버 설정
            //ws : true,
            changeOrigin : true
         },
      }
   },

    transpileDependencies: [
      'vuetify'
    ]
}