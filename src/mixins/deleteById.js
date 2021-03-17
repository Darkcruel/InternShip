export default {
  methods: {
    deleteById(target, id) {
      target.splice(
        target.findIndex((q) => q.id === id),
        1
      );
    },
  },
};
