export default {
  methods: {
    deleteSlot(managementBoardData, inventory, item, templateType) {
    //  *  지정된 슬롯 삭제 (FormsData 를 위해서 만들어진 Mixin)  
    //  *  @param  {[object]} managementBoardData [타켓 State Management Object]
    //  *  @param  {[array]} inventory [타켓 Array]
    //  *  @param  {[object]} item [삭제할 아이템의 인덱스 ]
    //  *  @param  {[string]} templateType [key 이름]
    //  *  @return {[null]}      []
    //  * 
      delete inventory["slotQuestions"][item.slotName];
      this.deleteById(managementBoardData[templateType]["selected"]["examples"], item.id);
    },
  },
};
