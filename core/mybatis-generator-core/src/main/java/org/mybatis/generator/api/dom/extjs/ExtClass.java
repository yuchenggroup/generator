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
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.config.xml.ParserEntityResolver;
import org.mybatis.generator.exception.XMLParserException;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by Administrator on 2015/11/4.
 */
public abstract class ExtClass {
    //
    public static String fieldNameKey = "fieldName";
    // 依据 Java 域来生成
    protected List<Field> fields;
    private String appName;
    private String shortPackage;
    private String modelName;


    public abstract String getResName();

    public ExtClass(List<Field> fields){
        this.fields = fields;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getShortPackage() {
        return shortPackage;
    }

    public void setShortPackage(String shortPackage) {
        this.shortPackage = shortPackage;
    }

    public Map<String, String> getAttributesMap() {
        //
        Map<String, String> resultMap = new HashMap<String, String>();
        //
        String appName = getAppName();
        String shortPackage = getShortPackage();
        String modelName = getModelName();
        //
        resultMap.put("appName", appName);
        resultMap.put("shortPackage", shortPackage);
        resultMap.put("modelName", modelName);
        //
        return resultMap;
    }



    /**
     * 实现: 获取模板,然后执行替换. <br/>
     * 有2个值: ${modelName}, ${fieldName}, 使用标签 <field></field>
     * @param indentLevel
     * @return
     */
    public String getFormattedContent(int indentLevel){
        //
        StringBuilder builder = new StringBuilder();
        //
        String resName = getResName();
        //
        List<ExtjsTemplate> templateList = getResourceAsTemplates(resName);
        //
        Map<String, String> attributesMap = getAttributesMap();
        //
        List<Map<String,String>> mapList = getFieldAttrMap();
        //
        Iterator<ExtjsTemplate> iteratorT = templateList.iterator();
        while(iteratorT.hasNext()){
            //
            ExtjsTemplate template = iteratorT.next();
            //
            String content = template.parseAttrs(attributesMap, mapList);
            //
            builder.append(content);
        }
        //
        return builder.toString();
    }

    protected List<Map<String,String>> getFieldAttrMap(){
        //
        List<Map<String,String>> mapList = new ArrayList<Map<String, String>>();

        //
        Iterator<Field> fldIter = fields.iterator();
        while (fldIter.hasNext()) {
            Field field = fldIter.next();
            //
            String name = field.getName();

            Map<String, String> map = new HashMap<String, String>();
            map.put(fieldNameKey, name);
            //
            mapList.add(map);
        }
        //
        return mapList;
    }

    public String _getFormattedContent(int indentLevel) {
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

    private String getFormattedContent(Field field, int indentLevel) {
        StringBuilder sb = new StringBuilder();

        sb.append("'");
        sb.append(field.getName());
        sb.append("'");

        return sb.toString();
    }

    protected List<ExtjsTemplate> getResourceAsTemplates(String resName) {
        //
        List<ExtjsTemplate> list = new ArrayList<ExtjsTemplate>();
        //
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resName);
            InputSource is = new InputSource(inputStream);
            List<ExtjsTemplate> templateList = parseTemplate(is);
            //
            list = templateList;
            //
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    private List<ExtjsTemplate> parseTemplate(InputSource inputSource)
            throws IOException, XMLParserException, SAXException {
        //
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //factory.setValidating(true);
        //
        List<ExtjsTemplate> list = new ArrayList<ExtjsTemplate>();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver(new ParserEntityResolver());
            Document document = builder.parse(inputSource);
            Element rootNode = document.getDocumentElement();
            //
            NodeList nodeList = rootNode.getChildNodes();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node childNode = nodeList.item(i);

                if (childNode.getNodeType() == Node.TEXT_NODE) {
                    // 处理文本节点
                    String textContent = getTextContent(childNode);
                    ExtjsTemplate template = ExtjsTemplate.getStringTemplate(textContent);
                    list.add(template);
                }
                if (childNode.getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }

                if ("field".equals(childNode.getNodeName())) { //$NON-NLS-1$
                    //
                    String textContent = getTextContent(childNode);
                    String separator = "";
                    NamedNodeMap nnm = childNode.getAttributes();
                    for (int n = 0; n < nnm.getLength(); n++) {
                        Node attribute = nnm.item(n);
                        //
                        String name = attribute.getNodeName();
                        String value = attribute.getNodeValue();
                        //
                        if("separator".equalsIgnoreCase(name)){
                            //
                            value = value.replace("\\n","\n");
                            separator = value;
                        }
                    }
                    //
                    ExtjsTemplate template = ExtjsTemplate.getFieldTemplate(textContent, separator);
                    list.add(template);
                }
            }

            return list;
        } catch (ParserConfigurationException e) {
            throw new XMLParserException("解析XML错误");
        }
    }

    private String getTextContent(Node childNode) {
        //
        String textContent = childNode.getTextContent();
        try {
            textContent = new String(textContent.getBytes("UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return textContent;
    }
}
