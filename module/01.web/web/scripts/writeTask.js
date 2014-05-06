//ueditor编辑器
var editor;
//所有员工Json数组
var userArray = new Array();

/**
 * 初始化
 */
$(document).ready(function() {
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    editor = UE.getEditor('editor');

    //处理所有员工json串
    processUserWithJson();

    //初始化有权限查看的用户
    initRightUsers();
});

/**
 * 初始化有权限查看的用户
 */
function initRightUsers(){
    if(rightUserWithComma != EMPTY){
        //用户id数组
        var userIdArray = rightUserWithComma.split(SYMBOL_COMMA);
        for(var i=0;i<userIdArray.length;i++){
            var id = userIdArray[i];
            var user = getUserById(id);
            document.getElementById("toUserId").innerHTML += "<option value='" + user["id"] + "'>" + user["name"] + "</option>"
        }
    }
}

/**
 * 提交
 */
function writeTask(){
    var fromUserId = document.getElementById("fromUserId").value;
    if(fromUserId == EMPTY){
        showAttention("请选择任务来源用户");
        return false;
    }
    var toUserId = document.getElementById("toUserId").value;
    if(toUserId == EMPTY){
        showAttention("请选择任务接受用户");
        return false;
    }
    var title = document.getElementById("title").value;
    if(title == EMPTY){
        showAttention("请输入任务名称");
        return false;
    }
    if(title.length > TASK_TITLE_LENGTH) {
        showAttention("任务名称大于" + TASK_TITLE_LENGTH + "个字符");
        return false;
    }
    //判断字符串是否含有非法字符
    var result = checkStr(title, SYMBOL_ARRAY_1);
    if(result["isSuccess"] == false){
        showAttention("标题包含非法字符:" + result["symbol"]);
        return false;
    }
    var content = editor.getContent();
    if(content == EMPTY){
        showAttention("请输入任务内容");
        return false;
    }
    if(content.length > TASK_CONTENT_LENGTH) {
        showAttention("任务内容大于" + TASK_CONTENT_LENGTH + "个字符");
        return false;
    }
    var beginDate = document.getElementById("beginDate").value;
    if(beginDate == EMPTY){
        showAttention("请输入开始日期");
        return false;
    }
    var endDate = document.getElementById("endDate").value;
    if(endDate == EMPTY){
        showAttention("请输入结束日期");
        return false;
    }
    document.getElementById("content").value = content;
    //提交表格
    document.forms["writeTaskForm"].submit();
}

/**
 * 处理所有员工json串
 */
function processUserWithJson() {
    //json串转json数组
    if(userJsonStr != EMPTY) {
        var array = userJsonStr.split(SYMBOL_BIT_AND);
        for(var i=0;i<array.length;i++) {
            userArray[userArray.length] = eval("(" + array[i] + ")");
        }
    }
}

/**
 * 根据id查用户
 * @param id
 */
function getUserById(id) {
    for(var i=0;i<userArray.length;i++){
        if(userArray[i]["id"] == id){
            return userArray[i];
        }
    }
    return null;
}