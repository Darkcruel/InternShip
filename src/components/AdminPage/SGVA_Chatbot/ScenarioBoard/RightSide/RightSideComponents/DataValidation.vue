<template>
  <div>

    {{inventory.formData}}

    <br> <br> <br> 
    
    {{inventory.slotQuestions}}

  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "DnD",
  components: {},
  props: ["templateType"],
  data() {
    return {};
  },

  computed: {
    ...mapState([
      "inventory",
    ]),
  },

  methods: {
  },

  created() {},
};
</script>
<style scoped>
</style>
