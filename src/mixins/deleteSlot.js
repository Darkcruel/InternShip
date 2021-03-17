export default {
  methods: {
    deleteSlot(managementBoardData, inventory, item, templateType) {
      delete inventory["slotQuestions"][item.slotName];
      this.deleteById(managementBoardData[templateType]["selected"]["examples"], item.id);
    },
  },
};
