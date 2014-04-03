//所有公司结构Json数组
var structureArray = new Array();

/**
 * 初始化
 */
$(document).ready(function() {
    //处理所有公司结构json串
    processWithJson();
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
        alert("请输入密码!");
        return;
    }
    //判断字符串是否含有非法字符
    var result = checkStr(password.value, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        alert("密码包含非法字符:" + result["symbol"]);
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
                    alert(data["message"]);
                    password.value = EMPTY;
                    return;
                } else {
                    //修改密码成功
                    alert(data["message"]);
                    document.getElementById("before_update_password_td").style.display = EMPTY;
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

/**
 * 处理 所有公司结构json串
 */
function processWithJson() {
    //json串转json数组
    if(structureJsonStr != EMPTY) {
        var array = structureJsonStr.split(SYMBOL_BIT_AND);
        for(var i=0;i<array.length;i++) {
            structureArray[structureArray.length] = eval("(" + array[i] + ")");
        }
    }
    //组织所有公司结构表格
    var html = "<tr><td onclick='chooseTd(this, 0)' class='top' colspan='" + calculateSize(0) + "'>组织架构</td></tr>";
    /**
     * pids格式:1,2,,,3
     * 空的可能是上一层某个没有子元素了
     * 全空,,,,,,则结束
     */
    var pids = "0";
    //循环每层展示
    while(true) {
        var array = pids.split(SYMBOL_COMMA);
        pids = EMPTY;
        var tempStr = "<tr>";
        for(var i=0;i<array.length;i++) {
            if(array[i] == EMPTY) {
                tempStr += "<td></td>";
                pids += ",";
                continue;
            }
            var hasSon = false;
            for(var j=0;j<structureArray.length;j++) {
                if(structureArray[j]["pid"] == array[i]) {
                    hasSon = true;
                    var className = (STRUCTURE_TYPE_COMPANY== structureArray[j]["type"])?"company":
                        ((STRUCTURE_TYPE_DEPT== structureArray[j]["type"])?"dept":"position");
                    tempStr += "<td";
                    if(structureArray[j]["type"] == STRUCTURE_TYPE_POSITION) {
                        tempStr += " onclick='chooseTd(this, " + structureArray[j]["id"] + ")'";
                    }
                    tempStr += " class='" + className + "' colspan='" +
                        calculateSize(structureArray[j]["id"]) + "'>" +
                        structureArray[j]["name"] + "</td>";
                    pids += structureArray[j]["id"] + "," ;
                }
            }
            if(hasSon == false) {
                tempStr += "<td></td>";
                pids += ",";
                continue;
            }
        }
        tempStr += "</tr>";
        pids = pids.substr(0, pids.length-1);
        if(pids.length == containCount(pids, SYMBOL_COMMA)) {
            break;
        }
        html += tempStr;
    }
    document.getElementById("structure_table").innerHTML = html;
}

/**
 * 算id所占用的空间
 * @param id
 */
function calculateSize(id) {
    var hasSon = false;//是否有子元素
    var size = 0;
    for(var i=0;i<structureArray.length;i++) {
        if(structureArray[i]["pid"] == id) {
            hasSon = true;
            size += calculateSize(structureArray[i]["id"]);
        }
    }
    if(hasSon == false) {
        size = 1;
    }
    return size;
}

/**
 * 选择节点
 * @param t
 * @param id
 */
function chooseTd(t, id) {
    //ajax操作
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "updatePosition.do",
        data:"id=" + id + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判修改密码是否成功
                if (false == data["isSuccess"]) {
                    alert(data["message"]);
                    return;
                } else {
                    //修改密码成功
                    alert(data["message"]);
                    cancelUpdatePosition();
                    document.getElementById("company_td").innerHTML = data["companyName"];
                    document.getElementById("dept_td").innerHTML = data["deptName"];
                    document.getElementById("position_td").innerHTML = data["positionName"];
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
 * 修改职位
 */
function updatePosition() {
    document.getElementById("structure_div").style.display = EMPTY;
}

/**
 * 取消修改职位
 */
function cancelUpdatePosition() {
    document.getElementById("structure_div").style.display = "none";
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
            alert("生日格式不对!");
            return;
        }
    }
    //判断字符串是否含有非法字符
    var result = checkStr(birthday, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        alert("生日包含非法字符:" + result["symbol"]);
        return;
    }

    var officeTel = document.getElementById("office_tel_input").value;
    result = checkStr(officeTel, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        alert("办公电话包含非法字符:" + result["symbol"]);
        return;
    }
    var mobileTel = document.getElementById("mobile_tel_input").value;
    result = checkStr(mobileTel, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        alert("移动电话包含非法字符:" + result["symbol"]);
        return;
    }
    var desk = document.getElementById("desk_input").value;
    result = checkStr(desk, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        alert("工位包含非法字符:" + result["symbol"]);
        return;
    }
    var email = document.getElementById("email_input").value;
    if(EMPTY != email && !isEmail(email)) {
        alert("email格式不对!");
        return;
    }
    result = checkStr(email, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        alert("邮件包含非法字符:" + result["symbol"]);
        return;
    }
    var qq = document.getElementById("qq_input").value;
    if(EMPTY != qq && !isNum(qq)) {
        alert("qq格式不对!");
        return;
    }
    result = checkStr(qq, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        alert("qq包含非法字符:" + result["symbol"]);
        return;
    }
    var msn = document.getElementById("msn_input").value;
    if(EMPTY != msn && !isEmail(msn)) {
        alert("msn格式不对!");
        return;
    }
    result = checkStr(msn, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        alert("msn包含非法字符:" + result["symbol"]);
        return;
    }
    var address = document.getElementById("address_input").value;
    result = checkStr(address, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        alert("地址包含非法字符:" + result["symbol"]);
        return;
    }
    var website = document.getElementById("website_input").value;
    result = checkStr(website, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        alert("个人网站包含非法字符:" + result["symbol"]);
        return;
    }

    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "updateInfo.do",
        data:"sex=" + sex + "&birthday=" + birthday + "&officeTel=" + officeTel + "&mobileTel=" + mobileTel +
            "&desk=" + desk + "&email=" + email + "&qq=" + qq + "&msn=" + msn + "&address=" + address +
            "&website=" + website + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判修改密码是否成功
                if (false == data["isSuccess"]) {
                    alert(data["message"]);
                    return;
                } else {
                    //修改密码成功
                    alert(data["message"]);
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
                alert("Connection failed,please try again later!");
            }
        },
        error:function (data, textStatus) {
            alert("Connection failed,please try again later!");
        }
    });
}
