//申成知道分类
var CLOUD_KNOW_TYPE_JSON = eval("({T:'1,公司制度&2,专业资料&3,工具介绍'," +
    "T1:'1_1,公司介绍&1_2,公司公告&1_3,考核制度'," +
    "T2:'2_1,技术&2_2,算料&2_3,报价'," +
    "T2_1:'2_1_1,画图&2_1_2,测量'" +
    "})");

//默认申成知道分类
var DEFAULT_CLOUD_KNOW_TYPE = "2_1_1";

//初始化申成知道分类
function initCloudKnowType(showId, valueId, value){
    var html = EMPTY;
    var valueSelectId = EMPTY;//最后一个select的id，可取到他的值
    if(value == EMPTY){
        var ts = CLOUD_KNOW_TYPE_JSON["T"].split("&");
        html += "<SELECT id=\"T\" onchange=\"changeCloudKnowType('" + showId + "','" + valueId + "',this.value)\">";
        for(var i=0;i<ts.length;i++){
            var v = ts[i].split(",");
            html += "<option value=\"" + v[0] + "\">" + v[1] + "</option>";
        }
        html += "</SELECT>";
        //取最后一个select的id，可取到他的值
        valueSelectId = "T";
    } else {
        var values = value.split("_");
        //第一层
        var ts = CLOUD_KNOW_TYPE_JSON["T"].split("&");
        html += "<SELECT id=\"T\" onchange=\"changeCloudKnowType('" + showId + "','" + valueId + "',this.value)\">";
        for(var i=0;i<ts.length;i++){
            var v = ts[i].split(",");
            html += "<option value=\"" + v[0] + "\"" + (v[0]==values[0]?" SELECTED":"") + ">" + v[1] + "</option>";
        }
        html += "</SELECT>";

        //取最后一个select的id，可取到他的值
        valueSelectId = "T";

        var tempValue = values[0];
        for(var i=0;i<values.length;i++){
            if(tempValue == value){
                break;
            }
            //最后一层
            if(CLOUD_KNOW_TYPE_JSON["T" + tempValue] != undefined && CLOUD_KNOW_TYPE_JSON["T" + tempValue] != null){
                ts = CLOUD_KNOW_TYPE_JSON["T" + tempValue].split("&");
                html += "<SELECT id=\"" + tempValue + "\" onchange=\"changeCloudKnowType('" + showId + "','" + valueId + "',this.value)\">";
                for(var j=0;j<ts.length;j++){
                    var v = ts[j].split(",");
                    html += "<option value=\"" + v[0] + "\"" + (v[0]==tempValue + "_" + values[i+1]?" SELECTED":"") + ">" + v[1] + "</option>";
                }
                html += "</SELECT>";

                //取最后一个select的id，可取到他的值
                valueSelectId = tempValue;
            }

            tempValue += "_" + values[i+1];
        }

        //最后一层
        if(CLOUD_KNOW_TYPE_JSON["T" + value] != undefined && CLOUD_KNOW_TYPE_JSON["T" + value] != null){
            ts = CLOUD_KNOW_TYPE_JSON["T" + value].split("&");
            html += "<SELECT id=\"" + value + "\" onchange=\"changeCloudKnowType('" + showId + "','" + valueId + "',this.value)\">";
            for(var i=0;i<ts.length;i++){
                var v = ts[i].split(",");
                html += "<option value=\"" + v[0] + "\">" + v[1] + "</option>";
            }
            html += "</SELECT>";

            //取最后一个select的id，可取到他的值
            valueSelectId = value;
        }
    }
    $("#" + showId).html(html);
    $("#" + valueId).val($("#" + valueSelectId).val());

    //如果最后一层的第一个值还有下一层，则再递归调一次这个方法
    var lastSelectValue = $("#" + valueSelectId).val();
    if(CLOUD_KNOW_TYPE_JSON["T" + lastSelectValue] != undefined && CLOUD_KNOW_TYPE_JSON["T" + lastSelectValue] != null){
        initCloudKnowType(showId, valueId, lastSelectValue);
    }
}

/**
 * 修改申成知道分类
 * @param valueType
 */
function changeCloudKnowType(showId, valueId, type){
    initCloudKnowType(showId, valueId, type);
}