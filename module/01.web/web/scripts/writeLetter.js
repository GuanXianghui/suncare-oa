//ueditor编辑器
var editor;
//所有员工Json数组
var userArray = new Array();
//所有公司结构Json数组
var structureArray = new Array();
//收件人
var USER_TYPE_RECEIVE = 1;
//抄送人
var USER_TYPE_CC = 2;
//选择用户类型
var chooseUserType = USER_TYPE_RECEIVE;
//选择收件人Id数组
var chooseToUserIds = new Array();
//选择抄送人Id数组
var chooseCcUserIds = new Array();

/**
 * 初始化
 */
$(document).ready(function() {
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    editor = UE.getEditor('editor');
    //一定要加载页面后隔上1秒钟(时间可能可以短一点)设置编辑器的内容
    setTimeout("setContent()", 1000);

    //处理所有员工json串
    processUserWithJson();
    //处理所有公司结构json串
    processStructureWithJson();
    //处理通讯录
    processContacts();

    //初始化收件人和抄送人
    initUserIds();
});

/**
 * 初始化收件人和抄送人
 */
function initUserIds(){
    if(initToUserIds != EMPTY){
        var chooseToUserIdValues = initToUserIds.split(SYMBOL_COMMA);
        for(var i=0;i<chooseToUserIdValues.length;i++){
            chooseToUserIds[chooseToUserIds.length] = parseInt(chooseToUserIdValues[i]);
        }
    }
    if(initCcUserIds != EMPTY){
        var chooseCcUserIdValues = initCcUserIds.split(SYMBOL_COMMA);
        for(var i=0;i<chooseCcUserIdValues.length;i++){
            chooseCcUserIds[chooseCcUserIds.length] = parseInt(chooseCcUserIdValues[i]);
        }
    }
    //展示收件人和抄送人
    showChooseUsers();
}

/**
 * 处理所有员工json串
 */
function processUserWithJson() {
    //json串转json数组
    if(userJsonStr != EMPTY) {
        var array = userJsonStr.split(SYMBOL_BIT_AND);
        for(var i=0;i<array.length;i++) {
            userArray[userArray.length] = eval("(" + array[i] + ")");
        }
    }
}

/**
 * 处理所有公司结构json串
 */
function processStructureWithJson() {
    //json串转json数组
    if(structureJsonStr != EMPTY) {
        var array = structureJsonStr.split(SYMBOL_BIT_AND);
        for(var i=0;i<array.length;i++) {
            structureArray[structureArray.length] = eval("(" + array[i] + ")");
        }
    }
}

/**
 * 处理通讯录
 */
function processContacts(){
    /**
     * 通讯录公司架构id数组：公司，部门，职位依次出现的id
     */
    //var contactsIdArray = new Array();
    //var layers = eval("({})");

    /**
     * 算多少个公司
     */
    var companies = new Array();
    for(var i=0;i<structureArray.length;i++) {
        if(structureArray[i]["type"] == STRUCTURE_TYPE_COMPANY){
            companies[companies.length] = structureArray[i]["id"];
        }
    }
    var html = EMPTY;

    /**
     * 循环每个公司
     * 1.先[递归查]找属于该公司，不属于任何部门的职位 -> 如果是公司或者部门则中断
     * 2.[递归查]属于该公司的所有部门 -> 如果是公司则中断
     * 3.循环每个部门，[递归查]找下面的职位 -> 如果是公司或者部门则中断
     */
    for(var i=0;i<companies.length;i++){
        // 遍历公司
        var company = companies[i];
        var structure = getStructureById(company);
        html += "<tr><td class='company' title='com_" + company + "' " +
            "onclick='chooseUser(this, " + structure["id"] + ")'>" + structure["name"] + "</td></tr>";

        //contactsIdArray[contactsIdArray.length] = company;//添加元素

        // 第一步
        var posArray = new Array();
        posArray = getAllPosWithOutDeptByCompany(posArray, company);
        //contactsIdArray = contactsIdArray.concat(posArray);//添加数组
        //layers["company_" + company + "_pos"] = posArray;
        for(var j=0;j<posArray.length;j++){
            structure = getStructureById(posArray[j]);
            html += "<tr><td class='position' title='com_" + company + "_pos_" + structure["id"] + "' " +
                "onclick='chooseUser(this, " + structure["id"] + ")' style='display: none;'>" + structure["name"] + "</td></tr>";
            var users = queryUsersByPosition(structure["id"]);
            for(var k=0;k<users.length;k++){
                html += "<tr><td class='user' title='com_" + company + "_pos_" + structure["id"] + "_user_" +
                    users[k]["id"] + "' onclick='chooseUser(this, " + users[k]["id"] + ")' style='display: none;'><img src='" + baseUrl + users[k]["headPhoto"] +
                    "' width='27px'>" + users[k]["name"] + "</td></tr>";
            }
        }

        // 第二步
        var deptArray = new Array();
        deptArray = getAllDeptsByCompany(deptArray, company);
        //layers["company_" + company + "_dept"] = deptArray;

        // 第三步
        for(var j=0;j<deptArray.length;j++){
            //contactsIdArray[contactsIdArray.length] = deptArray[j];//添加元素
            structure = getStructureById(deptArray[j]);
            var dept = structure["id"];
            html += "<tr><td class='dept' title='com_" + company + "_dept_" + dept + "' " +
                "onclick='chooseUser(this, " + structure["id"] + ")' style='display: none;'>" + structure["name"] + "</td></tr>";
            var posArray2 = new Array();
            posArray2 = getAllPosByDept(posArray2, deptArray[j]);
            //contactsIdArray = contactsIdArray.concat(posArray2);//添加数组
            //layers["company_" + company + "_dept_" + deptArray[j]] = posArray2;
            for(var k=0;k<posArray2.length;k++){
                structure = getStructureById(posArray2[k]);
                html += "<tr><td class='position' title='com_" + company + "_dept_" + dept + "_pos_" +
                    structure["id"] + "' onclick='chooseUser(this, " + structure["id"] + ")' style='display: none;'>" + structure["name"] + "</td></tr>";
                var users = queryUsersByPosition(structure["id"]);
                for(var l=0;l<users.length;l++){
                    html += "<tr><td class='user' title='com_" + company + "_dept_" + dept + "_pos_" +
                        structure["id"] + "_user_" + users[l]["id"] + "' onclick='chooseUser(this, " + users[l]["id"] + ")' style='display: none;'><img src='" +
                        baseUrl + users[k]["headPhoto"] + "' width='27px'>" + users[l]["name"] + "</td></tr>";
                }
            }
        }
    }
    $("#friends").html(html);
}

/**
 * 选择好友
 * @param t
 */
function chooseUser(t, id){
    var type = getType(t);
    if(type == TYPE_COM || type == TYPE_DEPT || type == TYPE_POS){
        var nodes =  $("[title^='" + t.title + "_']");//模糊查询
        var isHidden = isAllHidden(nodes);//是否都隐藏
        for(var i=0;i<nodes.length;i++){
            if(isHidden){
                //只展开下一级的元素
                if(nodes[i].title.split("_").length == t.title.split("_").length + 2){
                    nodes[i].style.display = "block";
                }
            } else {
                nodes[i].style.display = "none";
            }
        }
    }
    if(type == TYPE_USER){
        if(chooseUserType == USER_TYPE_RECEIVE){
            if(chooseToUserIds.indexOf(id) > -1){
                return;
            }
            chooseToUserIds[chooseToUserIds.length] = id;
        }
        if(chooseUserType == USER_TYPE_CC){
            if(chooseCcUserIds.indexOf(id) > -1){
                return;
            }
            chooseCcUserIds[chooseCcUserIds.length] = id;
        }
        //展示收件人和抄送人
        showChooseUsers();
    }
}

/**
 * 展示收件人和抄送人
 */
function showChooseUsers(){
    var html = "<b>收件人:</b>";
    var values = EMPTY;
    for(var i=0;i<chooseToUserIds.length;i++){
        var user = getUserById(chooseToUserIds[i]);
        var spanStr = "<span onclick='deleteUser(" + USER_TYPE_RECEIVE + "," + chooseToUserIds[i] + ")' " +
            "title='删除' style='cursor: pointer'>[<img src='" + baseUrl + user["headPhoto"] + "' width='27px'>" +
            user["name"] + "]</span>"
        html += spanStr;

        if(values != EMPTY){
            values += SYMBOL_COMMA;
        }
        values += chooseToUserIds[i];
    }
    $("#toUserIds").html(html);
    $("#toUserIdsValue").val(values);

    html = "<b>抄送人:</b>";
    values = EMPTY;
    for(var i=0;i<chooseCcUserIds.length;i++){
        var user = getUserById(chooseCcUserIds[i]);
        var spanStr = "<span onclick='deleteUser(" + USER_TYPE_CC + "," + chooseCcUserIds[i] + ")' " +
            "title='删除' style='cursor: pointer'>[<img src='" + baseUrl + user["headPhoto"] + "' width='27px'>" +
            user["name"] + "]</span>"
        html += spanStr;

        if(values != EMPTY){
            values += SYMBOL_COMMA;
        }
        values += chooseCcUserIds[i];
    }
    $("#ccUserIds").html(html);
    $("#ccUserIdsValue").val(values);
}

/**
 * 删除用户
 * @param array
 * @param id
 */
function deleteUser(type, id){
    if(type == USER_TYPE_RECEIVE){
        chooseToUserIds = removeAllOneFromArray(chooseToUserIds, id);
        showChooseUsers();
    }
    if(type == USER_TYPE_CC){
        chooseCcUserIds = removeAllOneFromArray(chooseCcUserIds, id);
        showChooseUsers();
    }
}

/**
 * 选择用户类型
 * @param type
 * @param t
 */
function changeUserType(type, t){
    chooseUserType = type;
    document.getElementById("toUserIdsTr").style.borderWidth = "0px";
    document.getElementById("ccUserIdsTr").style.borderWidth = "0px";
    t.style.borderWidth = "1px";
}

/**
 * 是否都隐藏
 * @param nodes
 * @return {Boolean}
 */
function isAllHidden(nodes){
    var count = 0;
    for(var i=0;i<nodes.length;i++){
        if(nodes[i].style.display == "none"){
            count ++;
        }
    }
    return count == nodes.length;
}

/**
 * 格子类型
 */
var TYPE_COM = 1;
var TYPE_DEPT = 2;
var TYPE_POS = 3;
var TYPE_USER = 4;

/**
 * 返回格子类型
 * @param t
 * @return {*}
 */
function getType(t){
    if(t.title.indexOf("user") > -1){
        return TYPE_USER;
    }
    if(t.title.indexOf("pos") > -1){
        return TYPE_POS;
    }
    if(t.title.indexOf("dept") > -1){
        return TYPE_DEPT;
    }
    if(t.title.indexOf("com") > -1){
        return TYPE_COM;
    }
    return EMPTY;
}

/**
 * [递归查]找属于该公司，不属于任何部门的职位
 * 如果是公司或者部门则中断
 * @param array
 * @param company
 */
function getAllPosWithOutDeptByCompany(array, company){
    for(var i=0;i<structureArray.length;i++){
        if(structureArray[i]["pid"] == company) {
            if(structureArray[i]["type"]==STRUCTURE_TYPE_POSITION) {
                array[array.length] = structureArray[i]["id"];
            }
            if(structureArray[i]["type"]==STRUCTURE_TYPE_POSITION) {
                array = getAllPosWithOutDeptByCompany(array, structureArray[i]["id"]);
            }
        }
    }
    return array;
}

/**
 * [递归查]属于该公司的所有部门
 * 如果是公司则中断
 * @param array
 * @param company
 */
function getAllDeptsByCompany(array, company){
    for(var i=0;i<structureArray.length;i++){
        if(structureArray[i]["pid"] == company) {
            if(structureArray[i]["type"]==STRUCTURE_TYPE_DEPT) {
                array[array.length] = structureArray[i]["id"];
            }
            if(structureArray[i]["type"]!=STRUCTURE_TYPE_COMPANY) {
                array = getAllDeptsByCompany(array, structureArray[i]["id"]);
            }
        }
    }
    return array;
}

/**
 * 循环每个部门，[递归查]找下面的职位
 * 如果是公司或者部门则中断
 * @param array
 * @param dept
 */
function getAllPosByDept(array, dept){
    for(var i=0;i<structureArray.length;i++){
        if(structureArray[i]["pid"] == dept) {
            if(structureArray[i]["type"]==STRUCTURE_TYPE_POSITION) {
                array[array.length] = structureArray[i]["id"];
            }
            if(structureArray[i]["type"]==STRUCTURE_TYPE_POSITION) {
                array = getAllPosWithOutDeptByCompany(array, structureArray[i]["id"]);
            }
        }
    }
    return array;
}

/**
 * 根据id查公司架构
 * @param id
 */
function getStructureById(id) {
    for(var i=0;i<structureArray.length;i++){
        if(structureArray[i]["id"] == id){
            return structureArray[i];
        }
    }
    return null;
}

/**
 * 根据职位来查员工
 * @param position
 */
function queryUsersByPosition(position){
    var users = new Array();
    for(var i=0;i<userArray.length;i++){
        if(userArray[i]["position"] == position){
            users[users.length] = userArray[i];
        }
    }
    return users;
}

/**
 * 根据id查用户
 * @param id
 */
function getUserById(id) {
    for(var i=0;i<userArray.length;i++){
        if(userArray[i]["id"] == id){
            return userArray[i];
        }
    }
    return null;
}

/**
 * 一定要加载页面后隔上1秒钟(时间可能可以短一点)设置编辑器的内容*
 */
function setContent(){
    //设置编辑器的内容
    editor.setContent(document.getElementById("content").value);
}

/**
 * 写信
 */
function writeLetter(){
    var toUserIds = document.getElementById("toUserIdsValue").value;
    if(toUserIds == EMPTY){
        showAttention("请选择收件人");
        return false;
    }
    var ccUserIds = document.getElementById("ccUserIdsValue").value;
    var title = document.getElementById("title").value;
    if(title == EMPTY){
        showAttention("标题不能为空");
        return false;
    }
    //判断字符串是否含有非法字符
    var result = checkStr(title, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        showAttention("标题包含非法字符:" + result["symbol"]);
        return false;
    }
    var content = editor.getContent();
    if(content.length > LETTER_CONTENT_LENGTH) {
        showAttention("站内信内容大于" + LETTER_CONTENT_LENGTH + "个字符");
        return false;
    }
    document.getElementById("content").value = content;
    //提交表格
    document.forms["writeLetterForm"].submit();
}