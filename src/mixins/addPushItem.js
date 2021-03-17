export default {
  methods: {
    addPushItem(target, inputType) {
      let dataInput = "";
      let dataTypeMapper = {
        slotMapper: () => {
          dataInput = { type: "", intent: "", value: "" };
        },
      };
      dataTypeMapper[inputType]();
      target.push(dataInput);
    },
  },
};
