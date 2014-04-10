//任务Json数组
var taskArray = new Array();

/**
 * 初始化
 */
$(document).ready(function() {
    //把初始taskJsonStr转换成taskArray
    taskArray = transferInitJsonStr2Array(taskJsonStr);

    //处理任务Json串
    processWithJson();

    //检查是否还有下一页
    checkHasNextPage();
});

/**
 * 把初始taskJsonStr转换成taskArray
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
 * 处理任务Json串
 */
function processWithJson(){
    //循环展示
    var html = "<tr></td><td width=\"10%\">序号</td><td width=\"15%\">任务来源用户</td>" +
        "<td width=\"15%\">任务接受用户</td><td width=\"30%\">任务名称</td>" +
        "<td width=\"10%\">状态</td><td width=\"10%\">开始日期</td>" +
        "<td width=\"10%\">结束日期</td></tr>";
    for(var i=0;i<taskArray.length;i++){
        html += "<tr><td>" + taskArray[i]["id"] + "</td><td><a href=\"" + baseUrl +"user.jsp?id=" +
            taskArray[i]["fromUserId"] + "\" target=\"_blank\">" + taskArray[i]["fromUserName"] +
            "</a></td>" + "<td><a href=\"" + baseUrl +"user.jsp?id=" + taskArray[i]["toUserId"] + "\" " +
            "target=\"_blank\">" + taskArray[i]["toUserName"] + "</a></td><td><a href=\"" + baseUrl +
            "showTask.jsp?id=" + taskArray[i]["id"] + "\">" + taskArray[i]["title"] + "</a></td><td>" +
            taskArray[i]["stateDesc"] + "</td><td>" + taskArray[i]["beginDate"] + "</td><td>" +
            taskArray[i]["endDate"] + "</td></tr>";
    }
    document.getElementById("task_table").innerHTML = html;
}

/**
 * 检查是否还有下一页
 */
function checkHasNextPage(){
    if(taskCount > taskArray.length){
        document.getElementById("nextPageDiv").style.display = EMPTY;
    } else {
        document.getElementById("nextPageDiv").style.display = "none";
    }
}

/**
 * 塞选任务
 */
function selectTask(){
    var fromUserId = document.getElementById("fromUserId").value;
    var toUserId = document.getElementById("toUserId").value;
    var state = document.getElementById("state").value;
    var condition = EMPTY;
    if(fromUserId != EMPTY){
        if(condition == EMPTY){
            condition += "?";
        } else {
            condition += "&";
        }
        condition += "fromUserId=" + fromUserId;
    }
    if(toUserId != EMPTY){
        if(condition == EMPTY){
            condition += "?";
        } else {
            condition += "&";
        }
        condition += "toUserId=" + toUserId;
    }
    if(state != EMPTY){
        if(condition == EMPTY){
            condition += "?";
        } else {
            condition += "&";
        }
        condition += "state=" + state;
    }

    location.href = baseUrl + "task.jsp" + condition;
}

/**
 * 加载下一页
 */
function showNextPageTasks(){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateTask.do",
        data:"type=nextPage&countNow=" + taskArray.length + "&fromUserId=" + fromUserId + "&toUserId=" +
            toUserId + "&state=" + state + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    alert(data["message"]);
                } else {
                    //请求成功
                    alert(data["message"]);
                    var nextPageJson = data["nextPageJson"];
                    if(EMPTY != nextPageJson) {
                        //把初始taskJsonStr转换成taskArray
                        var array = transferInitJsonStr2Array(nextPageJson);
                        taskArray = taskArray.concat(array);
                    }
                    //处理任务Json串
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