<template>
<div>
    <table class="tbl_list">
        <caption>대화이력조회</caption>
        <colgroup>
            <col>
            <col style="width:19%">
            <col style="width:19%">
            <col style="width:19%">
            <col style="width:19%">
            <col style="width:19%">
        </colgroup>
        <thead>
            <tr>
                <th scope="col">No</th>
                <th scope="col">아이디</th>
                <th scope="col">상담일</th>
                <th scope="col">상담시간</th>
                <th scope="col">디바이스</th>
                <th scope="col">신뢰유무</th>
            </tr>
        </thead>
        <tbody>
            <tr class="layer_detail link" v-for= 'list in listexample' v-bind:key= 'list.no' @click="openInitModal">
                <td>{{list.no}}</td>
                <td>{{list.id}}</td>
                <td>{{list.date}}</td>
                <td>{{list.time}}</td>
                <td>{{list.device}}</td>
                <td>{{list.trust}}</td>
            </tr>
        </tbody>
    </table>
    
</div>
    
</template>

<script>
export default {
    name: "ListContents",
    data: function(){
        return{
            listexample:[
                {no:10,id:"SGDefault",date:"2021-02-16",time:"00:02:28",device:"휴대폰",trust:"신뢰"},
                {no:9,id:"SGDefault",date:"2021-02-16",time:"00:02:28",device:"휴대폰",trust:"비신뢰"},
                {no:8,id:"SGDefault",date:"2021-02-16",time:"00:02:28",device:"휴대폰",trust:"신뢰"},
                {no:7,id:"SGDefault",date:"2021-02-16",time:"00:02:28",device:"휴대폰",trust:"신뢰"},
                {no:6,id:"SGDefault",date:"2021-02-16",time:"00:02:28",device:"PC",trust:"비신뢰"},
                {no:5,id:"SGDefault",date:"2021-02-16",time:"00:02:28",device:"휴대폰",trust:"신뢰"},
                {no:4,id:"SGDefault",date:"2021-02-16",time:"00:02:28",device:"휴대폰",trust:"신뢰"},
                {no:3,id:"SGDefault",date:"2021-02-16",time:"00:02:28",device:"PC",trust:"비신뢰"},
                {no:2,id:"SGDefault",date:"2021-02-16",time:"00:02:28",device:"휴대폰",trust:"신뢰"},
                {no:1,id:"SGDefault",date:"2021-02-16",time:"00:02:28",device:"휴대폰",trust:"신뢰"}
            ],
        }
    },
    methods:{
        openInitModal() {
        this.$emit("open-init-modal");
        },
    }    
}
</script>
