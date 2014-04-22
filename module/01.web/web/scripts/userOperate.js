//所有公司结构Json数组
var structureArray = new Array();

/**
 * 初始化
 */
$(document).ready(function() {
    //处理所有公司结构json串
    processWithJson();
});

//用户集合
var userArray = new Array();
//创建用户还是修改用户 create:创建用户 update:修改用户
var createOrUpdate = EMPTY;
//修改用户类型 重置密码：initPassword 修改职位：updatePosition 修改状态：updateState
var updateType = EMPTY;
//修改职位用户id
var updatePositionUserId = 0;

/**
 * 点击创建用户
 */
function chooseCreateUser(){
    document.getElementById("update_user_div").style.display = "none";
    document.getElementById("create_user_div").style.display = "";
}

/**
 * 点击修改用户
 */
function chooseUpdateUser(){
    document.getElementById("update_user_div").style.display = "";
    document.getElementById("create_user_div").style.display = "none";

}

/**
 * 把初始jsonStr转换成array
 */
function transferInitJsonStr2Array(jsonStr){
    var jsonArray = new Array();
    if(jsonStr != EMPTY) {
        var array = jsonStr.split(SYMBOL_BIT_AND);
        for(var i=0;i<array.length;i++) {
            jsonArray[jsonArray.length] = eval("(" + array[i] + ")");
        }
    }
    return jsonArray;
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
    var html = "<tr><td class='top' colspan='" + calculateSize(0) + "'>组织架构</td></tr>";
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
 * 根据id得到组织架构
 * @param id
 */
function getStructureById(id){
    var structure = null;
    for(var i=0;i<structureArray.length;i++){
        if(structureArray[i]["id"] == id){
            structure = structureArray[i];
        }
    }
    return structure;
}

/**
 * 选择节点
 * @param t
 * @param id
 */
function chooseTd(t, id) {
    var structure = getStructureById(id);
    positionId = structure["id"];
    cancelChoosePosition();

    //判断 创建用户还是修改用户
    if(createOrUpdate == 'create'){
        document.getElementById("position_td").innerHTML = structure["name"];
    } else if(createOrUpdate == 'update'){
        //修改职位
        updatePosition();
    }
}

/**
 * 选择职位
 */
function choosePosition() {
    document.getElementById("structure_div").style.display = EMPTY;
}

/**
 * 取消选择职位
 */
function cancelChoosePosition() {
    document.getElementById("structure_div").style.display = "none";
}

/**
 * 修改姓名
 */
function changeName() {
    letter = getAllFirstPinYin(document.getElementById("name").value);
    document.getElementById("letter").innerHTML = letter;
}

/**
 * 创建用户
 */
function createUser(){
    //姓名
    var name = document.getElementById("name").value;
    if(name == EMPTY){
        alert("请输入姓名！");
        return;
    }
    if(positionId == 0){
        alert("请选择职位！")
        return;
    }

    //ajax操作
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "createUser.do",
        data:"name=" + name + "&letter=" + letter + "&positionId=" + positionId + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判是否成功
                if (false == data["isSuccess"]) {
                    alert(data["message"]);
                } else {
                    //成功
                    alert(data["message"]);
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
 * 根据用户名字查用户
 */
function queryUser(){
    var name = document.getElementById("user_name").value;
    if(name == EMPTY){
        alert("请输入姓名！");
        return;
    }
    //ajax操作
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "queryUser.do",
        data:"name=" + name + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判是否成功
                if (false == data["isSuccess"]) {
                    alert(data["message"]);
                } else {
                    //成功
                    alert(data["message"]);
                    userArray = transferInitJsonStr2Array(data["jsonStr"]);
                    fillUserList();
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
 * 填充用户
 */
function fillUserList(){
    var html = "<table border='1' width='100%'>" +
        "<tr><td>用户名</td><td>重置密码</td><td>公司</td><td>部门</td><td>职位</td><td>修改职位</td><td>修改状态</td></tr>";
    if(userArray.length == 0){
        html += "<tr><td align='center' colspan='7'>无符合条件的用户</td></tr>"
    } else {
        for(var i=0;i<userArray.length;i++){
            //状态: 1正常，2锁定，3离职
            var selectContent = "<select id=\"select_" + userArray[i]["id"] + "\">" +
                "<option value=\"1\"" + (userArray[i]["state"]==1?" selected":"") + ">正常</option>" +
                "<option value=\"2\"" + (userArray[i]["state"]==2?" selected":"") + ">锁定</option>" +
                "<option value=\"3\"" + (userArray[i]["state"]==3?" selected":"") + ">离职</option>" +
                "</select>";

            html += "<tr><td><a href=\"user.jsp?id=" + userArray[i]["id"] + "\" target=\"_blank\">" +
                userArray[i]["name"] + "</a></td><td><button onclick='initPassword(" + userArray[i]["id"] +
                ")'>重置密码</button></td><td>" + userArray[i]["companyName"] + "</td><td>" +
                userArray[i]["deptName"] + "</td><td>" + userArray[i]["positionName"] + "</td>" +
                "<td><button onclick='beforeUpdatePosition(" + userArray[i]["id"] + ")'>修改职位</button></td>" +
                "<td>" + selectContent + "<button onclick='updateState(" + userArray[i]["id"] + ")'>修改</button></td>" +
                "</tr>"
        }
    }
    html += "</table>";

    document.getElementById("user_list").innerHTML = html;
}

/**
 * 重置密码
 * @param userId
 */
function initPassword(userId){
    //修改用户类型
    updateType = "initPassword";
    //ajax操作
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "updateUser.do",
        data:"updateType=" + updateType + "&userId=" + userId + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判是否成功
                if (false == data["isSuccess"]) {
                    alert(data["message"]);
                } else {
                    //成功
                    alert(data["message"]);
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
 * 修改用户职位
 * @param userId
 */
function beforeUpdatePosition(userId){
    createOrUpdate = 'update';
    updatePositionUserId = userId;
    choosePosition();
}

/**
 * 修改职位
 */
function updatePosition(){
    //修改用户类型
    updateType = "updatePosition";
    //ajax操作
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "updateUser.do",
        data:"updateType=" + updateType + "&userId=" + updatePositionUserId + "&positionId=" + positionId +
            "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判是否有新token
                if (data["hasNewToken"]) {
                    token = data["token"];
                }
                //判是否成功
                if (false == data["isSuccess"]) {
                    alert(data["message"]);
                } else {
                    //成功
                    alert(data["message"]);
                    //重新根据用户名字查用户
                    queryUser();
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
 * 修改用户状态
 *
 * @param userId
 */
function updateState(userId){
    updateType = "updateState";
    var state = document.getElementById("select_" + userId).value;

    //ajax操作
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "updateUser.do",
        data:"updateType=" + updateType + "&userId=" + userId + "&state=" + state + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判是否有新token
                if (data["hasNewToken"]) {
                    token = data["token"];
                }
                //判是否成功
                if (false == data["isSuccess"]) {
                    alert(data["message"]);
                } else {
                    //成功
                    alert(data["message"]);
                    //重新根据用户名字查用户
                    queryUser();
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