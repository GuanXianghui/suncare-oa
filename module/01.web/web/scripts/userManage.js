
//����޸�����
function beforeUpdatePassword(){
    document.getElementById("before_update_password_td").style.display = "none";
    document.getElementById("update_password_td").style.display = "";
    document.getElementById("password").value = "";
}

//ȡ���޸�����
function cancelUpdatePassword(){
    document.getElementById("before_update_password_td").style.display = "";
    document.getElementById("update_password_td").style.display = "none";
}

//����޸�����
function updatePassword(){
    //����md5ǩ��
    var password = document.getElementById("password");
    var md5Pwd = MD5(password.value + md5Key);
    password.value = md5Pwd;
    //ajax�޸�����
    var SUCCESS_STR = "success";//�ɹ�����
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "updatePassword.do",
        data:"password=" + md5Pwd + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //���޸������Ƿ�ɹ�
                if (false == data["isSuccess"]) {
                    alert(data["message"]);
                    password.value = "";
                    return;
                } else {
                    //�޸�����ɹ�
                    alert(data["message"]);
                    document.getElementById("before_update_password_td").style.display = "";
                    document.getElementById("update_password_td").style.display = "none";
                }
                //���Ƿ�����token
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
}
