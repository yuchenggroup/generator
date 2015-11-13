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
    private String serviceName;
    private String smodel;
    private String bmodel;
    private String comment;
    private String packageFile;
    private  String modePackage;
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
        Context context = introspectedTable.getContext();
        modePackage = context.getJavaModelGeneratorConfiguration().getTargetPackage();
        //所属包
        FullyQualifiedJavaType fullyQualifiedJavaType =new FullyQualifiedJavaType(packageName+"."+controllerName);
        JavaController javaController =new JavaController(fullyQualifiedJavaType);
        //引包
        importPackage();
        javaController.addImportedTypes(set);
        javaController.setVisibility(JavaVisibility.PUBLIC);
        //继承类
        javaController.getSuperClass(superClass());
        //添加注解
        javaController.addAnnotation("@RequestMapping(\"/"+packageFile+"/"+smodel+"\")");
        javaController.addAnnotation("@Controller");
        //添加Sevice
        javaController.addService("\n private Log logger = LogFactory.getLog("+controllerName+".class);");
        javaController.addService("\n @Autowired");
        javaController.addService(" private " + serviceName + " service;");

        //添加查询方法
        javaController.addMethod(queryMethod());
        //添加新增方法
        javaController.addMethod(createMethod());
        //添加修改方法
        javaController.addMethod(updateMethod());
        //添加删除方法
        javaController.addMethod(deleteMethod());

        GeneratedJavaFile generatedJavaFile = new GeneratedJavaFile(javaController,this.targetProject,"UTF-8",context.getJavaFormatter());
        generatedJavaFiles.add(generatedJavaFile);
        return generatedJavaFiles;
    }

    public void setTargetProject(String targetProject) {
        this.targetProject = targetProject;
    }
    public void setProperties(Properties properties) {
        this.properties = properties;
        this.packageFile= properties.get("packageFile").toString();
        this.packageName= properties.get("packageName").toString()+"."+packageFile;
        this.targetProject= properties.get("targetProject").toString();
        this.comment= properties.get("comment").toString();
        String model =properties.get("model").toString();
        this.controllerName= model+"Controller";
        this.serviceName=model+"Service";
        this.bmodel=model;
        this.smodel=model.substring(0,1).toLowerCase()+model.substring(1);
    }

    public  void importPackage(){
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
        FullyQualifiedJavaType imporType11 =new FullyQualifiedJavaType(modePackage+"."+bmodel);


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
    }

    /**
     * 继承类
     * @return
     */
    public FullyQualifiedJavaType superClass(){
        FullyQualifiedJavaType supclass =new FullyQualifiedJavaType("Test");
        return supclass;
    }

    /**
     *  添加 查询 方法
     * @return
     */
    public Method queryMethod(){
           Set<String> readBodyLines = new HashSet<String>();
        Method method =new Method();
        method.addAnnotation("@RequestMapping(value = \"/read.json\")");
        method.addAnnotation("@ResponseBody");
        FullyQualifiedJavaType pajavatype=new FullyQualifiedJavaType("HttpServletRequest");
        Parameter parameter=new Parameter(pajavatype,"request",false);
        FullyQualifiedJavaType returnType=new FullyQualifiedJavaType("GridData");
        method.setReturnType(returnType);
        method.setName("read");
        method.addParameter(parameter);
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addJavaDocLine(" /**\n" +
                "     * 查询"+comment+"列表 \n" +
                "     * @return\n" +
                "     */");
        readBodyLines.add(" Map<String, String> paramMap = WebUtil.parseParamMap(request);\n" +
                        "        Map map =new Page().parseParamMap(paramMap);\n" +
                        "        GridData gridData = GridData.newInstance();\n" +
                        "        String message=\"\";\n" +
                        "        try {\n" +
                        "            //gridData.setData(service.read(map));\n" +
                        "            //gridData.setTotalCount(service.countRead(map));\n" +
                        "        } catch (Exception e) {\n" +
                        "            logger.error(\"查询失败\", e);\n" +
                        "            return gridData;\n" +
                        "        }\n" +
                        "        gridData.setAsSuccess();\n" +
                        "        gridData.setMessage(message);\n" +
                        "        logger.debug(\" read 结束... gridData: \" + gridData);\n" +
                        "        return gridData;"
        );
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
        method.addAnnotation("@RequestMapping(value = \"/create.json\")");
        method.addAnnotation("@YcLog(requestName = \"新增"+comment+"\",owner = \"CRM\")");
        method.addAnnotation("@ResponseBody");


        FullyQualifiedJavaType pajavatype1=new FullyQualifiedJavaType("HttpServletRequest");
        Parameter parameter1=new Parameter(pajavatype1,"request",false);
        method.addParameter(parameter1);

        FullyQualifiedJavaType pajavatype2=new FullyQualifiedJavaType(bmodel);
        Parameter parameter2=new Parameter(pajavatype2,smodel,false);
        parameter2.addAnnotation(" @ModelAttribute ");
        method.addParameter(parameter2);


        FullyQualifiedJavaType returnType=new FullyQualifiedJavaType("ResultData");
        method.setReturnType(returnType);
        method.setName("create");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addJavaDocLine(" /**\n" +
                "     * 新增+" + comment + " \n" +
                "     * @return\n" +
                "     */");
        readBodyLines.add("" +
                " Map<String, String> paramMap = WebUtil.parseParamMap(request);\n" +
                "        ResultData resultData=null;\n" +
                "        try {\n" +
                "            resultData=GridData.newInstance();\n" +
                 "           //service.create("+smodel+");\n"+

                "        } catch (Exception e) {\n" +
                "            logger.error(\"新增"+comment+"\", e);\n" +
                "            e.printStackTrace();\n" +
                "            return resultData;\n" +
                "        }\n" +
                "        return resultData;");
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
        method.addAnnotation("@RequestMapping(value = \"/update.json\")");
        method.addAnnotation("@YcLog(requestName = \"修改"+comment+"\",owner = \"CRM\")");
        method.addAnnotation("@ResponseBody");


        FullyQualifiedJavaType pajavatype1=new FullyQualifiedJavaType("HttpServletRequest");
        Parameter parameter1=new Parameter(pajavatype1,"request",false);
        method.addParameter(parameter1);

        FullyQualifiedJavaType pajavatype2=new FullyQualifiedJavaType(bmodel);
        Parameter parameter2=new Parameter(pajavatype2,smodel,false);
        parameter2.addAnnotation(" @ModelAttribute ");
        method.addParameter(parameter2);


        FullyQualifiedJavaType returnType=new FullyQualifiedJavaType("ResultData");
        method.setReturnType(returnType);
        method.setName("update");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addJavaDocLine(" /**\n" +
                "     * 修改+" + comment + " \n" +
                "     * @return\n" +
                "     */");
        readBodyLines.add("  " +
                "       Map<String, String> paramMap = WebUtil.parseParamMap(request);\n" +
                "        ResultData resultData=null;\n" +
                "        try {\n" +
                "            resultData=GridData.newInstance();\n" +
                "            crmMarketService.deletesActivity(ids, LoginUserHelper.currentUser().getName(), new Long(LoginUserHelper.currentUser().getId()));\n" +
                "        } catch (Exception e) {\n" +
                "            logger.error(\"修改"+comment+"\", e);\n" +
                "            return resultData;\n" +
                "        }\n" +
                "        return resultData;");
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
        method.addAnnotation("@RequestMapping(value = \"/delete.json\")");
        method.addAnnotation("@YcLog(requestName = \"删除"+comment + "\",owner = \"CRM\")");
        method.addAnnotation("@ResponseBody");


        FullyQualifiedJavaType pajavatype1=new FullyQualifiedJavaType("HttpServletRequest");
        Parameter parameter1=new Parameter(pajavatype1,"request",false);
        method.addParameter(parameter1);

        FullyQualifiedJavaType pajavatype2=new FullyQualifiedJavaType("Long[]");
        Parameter parameter2=new Parameter(pajavatype2, "ids", false);
        parameter2.addAnnotation(" @RequestParam(\"ids\") ");
        method.addParameter(parameter2);


        FullyQualifiedJavaType returnType=new FullyQualifiedJavaType("ResultData");
        method.setReturnType(returnType);
        method.setName("delete");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addJavaDocLine(" /**\n" +
                "     * 删除+" + comment + " \n" +
                "     * @return\n" +
                "     */");
        readBodyLines.add("" +
                " Map<String, String> paramMap = WebUtil.parseParamMap(request);\n" +
                "        ResultData resultData=null;\n" +
                "        try {\n" +
                "            resultData=GridData.newInstance();\n" +
                "           //service.delete(ids);\n"+

                "        } catch (Exception e) {\n" +
                "            logger.error(\"删除"+comment+"\", e);\n" +
                "            e.printStackTrace();\n" +
                "            return resultData;\n" +
                "        }\n" +
                "        return resultData;");
        method.addBodyLines(readBodyLines);
        return method;
    }

}
