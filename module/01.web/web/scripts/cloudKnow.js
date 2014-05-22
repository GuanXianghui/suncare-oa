/**
 * 初始化
 */
$(document).ready(function() {
    if(message != EMPTY){
        showInformation(message);
    }
});

/**
 * 查询申成知道
 */
function queryAsk(ask){
    $("#cloudQueryAsk").val(ask);
    document.forms["cloudQueryAskForm"].submit();
}