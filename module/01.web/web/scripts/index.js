//登陆
function login() {
    var name = document.getElementById("name").value;
    var password = document.getElementById("password").value;
    //判非空
    if (name == "") {
        alert("请输入用户名!");
        return;
    }
    if (password == "") {
        alert("请输入密码!");
        return;
    }
    // 判断字符串是否含有非法字符
    var result = checkStr(name, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        alert("用户名包含非法字符:" + result["symbol"]);
        return;
    }
    result = checkStr(password, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        alert("密码包含非法字符:" + result["symbol"]);
        return;
    }
    //md5签名
    var md5Pwd = MD5(password + md5Key);
    document.getElementById("password").value = md5Pwd;
    //ajax登陆
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "ajax/login.jsp",
        data:"name=" + name + "&password=" + md5Pwd + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判登陆是否成功
                if (false == data["isSuccess"]) {
                    alert(data["message"]);
                    document.getElementById("password").value = "";
                    //判是否有新token
                    if (data["hasNewToken"]) {
                        token = data["token"];
                    }
                    return;
                } else {
                    //登陆成功
                    alert(data["message"]);
                }
                //是否跳转页面
                if (data["isRedirect"]) {
                    var redirectUrl = data["redirectUrl"];
                    location.href = redirectUrl;
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