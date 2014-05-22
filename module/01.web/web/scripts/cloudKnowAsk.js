//ueditor编辑器
var editor;

/**
 * 初始化
 */
$(document).ready(function() {
    if(message != EMPTY){
        showInformation(message);
    }

    $("#question").val(CLOUD_KNOW_ASK_EMPTY_QUESTION);
    $("#question").css("color", "gray");

    //初始化申成知道分类
    initCloudKnowType("show_type", "type", DEFAULT_CLOUD_KNOW_TYPE);

    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    editor = UE.getEditor('editor');
});

/**
 * 查询申成知道
 */
function queryAsk(ask){
    $("#cloudQueryAsk").val(ask);
    document.forms["cloudQueryAskForm"].submit();
}

/**
 * 聚焦
 * @param t
 */
function focusQuestion(t){
    if($(t).val() == CLOUD_KNOW_ASK_EMPTY_QUESTION){
        $(t).val(EMPTY);
    }
    $(t).css("color", "black");
}

/**
 * 离开
 * @param t
 */
function blurQuestion(t){
    if($(t).val() == EMPTY){
        $(t).val(CLOUD_KNOW_ASK_EMPTY_QUESTION);
        $(t).css("color", "gray");
    }
}

/**
 * 填写问题补充
 */
function writeDescription(t){
    if($(t).val() == "展开"){
        $(t).val("收起");
        $("#editor_div").css("display", "block");
    } else {
        $(t).val("展开");
        $("#editor_div").css("display", "none");
    }
}

/**
 * 提问
 */
function cloudKnowAsk(){
    if($("#question").val() == CLOUD_KNOW_ASK_EMPTY_QUESTION){
        showAttention("请输入问题");
        return;
    }
    $("#description").val(editor.getContent());

    document.forms["cloudKnowAskFrom"].submit();
}