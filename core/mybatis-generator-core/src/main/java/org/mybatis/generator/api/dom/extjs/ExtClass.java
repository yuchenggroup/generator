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

import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.*;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2015/11/4.
 */
public abstract class ExtClass {
    // 依据 Java 域来生成
    protected List<Field> fields;

    public ExtClass(List<Field> fields){
        this.fields = fields;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    /**
     * 实现: 获取模板,然后执行替换. <br/>
     * 有2个值: ${modelName}, ${fieldName}, 使用标签 <field></field>
     * @param indentLevel
     * @return
     */
    public String getFormattedContent(int indentLevel) {
        StringBuilder sb = new StringBuilder();

        OutputUtilities.javaIndent(sb, indentLevel);

        indentLevel++;

        Iterator<Field> fldIter = fields.iterator();
        while (fldIter.hasNext()) {
            OutputUtilities.newLine(sb);
            Field field = fldIter.next();
            sb.append(getFormattedContent(field, indentLevel));
            if (fldIter.hasNext()) {
                OutputUtilities.newLine(sb);
            }
        }
        indentLevel--;
        OutputUtilities.newLine(sb);
        OutputUtilities.javaIndent(sb, indentLevel);

        return sb.toString();
    }



    public String getFormattedContent(Field field, int indentLevel) {
        StringBuilder sb = new StringBuilder();

        sb.append("'");
        sb.append(field.getName());
        sb.append("'");

        return sb.toString();
    }
}
