//ueditor编辑器
var editor;

//评论类型:createReview:新增评论，updateReview:修改评论，replyReview:回复评论
var reviewType = EMPTY;
var updateReviewId = 0;//修改评论id

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
});

/**
 * 点击修改按钮
 */
function beforeUpdateDiary(){
    document.getElementById("showDiv").style.display = "none";
    document.getElementById("updateDiv").style.display = "";
    editor.setContent(document.getElementById("initContent").innerHTML);
}

/**
 * 取消修改按钮
 */
function cancelUpdateDiary(){
    document.getElementById("showDiv").style.display = "";
    document.getElementById("updateDiv").style.display = "none";
}

/**
 * 修改工作日志
 */
function updateDiary(){
    var date = document.getElementById("date").value;
    if(date == EMPTY){
        showAttention("请输入日期");
        return false;
    }
    var content = editor.getContent();
    if(content.length > DIARY_CONTENT_LENGTH) {
        showAttention("工作日志内容大于" + DIARY_CONTENT_LENGTH + "个字符");
        return false;
    }
    document.getElementById("content").value = content;
    //提交表格
    document.forms["updateDiaryForm"].submit();
}

/**
 * 删除工作日志
 */
function deleteDiary(){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateDiary.do",
        data:"type=delete&diaryId=" + diaryId + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                } else {
                    //请求成功
                    //showSuccess(data["message"]);
                    location.href = baseUrl + "diary.jsp?message=delete diray success!";
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
 * 点赞
 */
function clickZan(){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateDiary.do",
        data:"type=zan&diaryId=" + diaryId + "&token=" + token,
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
                    location.href = baseUrl + "showDiary.jsp?id=" + diaryId + "&message=click zan success!";
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
 * 取消赞
 */
function cancelZan(){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateDiary.do",
        data:"type=cancelZan&diaryId=" + diaryId + "&token=" + token,
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
                    location.href = baseUrl + "showDiary.jsp?id=" + diaryId + "&message=cancel zan success!";
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
function beforeUpdateDiaryReview(diaryReviewId){
    document.getElementById("review_div").style.display = EMPTY;
    document.getElementById("review_desc").innerHTML = document.getElementById("review_desc_" + diaryReviewId).innerText;
    document.getElementById("review_content").value = document.getElementById("review_content_" + diaryReviewId).innerHTML;
    reviewType = "updateReview";//修改评论
    updateReviewId = diaryReviewId;//修改评论id
}

/**
 * 点击回复按钮
 * @param diaryReviewId
 */
function beforeReplyDiaryReview(diaryReviewId){
    document.getElementById("review_div").style.display = EMPTY;
    document.getElementById("review_desc").innerHTML = "你回复" + document.getElementById("review_desc_" + diaryReviewId).innerText;
    document.getElementById("review_content").value = EMPTY;
    reviewType = "replyReview";//回复评论
    updateReviewId = diaryReviewId;//回复评论id
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
    if(content.length > DIARY_REVIEW_CONTENT_LENGTH) {
        showAttention("评语内容大于" + DIARY_REVIEW_CONTENT_LENGTH + "个字符");
        return false;
    }

    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateDiary.do",
        data:"type=" + reviewType + "&diaryId=" + diaryId + "&updateReviewId=" + updateReviewId + "&content=" + content + "&token=" + token,
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
                    location.href = baseUrl + "showDiary.jsp?id=" + diaryId + "&message=review success!";
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
 * @param diaryReviewId
 */
function deleteDiaryReview(diaryReviewId){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateDiary.do",
        data:"type=deleteReview&diaryId=" + diaryId + "&deleteReviewId=" + diaryReviewId + "&token=" + token,
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
                    location.href = baseUrl + "showDiary.jsp?id=" + diaryId + "&message=delete diary review success!";
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