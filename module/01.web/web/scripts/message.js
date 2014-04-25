//ueditor编辑器
var editor;
//消息Json数组
var messageArray = new Array();

/**
 * 初始化
 */
$(document).ready(function() {
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    editor = UE.getEditor('editor');

    //把初始messageJsonStr转换成messageArray
    messageArray = transferInitJsonStr2Array(messageJsonStr);

    //处理消息Json串
    processWithJson();

    //检查是否还有下一页
    checkHasNextPage();
});

/**
 * 把初始messageJsonStr转换成messageArray
 */
function transferInitJsonStr2Array(jsonStr){
    var jsonArray = new Array();
    if(jsonStr != EMPTY) {
        var array = jsonStr.split(SYMBOL_LOGIC_AND);
        for(var i=0;i<array.length;i++) {
            jsonArray[jsonArray.length] = eval("(" + array[i] + ")");
        }
    }
    return jsonArray;
}

/**
 * 处理消息Json串
 */
function processWithJson(){

    //循环展示
    var html = "<thead><tr><th>消息来源用户</th><th>是否已读</th><th>时间</th><th>操作</th></tr></thead>";
    for(var i=0;i<messageArray.length;i++){
        var isReaded = "未读";
        if(messageArray[i]["state"] == MESSAGE_STATE_READED){
            isReaded = "已读";
        }
        html += "<tr><td style='vertical-align: middle;'><img width='27px' src='" + messageArray[i]["headPhoto"] + "'><a href='" +
            messageArray[i]["url"] + "' target='_blank'>" + messageArray[i]["fromUserName"] + "</a></td><td>" +
            isReaded + "</td><td>" + messageArray[i]["date"] + " " + messageArray[i]["time"] + "</td>" + "<td>" +
            "<input class=\"button\" type=\"button\" onclick=\"showMessageDetail(" + messageArray[i]["id"] + ")\" value=\"查看\" />" +
            "<input class=\"button\" type=\"button\" onclick=\"deleteMessage(" + messageArray[i]["id"] + ")\" value=\"删除\" />" +
            "</td>" +
            "</tr>";
    }
    document.getElementById("message_table").innerHTML = html;
}

/**
 * 根据id查消息
 * @param messageId
 */
function getMessageById(messageId){
    for(var i=0;i<messageArray.length;i++){
        if(messageId == messageArray[i]["id"]){
            return messageArray[i];
        }
    }
    return null;
}

/**
 * 根据id删除消息
 * @param messageId
 */
function deleteMessageById(messageId){
    for(var i=0;i<messageArray.length;i++){
        if(messageId == messageArray[i]["id"]){
            messageArray.splice(i, 1);
        }
    }
    return null;
}

/**
 * 删除消息
 * @param messageId
 */
function deleteMessage(messageId){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateMessage.do",
        data:"type=delete&messageId=" + messageId + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                    return;
                } else {
                    //请求成功
                    showSuccess(data["message"]);
                    //根据id删除消息
                    deleteMessageById(messageId);
                    //总共消息的量要减一
                    messageCount--;
                    //处理消息Json串
                    processWithJson();
                    //检查是否还有下一页
                    checkHasNextPage();
                    $('tbody tr:even').addClass("alt-row");
                }
                //判是否有新token
                if (data["hasNewToken"]) {
                    token = data["token"];
                }
            } else {
                showAttention("服务器连接异常，请稍后再试！");
            }
        },
        error:function (data, textStatus) {
            showAttention("服务器连接异常，请稍后再试！");
        }
    });
}

/**
 * 查看消息
 * @param messageId
 */
function showMessageDetail(messageId){
    //显示查看框
    document.getElementById("showMessageDiv").style.display = EMPTY;
    //编译html
    var content = getMessageById(messageId)["content"];
    //将uuid->\r\n
    content = changeNewLineBack(content);
    document.getElementById("showMessageContentDiv").innerHTML = content;
    uParse("#showMessageContentDiv", {rootPath: baseUrl + '/ueditor/'});

    //判是否已读
    var isReaded = false;
    if(getMessageById(messageId)["state"] == MESSAGE_STATE_READED){
        isReaded = true;
    }

    //如果已读返回
    if(isReaded == true){
        return;
    }

    //如果未读调ajax请求置成已读
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateMessage.do",
        data:"type=read&messageId=" + messageId + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                    return;
                } else {
                    //请求成功
                    showSuccess(data["message"]);
                    //修改已读
                    getMessageById(messageId)["state"] = MESSAGE_STATE_READED
                    //处理消息Json串
                    processWithJson();
                    //检查是否还有下一页
                    checkHasNextPage();
                    $('tbody tr:even').addClass("alt-row");
                }
                //判是否有新token
                if (data["hasNewToken"]) {
                    token = data["token"];
                }
            } else {
                showAttention("服务器连接异常，请稍后再试！");
            }
        },
        error:function (data, textStatus) {
            showAttention("服务器连接异常，请稍后再试！");
        }
    });
}

/**
 * 检查是否还有下一页
 */
function checkHasNextPage(){
    if(messageCount > messageArray.length){
        document.getElementById("nextPageDiv").style.display = EMPTY;
    } else {
        document.getElementById("nextPageDiv").style.display = "none";
    }
}

/**
 * 加载下一页消息
 */
function showNextPageMessages(){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateMessage.do",
        data:"type=nextPage&countNow=" + messageArray.length + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                    return;
                } else {
                    //请求成功
                    showSuccess(data["message"]);
                    var nextPageJson = data["nextPageJson"];
                    if(EMPTY != nextPageJson) {
                        //把初始messageJsonStr转换成messageArray
                        var array = transferInitJsonStr2Array(nextPageJson);
                        messageArray = messageArray.concat(array);
                    }
                    //处理消息Json串
                    processWithJson();
                    //检查是否还有下一页
                    checkHasNextPage();
                    $('tbody tr:even').addClass("alt-row");
                }
                //判是否有新token
                if (data["hasNewToken"]) {
                    token = data["token"];
                }
            } else {
                showAttention("服务器连接异常，请稍后再试！");
            }
        },
        error:function (data, textStatus) {
            showAttention("服务器连接异常，请稍后再试！");
        }
    });
}