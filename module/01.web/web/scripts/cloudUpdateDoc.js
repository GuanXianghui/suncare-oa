/**
 * 初始化
 */
$(document).ready(function() {
    if(message != EMPTY){
        showInformation(message);
    }

    $("#title").val(initTitle);
    if(EMPTY == initType || initType == CLOUD_UPLOAD_DOC_EMPTY_TITLE){
        $("#title").val(CLOUD_UPLOAD_DOC_EMPTY_TITLE);
        $("#title").css("color", "gray");
    } else {
        $("#title").css("color", "black");
    }
    $("#description").val(initDescription);
    if(EMPTY == initDescription || initDescription == CLOUD_UPLOAD_DOC_EMPTY_DESCRIPTION){
        $("#description").val(CLOUD_UPLOAD_DOC_EMPTY_DESCRIPTION);
        $("#description").css("color", "gray");
    } else {
        $("#description").css("color", "black");
    }
    $("#tags").val(initTags);
    if(EMPTY == initTags || initTags == CLOUD_UPLOAD_DOC_EMPTY_TAGS){
        $("#tags").val(CLOUD_UPLOAD_DOC_EMPTY_TAGS);
        $("#tags").css("color", "gray");
    } else {
        $("#tags").css("color", "black");
    }

    //初始化申成文库分类
    initCloudDocType("show_type", "type", initType);
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
 * 上传文档
 */
function cloudUpdateDoc(){
    if($("#title").val() == CLOUD_UPLOAD_DOC_EMPTY_TITLE){
        showAttention("请输入标题");
        return;
    }
    if($("#type").val() == EMPTY){
        showAttention("请选择分类");
        return;
    }

    if($("#description").val() == CLOUD_UPLOAD_DOC_EMPTY_DESCRIPTION){
        $("#description").val(EMPTY);
    }

    if($("#tags").val() == CLOUD_UPLOAD_DOC_EMPTY_TAGS){
        $("#tags").val(EMPTY);
    }

    document.forms["cloudUpdateDocFrom"].submit();
}