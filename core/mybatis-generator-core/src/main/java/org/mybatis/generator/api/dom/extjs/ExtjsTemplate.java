/**
 *    Copyright 2006-2015 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator.api.dom.extjs;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2015/11/6.
 */
public class ExtjsTemplate {
    public static final int template_string = 1;
    public static final int template_field = 2;
    //
    private String templateContent;
    private int templateType;
    private String separator;

    public static ExtjsTemplate getStringTemplate(String templateContent){
        //
        ExtjsTemplate extjsTemplate = new ExtjsTemplate();
        //
        extjsTemplate.templateType = template_string;
        extjsTemplate.templateContent = templateContent;
        //
        return  extjsTemplate;
    }

    public static ExtjsTemplate getFieldTemplate(String templateContent, String separator){
        //
        ExtjsTemplate extjsTemplate = new ExtjsTemplate();
        //
        extjsTemplate.templateType = template_field;
        extjsTemplate.templateContent = templateContent;
        extjsTemplate.separator = separator;
        //
        return  extjsTemplate;
    }

    public String parseAttrs(Map<String, String> attrMap, List<Map<String, String>> fieldAttr){
        if(this.templateType == template_string){
            return paeseString(attrMap);
        } else if(this.templateType == template_field){
            return paeseFieldString(fieldAttr);
        } else {
            return "";
        }
    }

    private String paeseString(Map<String, String> attrMap) {
        //
        String content = this.templateContent;
        //
        return paeseString(content, attrMap);
    }

    private String paeseString(String content, Map<String, String> attrMap) {
        // 遍历
        Set<String> keySet = attrMap.keySet();
        Iterator<String> iteratorK = keySet.iterator();
        while(iteratorK.hasNext()) {
            String key = iteratorK.next();
            String value = attrMap.get(key);
            //
            content = replaceHolder(content, key, value);
        }
        //
        return content;
    }

    private String paeseFieldString(List<Map<String, String>> fieldAttr) {
        //
        String result = "";
        Iterator<Map<String, String>> iteratorF = fieldAttr.iterator();
        while(iteratorF.hasNext()){
            Map<String, String> attrMap = iteratorF.next();
            //
            String content = paeseString(this.templateContent, attrMap);
            result += content;
            if(iteratorF.hasNext()){
                result += this.separator;
            }
        }
        //
        return  result;
    }

    private String replaceHolder(String content, String key, String value) {
        //
        String replaceStr = "${" + key + "}";
        return content.replace(replaceStr, value);
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    public int getTemplateType() {
        return templateType;
    }

    public void setTemplateType(int templateType) {
        this.templateType = templateType;
    }
}
