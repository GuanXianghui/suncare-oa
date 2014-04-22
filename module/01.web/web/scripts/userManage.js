/**
 * 初始化
 */
$(document).ready(function() {
    //如果message非空则显示
    if(EMPTY != message){
        showInformation(message);
    }
});

//点击修改密码
function beforeUpdatePassword(){
    document.getElementById("before_update_password_td").style.display = "none";
    document.getElementById("update_password_td").style.display = EMPTY;
    document.getElementById("password").value = EMPTY;
}

//取消修改密码
function cancelUpdatePassword(){
    document.getElementById("before_update_password_td").style.display = EMPTY;
    document.getElementById("update_password_td").style.display = "none";
}

//点击修改密码
function beforeUploadHeadPhoto(){
    document.getElementById("before_upload_head_photo_td").style.display = "none";
    document.getElementById("upload_head_photo_td").style.display = EMPTY;
}

//取消修改密码
function cancelUploadHeadPhoto(){
    document.getElementById("before_upload_head_photo_td").style.display = EMPTY;
    document.getElementById("upload_head_photo_td").style.display = "none";
}

//上传图片
function uploadHeadPhoto(){
    document.forms["uploadHeadPhotoForm"].action = baseUrl + "uploadHeadPhoto.do?token=" + token;
    document.forms["uploadHeadPhotoForm"].submit();
}

/**
 * 点击修改密码
 * 用到checkStr()，依赖base.js
 */
function updatePassword(){
    //密码md5签名
    var password = document.getElementById("password");
    if (password.value == EMPTY) {
        showAttention("请输入密码!");
        return;
    }
    //判断字符串是否含有非法字符
    var result = checkStr(password.value, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        showAttention("密码包含非法字符:" + result["symbol"]);
        return;
    }

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
                    showError(data["message"]);
                    password.value = EMPTY;
                    return;
                } else {
                    //修改密码成功
                    showSuccess(data["message"]);
                    document.getElementById("before_update_password_td").style.display = EMPTY;
                    document.getElementById("update_password_td").style.display = "none";
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

//用户信息输入项
var infoArray = new Array("sex","birthday","office_tel","mobile_tel","desk","email","qq","msn","address","website");

//点击修改信息
function beforeUpdateInfo(){
    document.getElementById("before_update_info_td").style.display = "none";
    document.getElementById("update_info_td").style.display = EMPTY;
    for(var i=0;i<infoArray.length;i++) {
        document.getElementById(infoArray[i] + "_td_1").style.display = "none";
        document.getElementById(infoArray[i] + "_td_2").style.display = EMPTY;
    }
}

//取消修改信息
function cancelUpdateInfo(){
    document.getElementById("before_update_info_td").style.display = EMPTY;
    document.getElementById("update_info_td").style.display = "none";
    for(var i=0;i<infoArray.length;i++) {
        document.getElementById(infoArray[i] + "_td_1").style.display = EMPTY;
        document.getElementById(infoArray[i] + "_td_2").style.display = "none";
    }
}

//修改信息
function updateInfo(){
    //格式校验
    var sex = document.getElementById("sex_select").value;
    var birthday = document.getElementById("birthday_input").value;
    if(EMPTY != birthday) {
        if(!isNum(birthday) || birthday.length != 8) {
            showAttention("生日格式不对!");
            return;
        }
    }
    //判断字符串是否含有非法字符
    var result = checkStr(birthday, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        showAttention("生日包含非法字符:" + result["symbol"]);
        return;
    }

    var officeTel = document.getElementById("office_tel_input").value;
    result = checkStr(officeTel, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        showAttention("办公电话包含非法字符:" + result["symbol"]);
        return;
    }
    var mobileTel = document.getElementById("mobile_tel_input").value;
    result = checkStr(mobileTel, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        showAttention("移动电话包含非法字符:" + result["symbol"]);
        return;
    }
    var desk = document.getElementById("desk_input").value;
    result = checkStr(desk, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        showAttention("工位包含非法字符:" + result["symbol"]);
        return;
    }
    var email = document.getElementById("email_input").value;
    if(EMPTY != email && !isEmail(email)) {
        showAttention("email格式不对!");
        return;
    }
    result = checkStr(email, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        showAttention("邮件包含非法字符:" + result["symbol"]);
        return;
    }
    var qq = document.getElementById("qq_input").value;
    if(EMPTY != qq && !isNum(qq)) {
        showAttention("qq格式不对!");
        return;
    }
    result = checkStr(qq, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        showAttention("qq包含非法字符:" + result["symbol"]);
        return;
    }
    var msn = document.getElementById("msn_input").value;
    if(EMPTY != msn && !isEmail(msn)) {
        showAttention("msn格式不对!");
        return;
    }
    result = checkStr(msn, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        showAttention("msn包含非法字符:" + result["symbol"]);
        return;
    }
    var address = document.getElementById("address_input").value;
    result = checkStr(address, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        showAttention("地址包含非法字符:" + result["symbol"]);
        return;
    }
    var website = document.getElementById("website_input").value;
    result = checkStr(website, SYMBOL_ARRAY_2_CHECK_URL);
    if (result["isSuccess"] == false) {
        showAttention("个人网站包含非法字符:" + result["symbol"]);
        return;
    }

    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "updateInfo.do",
        data:"sex=" + filterStr(sex) + "&birthday=" + filterStr(birthday) + "&officeTel=" + filterStr(officeTel) +
            "&mobileTel=" + filterStr(mobileTel) + "&desk=" + filterStr(desk) + "&email=" + filterStr(email) +
            "&qq=" + filterStr(qq) + "&msn=" + filterStr(msn) + "&address=" + filterStr(address) + "&website=" +
            filterStr(website) + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判修改密码是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                    return;
                } else {
                    //修改密码成功
                    showSuccess(data["message"]);
                    document.getElementById("sex_td_1").innerHTML = data["sex"];
                    document.getElementById("birthday_td_1").innerHTML = data["birthday"];
                    document.getElementById("office_tel_td_1").innerHTML = officeTel;
                    document.getElementById("mobile_tel_td_1").innerHTML = mobileTel;
                    document.getElementById("desk_td_1").innerHTML = desk;
                    document.getElementById("email_td_1").innerHTML = email;
                    document.getElementById("qq_td_1").innerHTML = qq;
                    document.getElementById("msn_td_1").innerHTML = msn;
                    document.getElementById("address_td_1").innerHTML = address;
                    document.getElementById("website_td_1").innerHTML = "<a href=\"" + website +
                        "\" target=\"_blank\">" + website + "</a>";
                    cancelUpdateInfo();
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
