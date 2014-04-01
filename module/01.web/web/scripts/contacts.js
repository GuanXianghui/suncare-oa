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
    if(userJsonStr != "") {
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
    if(structureJsonStr != "") {
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
    var contactsIdArray = new Array();
    var layers = eval("({})");

    /**
     * 算多少个公司
     */
    var companies = new Array();
    for(var i=0;i<structureArray.length;i++) {
        if(structureArray[i]["type"] == STRUCTURE_TYPE_COMPANY){
            companies[companies.length] = structureArray[i]["id"];
        }
    }

    /**
     * 循环每个公司
     * 1.先[递归查]找属于该公司，不属于任何部门的职位 -> 如果是公司或者部门则中断
     * 2.[递归查]属于该公司的所有部门 -> 如果是公司则中断
     * 3.循环每个部门，[递归查]找下面的职位 -> 如果是公司或者部门则中断
     */
    for(var i=0;i<companies.length;i++){
        // 遍历公司
        var company = companies[i];
        contactsIdArray[contactsIdArray.length] = company;//添加元素

        // 第一步
        var posArray = new Array();
        posArray = getAllPosWithOutDeptByCompany(posArray, company);
        contactsIdArray = contactsIdArray.concat(posArray);//添加数组
        layers["company_" + company + "_pos"] = posArray;

        // 第二步
        var deptArray = new Array();
        deptArray = getAllDeptsByCompany(deptArray, company);
        layers["company_" + company + "_dept"] = deptArray;

        // 第三步
        for(var j=0;j<deptArray.length;j++){
            contactsIdArray[contactsIdArray.length] = deptArray[j];//添加元素
            var posArray2 = new Array();
            posArray2 = getAllPosByDept(posArray2, deptArray[j]);
            contactsIdArray = contactsIdArray.concat(posArray2);//添加数组
            layers["company_" + company + "_dept_" + deptArray[j]] = posArray2;
        }
    }

    /**
     * 依次展现通讯录中的公司架构
     */
    var html = "";
    for(var i=0;i<companies.length;i++){
        var company = getStructureById(companies[i]);
        html += "<tr><td col5='1' class='company' colspan='5'>" + company["name"] + "</td></tr>";
        html += "<tr><td>部门</td><td>职位</td><td>姓名</td><td>座机</td><td>手机</td></tr>";
        var poses = layers["company_" + company["id"] + "_pos"];
        for(var j=0;j<poses.length;j++){
            var pos = getStructureById(poses[j]);
            html += "<tr><td col2='1' colspan='2' class='position'>" + pos["name"] + "</td><td col3='1' colspan='3'>";
            var users = queryUsersByPosition(pos["id"]);
            for(var l=0;l<users.length;l++){
                if(l==0){
                    html += "<table width='100%'>"
                }
                html += "<tr><td><a href='" + baseUrl + "user.jsp?id=" + users[l]["id"] + "' target='_blank'>" +
                    users[l]["name"] + "</a></td><td>" + users[l]["officeTel"] + "</td><td>" +
                    users[l]["mobileTel"] + "</td></tr>";
                if(l==users.length-1){
                    html += "</table>"
                }
            }
            html += "</td></tr>";
        }
        var depts = layers["company_" + company["id"] + "_dept"];
        for(var j=0;j<depts.length;j++){
            var dept = getStructureById(depts[j]);
            html += "<tr><td class='dept'>" + dept["name"] + "</td><td col4='1' colspan='4'>";
            poses = layers["company_" + company["id"] + "_dept_" + dept["id"]];
            for(var k=0;k<poses.length;k++){
                if(k==0){
                    html += "<table width='100%'>"
                }
                var pos = getStructureById(poses[k]);
                html += "<tr><td class='position'>" + pos["name"] + "</td><td col3='1' colspan='3'>";
                var users = queryUsersByPosition(pos["id"]);
                for(var l=0;l<users.length;l++){
                    if(l==0){
                        html += "<table width='100%'>"
                    }
                    html += "<tr><td><a href='" + baseUrl + "user.jsp?id=" + users[l]["id"] + "' target='_blank'>" +
                        users[l]["name"] + "</a></td><td>" + users[l]["officeTel"] + "</td><td>" +
                        users[l]["mobileTel"] + "</td></tr>";
                    if(l==users.length-1){
                        html += "</table>"
                    }
                }
                html += "</td></tr>";
                if(k==poses.length-1){
                    html += "</table>"
                }
            }
            html += "</td></tr>";
        }
//        if(structure["type"] == STRUCTURE_TYPE_COMPANY) {
//        } else if(structure["type"] == STRUCTURE_TYPE_DEPT) {
//            html += "<tr><td class='dept'>" + structure["name"] + "</td></tr>";
//        } else if(structure["type"] == STRUCTURE_TYPE_POSITION) {
//            html += "<tr><td class='position'>" + structure["name"] + "</td></tr>";
//        }
    }
    document.getElementById("contacts_table").innerHTML = html;
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
 * 选择节点
 * @param t
 * @param id
 */
function chooseTd(t, id) {
    if(chooseNode != null) {
        chooseNode.style.border = "1px solid gray";
    }
    t.style.border = "2px solid red";
    chooseNode = t;
    chooseId = id;
    //修改信息
    document.getElementById("type2").value = 1;
    document.getElementById("name2").value = "";
    var structure = getStructureById(id);
    if(null != structure) {
        document.getElementById("type2").value = structure["type"];
        document.getElementById("name2").value = structure["name"];
    }
}

/**
 * 判节点
 */
function checkNode(type) {
    if(chooseNode == null) {
        alert("请选择节点");
        return false;
    }
    if("move2Left" == type && chooseId == 0) {
        return false;
    }
    if("move2Right" == type && chooseId == 0) {
        return false;
    }
    if("updateNode" == type && chooseId == 0) {
        alert("该节点不能修改");
        return false;
    }
    if("deleteNode" == type && chooseId == 0) {
        alert("该节点不能删除");
        return false;
    }
    if("addNode" == type) {
        var name = document.getElementById("name1").value;
        if(name == "") {
            alert("请输入名称");
            return false;
        }
    }
    if("updateNode" == type) {
        var name = document.getElementById("name2").value;
        if(name == "") {
            alert("请输入名称");
            return false;
        }
    }
    return true;
}

/**
 * 根据id取公司结构
 * @param id
 */
function getStructureById(id) {
    var structure = null;
    if(id != 0) {
        for(var i=0;i<structureArray.length;i++) {
            if(structureArray[i]["id"] == id) {
                structure = structureArray[i];
            }
        }
    }
    return structure;
}

/**
 * 左移
 */
function move2Left() {
    if(checkNode("move2Left") == false) {
        return;
    }
    manageOrgStructure("move2Left", chooseId, 0, "");
}

/**
 * 右移
 */
function move2Right() {
    if(checkNode("move2Right") == false) {
        return;
    }
    manageOrgStructure("move2Right", chooseId, 0, "");
}

/**
 * 新增
 */
function addNode() {
    if(checkNode("addNode") == false) {
        return;
    }
    var type = document.getElementById("type1").value;
    var name = document.getElementById("name1").value;
    // 判断字符串是否含有非法字符
    var result = checkStr(name, SYMBOL_ARRAY_ALL);
    if (result["isSuccess"] == false) {
        alert("节点名称包含非法字符:" + result["symbol"]);
        return;
    }
    manageOrgStructure("addNode", chooseId, type, name);
}

/**
 * 修改
 */
function updateNode() {
    if(checkNode("updateNode") == false) {
        return;
    }
    var type = document.getElementById("type2").value;
    var name = document.getElementById("name2").value;
    // 判断字符串是否含有非法字符
    var result = checkStr(name, SYMBOL_ARRAY_ALL);
    if (result["isSuccess"] == false) {
        alert("节点名称包含非法字符:" + result["symbol"]);
        return;
    }
    manageOrgStructure("updateNode", chooseId, type, name);
}

/**
 * 删除
 */
function deleteNode() {
    if(checkNode("deleteNode") == false) {
        return;
    }
    manageOrgStructure("deleteNode", chooseId, 0, "");
}

/**
 * 管理组织架构
 * @param configType
 * @param id
 * @param type
 * @param name
 */
function manageOrgStructure(configType, id, type, name) {
    //ajax操作
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "manageOrgStructure.do",
        data:"configType=" + configType + "&id=" + id + "&type=" + type + "&name=" + name + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判修改密码是否成功
                if (false == data["isSuccess"]) {
                    alert(data["message"]);
                    return;
                } else {
                    //修改密码成功
                    alert(data["message"]);
                    //所有公司结构json串
                    structureJsonStr = (data["newStructureJsonStr"]);
                    //所有公司结构Json数组
                    structureArray = new Array();
                    //处理所有公司结构json串
                    processWithJson();
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

    //清空
    chooseNode = null;
    chooseId = 0;
    document.getElementById("type1").value = 1;
    document.getElementById("name1").value = "";
    document.getElementById("type2").value = 1;
    document.getElementById("name2").value = "";
}