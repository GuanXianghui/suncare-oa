
//点击修改密码
function beforeUpdatePassword(){
    document.getElementById("before_update_password_td").style.display = "none";
    document.getElementById("update_password_td").style.display = "";
    document.getElementById("password").value = "";
}

//取消修改密码
function cancelUpdatePassword(){
    document.getElementById("before_update_password_td").style.display = "";
    document.getElementById("update_password_td").style.display = "none";
}

//点击修改密码
function updatePassword(){
    //密码md5签名
    var password = document.getElementById("password");
    var md5Pwd = MD5(password.value + md5Key);
    password.value = md5Pwd;
    //ajax修改密码
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "updatePassword.do",
        data:"password=" + md5Pwd + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判修改密码是否成功
                if (false == data["isSuccess"]) {
                    alert(data["message"]);
                    password.value = "";
                    return;
                } else {
                    //修改密码成功
                    alert(data["message"]);
                    document.getElementById("before_update_password_td").style.display = "";
                    document.getElementById("update_password_td").style.display = "none";
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
