//ueditor编辑器
var editor;

/**
 * 初始化
 */
$(document).ready(function() {
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    editor = UE.getEditor('editor');
});

/**
 * 提交
 */
function writeDiary(){
    var date = document.getElementById("date").value;
    if(date == EMPTY){
        showAttention("请输入日期");
        return false;
    }
    if(date != EMPTY && (isNum(date) == false || date.length != 8)){
        showAttention("日期输入有误！");
        return;
    }
    var content = editor.getContent();
    if(content.length > DIARY_CONTENT_LENGTH) {
        showAttention("工作日志内容大于" + DIARY_CONTENT_LENGTH + "个字符");
        return false;
    }
    document.getElementById("content").value = content;
    //提交表格
    document.forms["writeDiaryForm"].submit();
}