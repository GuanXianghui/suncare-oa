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
    document.getElementById("update_password_td").style.display = "";
    document.getElementById("password").value = "";
}

//取消修改密码
function cancelUpdatePassword(){
    document.getElementById("before_update_password_td").style.display = "";
    document.getElementById("update_password_td").style.display = "none";
}

/**
 * 点击修改密码
 * 用到checkStr()，依赖base.js
 */
function updatePassword(){
    //密码md5签名
    var password = document.getElementById("password");
    if (password.value == "") {
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

/**
 * 处理 所有公司结构json串
 */
function processWithJson() {
    //json串转json数组
    if(structureJsonStr != "") {
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
        pids = "";
        var tempStr = "<tr>";
        for(var i=0;i<array.length;i++) {
            if(array[i] == "") {
                tempStr += "<td></td>";
                pids += ",";
                continue;
            }
            var hasSon = false;
            for(var j=0;j<structureArray.length;j++) {
                if(structureArray[j]["pid"] == array[i]) {
                    hasSon = true;
                    var className = (1== structureArray[j]["type"])?"company":((2== structureArray[j]["type"])?"dept":"position");
                    tempStr += "<td";
                    if(structureArray[j]["type"] == 3) {
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
    document.getElementById("structure_div").style.display = "";
}

/**
 * 取消修改职位
 */
function cancelUpdatePosition() {
    document.getElementById("structure_div").style.display = "none";
}
