/**
 * 初始化
 */
$(document).ready(function() {
    if(message != EMPTY){
        showInformation(message);
    }

    $("#title").val(CLOUD_UPLOAD_DOC_EMPTY_TITLE);
    $("#title").css("color", "gray");
    $("#description").val(CLOUD_UPLOAD_DOC_EMPTY_DESCRIPTION);
    $("#description").css("color", "gray");
    $("#tags").val(CLOUD_UPLOAD_DOC_EMPTY_TAGS);
    $("#tags").css("color", "gray");

    //初始化申成文库分类
    initCloudDocType("show_type", "type", DEFAULT_CLOUD_DOC_TYPE);
});

/**
 * 查询文档
 */
function queryDoc(doc){
    $("#cloudQueryDocName").val(doc);
    document.forms["cloudQueryDocForm"].submit();
}

/**
 * 聚焦
 * @param t
 */
function focusTitle(t){
    if($(t).val() == CLOUD_UPLOAD_DOC_EMPTY_TITLE){
        $(t).val(EMPTY);
    }
    $(t).css("color", "black");
}

/**
 * 离开
 * @param t
 */
function blurTitle(t){
    if($(t).val() == EMPTY){
        $(t).val(CLOUD_UPLOAD_DOC_EMPTY_TITLE);
        $(t).css("color", "gray");
    }
}

/**
 * 聚焦
 * @param t
 */
function focusDescription(t){
    if($(t).val() == CLOUD_UPLOAD_DOC_EMPTY_DESCRIPTION){
        $(t).val(EMPTY);
    }
    $(t).css("color", "black");
}

/**
 * 离开
 * @param t
 */
function blurDescription(t){
    if($(t).val() == EMPTY){
        $(t).val(CLOUD_UPLOAD_DOC_EMPTY_DESCRIPTION);
        $(t).css("color", "gray");
    }
}

/**
 * 聚焦
 * @param t
 */
function focusTags(t){
    if($(t).val() == CLOUD_UPLOAD_DOC_EMPTY_TAGS){
        $(t).val(EMPTY);
    }
    $(t).css("color", "black");
}

/**
 * 离开
 * @param t
 */
function blurTags(t){
    if($(t).val() == EMPTY){
        $(t).val(CLOUD_UPLOAD_DOC_EMPTY_TAGS);
        $(t).css("color", "gray");
    }
}

/**
 * 选择文档
 */
function changeDoc(){
    if($("#doc").val() == undefined || $("#doc").val() == null || $("#doc").val() == EMPTY){
        return;
    }
    var symbol = "\\";
    if($("#doc").val().indexOf(symbol) == -1){
        symbol = SYMBOL_SLASH;
    }
    var title = EMPTY;
    if($("#doc").val().indexOf(symbol) == -1){
        title = $("#doc").val();
    } else {
        var index = $("#doc").val().lastIndexOf(symbol);
        title = $("#doc").val().substr(index + 1);
    }
    $("#title").val(title);
    $("#title").css("color","black");
}

/**
 * 上传文档
 */
function cloudUploadDoc(){
    if($("#title").val() == CLOUD_UPLOAD_DOC_EMPTY_TITLE){
        showAttention("请输入标题");
        return;
    }
    if($("#type").val() == EMPTY){
        showAttention("请选择分类");
        return;
    }
    if($("#doc").val() == undefined || $("#doc").val() == null || $("#doc").val() == EMPTY){
        showAttention("请选择文档");
        return;
    }

    if($("#description").val() == CLOUD_UPLOAD_DOC_EMPTY_DESCRIPTION){
        $("#description").val(EMPTY);
    }

    if($("#tags").val() == CLOUD_UPLOAD_DOC_EMPTY_TAGS){
        $("#tags").val(EMPTY);
    }

    document.forms["cloudUploadDocFrom"].submit();
}