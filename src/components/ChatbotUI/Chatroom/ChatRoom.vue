<template>
  <v-app>
    <div class="light-blue lighten-4" id="chatWindow">
      <template v-for="item in items">
        <div class="left" v-if="Array.isArray(item)" :key="item.id">
          <i class="fas fa-robot icon__custom"></i>
          <div v-for="response in item" :key="response.id">
            <div class="chat__content" v-show="response.text">
              {{ response.text }}
            </div>
            <a
              class="chat__content"
              :href="response.attachmentPayload"
              v-show="response.attachmentPayload && response.attachmentType === 'URL'"
              target="_blank"
            >
              {{ response.attachmentPayload }}
            </a>
            <iframe
              :src="response.attachmentPayload"
              width="400"
              height="225"
              frameborder="0"
              v-show="response.attachmentPayload && response.attachmentType === 'video'"
              allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
              allowfullscreen
              style="margin-left: 10px"
            ></iframe>
            <div v-show="response.image">
              <img :src="response.image" alt="" width="300px" height="300px" />
            </div>
            <div v-show="response.buttons">
              <v-simple-table dense id="buttonTable">
                <template v-slot:default>
                  <tbody>
                    <tr v-for="button in response.buttons" :key="button.title" class="button__item">
                      <td @click="clickButton(button.title)" style="font-size: 11px">
                        {{ button.title }}
                      </td>
                    </tr>
                  </tbody>
                </template>
              </v-simple-table>
            </div>
          </div>
        </div>
        <div class="right" v-else :key="item.id">
          <div>
            <div class="chat__content qtext__margin" v-show="item.text">
              <div>{{ item.text }}</div>
            </div>
            <div v-show="item.image">
              <img :src="item.image" @click="openImage(item.image)" alt="" width="300px" height="300px" style="cursor: pointer" />
            </div>
          </div>
        </div>
      </template>
    </div>
    <div class="bottom__container">
      <div id="inputContainer">
        <textarea id="inputArea" spellcheck="false" v-model="textMessage" @keydown="sendMessage"></textarea>
      </div>
      <div class="option__container">
        <div class="option__item">
          <v-file-input v-model="files" hide-input max-height="600" style="position: absolute; bottom: 5px"></v-file-input>
        </div>
      </div>
    </div>
    <div class="back__shadow" v-show="files || isNotImage"></div>
    <ImageModal :files="files" @close-modal="closeModal" @submit="submitImageFile" v-show="files" />
    <NotImageModal @confirm="clickConfirm" v-show="isNotImage" />
  </v-app>
</template>

<script>
import ImageModal from "@/components/ChatbotUI/Modals/ImageFileModal";
import NotImageModal from "@/components/ChatbotUI/Modals/NotImageModal";
import * as rasaApi from "@/apis/chat";

export default {
  data() {
    return {
      items: [],
      files: null,
      textMessage: "",
      msgIdx: 0,
      isShift: false,
      isNotImage: false,
      responseList: [],
    };
  },
  components: {
    ImageModal,
    NotImageModal,
  },
  async mounted() {
    this.restart();
    const firstMessage = "안녕";
    const data = {
      sender: "asasd",
      message: firstMessage,
    };

    this.items.push({
      id: this.msgIdx++,
      type: "request",
      text: firstMessage,
    });
    const responseData = await this.requestRasa(data);
    if (responseData.length) this.responseFromRasa(responseData);
  },
  updated() {
    if (!this.isShift) this.textMessage = this.textMessage.replace("\n", "");
    const chatWindow = document.getElementById("chatWindow");
    chatWindow.scrollTop = chatWindow.scrollHeight;
  },
  methods: {
    restart: async function() {
      const data = {
        sender: "asasd",
        message: "/restart",
      };
      this.requestRasa(data);
    },
    requestRasa: async function(data) {
      const responseData = await rasaApi.chat(data.sender, data.message);
      return responseData.data;
    },
    responseFromRasa: function(responseData) {
      this.responseList = [];
      responseData.forEach(response => {
        this.responseList.push({
          id: this.msgIdx++,
          type: "response",
          text: response.text,
          image: response.image,
          buttons: response.buttons,
          attachmentPayload: response.attachment ? response.attachment.payload : "",
          attachmentType: response.attachment ? response.attachment.type : "",
        });
      });
      this.items.push(this.responseList);
    },
    sendMessage: async function(e) {
      if (e.shiftKey) this.isShift = e.shiftKey;
      if (e.keyCode === 13 && !e.shiftKey) {
        if (!this.textMessage) return;

        const data = {
          sender: "asasd",
          message: this.textMessage,
        };

        this.items.push({
          id: this.msgIdx++,
          type: "request",
          text: this.textMessage,
        });
        this.textMessage = "";
        this.isShift = false;
        const responseData = await this.requestRasa(data);
        if (responseData.length) this.responseFromRasa(responseData);
      }
    },
    clickButton: async function(message) {
      const data = {
        sender: "asasd",
        message: message,
      };

      this.items.splice(
        this.items.findIndex(item => item.buttons !== undefined),
        1,
      );

      this.items.push({
        id: this.msgIdx++,
        type: "request",
        text: message,
      });

      const responseData = await this.requestRasa(data);
      if (responseData.length) this.responseFromRasa(responseData);
    },
    submitImageFile: function(file) {
      const extValidList = ["jpg", "PNG", "png", "jpeg"];
      const fileExt = file.name.slice(file.name.indexOf(".") + 1);
      const imgUrl = window.URL.createObjectURL(file);

      if (extValidList.indexOf(fileExt) === -1) {
        this.isNotImage = true;
        this.files = null;
        return;
      }
      this.items.push({
        id: this.msgIdx++,
        type: "request",
        image: imgUrl,
      });
      this.closeModal(null);
    },
    clickConfirm: function() {
      this.isNotImage = false;
    },
    closeModal: function(close) {
      this.files = close;
    },
    openImage: function(image) {
      window.open(image, "_blank", "height=650,width=1000,frame=true,show=true");
    },
  },
};
</script>

<style>
::-webkit-scrollbar {
  display: none;
}

/* Hide scrollbar for Chrome, Safari and Opera */
#chatWindow::-webkit-scrollbar {
  display: none;
}

#chatWindow {
  height: 80vh;
  overflow-y: scroll;
  padding-top: 20px;
  padding-bottom: 10px;
}

.chat__content {
  position: relative;
  display: inline-block;
  background-color: white;
  padding: 10px 10px;
  max-width: 50vw;
  border-radius: 10px;
  margin-bottom: 10px;
  font-size: 11px;
  word-break: break-all;
  white-space: pre-wrap;
}

.qtext__margin::after {
  border-top: 10px solid white;
  border-left: 0px solid transparent;
  border-right: 10px solid transparent;
  border-bottom: 0px solid transparent;
  content: "";
  position: absolute;
  top: 10px;
  left: 95%;
}

.right {
  text-align: right;
}

.qtext__margin {
  text-align: left;
  margin-right: 10px;
}

.left {
  text-align: left;
  margin-left: 10px;
}

.cursor__pointer {
  cursor: pointer;
}

#buttonTable {
  width: 60vw;
  margin-bottom: 10px;
}

.button__item {
  text-align: center;
  cursor: pointer;
}

#inputContainer {
  height: 13vh;
}

.bottom__container {
  display: flex;
  flex-direction: column;
}

#inputArea {
  width: 100%;
  height: 10vh;
  border: none;
  outline: none;
  resize: none;
  padding: 10px 20px;
  font-size: 14px;
}

#inputArea::-webkit-scrollbar {
  width: 3px;
}

#inputArea::-webkit-scrollbar-thumb {
  background-color: gray;
}

.option__container {
  display: flex;
  align-content: center;
}

@media only screen and (max-height: 400px) {
  .option__container {
    display: none !important;
  }
}

.option__item {
  margin-left: 15px;
}

.back__shadow {
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  position: absolute;
}

.icon__custom {
  font-size: 25px;
  margin-right: 10px;
}
</style>
