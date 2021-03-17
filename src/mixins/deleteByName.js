export default {
  methods: {
    deleteByName(target, name, category) {
      target.splice(
        target.findIndex((q) => q[category] === name),
        1
      );
    },
  },
};
