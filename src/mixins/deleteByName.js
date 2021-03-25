export default {
  methods: {
    deleteByName(target, name, category) {
    //  *  category 별 name을 사용해 정해진 아이템 삭제  
    //  *  @param  {[array]} target [타켓 Array]
    //  *  @param  {[string]} name [삭제할 아이템의 인덱스 ]
    //  *  @param  {[string]} category [key 이름]
    //  *  @return {[null]}      []
    //  * 
      target.splice(
        target.findIndex((q) => q[category] === name),
        1
      );
    },
  },
};
