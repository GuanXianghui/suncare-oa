//符号集合
var SYMBOL_ARRAY_1 = new Array("=","&","'","\"");
var SYMBOL_ARRAY_2 = new Array(",","=","&","'","\"");

/**
 * 判断字符串是否含有非法字符
 * 校验通过返回result["isSuccess"]=true
 * 校验失败返回result["isSuccess"]=false,result["symbol"]=包含的非法字符
 * @param value
 * @param symbolArray
 */
function checkStr(value, symbolArray) {
    var result = "";
    for(var i=0;i<symbolArray.length;i++){
        if(value.indexOf(symbolArray[i]) > -1) {
            if("'" == symbolArray[i]){
                //对单引号特殊处理
                result = "{isSuccess:false,symbol:\"\'\"}";
            } else {
                result = "{isSuccess:false,symbol:'" + symbolArray[i] + "'}";
            }
            break;
        }
    }
    if(result == ""){
        result = "{isSuccess:true}";
    }
    result = eval("(" + result + ")");
    return result;
}

/**
 * 退出
 * 注意：用到$.ajax，所以得依赖jquery-min.js
 * 注意：用到变量baseUrl，所以得依赖header.jsp
 */
function logOut(){
    //ajax退出
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "ajax/logOut.jsp",
        data:"",
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判退出是否成功
                if (false == data["isSuccess"]) {
                    alert(data["message"]);
                    return;
                } else {
                    //退出成功
                    alert(data["message"]);
                }
                //是否跳转页面
                if (data["isRedirect"]) {
                    var redirectUrl = data["redirectUrl"];
                    location.href = redirectUrl;
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
