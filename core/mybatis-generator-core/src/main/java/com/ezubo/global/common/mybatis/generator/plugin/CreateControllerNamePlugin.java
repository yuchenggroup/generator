package com.ezubo.global.common.mybatis.generator.plugin;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.TableConfiguration;

import java.util.*;

/**
 * Created by jeffer on 2015/11/12.
 */
public class CreateControllerNamePlugin extends PluginAdapter {
    private String packageName;
    private String controllerName;
    private String targetProject;

    private Properties properties;
    private List<GeneratedJavaFile>  generatedJavaFiles= new ArrayList<GeneratedJavaFile>();
    private  Set<FullyQualifiedJavaType> set = new HashSet<FullyQualifiedJavaType>();
    public CreateControllerNamePlugin() {
    }
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        super.contextGenerateAdditionalJavaFiles(introspectedTable);
        FullyQualifiedJavaType fullyQualifiedJavaType =new FullyQualifiedJavaType(packageName+"."+controllerName);
        JavaController javaController =new JavaController(fullyQualifiedJavaType);
        FullyQualifiedJavaType supclass =new FullyQualifiedJavaType("Test");
        javaController.getSuperClass(supclass);
        javaController.setVisibility(JavaVisibility.PUBLIC);

        FullyQualifiedJavaType imporType1 =new FullyQualifiedJavaType("com.yuchenggroup.global.client.Test");
        FullyQualifiedJavaType imporType2 =new FullyQualifiedJavaType("java.util.List");
        FullyQualifiedJavaType imporType3 =new FullyQualifiedJavaType("javax.servlet.http.HttpServletRequest");
        FullyQualifiedJavaType imporType4 =new FullyQualifiedJavaType("javax.servlet.http.HttpServletResponse");
        FullyQualifiedJavaType imporType5 =new FullyQualifiedJavaType("java.math.BigDecimal");
        FullyQualifiedJavaType imporType6 =new FullyQualifiedJavaType("java.util.Map");
        FullyQualifiedJavaType imporType7 =new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired");
        FullyQualifiedJavaType imporType8 =new FullyQualifiedJavaType("org.springframework.stereotype.Controller");
        FullyQualifiedJavaType imporType9 =new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RequestMapping");
        FullyQualifiedJavaType imporType10 =new FullyQualifiedJavaType("org.springframework.web.bind.annotation.ResponseBody");
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
        javaController.addImportedTypes(set);
        javaController.addAnnotation("@RequestMapping(\"/achieve/config\")");
        javaController.addAnnotation("@Controller");



        Method method =new Method();
        method.addAnnotation("@RequestMapping(value = \"/listBy.json\")");
        method.addAnnotation("@ResponseBody");
        FullyQualifiedJavaType pajavatype=new FullyQualifiedJavaType("String");
        Parameter parameter=new Parameter(pajavatype,"test",false);
        FullyQualifiedJavaType returnType=new FullyQualifiedJavaType("String");
        method.setReturnType(returnType);
        method.setName("read");
        method.addParameter(parameter);
        method.setVisibility(JavaVisibility.PUBLIC);
        javaController.addMethod(method);


        Context context = introspectedTable.getContext();
        List<TableConfiguration> tableConfigurations =context.getTableConfigurations();
        for(TableConfiguration tableConfiguration :tableConfigurations){
            System.out.println(tableConfiguration);
        }
        GeneratedJavaFile generatedJavaFile = new GeneratedJavaFile(javaController,this.targetProject,"UTF-8",context.getJavaFormatter());
        generatedJavaFiles.add(generatedJavaFile);
        return generatedJavaFiles;
    }

    public void setTargetProject(String targetProject) {
        this.targetProject = targetProject;
    }
    public void setProperties(Properties properties) {
        this.properties = properties;
        this.packageName= properties.get("packageName").toString();
        this.controllerName= properties.get("controllerName").toString();
        this.targetProject= properties.get("targetProject").toString();
    }
}
