package com.cainiao.wireless.crashdefend.plugin

import com.android.annotations.NonNull
import com.android.build.api.transform.Context
import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.android.build.api.transform.TransformOutputProvider
import com.cainiao.wireless.crashdefend.plugin.config.DefendConfigReader
import com.cainiao.wireless.crashdefend.plugin.config.domain.DefendConfig
import com.google.common.collect.ImmutableSet
import com.google.common.collect.Sets
import javassist.ClassPool
import javassist.CtClass
import org.apache.commons.io.FileUtils
import org.apache.commons.lang3.StringUtils
import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.api.transform.Transform
import org.gradle.api.logging.Logger

import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

import static com.android.build.api.transform.QualifiedContent.DefaultContentType.CLASSES


/**
 * @author 剑白 2020/10/28
 * */
public class CrashDefendTransform extends Transform  implements Plugin<Project>{

    private Project project
    private Logger logger
    private DefendConfig defendConfig;

    void apply(Project target) {
        project = target
        logger = project.logger
        project.android.registerTransform(this)
        def projectDir = "${project.projectDir}/build/intermediates/transforms";
        boolean  needFullTransform = false;
        //配置文件地址
        String[] configFiles = getConfigFiles();
        for(String configFileName : configFiles){
            File configFile = new File(configFileName);
            File distFile = new File(projectDir + "/" + getName() + "/"+ configFile.getName());
            if(distFile.exists()){
                if(configFile.lastModified() != distFile.lastModified()){
                    needFullTransform = true;
                }
            }
        }

        //删除transform目录，进行全量编译
        if(needFullTransform){
            File file = new File(projectDir);
            FileUtils.deleteDirectory(file)
        }
        log("apply register defend transform plugin success ");
    }

    @Override
    String getName() {
        return "com.cainiao.crashdefend"
    }

    /**
     *  TransformManager.CONTENT_CLASS
     */
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return ImmutableSet.of(CLASSES)
    }

    /**
     *  TransformManager.SCOPE_FULL_PROJECT
     */
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return Sets.immutableEnumSet(
                QualifiedContent.Scope.PROJECT,
                QualifiedContent.Scope.SUB_PROJECTS,
                QualifiedContent.Scope.EXTERNAL_LIBRARIES)
    }

    @Override
    boolean isIncremental() {
        return false
    }


    @Override
    public void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
    }

    @Override
    public void transform(@NonNull TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
            super.transform(transformInvocation)
            initDefendConfig();
            if(defendConfig == null){
                log("defend is off, none defend done for code")
                return
            }
            boolean  needDefend = true;
            if(isDebugTask()){
                if(defendConfig.isDefendOnDebug()){
                    log("<defendOnDebug>true</defendOnDebug> defend on for debug code");
                }else{
                    log("<defendOnDebug>false</defendOnDebug> defend off for debug code");
                    needDefend = false
                }
            }else{
                log("defend on for release code");
            }
            DefendConfig defendConfig = CrashDefendConfig.defendConfig;

            if(defendConfig == null || defendConfig.isDefendOff()){
                log("defend is off, none defend done for code")
                needDefend = false
            }
            def startTime = System.currentTimeMillis()
            try{
                TransformOutputProvider outputProvider = transformInvocation.getOutputProvider();
                outputProvider.deleteAll();
                Collection<TransformInput> transformInputs =  transformInvocation.getInputs();

                File jarFile = outputProvider.getContentLocation("main", getOutputTypes(), getScopes(),
                        Format.JAR);
                if(!jarFile.getParentFile().exists()){
                    jarFile.getParentFile().mkdirs();
                }
                if(jarFile.exists()){
                    jarFile.delete();
                }

                ClassPool classPool = new ClassPool();
                project.android.bootClasspath.each {
                    classPool.appendClassPath((String) it.absolutePath)
                }
                def box = ConvertUtils.toCtClasses(transformInputs, classPool)
                ZipOutputStream outStream = new JarOutputStream(new FileOutputStream(jarFile));
                for (CtClass ctClass : box) {
                    if(needDefend){
                        try{
                            CrashDefendCoder.addTryCatch(ctClass, classPool)
                        }catch(Exception e){
                            log(("defend class exception, class name " + ctClass.getName()))
                            e.printStackTrace();
                        }
                    }
                    zipFile(ctClass.toBytecode(), outStream, ctClass.getName().replaceAll("\\.", "/") + ".class");
                }
                outStream.close();


                def cost = (System.currentTimeMillis() - startTime) / 1000
                log("defend all config class cost $cost second")
            }catch(Exception e2){
                e2.printStackTrace();
            }
    }

    private void log(String message){
        if(logger != null){
            logger.quiet(message)
        }
    }


    protected void zipFile(byte[] classBytesArray, ZipOutputStream zos, String entryName) {
        try {
            ZipEntry entry = new ZipEntry(entryName);
            zos.putNextEntry(entry);
            zos.write(classBytesArray, 0, classBytesArray.length);
            zos.closeEntry();
            zos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String[] getConfigFiles(){
        String [] configFiles = ["${project.projectDir}/${Constants.DEFEND_XML}"];
        try{
            def exitConfig = project.extensions.getByName("ext")
            if(exitConfig && exitConfig.defendConfigFiles){
                def defendConfigFiles = exitConfig.defendConfigFiles;
                ArrayList<String> arrayList = new ArrayList<>();
                for(String splitFile : defendConfigFiles){
                    if(StringUtils.isNotEmpty(StringUtils.trim(splitFile))){
                        arrayList.add(StringUtils.trim(splitFile));
                    }
                }
                if(arrayList.size() > 0){
                    configFiles = new String[arrayList.size()];
                    for(int i=0; i<arrayList.size(); i++){
                        configFiles[i] = "${project.projectDir}/" + arrayList.get(i);
                    }
                }
            }
        }catch(Exception e){}
        return configFiles;
    }

    private void initDefendConfig(){
        String [] configFiles = getConfigFiles();
        def projectDir = "${project.projectDir}/build/intermediates/transforms";
        for(String configFileName : configFiles){
            File configFile = new File(configFileName);
            File distFile = new File(projectDir + "/" + getName() + "/"+ configFile.getName());
            if(!distFile.exists() || (configFile.lastModified() != distFile.lastModified())){
                FileUtils.copyFile(configFile, distFile);
            }
        }
        defendConfig = new DefendConfig();
        for(String configFile : configFiles){
            File file = new File(configFile);
            if(!file.exists()){
                if(logger != null){
                    logger.error("cann't find " + configFile + " in your project")
                    logger.error("please add "+  configFile + " to your project, \n see doc: https://yuque.antfin.com/docs/share/d8e181d2-784a-49b7-9f29-7e829e7987a5")
                }
            }else{
                DefendConfigReader.parseConfig(file.getAbsolutePath(), defendConfig);
            }
        }
        CrashDefendConfig.defendConfig = defendConfig;
    }


    private boolean  isDebugTask(){
        def isDebugTask = false;
        if(project && project.gradle
                && project.gradle.startParameter
                && project.gradle.startParameter.taskNames){
            def taskNames = project.gradle.startParameter.taskNames
            for (int index = 0; index < taskNames.size(); ++index) {
                def taskName = taskNames[index]
                if (taskName.endsWith("Debug") && taskName.contains("Debug")) {
                    isDebugTask = true
                    break;
                }
            }
        }
        return isDebugTask;
    }
}