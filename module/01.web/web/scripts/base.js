//���ż���
var SYMBOL_ARRAY_1 = new Array("=","&","'","\"");
var SYMBOL_ARRAY_2 = new Array(",","=","&","'","\"");

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
