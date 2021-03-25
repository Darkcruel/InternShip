export default {

    //  * Drag and Drop 에 관련된 대부분의 
    //  * 함수는 여기 있다고 보면 됨
    methods: {
        handleDeleteStep(item, scenarioSteps) {
          //  *  시나리오 단계 중 하나 삭제  
          //  *  @param  {[object]} item [삭제할 아이템]
          //  *  @param  {[array]} scenarioSteps [단계 매니저 ]
          //  *  @return {[null]}      []
          //  * 
          let targetIndex = item.id;
          scenarioSteps.splice(targetIndex, 1,);

          for (targetIndex ; targetIndex < scenarioSteps.length ; targetIndex ++) {
            // 삭제된 아이템 보다 뒤에 있는 아이템들 Index 조정
            scenarioSteps[targetIndex].id = scenarioSteps[targetIndex].id - 1;
          }
          
        },

        /**
         * 왼쪽 탭에서 드래깅 시작했을때 작용
         * @param {Event} evt  
         * @param {Object} item 
         * @param {Object} stateManager - Vuex 데이터 
         */
        handleDragStartInventory(evt, item, stateManager) {
            stateManager.selectedStepObjectStart = [item, "new step"];
            evt.dataTransfer.dropEffect = "move";
            evt.dataTransfer.effectAllowed = "move";
          },

         /**
         * 왼쪽 탭에서 드래그 한 아이템을 놨을때 작용
         * @param {Event} evt  
         * @param {Object} stateManager - Vuex 데이터 
         */
          handleDragEndInventory(evt, stateManager) {
            stateManager.selectedStepObjectStart = "";
          },
          
          /**
           * 시나리오 차트 내에서 드래그 할 경우
           * @param {Event} evt 
           * @param {Object} item 
           * @param {Object} stateManager - Vuex 데이터 
           */
          handleDragStartScenario(evt, item, stateManager) {
            evt.dataTransfer.dropEffect = "move";
            evt.dataTransfer.effectAllowed = "move";
            stateManager.selectedStepObjectStart = [
              item,
              "rearrange step",
            ];
            stateManager.selectedStepObjectIndex = item.id;
          },


          handleDragEndScenario(evt) {
            evt.preventDefault();
          },
      
          
          /**
           * 
           * @param {Event} evt 
           * @param {Object} stateManager - Vuex 데이터
           * @param {Array} refList - 바꿔야 될 타겟 Array 
           */
          onDrop(evt, stateManager, refList) {
            evt.preventDefault();
            let newStepObject = stateManager.selectedStepObjectEnd;
            let stepType = newStepObject[1];
            let step = newStepObject[0];
            stateManager.selectedStepObjectEnd = "";
            let targetId = evt.target.id;

            // 드랍이 아무것도 없는 곳에 실행 됬을 경우 
            if (targetId == "") {
              // Notify the user that, the operation didnt work
              return;
            }
            let newStepObj = { type: "", id: targetId };
            newStepObj["object"] = step;

            newStepObj["type"] = (stateManager.selectedTabType == "") ? "intent" : "action";
            
            // 새로운 스텝일 경우 처리 
            if (stepType === "new step") {
                newStepObj["actionType"] = stateManager.selectedTabType;
              for (let i = 0; i < refList.length; i++) {
                if (i >= targetId) {
                  refList[i].id = Number(refList[i].id) + 1;
                }
              }
              refList.splice(targetId, 0, newStepObj);
            } else {
              // 순서만 바꾸는 경우
              let temp = refList[stateManager.selectedStepObjectIndex];
              let selectedIndex = Number(stateManager.selectedStepObjectIndex);
              temp.id = targetId;
              let start = 0;
              let end = 0;
      
              if (selectedIndex < targetId) {
                end = targetId;
                start = selectedIndex + 1;
      
                for (start; start <= end; start++) {
                  refList[start].id = Number(refList[start].id) - 1;
                  refList[start - 1] = refList[start];
                }
              } else {
                start = selectedIndex - 1;
                end = targetId;
                for (start; start >= end; start--) {
                  refList[start].id = Number(refList[start].id) + 1;
                  refList[start + 1] = refList[start];
                }
              }
      
              refList[targetId] = temp;
            }
          },
      
          /**
           * 드랍존에 들어왔을 경우 
           * @param {Event} evt 
           * @param {Object} stateManager - Vuex Data 
           */
          onDragEnter(evt, stateManager) {
            
            evt.preventDefault();
            stateManager.selectedStepObjectEnd = stateManager.selectedStepObjectStart;
          },
          
          /**
           * 드랍존을 나갔을 경우 
           * @param {Event} evt 
           * @param {Object} stateManager - Vuex Data 
           */
          onDragLeave(evt, stateManager) {
            evt.preventDefault();
            stateManager.selectedStepObjectEnd = "";
          },
    },
  };
  