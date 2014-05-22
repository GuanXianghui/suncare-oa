/**
 * 初始化
 */
$(document).ready(function() {
    if(message != EMPTY){
        showInformation(message);
    }
});

/**
 * 查询文档
 */
function queryDoc(doc){
    $("#cloudQueryDocName").val(doc);
    document.forms["cloudQueryDocForm"].submit();
}