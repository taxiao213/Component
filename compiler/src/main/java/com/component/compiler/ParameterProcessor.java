package com.component.compiler;

import com.component.annotation.ARouterBean;
import com.component.annotation.Parameter;
import com.component.compiler.utils.Constant;
import com.component.compiler.utils.EmptyUtils;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Constants;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;


/**
 * 属性注解处理器
 * Created by A35 on 2020/3/5
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
@AutoService(Processor.class)
//  允许/支持的注解类型，让注解处理器处理（新增annotation module） 对应方法 getSupportedAnnotationTypes
@SupportedAnnotationTypes({"com.component.annotation.Parameter"})
//  指定JDK编译版本 对应方法 getSupportedSourceVersion
@SupportedSourceVersion(SourceVersion.RELEASE_7)
//  Android 和 Java 交互 注解处理器接收的参数  对应方法 getSupportedOptions
@SupportedOptions({Constant.MODULE_NAME, Constant.PACKAGE_NAME_FOR_APT})
public class ParameterProcessor extends AbstractProcessor {

    //  打印日志
    private Messager messager;
    //  操作Elements 工具类
    private Elements elementUtils;
    //  type(类信息)工具类
    private Types typeUtils;
    //  生成的文件
    private Filer filer;
    //  项目名称
    private String moduleName;
    //  生成的apt文件路径 包名
    private String packageNameForApt;
    //  存放类对应的属性值   key:TypeElement,value:List<Element>
    private HashMap<TypeElement, List<Element>> tempTypeMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
        typeUtils = processingEnv.getTypeUtils();
        Map<String, String> options = processingEnv.getOptions();
        if (!EmptyUtils.isEmpty(options)) {
            moduleName = options.get(Constant.MODULE_NAME);
//            packageNameForApt = options.get(Constant.PACKAGE_NAME_FOR_APT);
            messager.printMessage(Diagnostic.Kind.NOTE, " moduleName >> " + moduleName + " packageNameForApt >> " + packageNameForApt);
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (EmptyUtils.isEmpty(annotations)) return false;
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(Parameter.class);
        if (!EmptyUtils.isEmpty(elementsAnnotatedWith)) {
            // 赋值临时map存储，用来存放被注解的属性集合
            valueOfParameterMap(elementsAnnotatedWith);
            // 生成类文件
            createParameterFile();
            return true;
        }
        return false;
    }

    /**
     * 生成类文件
     * eg: Parameter$$XActivity
     */
    private void createParameterFile() {
        if (EmptyUtils.isEmpty(tempTypeMap)) return;
        //  通过Elements工具类 获取Parameter类型
        TypeElement activityElement = elementUtils.getTypeElement(Constant.ACTIVITY);
        TypeElement callElement = elementUtils.getTypeElement(Constant.CALL);
        TypeElement paramenterElement = elementUtils.getTypeElement(Constant.PARAMETER_PATH);

        messager.printMessage(Diagnostic.Kind.NOTE, "开始生成类文件");
        Set<Map.Entry<TypeElement, List<Element>>> entries = tempTypeMap.entrySet();
        for (Map.Entry<TypeElement, List<Element>> entry : entries) {
            // Map集合中的key是类名，如：MainActivity
            TypeElement element = entry.getKey();
            List<Element> value = entry.getValue();
            // 如果类名的类型和Activity类型不匹配
            if (!typeUtils.isSubtype(element.asType(), activityElement.asType()) && !typeUtils.isSubtype(element.asType(), callElement.asType())) {
                messager.printMessage(Diagnostic.Kind.NOTE, "@Parameter该注解目前只能用于Activity之上");
                throw new RuntimeException("@Parameter该注解目前只能用于Activity之上");
            }
            ClassName className = ClassName.get(element);
            String finallClassName = Constant.APT_NAME_PREFIX_PARAMETER + className.simpleName();
//            ParameterizedTypeName type = ParameterizedTypeName.get(ClassName.get(element));
            MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder(Constant.PARAMETER_METHOD_NAME)
                    .addAnnotation(Override.class)//    重写注解
                    .addModifiers(Modifier.PUBLIC)// public修饰符
                    .returns(void.class)//    方法返回值
                    .addParameter(Object.class, Constant.PARAMETER_METHOD_NAME2);//   参数
//             ParameterActivity mainActivity = (ParameterActivity) object;
            methodSpecBuilder.addStatement("$T $N = ($T)$N", className, Constant.PARAMETER_METHOD_NAME3, className, Constant.PARAMETER_METHOD_NAME2);
            if (value != null && value.size() > 0) {
                //  循环
//                mainActivity.name = mainActivity.getIntent().getStringExtra("name");
//                mainActivity.age = mainActivity.getIntent().getIntExtra("age", mainActivity.age);
                for (Element element1 : value) {
                    Parameter annotation = element1.getAnnotation(Parameter.class);
                    // 遍历注解的属性节点 生成函数体
                    TypeMirror typeMirror = element1.asType();
                    // 获取 TypeKind 枚举类型的序列号
                    int type = typeMirror.getKind().ordinal();
                    // 获取属性名
                    String fieldName = element1.getSimpleName().toString();
                    messager.printMessage(Diagnostic.Kind.NOTE, " >>> fieldName " + fieldName);
                    // 获取属性值
                    String annotationValue = annotation.name();
                    messager.printMessage(Diagnostic.Kind.NOTE, " >>> annotationValue " + annotationValue);
                    String finalValue = "$N." + fieldName;
                    // 判断注解的值为空的情况下的处理（注解中有name值就用注解值）
                    annotationValue = EmptyUtils.isEmpty(annotationValue) ? fieldName : annotationValue;
                    // 最终拼接的前缀：
                    // t.s = t.getIntent().
                    String methodContent = finalValue + " = $N.getIntent().";
                    boolean isString = false;//是否是String类型
                    // TypeKind 枚举类型不包含String
                    if (type == TypeKind.INT.ordinal()) {
                        // t.s = t.getIntent().getIntExtra("age", t.age);
                        methodContent += "getIntExtra($S, " + finalValue + ")";
                    } else if (type == TypeKind.BOOLEAN.ordinal()) {
                        // t.s = t.getIntent().getBooleanExtra("isSuccess", t.age);
                        methodContent += "getBooleanExtra($S, " + finalValue + ")";
                    } else {
                        messager.printMessage(Diagnostic.Kind.NOTE, " >>> typeMirror " + typeMirror + " callElement " + callElement.asType().toString());
                        // t.s = t.getIntent.getStringExtra("s");
                        if (typeMirror.toString().equalsIgnoreCase(Constants.STRING)) {
                            methodContent += "getStringExtra($S)";
                            isString = true;
                        } else if (typeUtils.isSubtype(typeMirror, callElement.asType())) {
                            messager.printMessage(Diagnostic.Kind.NOTE, " >>> isSubtype) ");
                            // t.s = ARouterManager.getInstance().build("/componentA/ComponentA_MainActivity").navigation(this);
                            methodContent = finalValue + " = ($T)$T.getInstance().build($S).navigation($N)";
                        }
                    }
                    messager.printMessage(Diagnostic.Kind.NOTE, " >>> methodContent " + methodContent);
                    // 健壮代码
                    if (methodContent.endsWith(")")) {
                        // 添加最终拼接方法内容语句
                        if (typeUtils.isSubtype(typeMirror, callElement.asType())) {
                            // pathMap.put("/componenta/getDrawable", ARouterBean.create(ARouterBean.Type.CALL,ComponentADrawableImpl.class,"/componenta/getDrawable","componenta"));
                            // t.s = ARouterManager.getInstance().build("/componentA/ComponentA_MainActivity").navigation(this);
                            // $N.drawable = ($T)$T.getInstance().build($S).navigation($N)
                            // MainActivity activity = (MainActivity)object;
                            // activity.drawable = (ComponentADrawable)ARouterManager.getInstance().build("/componentA/getDrawable").navigation(activity);
                            methodSpecBuilder.addStatement(methodContent, Constant.PARAMETER_METHOD_NAME3, ClassName.get(typeMirror), ClassName.get(Constant.BASE_PACKAGE, Constant.AROUTER_MANAGER), annotationValue, Constant.PARAMETER_METHOD_NAME3);
                        } else if (isString) {
                            methodSpecBuilder.addStatement(methodContent, Constant.PARAMETER_METHOD_NAME3, Constant.PARAMETER_METHOD_NAME3, annotationValue);
                        } else {
                            methodSpecBuilder.addStatement(methodContent, Constant.PARAMETER_METHOD_NAME3, Constant.PARAMETER_METHOD_NAME3, annotationValue, Constant.PARAMETER_METHOD_NAME3);
                        }
                    } else {
                        messager.printMessage(Diagnostic.Kind.ERROR, "目前暂支持String、int、boolean传参");
                    }
                }
            }
            MethodSpec methodSpec = methodSpecBuilder.build();
            TypeSpec typeSpec = TypeSpec.classBuilder(finallClassName)//    类名
                    .addSuperinterface(ClassName.get(paramenterElement))// 实现ParameterLoad接口
                    .addModifiers(Modifier.PUBLIC)// public修饰符
                    .addMethod(methodSpec)
                    .build();
            JavaFile javaFile = JavaFile.builder(packageNameForApt, typeSpec).build();
            try {
                javaFile.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 赋值临时map存储，用来存放被@Parameter注解的属性集合，生成类文件时遍历
     *
     * @param elements 被 @Parameter 注解的 元素集合
     */
    private void valueOfParameterMap(Set<? extends Element> elements) {
        for (Element element : elements) {
            //  注解在属性之上，属性节点父节点是类节点
            TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
            packageNameForApt = enclosingElement.getEnclosingElement().toString();

            if (tempTypeMap.containsKey(enclosingElement)) {
                tempTypeMap.get(enclosingElement).add(element);
            } else {
                ArrayList<Element> list = new ArrayList<>();
                list.add(element);
                tempTypeMap.put(enclosingElement, list);
            }
        }
    }
}
