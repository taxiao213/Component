package com.example.compilea;

import com.component.annotationa.ARouter2;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.reflect.Type;
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
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * 用javapoet 写法{https://github.com/square/javapoet}
 * Created by A35 on 2020/2/27
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.component.annotationa.ARouter2"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedOptions("content")
public class ArouterProcessor extends AbstractProcessor {
    Elements elementsUtils;
    Types typesUtils;
    Filer filer;
    Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementsUtils = processingEnv.getElementUtils();
        typesUtils = processingEnv.getTypeUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        String content = processingEnv.getOptions().get("content");
        messager.printMessage(Diagnostic.Kind.NOTE, " 传的值 " + content);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) return false;
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(ARouter2.class);
        for (Element element : elementsAnnotatedWith) {
            //  获取包名
            String packageName = elementsUtils.getPackageOf(element).getQualifiedName().toString();
            //  获取类名
            String className = element.getSimpleName().toString();
            //  最终要生成的类名
            String finalClassName = className + "$$ARouter2";
            ARouter2 router2 = element.getAnnotation(ARouter2.class);
            String path = router2.path();
            messager.printMessage(Diagnostic.Kind.NOTE, "被注解的类有：" + className);

            // 高级写法，javapoet构建工具，参考（https://github.com/JakeWharton/butterknife）
            // https://github.com/square/javapoet
            MethodSpec today = MethodSpec.methodBuilder("findClass")    // 方法名
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(Class.class)   // 返回值Class<?>
                    .addParameter(String.class, "pathName") // 参数(String pathName)
                    // 方法内容拼接：
                    // return path.equals("/app/MainActivity") ? MainActivity.class : null
                    .addStatement("return pathName.equals($S) ? $T.class : null", path, ClassName.get(element.asType()))
                    .build();
            TypeSpec classNa = TypeSpec.classBuilder(finalClassName)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(today)
                    .build();
            // 在指定的包名下，生成Java类文件
            JavaFile javaFile = JavaFile.builder(packageName, classNa)
                    .build();
            try {
                javaFile.writeTo(filer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
