//工作日志Json数组
var diaryArray = new Array();

/**
 * 初始化
 */
$(document).ready(function() {
    //把初始diaryJsonStr转换成diaryArray
    diaryArray = transferInitJsonStr2Array(diaryJsonStr);

    //处理工作日志Json串
    processWithJson();

    //检查是否还有下一页
    checkHasNextPage();
});

/**
 * 把初始diaryJsonStr转换成diaryArray
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
 * 处理工作日志Json串
 */
function processWithJson(){
    //循环展示
    var html = "<tr></td><td width=\"10%\">序号</td><td width=\"50%\">用户</td><td width=\"40%\">时间</td></tr>";
    for(var i=0;i<diaryArray.length;i++){
        html += "<tr><td>" + diaryArray[i]["id"] + "</td><td><a href=\"" + baseUrl +"user.jsp?id=" +
            diaryArray[i]["userId"] + "\" target=\"_blank\">" + diaryArray[i]["userName"] +
            "</a> </td><td><a href=\"" + baseUrl + "showDiary.jsp?id=" + diaryArray[i]["id"] + "\">" +
            diaryArray[i]["date"] + "</a></td></tr>";
    }
    document.getElementById("diary_table").innerHTML = html;
}

/**
 * 检查是否还有下一页
 */
function checkHasNextPage(){
    if(diaryCount > diaryArray.length){
        document.getElementById("nextPageDiv").style.display = EMPTY;
    } else {
        document.getElementById("nextPageDiv").style.display = "none";
    }
}

/**
 * 塞选工作日志
 */
function selectDiary(){
    var userId = document.getElementById("userId").value;
    var date = document.getElementById("date").value;
    var condition = EMPTY;
    if(userId != EMPTY){
        if(condition == EMPTY){
            condition += "?";
        } else {
            condition += "&";
        }
        condition += "userId=" + userId;
    }
    if(date != EMPTY){
        if(condition == EMPTY){
            condition += "?";
        } else {
            condition += "&";
        }
        condition += "date=" + date;
    }

    location.href = baseUrl + "diary.jsp" + condition;
}

/**
 * 加载下一页
 */
function showNextPageDiaries(){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateDiary.do",
        data:"type=nextPage&countNow=" + diaryArray.length + "&userId=" + userId + "&date=" + date + "&token=" + token,
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
                    var nextPageJson = data["nextPageJson"];
                    if(EMPTY != nextPageJson) {
                        //把初始letterJsonStr转换成letterArray
                        var array = transferInitJsonStr2Array(nextPageJson);
                        diaryArray = diaryArray.concat(array);
                    }
                    //处理站内信Json串
                    processWithJson();
                    //检查是否还有下一页
                    checkHasNextPage();
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