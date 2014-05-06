//任务Json数组
var taskArray = new Array();
//所有员工Json数组
var userArray = new Array();

/**
 * 初始化
 */
$(document).ready(function() {
    if(message != EMPTY){
        showInformation(message);
    }

    //把初始taskJsonStr转换成taskArray
    taskArray = transferInitJsonStr2Array(taskJsonStr);

    //处理任务Json串
    processWithJson();

    //检查是否还有下一页
    checkHasNextPage();

    //初始化用户
    initUsers();
});

/**
 * 初始化用户
 */
function initUsers(){
    //json串转json数组
    if(userJsonStr != EMPTY) {
        var array = userJsonStr.split(SYMBOL_BIT_AND);
        for(var i=0;i<array.length;i++) {
            userArray[userArray.length] = eval("(" + array[i] + ")");
        }
    }
    for(var i=0;i<userArray.length;i++){
        document.getElementById("userId").innerHTML += " <option value=\"" + userArray[i]["id"] + "\"" +
            (userId==userArray[i]["id"]?" selected":"") + ">" + userArray[i]["name"] + "</option>";
    }
}

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
    var html = "<tr></td><td>任务来源用户</td><td>任务接受用户</td><td>任务名称</td>" +
        "<td>状态</td><td>开始日期</td><td>结束日期</td></tr>";
    for(var i=0;i<taskArray.length;i++){
        html += "<tr><td><a href=\"" + baseUrl +"user.jsp?id=" +
            taskArray[i]["fromUserId"] + "\" target=\"_blank\">" + taskArray[i]["fromUserName"] +
            "</a></td>" + "<td><a href=\"" + baseUrl +"user.jsp?id=" + taskArray[i]["toUserId"] + "\" " +
            "target=\"_blank\">" + taskArray[i]["toUserName"] + "</a></td><td><a href=\"" + baseUrl +
            "showTask.jsp?id=" + taskArray[i]["id"] + "\">" + taskArray[i]["title"] + "</a></td><td>" +
            taskArray[i]["stateDesc"] + "</td><td>" + taskArray[i]["beginDate"] + "</td><td>" +
            taskArray[i]["endDate"] + "</td></tr>";
    }
    document.getElementById("task_table").innerHTML = html;
    $('tbody tr:even').addClass("alt-row");
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
    var type = document.getElementById("type").value;
    var userId = document.getElementById("userId").value;
    var state = document.getElementById("state").value;
    var condition = EMPTY;
    if(type != EMPTY){
        if(condition == EMPTY){
            condition += "?";
        } else {
            condition += "&";
        }
        condition += "type=" + type;
    }
    if(userId != EMPTY){
        if(condition == EMPTY){
            condition += "?";
        } else {
            condition += "&";
        }
        condition += "userId=" + userId;
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
                    showError(data["message"]);
                } else {
                    //请求成功
                    showSuccess(data["message"]);
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
                showAttention("服务器连接异常，请稍后再试！");
            }
        },
        error:function (data, textStatus) {
            showAttention("服务器连接异常，请稍后再试！");
        }
    });
}