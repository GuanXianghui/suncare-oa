//ueditor编辑器
var editor;

//评论类型:createReview:新增评论，updateReview:修改评论，replyReview:回复评论，deleteReview:删除评论
var reviewType = EMPTY;
var updateReviewId = 0;//修改评论id

//所有员工Json数组
var userArray = new Array();

/**
 * 初始化
 */
$(document).ready(function() {
    if(message != EMPTY){
        showInformation(message);
    }

    uParse("#showContent", {rootPath: baseUrl + '/ueditor/'});

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
            document.getElementById("toUserId").innerHTML += "<option value='" + user["id"] + "'" +
                (toUserId==id?" selected":"") + ">" + user["name"] + "</option>"
        }
    }
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

/**
 * 点击修改按钮
 */
function beforeUpdateTask(){
    document.getElementById("showDiv").style.display = "none";
    document.getElementById("updateDiv").style.display = "";
    editor.setContent(document.getElementById("initContent").innerHTML);
}

/**
 * 取消修改按钮
 */
function cancelUpdateTask(){
    document.getElementById("showDiv").style.display = "";
    document.getElementById("updateDiv").style.display = "none";
}

/**
 * 修改工作日志
 */
function updateTask(){
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
    document.forms["updateTaskForm"].submit();
}

/**
 * 删除工作日志
 */
function deleteTask(){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateTask.do",
        data:"type=delete&taskId=" + taskId + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                } else {
                    //请求成功
                    //showSuccess(data["message"]);
                    location.href = baseUrl + "task.jsp?message=delete task success!";
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
 * 点击评论按钮
 */
function beforeReview(){
    document.getElementById("review_div").style.display = EMPTY;
    document.getElementById("review_desc").innerHTML = "你的评语：";
    document.getElementById("review_content").value = EMPTY;
    reviewType = "createReview";//新增评论
}

/**
 * 点击修改工作日志评论
 */
function beforeUpdateTaskReview(taskReviewId){
    document.getElementById("review_div").style.display = EMPTY;
    document.getElementById("review_desc").innerHTML = document.getElementById("review_desc_" + taskReviewId).innerText;
    document.getElementById("review_content").value = document.getElementById("review_content_" + taskReviewId).innerHTML;
    reviewType = "updateReview";//修改评论
    updateReviewId = taskReviewId;//修改评论id
}

/**
 * 点击回复按钮
 * @param taskReviewId
 */
function beforeReplyTaskReview(taskReviewId){
    document.getElementById("review_div").style.display = EMPTY;
    document.getElementById("review_desc").innerHTML = "你回复" + document.getElementById("review_desc_" + taskReviewId).innerText;
    document.getElementById("review_content").value = EMPTY;
    reviewType = "replyReview";//回复评论
    updateReviewId = taskReviewId;//回复评论id
}

/**
 * 取消评论
 */
function cancelReview(){
    document.getElementById("review_div").style.display = "none";
}

/**
 * 评论
 */
function review(){
    var content = document.getElementById("review_content").value;
    //判断字符串是否含有非法字符
    var result = checkStr(content, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        showAttention("评语包含非法字符:" + result["symbol"]);
        return;
    }
    if(content.length > TASK_REVIEW_CONTENT_LENGTH) {
        showAttention("评语内容大于" + TASK_REVIEW_CONTENT_LENGTH + "个字符");
        return false;
    }

    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateTask.do",
        data:"type=" + reviewType + "&taskId=" + taskId + "&updateReviewId=" + updateReviewId +
            "&content=" + content + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                } else {
                    //请求成功
                    //showSuccess(data["message"]);
                    //刷新页面
                    location.href = baseUrl + "showTask.jsp?id=" + taskId + "&message=review success!";
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
 * 删除工作日志评论
 * @param taskReviewId
 */
function deleteTaskReview(taskReviewId){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateTask.do",
        data:"type=deleteReview&taskId=" + taskId + "&deleteReviewId=" + taskReviewId + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                } else {
                    //请求成功
                    //showSuccess(data["message"]);
                    //刷新页面
                    location.href = baseUrl + "showTask.jsp?id=" + taskId + "&message=delete task review success!";
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
 * 修改任务状态
 * @param newState
 */
function updateTaskState(newState){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateTask.do",
        data:"type=updateTaskState&taskId=" + taskId + "&newState=" + newState + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                } else {
                    //请求成功
                    //showSuccess(data["message"]);
                    //刷新页面
                    location.href = baseUrl + "showTask.jsp?id=" + taskId + "&message=update task state success!";
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
 * 催下进度
 */
function cui(){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateTask.do",
        data:"type=cui&taskId=" + taskId + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                } else {
                    //请求成功
                    //showSuccess(data["message"]);
                    //刷新页面
                    location.href = baseUrl + "showTask.jsp?id=" + taskId + "&message=cui success!";
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