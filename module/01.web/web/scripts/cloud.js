/**
 * 初始化
 */
$(document).ready(function() {
    if(message != EMPTY){
        showInformation(message);
    }
    //加载文件夹
    loadDir(dir);
});

/**
 * 加载文件夹
 * @param dirRoute
 */
function loadDir(dirRoute){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "cloudLoadDir.do",
        data:"dir=" + dirRoute + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                } else {
                    //请求成功
                    //showSuccess(data["message"]);

                    //把初始taskJsonStr转换成taskArray
                    files = transferJsonStr2Array(data["filesJsonStr"]);
                    //展示所有文件
                    displayFiles();
                    //展示文件成功后，更新当前文件夹
                    dir = dirRoute;
                    //展示文件夹路径
                    showDirRoute();
                    //清空选择对象
                    chooseClouds = new Array();
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
 * 把初始jsonStr转换成array
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
 * 根据id获取申成云
 * @param id
 */
function getCloudById(id){
    for(var i=0;i<files.length;i++) {
        if(files[i]["id"] == id){
            return files[i];
        }
    }
    return null;
}

/**
 * 展示所有文件
 */
function displayFiles(){
    var html = EMPTY;
    if(files.length == 0){
        html = "无文件！ToT";
    }
    for(var i=0;i<files.length;i++){
        html += getDisplayFileByIdAndTypeAndName(files[i]["id"], files[i]["type"], files[i]["name"]);
    }
    $("#files").html(html);
}

/**
 * 展示文件
 * @param type
 * @param name
 */
function getDisplayFileByIdAndTypeAndName(id, type, name){
    var file = "<li>";
    file += "<span class=\"shortcut-button\" onclick=\"chooseCloud(this)\" name=\"" + id + "\">";
    file += "<span class=\"wrap\">";
    if(CLOUD_TYPE_FILE == type){
        file += "<img src=\"images/file.jpg\" alt=\"icon\" width=\"48\" height=\"48\"/>";
    } else if(CLOUD_TYPE_DIR == type){
        file += "<img src=\"images/dir.jpg\" alt=\"icon\" width=\"48\" height=\"48\" ondblclick=\"openDir('" + name + "')\"/>";
    }
    file += "<br/>" + name;
    file += "</span>";
    file += "</span>";
    file += "</li>";
    return file;
}

/**
 * 点击上传按钮
 */
function beforeUpload(){
    $("#uploadFile").click();
}

/**
 * 上传
 */
function upload(t){
    //判是否选择文件
    if(t.value==EMPTY){
        return;
    }
    $("#uploadDir").val(dir);
    document.forms["uploadForm"].action = baseUrl + "cloudUpload.do?token=" + token;
    document.forms["uploadForm"].submit();
}

/**
 * 点击下载按钮
 */
function download(){
    if(chooseClouds.length == 0){
        showAttention("请选择要下载的文件");
        return;
    }
    var hasFile = false;
    for(var i=0;i<chooseClouds.length;i++){
        var id = parseInt($(chooseClouds[i]).attr("name"));
        //根据id获取申成云
        var cloud = getCloudById(id);
        if(cloud["type"] == CLOUD_TYPE_FILE){
            window.open(cloud["route"]);
            hasFile = true;
        }
    }
    if(hasFile == false){
        showAttention("请选择要下载的文件");
        return;
    }
}

/**
 * 点击新建文件夹按钮
 */
function beforeNewDir(){
    $("#new_dir_name").val("新建文件夹");
    $("#showNewDirDiv").click();
}

/**
 * 点击新建按钮
 * 注意：这里使用facebox.js，浮层是拷贝一份，直接用id来找对象取出来不是同一个对象，所以用位置关系来查找对象
 */
function newDir(t){
    //新文件夹名字
    var newDir = ($(t).parent().find(".text-input").val());
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "cloudNewDir.do",
        data:"dir=" + dir + "&newDir=" + newDir + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                } else {
                    //请求成功
                    showSuccess(data["message"]);

                    //把初始taskJsonStr转换成taskArray
                    files = transferJsonStr2Array(data["filesJsonStr"]);
                    //展示所有文件
                    displayFiles();
                    //清空选择对象
                    chooseClouds = new Array();
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
    //关闭浮层
    $(".close_image").click();
}

/**
 * 点击删除按钮
 */
function deleteFile(){
    if(chooseClouds.length == 0){
        showAttention("请选择要删除的对象！");
        return;
    }
    //确认是否删除
    var result = confirm("确定删除选中的" + chooseClouds.length + "个文件吗？");
    if(result == false){
        return;
    }
    var deleteIds = EMPTY;
    for(var i=0;i<chooseClouds.length;i++){
        if(deleteIds != EMPTY){
            deleteIds += SYMBOL_COMMA;
        }
        deleteIds += ($(chooseClouds[i]).attr("name"));
    }

    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "cloudDeleteFile.do",
        data:"dir=" + dir + "&deleteIds=" + deleteIds + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                } else {
                    //请求成功
                    showSuccess(data["message"]);

                    //把初始taskJsonStr转换成taskArray
                    files = transferJsonStr2Array(data["filesJsonStr"]);
                    //展示所有文件
                    displayFiles();
                    //清空选择对象
                    chooseClouds = new Array();
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
 * 打开目录
 * @param dirName
 */
function openDir(dirName){
    loadDir(dir + dirName + SYMBOL_SLASH);
}

/**
 * 展示文件夹路径
 */
function showDirRoute(){
    //<span class="dir">我的网盘/</span>
    var html = "<span class=\"dir\" onclick=\"loadDir('" + SYMBOL_SLASH + "')\">我的网盘/</span>";
    var dirRoute = SYMBOL_SLASH;
    var dirParts = dir.split(SYMBOL_SLASH);
    for(var i=0;i<dirParts.length;i++){
        if(EMPTY == dirParts[i]){
            continue;
        }
        dirRoute += dirParts[i] + SYMBOL_SLASH;
        html += "<span class=\"dir\" onclick=\"loadDir('" + dirRoute + "')\">" + dirParts[i] + "/</span>";
    }
    $("#dir_route").html(html);
}

/**
 * 选择对象
 * @param t
 */
function chooseCloud(t){
    if(isCtrlDown == false){
        for(var i=0;i<chooseClouds.length;i++){
            chooseClouds[i].style.background=''
        }
        chooseClouds = new Array();
        chooseClouds[chooseClouds.length] = t;
        t.style.background='#0A7CA0'
    } else {
        for(var i=0;i<chooseClouds.length;i++){
            if(t == chooseClouds[i]){
                chooseClouds[i].style.background='';
                chooseClouds = removeAllOneFromArray(chooseClouds, chooseClouds[i]);
                return;
            }
        }
        chooseClouds[chooseClouds.length] = t;
        t.style.background='#0A7CA0'
    }
}

/**
 * 按键按下监听
 * @param e
 */
function keyDown(e){
    if(KEY_CODE_CTRL == e.keyCode){
        isCtrlDown = true;
    }
}

/**
 * 按键放开监听
 * @param event
 */
function keyUp(e){
    if(KEY_CODE_ENTER == e.keyCode){
    }
    if(KEY_CODE_CTRL == e.keyCode){
        isCtrlDown = false;
    }
    if(KEY_CODE_DELETE == e.keyCode){
        //触发点击删除按钮
        deleteFile();
    }
}

/**
 * 返回上一层
 */
function lastDir(){
    if(SYMBOL_SLASH == dir){
        showAttention("已经是最上层了");
        return;
    }
    var tempDir = dir.substr(0, dir.length-1);
    tempDir = tempDir.substr(0, tempDir.lastIndexOf(SYMBOL_SLASH)+1);
    loadDir(tempDir);
}