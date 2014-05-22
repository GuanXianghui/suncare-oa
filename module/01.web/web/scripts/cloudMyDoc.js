/**
 * 初始化
 */
$(document).ready(function() {
    if(message != EMPTY){
        showInformation(message);
    }
});

/**
 * 删除文档
 * @param docId
 */
function cloudDeleteDoc(docId){
    if(confirm("你确定删除文档吗？") == false){
        return;
    }
    $("#cloudDeleteDocId").val(docId);
    document.forms["cloudDeleteDocForm"].submit();
}

/**
 * 查询文档
 */
function queryDoc(doc){
    $("#cloudQueryDocName").val(doc);
    document.forms["cloudQueryDocForm"].submit();
}