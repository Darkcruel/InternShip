export default {
  methods: {
    deleteById(target, id) {
    //  *  Id 를 사용해 정해진 아이템 삭제  
    //  *  @param  {[array]} target [타켓 Array]
    //  *  @param  {[number]} id [삭제할 아이템의 인덱스 ]
    //  *  @return {[null]}      []
    //  * 
      target.splice(
        target.findIndex((q) => q.id === id),
        1
      );
    },
  },
};
