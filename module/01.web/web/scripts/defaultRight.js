/**
 * 初始化
 */
$(document).ready(function() {
    if(message != EMPTY){
        showInformation(message);
    }
});

/**
 * 修改默认权限
 */
function updateDefaultRight() {
    var nodes = $("input[type=checkbox]");
    var rightCodes = EMPTY;
    for(var i=0;i<nodes.length;i++){
        if(true ==nodes[i].checked){
            if(rightCodes != EMPTY){
                rightCodes += SYMBOL_COMMA;
            }
            rightCodes += nodes[i].name;
        }
    }

    //ajax操作
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "updateDefaultRight.do",
        data:"rightCodes=" + rightCodes + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判修改密码是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                } else {
                    //修改密码成功
                    showSuccess(data["message"]);
                    location.href = baseUrl + "defaultRight.jsp?message=update default right success!"
                    return;
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
