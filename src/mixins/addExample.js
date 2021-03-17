export default {
  methods: {
    addExample(target, button = false) {
      
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
