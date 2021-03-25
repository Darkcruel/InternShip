export default {
  methods: {

    

    addExample(target, button = false) {    
    //  *  ExampleRow 에서 많이 사용되는 함수 Target Array에  새로운 아이템을 추가 
    //  *  @param  {[array]} target [새로운 아이템이 들어갈 Array]
    //  *  @param  {[boolean]} button [버튼에 들어갈 아이템인지에 대한 여부]
    //  *  @return {[null]}      []
    //  * 
      let lastElementIndex = target.length - 1;
      let newId = 0;
      if (lastElementIndex >= 0) {
        let lastElementId = target[lastElementIndex].id;
        newId = lastElementId + 1; 
      }
      
      target.push({
        id: newId,
        content: button ? { title: "", content: "" } : "",
      });
    },
  },
};
