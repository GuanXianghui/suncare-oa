//选择日期
var chooseDate = EMPTY;
//提醒数组
var remindArray = new Array();
//新增还是修改 insertRemind/updateRemind
var insertOrUpdate = EMPTY;
//修改提醒id
var updateRemindId = 0;

/**
 * 初始化
 */
$(document).ready(function() {
    $("#date").datepicker();
    $( "#date" ).datepicker( "option", "dateFormat", "yymmdd" );
    $( "#date" ).datepicker( "option", "showAnim", "drop" );
    $( "#date" ).datepicker( "option", "onSelect", function(dateText, inst ){
        //对选择日期赋值
        chooseDate = dateText;
        document.getElementById("create_date").value = chooseDate;
        //根据日期查询提醒
        query();
    });
    //页面加载完查询当前日期
    query();
});

/**
 * 根据提醒id得到提醒
 * @param remindId
 */
function getRemindById(remindId){
    var remind = null;
    for(var i=0;i<remindArray.length;i++){
        if(remindArray[i]["id"] == remindId){
            remind = remindArray[i];
            break;
        }
    }
    return remind;
}

/**
 * 把json字符串转换object数组
 * @param jsonStr
 * @return {Array}
 */
function transferJsonStr2Array(jsonStr){
    var jsonArray = new Array();
    if(jsonStr != EMPTY) {
        var array = jsonStr.split(SYMBOL_LOGIC_AND);
        for(var i=0;i<array.length;i++) {
            jsonArray[jsonArray.length] = eval("(" + array[i] + ")");
        }
    }
    return jsonArray;
}

/**
 * 处理提醒数组
 */
function processRemindArray(){
    //循环展示
    var html = "<thead><tr><th width=\"20%\">内容</th><th width=\"20%\">是否提醒</th>" +
        "<th width=\"20%\">提醒时间</th><th width=\"40%\">操作</th></tr></thead>";
    for(var i=0;i<remindArray.length;i++){
        html += "<tr><td>" + remindArray[i]["content"] + "</td>" +
            "<td>" + remindArray[i]["remindTypeDesc"] + "</td><td>" + remindArray[i]["remindDateTime"] +
            "</td><td><input class=\"button\" type=\"button\" onclick=\"showRemind(" + remindArray[i]["id"] + ")\" value=\"查看\" />" +
            "<input class=\"button\" type=\"button\" onclick=\"beforeUpdateRemind(" + remindArray[i]["id"] + ")\" value=\"修改\" />" +
            "<input class=\"button\" type=\"button\" onclick=\"deleteRemind(" + remindArray[i]["id"] + ")\" value=\"删除\" /></td></tr>";
    }
    html += "<tr><td colspan=\"5\" align=\"center\"><input class=\"button\" type=\"button\" onclick=\"beforeCreateRemind()\" value=\"新增提醒\" /></td></tr>";
    document.getElementById("remind_table").innerHTML = html;
    $('#remind_table tr:even').addClass("alt-row");
}

/**
 * 根据日期查询提醒
 */
function query(){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateRemind.do",
        data:"type=query&date=" + chooseDate + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                    return;
                } else {
                    //请求成功
                    showSuccess(data["message"]);
                    var remindsJson = data["remindsJson"];
                    //把json字符串转换object数组
                    remindArray = transferJsonStr2Array(remindsJson);
                    //处理提醒数组
                    processRemindArray();
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
 * 点击新增提醒按钮
 */
function beforeCreateRemind(){
    if(chooseDate == EMPTY){
        showAttention("请选择日期");
        return;
    }
    document.getElementById("create_remind_table").style.display = EMPTY;
    document.getElementById("show_remind_table").style.display = "none";
    document.getElementById("create_date").value = EMPTY;
    document.getElementById("create_content").value = EMPTY;
    document.getElementById("create_remind_type").value = "1";
    document.getElementById("create_remind_date_time").value = EMPTY;
    insertOrUpdate = "insertRemind";
}

/**
 * 新增提醒
 */
function createRemind(){
    var date = document.getElementById("create_date").value;
    if(date == EMPTY){
        showAttention("请选择日期");
        return false;
    }
    var content = document.getElementById("create_content").value;
    if(content == EMPTY){
        showAttention("请输入内容");
        return false;
    }
    //判断字符串是否含有非法字符
    var result = checkStr(content, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        showAttention("评语包含非法字符:" + result["symbol"]);
        return;
    }
    if(content.length > REMIND_CONTENT_LENGTH) {
        showAttention("内容大于" + REMIND_CONTENT_LENGTH + "个字符");
        return false;
    }
    var remindType = document.getElementById("create_remind_type").value;
    if(remindType == EMPTY){
        showAttention("请选择提醒类型");
        return false;
    }
    var remindDateTime = document.getElementById("create_remind_date_time").value;
    if(remindType != REMIND_TYPE_NO_REMIND && remindDateTime == EMPTY){
        showAttention("请输入提醒时间");
        return false;
    }

    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateRemind.do",
        data:"type=" + insertOrUpdate + "&updateRemindId=" + updateRemindId + "&date=" + date + "&content=" +
            content + "&remindType=" + remindType + "&remindDateTime=" + remindDateTime + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                    return;
                } else {
                    //请求成功
                    showSuccess(data["message"]);
                    var remindsJson = data["remindsJson"];
                    //把json字符串转换object数组
                    remindArray = transferJsonStr2Array(remindsJson);
                    //处理提醒数组
                    processRemindArray();
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
 * 查看提醒
 * @param remindId
 */
function showRemind(remindId){
    var remind = getRemindById(remindId);
    document.getElementById("show_date").innerHTML = remind["date"];
    document.getElementById("show_content").innerHTML = remind["content"];
    document.getElementById("show_remind_type").innerHTML = remind["remindTypeDesc"];
    document.getElementById("show_remind_date_time").innerHTML = remind["remindDateTime"];
    document.getElementById("create_remind_table").style.display = "none";
    document.getElementById("show_remind_table").style.display = EMPTY;
}

/**
 * 点击修改提醒按钮
 * @param remindId
 */
function beforeUpdateRemind(remindId){
    insertOrUpdate = "updateRemind";
    updateRemindId = remindId;
    var remind = getRemindById(remindId);
    document.getElementById("create_date").value = remind["date"];
    document.getElementById("create_content").value = remind["content"];
    document.getElementById("create_remind_type").value = remind["remindType"];
    document.getElementById("create_remind_date_time").value = remind["remindDateTime"];
    document.getElementById("create_remind_table").style.display = EMPTY;
    document.getElementById("show_remind_table").style.display = "none";
}

/**
 * 删除提醒
 * @param remindId
 */
function deleteRemind(remindId){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateRemind.do",
        data:"type=deleteRemind&deleteRemindId=" + remindId + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                    return;
                } else {
                    //请求成功
                    showSuccess(data["message"]);
                    var remindsJson = data["remindsJson"];
                    //把json字符串转换object数组
                    remindArray = transferJsonStr2Array(remindsJson);
                    //处理提醒数组
                    processRemindArray();
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
 * 切换模式
 */
function changeMode(){
    var className = $("#content-box1").attr("class");
    if("content-box" == className){
        $("#content-box1").attr("class", "content-box column-left");
        $("#content-box2").attr("class", "content-box column-right");
    } else {
        $("#content-box1").attr("class", "content-box");
        $("#content-box2").attr("class", "content-box");
    }
    return false;
}