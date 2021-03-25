<template>
    <div class="list">
        <table class="tbl_list">
            <caption>금칙어 리스트</caption>
            <colgroup>
                <col style="width:60%">
                <col>
            </colgroup>
            <thead>
                <th scope="col">금칙어</th>
                <th scope="col">사용여부</th>
            </thead>
            <tbody>
                <tr class="link" v-for= 'item in banwordlist' v-bind:key= 'item.id'>
                    <td>{{item.banword}}</td>
                    <td>{{item.useyn}}</td>
                </tr>
            </tbody>
        </table>  
    </div>
</template>
    
<script>
export default {
    name: "BanWordList",
    data : function(){
        return{
            banwordlist:[
                {banword:"샤발라" , useyn:"사용"},
                {banword:"십장생" , useyn:"사용"},
                {banword:"수박씨 발라" , useyn:"사용"},
                {banword:"니 취팔러마" , useyn:"사용"},
                {banword:"쩐다" , useyn:"사용안함"},
                {banword:"찐따" , useyn:"사용"},
                {banword:"존나" , useyn:"사용"},
                {banword:"대박" , useyn:"사용안함"},
                {banword:"병신" , useyn:"사용"},
                {banword:"개이쉑" , useyn:"사용"}
            ]
        }
    },
    
}
</script>

<style>

</style>