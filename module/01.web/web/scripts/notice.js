//ueditor编辑器
var editor;
//公告Json数组
var noticeArray = new Array();

/**
 * 初始化
 */
$(document).ready(function() {
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    editor = UE.getEditor('editor');

    //处理公告Json串
    processWithJson();

    //检查是否还有下一页
    checkHasNextPage();
});

/**
 * 处理公告Json串
 */
function processWithJson(){
    //json串转json数组
    if(noticeJsonStr != EMPTY) {
        noticeArray = new Array();
        var array = noticeJsonStr.split(SYMBOL_LOGIC_AND);
        for(var i=0;i<array.length;i++) {
            noticeArray[noticeArray.length] = eval("(" + array[i] + ")");
        }
        //因为加载下一页会把结果拼接到json串后面再生成一次json数组重新加载一次，所以得过滤掉已删除的部分
        for(var i=noticeArray.length-1;i>=0;i--){
            //判已删除，删掉该元素
            if((SYMBOL_COMMA + deletedIds + SYMBOL_COMMA).indexOf(SYMBOL_COMMA + noticeArray[i]["id"] + SYMBOL_COMMA) > -1){
                noticeArray.splice(i, 1);
            }
        }
    }
    var html = "<tr><td width=\"40%\">标题</td><td width=\"20%\">是否已读</td><td width=\"20%\">时间</td><td width=\"20%\">操作</td></tr>";
    for(var i=0;i<noticeArray.length;i++){
        var isReaded = "未读";
        if((SYMBOL_COMMA + readedIds + SYMBOL_COMMA).indexOf(SYMBOL_COMMA + noticeArray[i]["id"] + SYMBOL_COMMA) > -1){
            isReaded = "已读";
        }
        html += "<tr><td>" + noticeArray[i]["title"] + "</td><td>" + isReaded + "</td><td>" + noticeArray[i]["createDate"] + "</td>" +
            "<td>" +
            "<button onclick=\"showNotice(" + noticeArray[i]["id"] + ")\">查看</button>" +
            "<button onclick=\"deleteNotice(" + noticeArray[i]["id"] + ")\">删除</button>" +
            "</td>" +
            "</tr>";
    }
    document.getElementById("notice_table").innerHTML = html;
}

/**
 * 根据id查公告
 * @param noticeId
 */
function getNoticeById(noticeId){
    for(var i=0;i<noticeArray.length;i++){
        if(noticeId == noticeArray[i]["id"]){
            return noticeArray[i];
        }
    }
    return null;
}

/**
 * 删除公告
 * @param noticeId
 */
function deleteNotice(noticeId){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateUserNotice.do",
        data:"type=delete&noticeId=" + noticeId + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    alert(data["message"]);
                    return;
                } else {
                    //请求成功
                    alert(data["message"]);
                    if(deletedIds != EMPTY){
                        deletedIds += SYMBOL_COMMA;
                    }
                    deletedIds += noticeId;
                    //在本页面中删除的个数累加
                    deleteCount++;
                    //处理公告Json串
                    processWithJson();
                    //检查是否还有下一页
                    checkHasNextPage();
                }
                //判是否有新token
                if (data["hasNewToken"]) {
                    token = data["token"];
                }
            } else {
                alert("Connection failed,please try again later!");
            }
        },
        error:function (data, textStatus) {
            alert("Connection failed,please try again later!");
        }
    });
}

/**
 * 查看公告
 * @param noticeId
 */
function showNotice(noticeId){
    //显示查看框
    document.getElementById("showNoticeDiv").style.display = EMPTY;
    //编译html
    document.getElementById("showNoticeTitleDiv").innerHTML = "<b>标题:" + getNoticeById(noticeId)["title"] + "</b>";
    var content = getNoticeById(noticeId)["content"];
    //将uuid->\r\n
    content = changeNewLineBack(content);
    document.getElementById("showNoticeContentDiv").innerHTML = content;
    uParse("#showNoticeContentDiv", {rootPath: baseUrl + '/ueditor/'});

    //判是否已读
    var isReaded = false;
    if((SYMBOL_COMMA + readedIds + SYMBOL_COMMA).indexOf(SYMBOL_COMMA + noticeId + SYMBOL_COMMA) > -1){
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
        url:baseUrl + "operateUserNotice.do",
        data:"type=read&noticeId=" + noticeId + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    alert(data["message"]);
                    return;
                } else {
                    //请求成功
                    alert(data["message"]);
                    if(readedIds != EMPTY){
                        readedIds += SYMBOL_COMMA;
                    }
                    readedIds += noticeId;
                    //处理公告Json串
                    processWithJson();
                    //检查是否还有下一页
                    checkHasNextPage();
                }
                //判是否有新token
                if (data["hasNewToken"]) {
                    token = data["token"];
                }
            } else {
                alert("Connection failed,please try again later!");
            }
        },
        error:function (data, textStatus) {
            alert("Connection failed,please try again later!");
        }
    });
}

/**
 * 检查是否还有下一页
 */
function checkHasNextPage(){
    if(notDeletedNoticeCount > noticeArray.length+deleteCount){
        document.getElementById("nextPageDiv").style.display = EMPTY;
    } else {
        document.getElementById("nextPageDiv").style.display = "none";
    }
}

/**
 * 加载下一页公告
 */
function showNextPageNotices(){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateUserNotice.do",
        data:"type=nextPage&countNow=" + noticeArray.length + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    alert(data["message"]);
                    return;
                } else {
                    //请求成功
                    alert(data["message"]);
                    var nextPageJson = data["nextPageJson"];
                    if(EMPTY != nextPageJson) {
                        noticeJsonStr += SYMBOL_LOGIC_AND + nextPageJson;
                    }
                    //处理公告Json串
                    processWithJson();
                    //检查是否还有下一页
                    checkHasNextPage();
                }
                //判是否有新token
                if (data["hasNewToken"]) {
                    token = data["token"];
                }
            } else {
                alert("Connection failed,please try again later!");
            }
        },
        error:function (data, textStatus) {
            alert("Connection failed,please try again later!");
        }
    });
}