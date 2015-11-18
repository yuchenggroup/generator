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
package com.ezubo.global.common.mybatis.generator.plugin;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.Context;

import java.util.*;

/**
 * Created by jeffer on 2015/11/18.
 */
public class CreateInterfaceNamePlugin  extends PluginAdapter {
    private String packageName;
    private String controllerName;
    private String targetProject;
    private String serviceName;
    private String smodel;
    private String bmodel;
    private String comment;
    private String packageFile;
    private  String modePackage;
    private Properties properties;
    private List<GeneratedJavaFile>  generatedJavaFiles= new ArrayList<GeneratedJavaFile>();
    private Set<FullyQualifiedJavaType> set = new HashSet<FullyQualifiedJavaType>();
    public CreateInterfaceNamePlugin() {
    }
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        super.contextGenerateAdditionalJavaFiles(introspectedTable);
        Context context = introspectedTable.getContext();
        modePackage = context.getJavaModelGeneratorConfiguration().getTargetPackage();
        //所属包
        FullyQualifiedJavaType fullyQualifiedJavaType =new FullyQualifiedJavaType(packageName+"."+serviceName);
        Interface anInterface =new Interface(fullyQualifiedJavaType);
        //引包
        importPackage();
        anInterface.addImportedTypes(set);
        anInterface.setVisibility(JavaVisibility.PUBLIC);

        //添加查询方法
        anInterface.addMethod(queryMethod());
        anInterface.addMethod(queryMethod1());
        //添加新增方法
        anInterface.addMethod(createMethod());
        //添加修改方法
        anInterface.addMethod(updateMethod());
        //添加删除方法
        anInterface.addMethod(deleteMethod());

        GeneratedJavaFile generatedJavaFile = new GeneratedJavaFile(anInterface,this.targetProject,"UTF-8",context.getJavaFormatter());
        generatedJavaFiles.add(generatedJavaFile);
        return generatedJavaFiles;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
        this.packageFile= properties.get("packageFile").toString();
        this.packageName= properties.get("packageName").toString()+"."+packageFile;
        this.targetProject= properties.get("targetProject").toString();
        String model =properties.get("model").toString();
        this.serviceName=model+"Service";
        this.bmodel=model;
        this.smodel=model.substring(0,1).toLowerCase()+model.substring(1);
    }

    //引包
    public  void importPackage(){
        FullyQualifiedJavaType imporType8 =new FullyQualifiedJavaType("java.util.List");
        FullyQualifiedJavaType imporType9 =new FullyQualifiedJavaType("java.util.Map");
        FullyQualifiedJavaType imporType11 =new FullyQualifiedJavaType(modePackage+"."+bmodel);
        FullyQualifiedJavaType imporType10 =new FullyQualifiedJavaType(packageName+"."+serviceName);
        set.add(imporType8);
        set.add(imporType9);
        set.add(imporType10);
        set.add(imporType11);
    }

    /**
     *  添加 查询 方法
     * @return
     */
    public Method queryMethod(){
        Method method =new Method();
        FullyQualifiedJavaType pajavatype=new FullyQualifiedJavaType("Map");
        Parameter parameter=new Parameter(pajavatype,"map",false);
        FullyQualifiedJavaType returnType=new FullyQualifiedJavaType("List<"+bmodel+">");
        method.setReturnType(returnType);
        method.setName("read");
        method.addParameter(parameter);
        method.setVisibility(JavaVisibility.PUBLIC);
        return method;
    }
    /**
     *  添加 查询 方法
     * @return
     */
    public Method queryMethod1(){
        Method method =new Method();
        FullyQualifiedJavaType pajavatype=new FullyQualifiedJavaType("Map");
        Parameter parameter=new Parameter(pajavatype,"map",false);
        FullyQualifiedJavaType returnType=new FullyQualifiedJavaType("Integer");
        method.setReturnType(returnType);
        method.setName("countRead");
        method.addParameter(parameter);
        method.setVisibility(JavaVisibility.PUBLIC);
        return method;
    }

    /**
     * 添加新增方法
     * @return
     */
    public Method createMethod(){
        Method method = new Method();
        FullyQualifiedJavaType pajavatype1=new FullyQualifiedJavaType(bmodel);
        Parameter parameter1=new Parameter(pajavatype1,smodel,false);
        method.addParameter(parameter1);
        method.setName("create");
        method.setVisibility(JavaVisibility.PUBLIC);
        return method;
    }
    /**
     * 修改方法
     * @return
     */
    public Method  updateMethod(){
        Method method = new Method();
        FullyQualifiedJavaType pajavatype1=new FullyQualifiedJavaType(bmodel);
        Parameter parameter1=new Parameter(pajavatype1,smodel,false);
        method.addParameter(parameter1);
        method.setName("update");
        method.setVisibility(JavaVisibility.PUBLIC);
        return method;
    }

    /**
     * 删除方法
     * @return
     */
    public Method  deleteMethod(){
        Method method = new Method();
        FullyQualifiedJavaType pajavatype1=new FullyQualifiedJavaType("Long");
        Parameter parameter1=new Parameter(pajavatype1,"id",false);
        method.addParameter(parameter1);
        method.setName("delete");
        method.setVisibility(JavaVisibility.PUBLIC);
        return method;
    }
}
