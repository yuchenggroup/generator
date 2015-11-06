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
package org.mybatis.generator.api;

import org.mybatis.generator.api.dom.extjs.*;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.codegen.AbstractExtjsGenerator;
import org.mybatis.generator.codegen.mybatis3.extjs.ExtjsControllerGenerator;
import org.mybatis.generator.codegen.mybatis3.extjs.ExtjsGridGenerator;
import org.mybatis.generator.codegen.mybatis3.extjs.ExtjsModelGenerator;
import org.mybatis.generator.codegen.mybatis3.extjs.ExtjsStoreGenerator;
import org.mybatis.generator.config.ExtjsGeneratorConfiguration;

import java.util.List;

/**
 * @author Jeff Butler
 */
public class GeneratedExtjsFile extends GeneratedFile {
    private CompilationUnit compilationUnit;
    private String fileEncoding;
    private ExtjsFormatter extjsFormatter;

    private AbstractExtjsGenerator generator;
    private ExtjsGeneratorConfiguration extjsGeneratorConfiguration;

    /**
     * Default constructor
     */
    public GeneratedExtjsFile(CompilationUnit compilationUnit,
                              String targetProject,
                              String fileEncoding,
                              ExtjsFormatter extjsFormatter) {
        super(targetProject);
        this.compilationUnit = compilationUnit;
        this.fileEncoding = fileEncoding;
        this.extjsFormatter = extjsFormatter;
    }

    public GeneratedExtjsFile(CompilationUnit compilationUnit,
                              String targetProject,
                              ExtjsFormatter extjsFormatter) {
        this(compilationUnit, targetProject, null, extjsFormatter);
        //
    }

    public GeneratedExtjsFile(AbstractExtjsGenerator generator,
                              CompilationUnit compilationUnit,
                              ExtjsGeneratorConfiguration extjsGeneratorConfiguration, String property, ExtjsFormatter extjsFormatter) {
        this( compilationUnit,  extjsGeneratorConfiguration.getTargetProject(),  property,  extjsFormatter);
        //
        this.generator = generator;
        this.extjsGeneratorConfiguration = extjsGeneratorConfiguration;
    }

    @Override
    public String getFormattedContent() {
        // TODO 在此处使用生成
        ExtClass moduleClass = getModuleCLass();
        if(null != moduleClass){
            return  moduleClass.getFormattedContent(0);
        }
        //
        return extjsFormatter.getFormattedContent(compilationUnit,this, generator, extjsGeneratorConfiguration);
    }

    private ExtClass getModuleCLass(){
        ExtClass moduleClass = null;
        //
        if( compilationUnit instanceof InnerClass){
            InnerClass innerClass = (InnerClass)compilationUnit;
            //
            List<Field> fields = innerClass.getFields();
            //
            if(generator instanceof ExtjsModelGenerator){
                moduleClass = new ExtModelClass(fields);
            }else if(generator instanceof ExtjsStoreGenerator){
                moduleClass = new ExtStoreClass(fields);
            }else if(generator instanceof ExtjsGridGenerator){
                moduleClass = new ExtGridClass(fields);
            }else if(generator instanceof ExtjsControllerGenerator){
                moduleClass = new ExtControllerClass(fields);
            }
            //
            String appName = extjsGeneratorConfiguration.getAppName();
            String shortPackage = extjsGeneratorConfiguration.getTargetPackage();
            String modelName = compilationUnit.getType().getShortName();
            //
            if(null != moduleClass){
                moduleClass.setAppName(appName);
                moduleClass.setShortPackage(shortPackage);
                moduleClass.setModelName(modelName);
            }
        }

        //
        return moduleClass;
    }

    @Override
    public String getFileName() {
        // 这里其实不太友好
        String append = "";
        //
        if(generator instanceof ExtjsModelGenerator){
            append = "";
        }else if(generator instanceof ExtjsStoreGenerator){
            append = "Store";
        }else if(generator instanceof ExtjsGridGenerator){
            append = "Grid";
        }else if(generator instanceof ExtjsControllerGenerator){
            append = "Controller";
        }else{
            append = "";
        }
        //
        return compilationUnit.getType().getShortName() + append + ".js"; //$NON-NLS-1$
    }



    public String getTargetPackage() {
        // TODO
        String pakkage = "";
        if(null != extjsGeneratorConfiguration){

             String targetPackage = extjsGeneratorConfiguration.getTargetPackage();
             //String targetProject = extjsGeneratorConfiguration.getTargetProject();
             //String appName = extjsGeneratorConfiguration.getAppName();
            // appName + "." +
            pakkage =  getSubPackage() + "." + targetPackage;
            return pakkage;
        }

        return compilationUnit.getType().getPackageName();
    }

    private String getSubPackage(){
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
        return module.toLowerCase();
    }

    /**
     * This method is required by the Eclipse Java merger. If you are not
     * running in Eclipse, or some other system that implements the Java merge
     * function, you may return null from this method.
     * 
     * @return the CompilationUnit associated with this file, or null if the
     *         file is not mergeable.
     */
    public CompilationUnit getCompilationUnit() {
        return compilationUnit;
    }

    /**
     * A Java file is mergeable if the getCompilationUnit() method returns a
     * valid compilation unit.
     * 
     */
    @Override
    public boolean isMergeable() {
        return true;
    }

    public String getFileEncoding() {
        return fileEncoding;
    }
}
