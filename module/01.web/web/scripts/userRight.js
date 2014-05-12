//所有员工Json数组
var userArray = new Array();
//所有公司结构Json数组
var structureArray = new Array();

/**
 * 初始化
 */
$(document).ready(function() {
    //处理所有员工json串
    processUserWithJson();
    //处理所有公司结构json串
    processStructureWithJson();
    //处理通讯录
    processContacts();
});

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
        //读取权限
        readUserRight(id);
    }
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
 * 读取权限
 */
function readUserRight(userId) {
    //ajax操作
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "readUserRight.do",
        data:"userId=" + userId + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判修改密码是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                } else {
                    //修改密码成功
                    showSuccess(data["message"]);
                    var userRight = data["userRight"];
                    //处理用户权限
                    processUserRight(userId, userRight);
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
 * 处理用户权限
 * @param userId
 * @param userRight
 */
function processUserRight(userId, userRight){
    //清空
    var nodes = $("input[type=checkbox]");
    for(var i=0;i<nodes.length;i++){
        nodes[i].checked = "";
    }
    var user = getUserById(userId);
    chooseUserId = userId;
    chooseUserName = user["name"];
    var rightArray = userRight.split(SYMBOL_COMMA);
    for(var i=0;i<rightArray.length;i++){
        var right = rightArray[i];
        var nodes = $("input[type=checkbox]");
        for(var j=0;j<nodes.length;j++){
            if(nodes[j]["name"] == right){
                nodes[j].checked = "checked";
            }
        }
    }
    $("#choose_user").html(chooseUserName);
}

/**
 * 修改用户权限
 */
function updateUserRight(){
    //判是否已经选择用户
    if(chooseUserId == 0 || chooseUserName == EMPTY){
        showAttention("请先选择用户");
        return;
    }
    //得到新的用户权限
    var userRight = EMPTY;
    var nodes = $("input[type=checkbox]");
    for(var i=0;i<nodes.length;i++){
        if(nodes[i].checked == true){
            if(userRight != EMPTY){
                userRight += SYMBOL_COMMA;
            }
            userRight += nodes[i]["name"];
        }
    }

    //ajax操作
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "updateUserRight.do",
        data:"userId=" + chooseUserId + "&userRight=" + userRight + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判修改密码是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                } else {
                    //修改密码成功
                    showSuccess(data["message"]);
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