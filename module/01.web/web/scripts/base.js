//���ż���
var SYMBOL_COMMA = ",";
var SYMBOL_EQUAL = "=";
var SYMBOL_BIT_AND = "&";
var SYMBOL_SINGLE_QUOT = "'";
var SYMBOL_DOUBLE_QUOT = "\"";
var SYMBOL_WAVE = "~";
var SYMBOL_EXCLAMATION = "!";
var SYMBOL_MOUSE = "@";
var SYMBOL_WELL = "#";
var SYMBOL_DOLLAR = "$";
var SYMBOL_PERCENT = "%";
var SYMBOL_BIT_DIFF = "^";
var SYMBOL_STAR = "*";
var SYMBOL_SLASH = "/";
var SYMBOL_DOT = ".";
var SYMBOL_COLON = ":";
var SYMBOL_ARRAY_ALL = new Array(SYMBOL_COMMA,SYMBOL_EQUAL,SYMBOL_BIT_AND,SYMBOL_SINGLE_QUOT,SYMBOL_DOUBLE_QUOT
    ,SYMBOL_WAVE,SYMBOL_EXCLAMATION,SYMBOL_MOUSE,SYMBOL_WELL,SYMBOL_DOLLAR,SYMBOL_PERCENT,SYMBOL_BIT_DIFF,
    SYMBOL_STAR,SYMBOL_SLASH,SYMBOL_DOT,SYMBOL_COLON);
var SYMBOL_ARRAY_1 = new Array(SYMBOL_EQUAL,SYMBOL_BIT_AND,SYMBOL_SINGLE_QUOT,SYMBOL_DOUBLE_QUOT);
var SYMBOL_ARRAY_2 = new Array(SYMBOL_COMMA,SYMBOL_EQUAL,SYMBOL_BIT_AND,SYMBOL_SINGLE_QUOT,SYMBOL_DOUBLE_QUOT);

/**
 * ����str1�л��м���str2
 * @param str1
 * @param str2
 */
function containCount(str1, str2) {
    var count = 0;
    while(str1.indexOf(str2) > -1) {
         count ++;
        str1 = str1.substr(str1.indexOf(str2) + str2.length);
    }
    return count;
}

/**
 * �ж��ַ����Ƿ��зǷ��ַ�
 * У��ͨ������result["isSuccess"]=true
 * У��ʧ�ܷ���result["isSuccess"]=false,result["symbol"]=�����ķǷ��ַ�
 * @param value
 * @param symbolArray
 */
function checkStr(value, symbolArray) {
    var result = "";
    for(var i=0;i<symbolArray.length;i++){
        if(value.indexOf(symbolArray[i]) > -1) {
            if("'" == symbolArray[i]){
                //�Ե��������⴦��
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
 * �˳�
 * ע�⣺�õ�$.ajax�����Ե�����jquery-min.js
 * ע�⣺�õ�����baseUrl�����Ե�����header.jsp
 */
function logOut(){
    //ajax�˳�
    var SUCCESS_STR = "success";//�ɹ�����
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "ajax/logOut.jsp",
        data:"",
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //���˳��Ƿ�ɹ�
                if (false == data["isSuccess"]) {
                    alert(data["message"]);
                    return;
                } else {
                    //�˳��ɹ�
                    alert(data["message"]);
                }
                //�Ƿ���תҳ��
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
