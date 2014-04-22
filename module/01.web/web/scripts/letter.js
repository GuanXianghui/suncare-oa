//站内信Json数组
var letterArray = new Array();

/**
 * 初始化
 */
$(document).ready(function() {
    //把初始letterJsonStr转换成letterArray
    letterArray = transferInitJsonStr2Array(letterJsonStr);

    //处理站内信Json串
    processWithJson();

    //检查是否还有下一页
    checkHasNextPage();
});

/**
 * 把初始letterJsonStr转换成letterArray
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
 * 处理站内信Json串
 */
function processWithJson(){
    //循环展示
    var html = "<tr><td width=\"5%\"><input id=\"all_check_box\" type=\"checkbox\" onchange=\"chooseAll(this)\">" +
        "</td><td width=\"15%\">站内信来源用户头像</td><td width=\"20%\">站内信来源用户名称</td><td width=\"20%\">" +
        "是否已读</td><td width=\"20%\">标题</td><td width=\"20%\">时间</td></tr>";
    for(var i=0;i<letterArray.length;i++){
        var isReaded = "未读";
        if(letterArray[i]["readState"] == LETTER_READ_STATE_READED){
            isReaded = "已读";
        }
        html += "<tr><td><input class=\"letter_box\" type=\"checkbox\" onchange=\"choose(this, " +
            letterArray[i]["id"] + ")\"></td><td><a href=\"" + letterArray[i]["url"] + "\" target=\"_blank\">" +
            "<img width=\"54px\" src=\"" + letterArray[i]["headPhoto"] + "\"></a></td><td>" +
            letterArray[i]["fromUserName"] + "</td><td>" + isReaded + "</td><td><a href=\"" + baseUrl +
            "showLetter.jsp?id=" + letterArray[i]["id"] + "\">" + letterArray[i]["title"] + "</a></td><td>" +
            letterArray[i]["createDate"] + "</td></tr>";
    }
    document.getElementById("letter_table").innerHTML = html;
}

/**
 * 全选站内信
 * @param t
 */
function chooseAll(t){
    var checked = t.checked;
    var boxes = document.getElementsByClassName("letter_box");
    for(var i=0;i<boxes.length;i++){
        if(checked){
            boxes[i].checked = true;
        } else {
            boxes[i].checked = false;
        }
    }
    chooseLetterIds = EMPTY;
    if(checked){
        for(var i=0;i<letterArray.length;i++){
            if(chooseLetterIds != EMPTY){
                chooseLetterIds += SYMBOL_COMMA;
            }
            chooseLetterIds += letterArray[i]["id"];
        }
    }
}

/**
 * 选择站内信
 * @param t
 */
function choose(t, id){
    if(t.checked){
        if(chooseLetterIds != EMPTY){
            chooseLetterIds += SYMBOL_COMMA;
        }
        chooseLetterIds += id;
    } else {
        var idArray = chooseLetterIds.split(SYMBOL_COMMA);
        var newIds = EMPTY;
        for(var i=0;i<idArray.length;i++){
            if(id == idArray[i]){
                continue;
            }
            if(newIds != EMPTY){
                newIds += SYMBOL_COMMA;
            }
            newIds += idArray[i];
        }
        chooseLetterIds = newIds;
    }
}

/**
 * 删除
 */
function deleteLetter(){
    if(chooseLetterIds == EMPTY){
        alert("请选择站内信");
        return;
    }
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateLetter.do",
        data:"type=delete&ids=" + chooseLetterIds + "&token=" + token,
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
                    var idArray = chooseLetterIds.split(SYMBOL_COMMA);
                    for(var i=0;i<idArray.length;i++){
                        var id = idArray[i];
                        for(var j=letterArray.length-1;j>=0;j--){
                            if(id == letterArray[j]["id"]){
                                letterArray.splice(j, 1);
                            }
                        }
                    }
                    //处理站内信Json串
                    processWithJson();
                    //总共站内信的量减去删除的量
                    letterCount -= idArray.length;
                    //检查是否还有下一页
                    checkHasNextPage();
                    //清空选择站内信ids
                    chooseLetterIds = EMPTY;
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
 * 彻底删除
 */
function ctrlDeleteLetter(){
    if(chooseLetterIds == EMPTY){
        alert("请选择站内信");
        return;
    }
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateLetter.do",
        data:"type=ctrlDelete&ids=" + chooseLetterIds + "&token=" + token,
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
                    var idArray = chooseLetterIds.split(SYMBOL_COMMA);
                    for(var i=0;i<idArray.length;i++){
                        var id = idArray[i];
                        for(var j=letterArray.length-1;j>=0;j--){
                            if(id == letterArray[j]["id"]){
                                letterArray.splice(j, 1);
                            }
                        }
                    }
                    //处理站内信Json串
                    processWithJson();
                    //总共站内信的量减去删除的量
                    letterCount -= idArray.length;
                    //检查是否还有下一页
                    checkHasNextPage();
                    //清空选择站内信ids
                    chooseLetterIds = EMPTY;
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
 * 转发
 */
function transmit(){
    if(chooseLetterIds == EMPTY){
        alert("请选择站内信");
        return;
    }
    if(chooseLetterIds.split(SYMBOL_COMMA).length > 1){
        alert("你只能选择一封站内信进行转发");
        return;
    }
    location.href = baseUrl + "writeLetter.jsp?type=transmit&id=" + chooseLetterIds;
}

/**
 * 标记成已读
 */
function setReaded(){
    if(chooseLetterIds == EMPTY){
        alert("请选择站内信");
        return;
    }
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateLetter.do",
        data:"type=setReaded&ids=" + chooseLetterIds + "&token=" + token,
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
                    var idArray = chooseLetterIds.split(SYMBOL_COMMA);
                    for(var i=0;i<idArray.length;i++){
                        var id = idArray[i];
                        for(var j=letterArray.length-1;j>=0;j--){
                            if(id == letterArray[j]["id"]){
                                letterArray[j]["readState"] = LETTER_READ_STATE_READED;
                            }
                        }
                    }
                    //处理站内信Json串
                    processWithJson();
                    //检查是否还有下一页
                    checkHasNextPage();
                    //清空选择站内信ids
                    chooseLetterIds = EMPTY;
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
 * 还原
 */
function restore(){
    if(chooseLetterIds == EMPTY){
        alert("请选择站内信");
        return;
    }
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateLetter.do",
        data:"type=restore&ids=" + chooseLetterIds + "&token=" + token,
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
                    var idArray = chooseLetterIds.split(SYMBOL_COMMA);
                    for(var i=0;i<idArray.length;i++){
                        var id = idArray[i];
                        for(var j=letterArray.length-1;j>=0;j--){
                            if(id == letterArray[j]["id"]){
                                letterArray.splice(j, 1);
                            }
                        }
                    }
                    //处理站内信Json串
                    processWithJson();
                    //总共站内信的量减去删除的量
                    letterCount -= idArray.length;
                    //检查是否还有下一页
                    checkHasNextPage();
                    //清空选择站内信ids
                    chooseLetterIds = EMPTY;
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
 * 检查是否还有下一页
 */
function checkHasNextPage(){
    if(letterCount > letterArray.length){
        document.getElementById("nextPageDiv").style.display = EMPTY;
    } else {
        document.getElementById("nextPageDiv").style.display = "none";
    }
}

/**
 * 加载下一页站内信
 */
function showNextPageLetters(){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateLetter.do",
        data:"type=nextPage&countNow=" + letterArray.length + "&box=" + box + "&token=" + token,
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
                        letterArray = letterArray.concat(array);
                    }
                    //处理站内信Json串
                    processWithJson();
                    //检查是否还有下一页
                    checkHasNextPage();
                    //清空选择站内信ids
                    chooseLetterIds = EMPTY;
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