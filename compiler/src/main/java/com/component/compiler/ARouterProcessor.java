package com.component.compiler;

import com.component.annotation.ARouter;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;


/**
 * Annotation Processing Tool
 * 注解处理器
 * AutoService则是固定的写法，加个注解即可
 * 通过auto-service中的@AutoService可以自动生成AutoService注解处理器，用来注册
 * 用来生成 META-INF/services/javax.annotation.processing.Processor 文件
 * Created by A35 on 2020/2/27
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
@AutoService(Processor.class)
//  允许/支持的注解类型，让注解处理器处理（新增annotation module） 对应方法 getSupportedAnnotationTypes
@SupportedAnnotationTypes({"com.component.annotation.ARouter"})
// 指定JDK编译版本 对应方法 getSupportedSourceVersion
@SupportedSourceVersion(SourceVersion.RELEASE_7)
// Android 和 Java 交互 注解处理器接收的参数  对应方法 getSupportedOptions
@SupportedOptions("content")
public class ARouterProcessor extends AbstractProcessor {
    //  操作Elements 工具类
    private Elements elementUtils;
    //  type(类信息)工具类
    private Types typeUtils;
    //  用来输出日志
    private Messager messager;
    //  生成的文件
    private Filer filer;
    /*
     需要重写的方法 可以用注解代替
     @Override
     public Set<String> getSupportedAnnotationTypes() {
         return super.getSupportedAnnotationTypes();
     }

     @Override
     public SourceVersion getSupportedSourceVersion() {
         return super.getSupportedSourceVersion();
     }

     @Override
     public Set<String> getSupportedOptions() {
         return super.getSupportedOptions();
     }*/

    // 该方法主要用于一些初始化的操作，通过该方法的参数ProcessingEnvironment可以获取一些列有用的工具类
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
        typeUtils = processingEnv.getTypeUtils();
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
        String content = processingEnv.getOptions().get("content");
        //  有坑：Diagnostic.Kind.ERROR，异常会自动结束，不像安卓中Log.e那么好使
        messager.printMessage(Diagnostic.Kind.NOTE, content);
    }

    /**
     * 相当于main函数，开始处理注解
     * 注解处理器的核心方法，处理具体的注解，生成Java文件
     *
     * @param annotations 使用了支持处理注解的节点集合（类 上面写了注解）
     * @param roundEnv    当前或是之前的运行环境,可以通过该对象查找找到的注解。
     * @return true 表示后续处理器不会再处理（已经处理完成）
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) return false;
        //  获取项目中所有使用ARouter节点
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(ARouter.class);
        for (Element element : elementsAnnotatedWith) {
            //  类节点之上 是包节点
            String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
            //  获取简单类名
            String className = element.getSimpleName().toString();
            messager.printMessage(Diagnostic.Kind.NOTE, "被注解的类有：" + className);
            messager.printMessage(Diagnostic.Kind.NOTE, "包名：" + packageName);
            //  最终生成类 eg：MainActivity$$ARouter
            String finalClassName = className + "$$ARouter";
            try {
                JavaFileObject sourceFile = filer.createSourceFile(packageName + "." + finalClassName);
                Writer writer = sourceFile.openWriter();
                writer.append("package ").append(packageName).append(";\n");
                writer.append("public class ").append(finalClassName).append(" {" + "\n");
                writer.append("public static Class<?> findClass(String pathName){" + "\n");
                ARouter annotation = element.getAnnotation(ARouter.class);
                String path = annotation.path();
                writer.append("if (pathName.equalsIgnoreCase(\"").append(path).append("\")){").append("\n");
                writer.append("return MainActivity.class;" + "\n");
                writer.append("}" + "\n");
                writer.append("return null;" + "\n");
                writer.append("}" + "\n");
                writer.append("}" + "\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
