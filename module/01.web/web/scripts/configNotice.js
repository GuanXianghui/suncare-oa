//ueditor编辑器
var editor;
//公告Json数组
var noticeArray = new Array();

/**
 * 初始化
 */
$(document).ready(function() {
    if(message != EMPTY){
        showInformation(message);
    }
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
    }
    var html = "<thead><tr><th>标题</th><th>时间</th><th>操作<input class=\"button\" type=\"button\" " +
        "onclick=\"beforeCreateNotice()\" value=\"新增\" /></th></tr></thead>";
    for(var i=0;i<noticeArray.length;i++){
        html += "<tr><td>" + noticeArray[i]["title"] + "</td><td>" + noticeArray[i]["createDate"] + "</td>" +
            "<td>" +
            "<input class=\"button\" type=\"button\" onclick=\"showNotice(" + noticeArray[i]["id"] + ")\" value=\"查看\" />" +
            "<input class=\"button\" type=\"button\" onclick=\"beforeUpdateNotice(" + noticeArray[i]["id"] + ")\" value=\"修改\" />" +
            "<input class=\"button\" type=\"button\" onclick=\"deleteNotice(" + noticeArray[i]["id"] + ")\" value=\"删除\" />" +
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
 * 输入检测
 */
function checkInput(){
    var title = document.getElementById("title").value;
    if(title == EMPTY){
        showAttention("标题不能为空");
        return false;
    }
    //判断字符串是否含有非法字符
    var result = checkStr(title, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        showAttention("标题包含非法字符:" + result["symbol"]);
        return false;
    }
    if(title.length > NOTICE_TITLE_LENGTH) {
        showAttention("公告标题大于" + NOTICE_TITLE_LENGTH + "个字符");
        return false;
    }
    var content = editor.getContent();
    if(content == EMPTY){
        showAttention("内容不能为空");
        return false;
    }
    if(content.length > NOTICE_CONTENT_LENGTH) {
        showAttention("公告内容大于" + NOTICE_CONTENT_LENGTH + "个字符");
        return false;
    }
    document.getElementById("content").value = content;
    return true;
}

/**
 * 点击创建按钮
 */
function beforeCreateNotice(){
    //修改标题，内容和按钮
    document.getElementById("title").value = EMPTY;
    editor.setContent(EMPTY);
    document.getElementById("configButton").value = "新增公告";
    document.getElementById("configButton").onclick = function(){createNotice();};
    //显示配置框，隐藏查看框
    document.getElementById("configNoticeDiv").style.display = EMPTY;
    document.getElementById("showNoticeDiv").style.display = "none";
}

/**
 * 创建公告
 */
function createNotice(){
    //输入检测
    if(checkInput() == false){
        return;
    }
    //更新token
    document.getElementById("token").value = token;
    //修改公告管理类型 和 修改公告ID
    document.getElementById("type").value = NOTICE_TYPE_ADD;
    document.getElementById("noticeId").value = EMPTY;
    //提交表单
    document.forms["configNoticeForm"].submit();
}

/**
 * 点击修改按钮
 * @param noticeId
 */
function beforeUpdateNotice(noticeId){
    //取得公告
    var notice = getNoticeById(noticeId);
    //修改标题，内容和按钮
    document.getElementById("title").value = notice["title"];
    var content = notice["content"];
    //将uuid->\r\n
    content = changeNewLineBack(content);
    editor.setContent(content);
    document.getElementById("configButton").value = "修改公告";
    document.getElementById("configButton").onclick = function(){updateNotice(noticeId)};
    //显示配置框，隐藏查看框
    document.getElementById("configNoticeDiv").style.display = EMPTY;
    document.getElementById("showNoticeDiv").style.display = "none";
}

/**
 * 修改公告
 * @param noticeId
 */
function updateNotice(noticeId){
    //输入检测
    if(checkInput() == false){
        return;
    }
    //更新token
    document.getElementById("token").value = token;
    //修改公告管理类型 和 修改公告ID
    document.getElementById("type").value = NOTICE_TYPE_UPDATE;
    document.getElementById("noticeId").value = noticeId;
    //提交表单
    document.forms["configNoticeForm"].submit();
}

/**
 * 删除公告
 * @param noticeId
 */
function deleteNotice(noticeId){
    //更新token
    document.getElementById("token").value = token;
    //修改公告管理类型 和 修改公告ID
    document.getElementById("type").value = NOTICE_TYPE_DELETE;
    document.getElementById("noticeId").value = noticeId;
    //提交表单
    document.forms["configNoticeForm"].submit();
}

/**
 * 查看公告
 * @param noticeId
 */
function showNotice(noticeId){
    //隐藏配置框，显示查看框
    document.getElementById("showNoticeDiv").style.display = EMPTY;
    document.getElementById("configNoticeDiv").style.display = "none";
    //编译html
    document.getElementById("showNoticeTitleDiv").innerHTML = "<b>标题:" + getNoticeById(noticeId)["title"] + "</b>";
    var content = getNoticeById(noticeId)["content"];
    //将uuid->\r\n
    content = changeNewLineBack(content);
    document.getElementById("showNoticeContentDiv").innerHTML = content;
    uParse("#showNoticeContentDiv", {rootPath: baseUrl + '/ueditor/'});
}

/**
 * 检查是否还有下一页
 */
function checkHasNextPage(){
    if(noticeCount > noticeArray.length){
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
        url:baseUrl + "showNextPageNotices.do",
        data:"countNow=" + noticeArray.length + "&token=" + token,
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
                    noticeJsonStr += SYMBOL_LOGIC_AND + nextPageJson;
                    //处理公告Json串
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
 * 切换模式
 */
function changeMode(){
    var className = $("#content-box1").attr("class");
    if("content-box" == className){
        $("#content-box1").attr("class", "content-box column-left");
        $("#content-box2").attr("class", "content-box column-right");
    } else {
        $("#content-box1").attr("class", "content-box");
        $("#content-box2").attr("class", "content-box");
    }
    return false;
}