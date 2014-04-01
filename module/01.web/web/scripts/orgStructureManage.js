//所有公司结构Json数组
var structureArray = new Array();
//选择节点
var chooseNode = null;
//选择id
var chooseId = 0;

/**
 * 初始化
 */
$(document).ready(function() {
    //处理所有公司结构json串
    processWithJson();
});

/**
 * 处理 所有公司结构json串
 */
function processWithJson() {
    //json串转json数组
    if(structureJsonStr != "") {
        var array = structureJsonStr.split(SYMBOL_BIT_AND);
        for(var i=0;i<array.length;i++) {
            structureArray[structureArray.length] = eval("(" + array[i] + ")");
        }
    }
    //组织所有公司结构表格
    var html = "<tr><td onclick='chooseTd(this, 0)' class='top' colspan='" + calculateSize(0) + "'>组织架构</td></tr>";
    /**
     * pids格式:1,2,,,3
     * 空的可能是上一层某个没有子元素了
     * 全空,,,,,,则结束
     */
    var pids = "0";
    //循环每层展示
    while(true) {
        var array = pids.split(SYMBOL_COMMA);
        pids = "";
        var tempStr = "<tr>";
        for(var i=0;i<array.length;i++) {
            if(array[i] == "") {
                tempStr += "<td></td>";
                pids += ",";
                continue;
            }
            var hasSon = false;
            for(var j=0;j<structureArray.length;j++) {
                if(structureArray[j]["pid"] == array[i]) {
                    hasSon = true;
                    var className = (1== structureArray[j]["type"])?"company":((2== structureArray[j]["type"])?"dept":"position");
                    tempStr += "<td onclick='chooseTd(this, " + structureArray[j]["id"] +
                        ")' class='" + className + "' colspan='" +
                        calculateSize(structureArray[j]["id"]) + "'>" +
                        structureArray[j]["name"] + "</td>";
                    pids += structureArray[j]["id"] + "," ;
                }
            }
            if(hasSon == false) {
                tempStr += "<td></td>";
                pids += ",";
                continue;
            }
        }
        tempStr += "</tr>";
        pids = pids.substr(0, pids.length-1);
        if(pids.length == containCount(pids, SYMBOL_COMMA)) {
            break;
        }
        html += tempStr;
    }
    document.getElementById("structure_table").innerHTML = html;
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