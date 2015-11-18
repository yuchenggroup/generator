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
public class CreateInterfaceImplNamePlugin extends PluginAdapter {
    private String packageName;
    private String targetProject;
    private String serviceNameImpl;
    private String serviceName;
    private String smodel;
    private String bmodel;
    private String packageFile;
    private  String modePackage;
    private String bMapper;
    private String sMapper;
    private List<GeneratedJavaFile>  generatedJavaFiles= new ArrayList<GeneratedJavaFile>();
    private Set<FullyQualifiedJavaType> set = new HashSet<FullyQualifiedJavaType>();
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }
    public CreateInterfaceImplNamePlugin() {
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        super.contextGenerateAdditionalJavaFiles(introspectedTable);
        Context context = introspectedTable.getContext();
        modePackage = context.getJavaModelGeneratorConfiguration().getTargetPackage();
        //所属包
        FullyQualifiedJavaType fullyQualifiedJavaType =new FullyQualifiedJavaType(packageName+".impl."+serviceNameImpl);
        JavaController javaService =new JavaController(fullyQualifiedJavaType);
        //实现类
        FullyQualifiedJavaType interfacejava = new FullyQualifiedJavaType(serviceName);
        javaService.addSuperInterface(interfacejava);
        //引包
        importPackage();
        javaService.addImportedTypes(set);
        javaService.setVisibility(JavaVisibility.PUBLIC);
        //添加注解
        javaService.addAnnotation("@Service(\"" + serviceNameImpl + "\")");
        //添加Mapper
        javaService.addService(" \n" + " @Autowired  \n  private " + bMapper +" "+ sMapper + ";");
        //添加查询方法
        javaService.addMethod(queryMethod());
        javaService.addMethod(queryMethod1());
        //添加新增方法
        javaService.addMethod(createMethod());
        //添加修改方法
        javaService.addMethod(updateMethod());
        //添加删除方法
        javaService.addMethod(deleteMethod());


        GeneratedJavaFile generatedJavaFile = new GeneratedJavaFile(javaService,this.targetProject,"UTF-8",context.getJavaFormatter());
        generatedJavaFiles.add(generatedJavaFile);
        return generatedJavaFiles;
    }
    public void setProperties(Properties properties) {
        this.properties = properties;
        this.packageFile= properties.get("packageFile").toString();
        this.packageName= properties.get("packageName").toString()+"."+packageFile;
        this.targetProject= properties.get("targetProject").toString();
        String model =properties.get("model").toString();
        String mapper="Mapper";
        this.serviceNameImpl=model+"ServiceImpl";
        this.serviceName=model+"Service";
        this.bmodel=model;
        this.smodel=model.substring(0,1).toLowerCase()+model.substring(1);
        this.bMapper=model+mapper;
        this.sMapper=model.substring(0,1).toLowerCase()+model.substring(1)+mapper;


    }

    //引包
    public  void importPackage(){
        FullyQualifiedJavaType imporType1=new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired");
        FullyQualifiedJavaType imporType2 =new FullyQualifiedJavaType("org.slf4j.Logger");
        FullyQualifiedJavaType imporType3 =new FullyQualifiedJavaType("org.slf4j.LoggerFactory");
        FullyQualifiedJavaType imporType4 =new FullyQualifiedJavaType("org.springframework.stereotype.Service");
        FullyQualifiedJavaType imporType5 =new FullyQualifiedJavaType("org.springframework.transaction.annotation.Propagation");
        FullyQualifiedJavaType imporType6 =new FullyQualifiedJavaType("org.springframework.transaction.annotation.Transactional");
        FullyQualifiedJavaType imporType7 =new FullyQualifiedJavaType("javax.annotation.Resource");
        FullyQualifiedJavaType imporType8 =new FullyQualifiedJavaType("java.util.List");
        FullyQualifiedJavaType imporType9 =new FullyQualifiedJavaType("java.util.Map");
        FullyQualifiedJavaType imporType11 =new FullyQualifiedJavaType(modePackage+"."+bmodel);
        FullyQualifiedJavaType imporType10 =new FullyQualifiedJavaType(packageName+"."+serviceName);
        FullyQualifiedJavaType imporType12 =new FullyQualifiedJavaType("com.ezubo.global.achievement.utils.exception.ServicePortalException");
        FullyQualifiedJavaType imporType13 =new FullyQualifiedJavaType("com.ezubo.global.achievement.dao."+bMapper);
        set.add(imporType1);
        set.add(imporType2);
        set.add(imporType3);
        set.add(imporType4);
        set.add(imporType5);
        set.add(imporType6);
        set.add(imporType7);
        set.add(imporType8);
        set.add(imporType9);
        set.add(imporType10);
        set.add(imporType11);
        set.add(imporType12);
        set.add(imporType13);
    }

    /**
     *  添加 查询 方法
     * @return
     */
    public Method queryMethod(){
        Set<String> readBodyLines = new HashSet<String>();
        Method method =new Method();
        method.addAnnotation("@Override");
        FullyQualifiedJavaType pajavatype=new FullyQualifiedJavaType("Map");
        Parameter parameter=new Parameter(pajavatype,"map",false);
        FullyQualifiedJavaType returnType=new FullyQualifiedJavaType("List<"+bmodel+">");
        method.setReturnType(returnType);
        method.setName("read");
        method.addParameter(parameter);
        method.setVisibility(JavaVisibility.PUBLIC);
        readBodyLines.add("return "+sMapper+".read(map);");
        method.addBodyLines(readBodyLines);
        return method;
    }

    /**
     *  添加 查询 方法
     * @return
     */
    public Method queryMethod1(){
        Set<String> readBodyLines = new HashSet<String>();
        Method method =new Method();
        method.addAnnotation("@Override");
        FullyQualifiedJavaType pajavatype=new FullyQualifiedJavaType("Map");
        Parameter parameter=new Parameter(pajavatype,"map",false);
        FullyQualifiedJavaType returnType=new FullyQualifiedJavaType("Integer");
        method.setReturnType(returnType);
        method.setName("countRead");
        method.addParameter(parameter);
        method.setVisibility(JavaVisibility.PUBLIC);
        readBodyLines.add("return "+sMapper+".countRead(map);");
        method.addBodyLines(readBodyLines);
        return method;
    }
    /**
     * 添加新增方法
     * @return
     */
    public Method createMethod(){
        Set<String> readBodyLines = new HashSet<String>();
        Method method =new Method();
        method.addAnnotation("@Override");
        method.addAnnotation("@Transactional (propagation = Propagation.REQUIRED,rollbackFor=ServicePortalException.class)");
        FullyQualifiedJavaType pajavatype1=new FullyQualifiedJavaType(bmodel);
        Parameter parameter1=new Parameter(pajavatype1,smodel,false);
        method.addParameter(parameter1);
        method.setName("create");
        method.setVisibility(JavaVisibility.PUBLIC);
        readBodyLines.add(sMapper+".insert("+smodel+");");
        method.addBodyLines(readBodyLines);
        return method;
    }
    /**
     * 修改方法
     * @return
     */
    public Method  updateMethod(){
        Set<String> readBodyLines = new HashSet<String>();
        Method method =new Method();
        method.addAnnotation("@Override");
        method.addAnnotation("@Transactional (propagation = Propagation.REQUIRED,rollbackFor=ServicePortalException.class)");
        FullyQualifiedJavaType pajavatype1=new FullyQualifiedJavaType(bmodel);
        Parameter parameter1=new Parameter(pajavatype1,smodel,false);
        method.addParameter(parameter1);
        method.setName("update");
        method.setVisibility(JavaVisibility.PUBLIC);
        readBodyLines.add(sMapper+".updateByPrimaryKey("+smodel+");");
        method.addBodyLines(readBodyLines);
        return method;
    }

    /**
     * 删除方法
     * @return
     */
    public Method  deleteMethod(){
        Set<String> readBodyLines = new HashSet<String>();
        Method method =new Method();
        method.addAnnotation("@Override");
        method.addAnnotation("@Transactional (propagation = Propagation.REQUIRED,rollbackFor=ServicePortalException.class)");
        FullyQualifiedJavaType pajavatype1=new FullyQualifiedJavaType("Long");
        Parameter parameter1=new Parameter(pajavatype1,"id",false);
        method.addParameter(parameter1);
        method.setName("delete");
        method.setVisibility(JavaVisibility.PUBLIC);
        readBodyLines.add(sMapper + ".deleteByPrimaryKey(id);");
        method.addBodyLines(readBodyLines);
        return method;
    }
}
