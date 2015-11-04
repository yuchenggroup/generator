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
package org.mybatis.generator.api.dom;

import org.mybatis.generator.api.ExtjsFormatter;
import org.mybatis.generator.api.GeneratedExtjsFile;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.codegen.AbstractExtjsGenerator;
import org.mybatis.generator.codegen.mybatis3.extjs.ExtjsControllerGenerator;
import org.mybatis.generator.codegen.mybatis3.extjs.ExtjsGridGenerator;
import org.mybatis.generator.codegen.mybatis3.extjs.ExtjsModelGenerator;
import org.mybatis.generator.codegen.mybatis3.extjs.ExtjsStoreGenerator;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.ExtjsGeneratorConfiguration;

import java.util.List;

/**
 * EXTJS 内容的默认实现
 *
 */
public class DefaultExtjsFormatter implements ExtjsFormatter {
    protected Context context;

    public void setContext(Context context) {
        this.context = context;
    }
    
    public String getFormattedContent(CompilationUnit compilationUnit) {
        //
        //
        return "extjs-file-content" + ";TODO 需要自己实现" + this.getClass() + ".getFormattedContent()!";
    }

    @Override
    public String getFormattedContent(CompilationUnit compilationUnit,
                                      GeneratedExtjsFile generatedExtjsFile,
                                      AbstractExtjsGenerator generator,
                                      ExtjsGeneratorConfiguration extjsGeneratorConfiguration) {
        if(null == generatedExtjsFile
                || null == generator
                || null == extjsGeneratorConfiguration
                ){
            return  getFormattedContent(compilationUnit);
        }
        // TODO 需要自己实现
        String moduleName = getModuleName(compilationUnit, generator, extjsGeneratorConfiguration);
        //
        if( compilationUnit instanceof InnerClass){

            InnerClass innerClass = (InnerClass)compilationUnit;
            //
            List<Field> fields = innerClass.getFields();

        }

        //
        return "Ext.define('" + moduleName + "', {" + "\n" +
                "\textends : ''" +

                "});";
    }


    public String getModuleName(CompilationUnit compilationUnit,AbstractExtjsGenerator generator,
                              ExtjsGeneratorConfiguration extjsGeneratorConfiguration) {
        //
        String pakkage = getPackage(compilationUnit, generator, extjsGeneratorConfiguration);
        String append = getAppend(generator);
        //
        String moduleName = pakkage + "."+ compilationUnit.getType().getShortName() + append;

        //
        return moduleName; //$NON-NLS-1$
    }


    private String getAppend(AbstractExtjsGenerator generator){
        String append = getSubPackage(generator);
        //
        if(generator instanceof ExtjsModelGenerator){
            append = "";
        } else if(generator instanceof ExtjsGridGenerator){
            append = "Grid";
        }
        return append;
    }

    public String getPackage(CompilationUnit compilationUnit,AbstractExtjsGenerator generator,
                                   ExtjsGeneratorConfiguration extjsGeneratorConfiguration) {
        // TODO
        String pakkage = "";
        if(null != extjsGeneratorConfiguration){

            String targetPackage = extjsGeneratorConfiguration.getTargetPackage();
            //String targetProject = extjsGeneratorConfiguration.getTargetProject();
            String appName = extjsGeneratorConfiguration.getAppName();
            //
            pakkage =  appName + "." + getSubPackage(generator).toLowerCase() + "." + targetPackage;
            return pakkage;
        }

        return compilationUnit.getType().getPackageName();
    }

    private String getSubPackage(AbstractExtjsGenerator generator){
        String module = "";
        //
        if(generator instanceof ExtjsModelGenerator){
            module = "Model";
        }else if(generator instanceof ExtjsStoreGenerator){
            module = "Store";
        }else if(generator instanceof ExtjsGridGenerator){
            module = "View";
        }else if(generator instanceof ExtjsControllerGenerator){
            module = "Controller";
        }else{
            module = "";
        }
        return module;
    }

    public static class Store {

        private List<Field> fields;
    }
}
