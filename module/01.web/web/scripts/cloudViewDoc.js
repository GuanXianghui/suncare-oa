/**
 * 初始化
 */
$(document).ready(function() {
    if(message != EMPTY){
        showInformation(message);
    }
    $("#title").html(initTitle);
    if(initDescription != EMPTY){
        $("#description").html(initDescription);
    }
    if(initTags != EMPTY){
        $("#tags").html(initTags);
    }
    //初始化申成文库分类
    initCloudDocType("show_type", "type", initType);
});

/**
 * 查询文档
 */
function queryDoc(doc){
    $("#cloudQueryDocName").val(doc);
    document.forms["cloudQueryDocForm"].submit();
}