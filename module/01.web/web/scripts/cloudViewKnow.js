//ueditor编辑器
var editor;

/**
 * 初始化
 */
$(document).ready(function() {
    if(message != EMPTY){
        showInformation(message);
    }

    //初始化申成知道分类
    initCloudKnowType("show_type", "type", initType);

    uParse("#description_content", {rootPath: baseUrl + '/ueditor/'});
    uParse(".answer_content", {rootPath: baseUrl + '/ueditor/'});


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
 * 展开回答
 */
function showAnswer(t){
    if($(t).val() == "展开回答"){
        $(t).val("收起回答");
        $("#editor_div").css("display", "block");
    } else {
        $(t).val("展开回答");
        $("#editor_div").css("display", "none");
    }
}

/**
 * 回答
 */
function cloudKnowAnswer(){
    if(EMPTY == editor.getContent()){
        showAttention("请回答提问再提交哦~");
        return;
    }
    $("#answer").val(editor.getContent());
    document.forms["cloudKnowAnswerFrom"].submit();
}

/**
 * 删除提问
 */
function cloudKnowDeleteAsk(){
    if(confirm("你确定要删除提问吗？")){
        document.forms["cloudKnowDeleteAskForm"].submit();
    }
}

/**
 * 删除回答
 * @param answerId
 */
function cloudKnowDeleteAnswer(answerId){
    if(confirm("你确定要删除回答吗？")){
        $("#answerId").val(answerId);
        document.forms["cloudKnowDeleteAnswerForm"].submit();
    }
}

/**
 * 展示更新回答
 * @param answerId
 */
function showCloudKnowUpdateAnswer(answerId){
    //修改回答类型
    $("#cloudKnowAnswerType").val("update");
    $("#cloudKnowAnswerAnswerId").val(answerId);
    //展开回答
    editor.setContent($("#answer_" + answerId).html());
    $("#writeAnswerButton").val("收起回答");
    $("#editor_div").css("display", "block");
    location.href = "#writeAnswerButtonA";
}

/**
 * 点赞回答
 * @param answerId
 */
function cloudKnowZanAnswer(answerId){
    $("#cloudKnowZanAnswerId").val(answerId);
    document.forms["cloudKnowZanAnswerForm"].submit();
}