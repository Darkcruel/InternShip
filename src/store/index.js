import Vue from "vue";
import Vuex from "vuex";
import * as rasaAdminApi from "@/apis/admin";
import { categorizeResponse, humanizePhrase } from "@/utils/DataProcessing.js";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    navObj: {
      title: "",
      selected: "",
      sub: [],
    },

    // What's included in ManagementBoardData? : FAQ, IntentMapping, Responses
    managementBoardData: {
      IntentMapping: {
        pageTitle: "의도 분석",
        pageSubtitle: "사용자의 의도 분석용 데이터를 관리합니다.",
        selected: "",
        exampleTitle: "의도별 예제",
        items: [],
      },
      Responses: {
        pageTitle: "단순 답변",
        pageSubtitle: "단순 답변 데이터를 관리합니다.",
        selected: "",
        exampleTitle: "답변 예제",
        items: [],
      },
      Forms: {
        pageTitle: "질문형 대화",
        pageSubtitle: "질문형 대화 및 설문 데이터를 관리합니다.",
        selected: "",
        exampleTitle: "답변 예제",
        items: [],
      },
      FAQ: {
        pageTitle: "FAQ",
        pageSubtitle: "FAQ 데이터를 관리합니다.",
        selected: "",
        exampleTitle: "질문 예제",
        items: [],
      },
      Scenario: {
        pageTitle: "시나리오",
        pageSubtitle: "여러 대화 시나리오를 관리합니다.",
        selected: "",
        exampleTitle: "질문 예제",
        items: [],
      },
      ConversationHistory: {
        pageTitle: "대화 이력 조회",
        pageSubtitle: "대화이력을 조회합니다.",
        selected: "",
        exampleTitle: "대화 이력 예제",
        items: [],
      },
    },

    // Rasa Data Lists
    inventory: {
      intentData: [],
      responseData: [],
      slotQuestions: [],
      formData: [],
      scenarioData: [],
      actionData: [{title: "bert faq"}, {title: "all slots reset"},],
      actionType: ["질문형 대화", "단순 답변"],
      entryType: ["사용자 답변", "의도별 값 지정"],
      attachmentType: ["URL", "Image", "Video", "Document"],
    },
    scenarioDataKeeper: {
      selectedObjectStart: "",
      selectedObjectEnd: "",
      selectedObjectIndex: "",
      

    },
    indexKeeper: {
      newSlotIndex: 1,
      slotNameIndex: 0,
    },
    mapperKeeper: {
      entryType: {
        from_intent: "의도별 값 지정",
        from_text: "사용자 답변",
      },
    },
  },
  getters: {},
  mutations: {
    processIntentData(state, data) {
      console.log(data);
      const nluData = data.nlu;
      state.intentData = [];
      nluData.forEach((n) => {
        const intentObj = {};
        intentObj["title"] = humanizePhrase(n["intent"]);
        intentObj["examples"] = [];
        n["examples"].forEach((i) => {
          intentObj["examples"].push({
            id: intentObj["examples"].length,
            content: i,
          });
        });
        state.inventory.intentData.push(intentObj);
      });
      state.managementBoardData.IntentMapping.items = state.inventory.intentData;
      state.managementBoardData.IntentMapping.selected = state.inventory.intentData[0];
    },

    processResponseData(state, data) {
      const responsesData = data.domain.responses;
      state.responsesData = [];

      for (const [key, value] of Object.entries(responsesData)) {
        const responseObj = { buttons: [{ id: 0, content: {} }], attachment: { type: "", payload: "" } };
        const { title, forSlot } = categorizeResponse(key);
        responseObj["title"] = humanizePhrase(title);
        if (value[0].buttons) {
          const buttonObjArray = [];
          value[0].buttons.forEach((button) => {
            const buttonObj = {
              id: responseObj.buttons.length,
              content: button,
            };
            buttonObjArray.push(buttonObj);
          });
          responseObj["buttons"] = buttonObjArray;
        }

        if (value[0].attachment) {
          responseObj["attachment"] = value[0].attachment;
        }

        responseObj["examples"] = [];
        value.forEach((response) => {
          responseObj["examples"].push({
            id: responseObj["examples"].length,
            content: response["text"],
          });
        });

        if (forSlot) {
          state.inventory.slotQuestions.push(responseObj);
        } else {
          state.inventory.responseData.push(responseObj);
        }
      }

      state.managementBoardData.Responses.items = state.inventory.responseData;
      state.managementBoardData.Responses.selected = state.inventory.responseData[0];
    },
    processFormData(state, data) {
      const formsData = data.domain.forms;
      for (const [key, value] of Object.entries(formsData)) {
        const formObj = {};
        formObj["title"] = humanizePhrase(key, ["form"]);

        formObj["examples"] = [];
        for (let [slotName, slotContent] of Object.entries(value)) {
          slotName = humanizePhrase(slotName);

          slotContent.forEach((content) => {
            content.type = state.mapperKeeper.entryType[content.type];
          });

          formObj["examples"].push({
            id: formObj["examples"].length,
            slotName: slotName,
            content: slotContent,
          });
        }

        formObj["selectedSlot"] = formObj["examples"][0];

        state.inventory.formData.push(formObj);
      }

      state.managementBoardData.Forms.items = state.inventory.formData;
      state.managementBoardData.Forms.selected = state.inventory.formData[0];
    },
    processScenarioData(state, data) {
      
      const rulesData = data.rules;
      rulesData.forEach(rule => {
        const scenarioObj = {};
        scenarioObj["title"] = rule.rule;
        scenarioObj["steps"] = [];

        
        rule.steps.forEach((step) => {
          const stepObj = {};
          let stepArray = Object.entries(step)[0];
          let key = stepArray[0];
          let value = stepArray[1];
          if (key != "active_loop") {
            let stepName = humanizePhrase(value, ['utter', 'action', 'form']);
            let refTargets = (key == "intent")? [state.inventory.intentData] : [state.inventory.responseData, state.inventory.formData, state.inventory.actionData];  
            
             
            refTargets.every(target => {
              let targetIndex = target.findIndex(q => q.title === stepName);
              if (targetIndex != -1) {
                stepObj["object"] = target[targetIndex] ;
                return false; 
              } 
              return true;
            })
            stepObj["type"] = key;
            stepObj["id"] = scenarioObj['steps'].length - 1;
            scenarioObj['steps'].push(stepObj);
          }
        });
        let trigger = scenarioObj.steps.shift();
        scenarioObj['trigger'] = trigger; 
        state.inventory.scenarioData.push(scenarioObj);
      });



      state.managementBoardData.Scenario.items = state.inventory.scenarioData;
      state.managementBoardData.Scenario.selected = state.inventory.scenarioData[0];
    },
    processFAQData(state, data) {
      state.managementBoardData.FAQ.items = data;
      state.managementBoardData.FAQ.items.forEach((datum) => {
        datum.selectedAction = humanizePhrase(datum.selectedAction, ["form", "utter"]);
      });
      state.managementBoardData.FAQ.selected = data[0];
    },
    setSlotIndex(state, templateType) {
      state.indexKeeper.slotNameIndex = state.inventory.slotQuestions.findIndex((s) => s.title === state.managementBoardData[templateType]["selected"]["selectedSlot"]["slotName"]);
    },
  },
  actions: {
    prepareVuexRasaData({ commit }) {
      rasaAdminApi
        .loadRasaData()
        .then((res) => {
          commit("processIntentData", res.data);
          commit("processResponseData", res.data);
          commit("processFormData", res.data);
          commit("processScenarioData", res.data);
        })
        .catch((err) => console.log(err));
    },
    getFAQData({ commit }) {
      rasaAdminApi
        .loadFAQData()
        .then((res) => commit("processFAQData", res.data))
        .catch((err) => console.log(err));
    },
  },
  modules: {},
});
