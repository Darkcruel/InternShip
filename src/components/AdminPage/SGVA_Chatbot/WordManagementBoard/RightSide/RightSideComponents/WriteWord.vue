<template>
    <div class="write">
        <!-- list -->
        <table class="tbl_list">
            <caption>금칙어 등록</caption>
            <colgroup>
                <col style="width:12%">
                <col>
            </colgroup>
            <thead>
                <th scope="col">용어 코드</th>
                <td class="tleft"><input type="text" style="width:98%" title="ID"></td>
            </thead>
            <tbody>
                <tr>
                    <th scope="col">용어 타입</th>
                    <td class="tleft"><input type="text" style="width:98%" title="용어 타입"></td>
                </tr>
                <tr>
                    <th scope="col">용어</th>
                    <td class="tleft"><input type="text" style="width:98%" title="용어"></td>
                </tr>
                <tr>
                    <th scope="col">용어설명</th>
                    <td class="tleft"><input type="text" style="width:98%" title="용어설명"></td>
                </tr>
                <tr>
                    <th scope="col">사용여부</th>
                    <td class="tleft">
                        <select style="width:120px;" title="사용여부">
                            <option value="">사용</option>
                            <option value="">사용안함</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th scope="col">등록일자</th>
                    <td class="tleft">2020-03-16</td>
                </tr>
                <tr>
                    <th scope="col">등록자 ID</th>
                    <td class="tleft">Admin</td>
                </tr>
            </tbody>
        </table>
        <div class="btn_right type4">        
            <a href="#" class="btn_black">삭제하기</a>
            <a href="#" class="btn_save">저장하기</a>
        </div>
    <!-- //list -->                		
    </div>
</template>

<script>
export default {
    name : "WriteWord"
}
</script>

