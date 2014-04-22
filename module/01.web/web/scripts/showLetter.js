/**
 * 初始化
 */
$(document).ready(function() {
    /**
     * 初始化
     */
    $(document).ready(function() {
        uParse("#editor", {rootPath: baseUrl + '/ueditor/'});
    });
});

/**
 * 回复
 */
function reply(){
    location.href = baseUrl + "writeLetter.jsp?type=reply&id=" + letterId;
}

/**
 * 回复全部
 */
function replyAll(){
    location.href = baseUrl + "writeLetter.jsp?type=replyAll&id=" + letterId;
}

/**
 * 转发
 */
function transmit(){
    location.href = baseUrl + "writeLetter.jsp?type=transmit&id=" + letterId;
}

/**
 * 删除
 */
function deleteLetter(){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateLetter.do",
        data:"type=delete&ids=" + letterId + "&token=" + token,
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
                    location.href = baseUrl + "letter.jsp";
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
 * 彻底删除
 */
function ctrlDeleteLetter(){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateLetter.do",
        data:"type=ctrlDelete&ids=" + letterId + "&token=" + token,
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
                    location.href = baseUrl + "letter.jsp";
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