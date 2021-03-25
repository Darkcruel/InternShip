import Vue from "vue";
import Vuex from "vuex";
import * as rasaAdminApi from "@/apis/admin";
import { categorizeResponse, humanizePhrase } from "@/utils/DataProcessing.js";
import axios from "axios";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    navObj: {
      title: "",
      selected: "",
      sub: [],
    },

    // ManagementBoard 에 사용되는 데이터 Structure Skeleton Code
    // 실질적으로 Scenario Board 데이터까지 포함되어 있음
    /**
     * @key pageTitle - 페이지 타이틀
     * @key pageSubtitle - 페이지에 들어가는 Subtitle
     * @key selected - 현재 화면에 보여지는 아이템 
     * @key exampleTitle - 예제 이름 
     * @key items - 관련 예시들 / 각 페이지 관련 객체들이 다 여기 있다고 보면 됨
     */
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
      UserAccount: {
        pageTitle: "계정 관리",
        pageSubtitle: "사용자 계정을 관리합니다.",
        selected: "",
        exampleTitle: "사용자 계정 예제",
        items: [],
      },
      BanWord:{
        pageTitle: "금칙어",
        pageSubtitle: "금칙어를 관리합니다.",
        selected: "",
        exampleTitle: "금칙어 예제",
        items:[]
      },
    },

    // ManagementBoard Data 객체들의 items 로 들어가는 객체들 
    // managementboardData 는 각 페이지 별 데이터 정리를 해 놓은 것이라 보면 되고 
    // 거기에서 사용되는 데이터들이 다 inventory 에서 온다고 간주하면 됨 
    // ** managementBoardData 에 있는 모든 데이터들은 inventory 에 있는 객체들을 바라보고 있음 (referenced)
    //

    inventory: {
      intentData: [], // 의도
      responseData: [], // 단순답변 
      slotQuestions: [], // 폼 슬로에 관한 답변
      formData: [], // 질문형 대화
      scenarioData: [], // 시나리오 
      actionData: [{title: "bert faq"}, {title: "all slots reset"}, {title: "slot check"}, {title: "address search"}], // 커스텀 액션
      actionType: ["질문형 대화", "단순 답변"], 
      entryType: ["사용자 답변", "의도별 값 지정"], // 질문형 대화 답변 처리 방식 참조 
      attachmentType: ["URL", "image", "video", "document"], // 첨부파일 옵션 참조
    },

    // ** 시나리오 Drag and Drop 때문에 만들게 됨
    // dragAndDrop.js Mixin 참조
    scenarioDataKeeper: {
      selectedStepObjectStart: "",
      selectedStepObjectEnd: "",
      selectedStepObjectIndex: "",
      selectedTriggerObject: "",
      selectedTab: [],
      selectedTabType: "",

    },

    // ** Form slot 관련
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

    /**
     * NLU 데이터 전처리 및 정리
     * @param {Vuex Data} state - this 라고 간주해도 될 듯
     * @param {Object} data - 자바에서 받은 데이터 
     */
    processIntentData(state, data) {
      const nluData = data.nlu;
      nluData.forEach((n) => {
        const intentObj = {};
        
        intentObj["title"] = humanizePhrase(n["intent"])[0]; // 라사 포멧을 더욱 User Friendly 하게 만들어 줌
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

    /**
     * Responses 데이터 전처리 및 정리
     * @param {Vuex Data} state - this 라고 간주해도 될 듯
     * @param {Object} data - 자바에서 받은 데이터 
     */
    processResponseData(state, data) {
      const responsesData = data.domain.responses;
      state.responsesData = [];

      for (const [key, value] of Object.entries(responsesData)) {
        const responseObj = { buttons: [{ id: 0, content: {} }], attachment: { type: "", payload: "" } };
        const { title, forSlot } = categorizeResponse(key); // Slot 과 관련 있는지 구분
        responseObj["title"] = humanizePhrase(title)[0];
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

    /**
     * Forms 데이터 전처리 및 정리
     * @param {Vuex Data} state - this 라고 간주해도 될 듯
     * @param {Object} data - 자바에서 받은 데이터 
     */
    processFormData(state, data) {
      const formsData = data.domain.forms;
      for (const [key, value] of Object.entries(formsData)) {
        const formObj = {};
        formObj["title"] = humanizePhrase(key, ["form"])[0];

        formObj["examples"] = [];
        for (let [slotName, slotContent] of Object.entries(value)) {
          slotName = humanizePhrase(slotName)[0];

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

    /**
     * Rules 데이터 전처리 및 정리
     * @param {Vuex Data} state - this 라고 간주해도 될 듯
     * @param {Object} data - 자바에서 받은 데이터 
     */
    processScenarioData(state, data) {
      
      const rulesData = data.rules;
      const storyData = data.stories;
      
      const scenarios = [rulesData, storyData];

      scenarios.forEach((scenario) => {

        scenario.forEach(rule => {
          const scenarioObj = {};
          console.log(rule);
          scenarioObj["title"] = rule.rule ? rule.rule : rule.story;
          scenarioObj["steps"] = [];
  
          
          rule.steps.forEach((step) => {
            const stepObj = {};
            let stepArray = Object.entries(step)[0];
            let key = stepArray[0];
            let value = stepArray[1];
            if (key != "active_loop") {
              let humanizedResult = humanizePhrase(value, ['utter', 'action', 'form']);
              let stepName = humanizedResult[0];
              let stepType = humanizedResult[1];
              let refTargets = [];
              
              // 인벤토리 와 Object mapping 해서 Reference 하게 하는 작업 
              if (key == "intent") {
                refTargets = [state.inventory.intentData];
              } else {
                switch (stepType) {
                  case "utter":
                    refTargets = [state.inventory.responseData];
                    break;
                  
                  case "form":
                    refTargets = [state.inventory.formData, state.inventory.actionData];
                    break;
                  
                  case "action":
                    refTargets = [state.inventory.actionData];
                    break;
              }}
  
              refTargets.every(target => {
                let targetIndex = target.findIndex(q => q.title === stepName);
                if (targetIndex != -1) {
                  stepObj["object"] = target[targetIndex] ;
                  return false; 
                } 
                return true;
              })
  
              stepObj["type"] = key;
              stepObj["actionType"] = stepType;
              stepObj["id"] = scenarioObj['steps'].length - 1;
              scenarioObj['steps'].push(stepObj);
            }
          });
  
          // 첫번째 Step 은 항상 트리거로
          let trigger = scenarioObj.steps.shift();
          scenarioObj['trigger'] = trigger; 
          state.inventory.scenarioData.push(scenarioObj);
        });
      })

      state.managementBoardData.Scenario.items = state.inventory.scenarioData;
      state.managementBoardData.Scenario.selected = state.inventory.scenarioData[0];

      state.scenarioDataKeeper.selectedTab = state.inventory.intentData;
    },

    /**
     * FAQ 데이터 전처리 및 정리
     * @param {Vuex Data} state - this 라고 간주해도 될 듯
     * @param {Object} data - 자바에서 받은 데이터 
     */
    processFAQData(state, data) {
      state.managementBoardData.FAQ.items = data;
      state.managementBoardData.FAQ.items.forEach((datum) => {
        datum.selectedAction = humanizePhrase(datum.selectedAction, ["form", "utter"])[0];
      });
      state.managementBoardData.FAQ.selected = data[0];
    },
    
    setSlotIndex(state, templateType) {
      state.indexKeeper.slotNameIndex = state.inventory.slotQuestions.findIndex((s) => s.title === state.managementBoardData[templateType]["selected"]["selectedSlot"]["slotName"]);
    },
    //----------------------------------
    showAccountList(){
      console.log("LONGLONGLONG");
    }
  },
  actions: {
    prepareVuexRasaData({ commit }) {
      // 라사 데이터 호출 및 전처리
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
      // FAQ 데이터 호출 및 전처리
      rasaAdminApi
        .loadFAQData()
        .then((res) => commit("processFAQData", res.data))
        .catch((err) => console.log(err));
    },
    //--------------------------------------------------------
    getAccountData({ commit }) {
      //계정 관리의 데이터를 호출 할 겁니다. 
      axios
        .post("/admin/account/leftlist")
        .then((res) => commit("showAccountList", res.data))
        .catch((err) => console.log(err));
    }
  },
  modules: {},
});
