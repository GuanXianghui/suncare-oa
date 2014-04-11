//短信Json数组
var smsArray = new Array();

/**
 * 初始化
 */
$(document).ready(function() {
    $("#date").datepicker();
    $( "#date" ).datepicker( "option", "dateFormat", "yymmdd" );
    $( "#date" ).datepicker( "option", "showAnim", "drop" );
    $( "#date" ).datepicker( "option", "onSelect", function(dateText, inst ){
        //根据日期查询短信
        location.href = baseUrl + "sms.jsp?date=" + dateText;
    });
    //把初始smsJsonStr转换成smsArray
    smsArray = transferInitJsonStr2Array(smsJsonStr);

    //处理短信Json串
    processWithJson();
});

/**
 * 把初始smsJsonStr转换成smsArray
 */
function transferInitJsonStr2Array(jsonStr){
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
 * 处理短信Json串
 */
function processWithJson(){
    //循环展示
    var html = "<tr><td width=\"10%\">电话</td><td width=\"50%\">电话</td><td width=\"10%\">状态</td>" +
        "<td width=\"10%\">时间</td><td width=\"10%\">操作</td></tr>";
    for(var i=0;i<smsArray.length;i++){
        html += "<tr><td>" + smsArray[i]["phone"] + "</td><td>" + changeNewLineBack2(smsArray[i]["content"]) +
            "</td><td>" + smsArray[i]["stateDesc"] + "</td><td>" + smsArray[i]["time"] + "</td><td>" +
            "<button onclick=\"transmit(" + smsArray[i]["id"] + ")\">转发</button></td></tr>";
    }
    document.getElementById("sms_table").innerHTML = html;
}

/**
 * 根据id查短信
 * @param smsId
 */
function getSMSById(smsId){
    for(var i=0;i<smsArray.length;i++){
        if(smsId == smsArray[i]["id"]){
            return smsArray[i];
        }
    }
    return null;
}

/**
 * 点击发送短信按钮
 */
function beforeSendSMS(){
    document.getElementById("sendSmsDiv").style.display = EMPTY;
}

/**
 * 点击转发短信按钮
 * @param smsId
 */
function transmit(smsId){
    var sms = getSMSById(smsId);
    document.getElementById("content").value = changeNewLineBack2(sms["content"]);
    document.getElementById("sendSmsDiv").style.display = EMPTY;
}

/**
 * 发送短信
 */
function operateSMS(){
    var phone = document.getElementById("phone").value;
    var content = document.getElementById("content").value;
    if(phone == EMPTY){
        alert("请输入手机号");
        return false;
    }
    if(content == EMPTY){
        alert("请输入内容");
        return false;
    }
    var result = checkStr(content, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        alert("内容包含非法字符:" + result["symbol"]);
        return false;
    }
    if(content.length > SMS_CONTENT_LENGTH) {
        alert("短信内容大于" + SMS_CONTENT_LENGTH + "个字符");
        return false;
    }

    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateSMS.do",
        data:"type=send&phone=" + phone + "&content=" + content + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    alert(data["message"]);
                } else {
                    //请求成功
                    alert(data["message"]);
                    location.href = baseUrl + "sms.jsp?date=" + date;
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