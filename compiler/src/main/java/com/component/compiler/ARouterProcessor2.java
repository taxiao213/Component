package com.component.compiler;

import com.component.annotation.ARouter2;
import com.component.annotation.ARouterBean;
import com.component.compiler.utils.Constant;
import com.component.compiler.utils.EmptyUtils;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

import java.io.IOException;
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
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.print.DocFlavor;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import sun.reflect.generics.reflectiveObjects.WildcardTypeImpl;


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
@SupportedAnnotationTypes({"com.component.annotation.ARouter2"})
// 指定JDK编译版本 对应方法 getSupportedSourceVersion
@SupportedSourceVersion(SourceVersion.RELEASE_7)
// Android 和 Java 交互 注解处理器接收的参数  对应方法 getSupportedOptions
@SupportedOptions({Constant.MODULE_NAME, Constant.PACKAGE_NAME_FOR_APT})
public class ARouterProcessor2 extends AbstractProcessor {
    //  操作Elements 工具类
    private Elements elementUtils;
    //  type(类信息)工具类
    private Types typeUtils;
    //  用来输出日志
    private Messager messager;
    //  生成的文件
    private Filer filer;
    //  生成的apt文件路径 包名
    private String packageNameForApt;
    //  项目名称
    private String moduleName;

    // 临时map存储，用来存放路由组Group对应的详细Path类对象，生成路由路径类文件时遍历
    // key:组名"app", value:"app"组的路由路径"ARouter$$Path$$app.class"
    private Map<String, List<ARouterBean>> tempPathMap = new HashMap<>();
    // 临时map存储，用来存放路由Group信息，生成路由组类文件时遍历
    // key:组名"app", value:类名"ARouter$$Path$$app.class"
    private Map<String, String> tempGroupMap = new HashMap<>();

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

        Map<String, String> options = processingEnv.getOptions();
        if (!EmptyUtils.isEmpty(options)) {
            moduleName = options.get(Constant.MODULE_NAME);
            packageNameForApt = options.get(Constant.PACKAGE_NAME_FOR_APT);
            //  有坑：Diagnostic.Kind.ERROR，异常会自动结束，不像安卓中Log.e那么好使
            messager.printMessage(Diagnostic.Kind.NOTE, moduleName + " packageNameForApt " + packageNameForApt);
        }
        if (EmptyUtils.isEmpty(moduleName) || EmptyUtils.isEmpty(packageNameForApt)) {
            throw new RuntimeException("注解处理器需要的参数moduleName或者packageNameForApt为空，请在对应build.gradle配置参数");
        }
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
        if (!EmptyUtils.isEmpty(annotations)) {
            //  获取项目中所有使用ARouter节点
            Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(ARouter2.class);
            if (!EmptyUtils.isEmpty(elementsAnnotatedWith)) {
                try {
                    parseElements(elementsAnnotatedWith);
                    return true;
                } catch (Exception e) {
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 解析所有被  @ARouter 注解的 类元素集合
     *
     * @param elementsAnnotatedWith
     */
    private void parseElements(Set<? extends Element> elementsAnnotatedWith) {
        // 通过Element工具类，获取Activity、Callback类型
        TypeElement activityElement = elementUtils.getTypeElement(Constant.ACTIVITY);
        // 显示类信息（获取被注解节点，类节点）这里也叫自描述 Mirror
        TypeMirror activityMirror = activityElement.asType();
        for (Element element : elementsAnnotatedWith) {
            //  获取每个元素类信息，用于比较
            TypeMirror typeMirror = element.asType();
            messager.printMessage(Diagnostic.Kind.NOTE, "被注解的类有：" + typeMirror.toString());
            //  获取注解的值
            ARouter2 aRouter2 = element.getAnnotation(ARouter2.class);
            String path = aRouter2.path();
            String groupName = aRouter2.groupName();
            //路由详细信息封装
            ARouterBean bean = new ARouterBean.Builder()
                    .setPath(path)
                    .setGroupName(groupName)
                    .setElement(element)
                    .build();
            // 高级判断：ARouter注解仅能用在类之上，并且是规定的Activity
            // 类型工具类方法isSubtype，相当于instance一样
            if (typeUtils.isSubtype(typeMirror, activityMirror)) {
                bean.setType(ARouterBean.Type.ACTIVITY);
            } else {
                //  不匹配抛出异常，谨慎使用！考虑维护问题
                throw new RuntimeException("@ARouter注解目前仅限用于Activity类之上");
            }
            // 赋值临时map存储，用来存放路由组Group对应的详细Path类对象
            valueOfPathMap(bean);
        }
        // routerMap遍历后，用来生成类文件
        // 获取ARouterLoadGroup、ARouterLoadPath类型（生成类文件需要实现的接口）
        TypeElement groupElement = elementUtils.getTypeElement(Constant.AROUTE_GROUP);
        TypeElement pathElement = elementUtils.getTypeElement(Constant.AROUTE_PATH);
        //  最终生成类 eg：ARouter$$Group$$componentA
        // 第一步：生成路由组Group对应详细Path类文件，如：ARouter$$Path$$app
        createPath(pathElement);
        // 第二步：生成路由组Group类文件（没有第一步，取不到类文件），如：ARouter$$Group$$app
        createGroup(pathElement, groupElement);
    }

    /**
     * 赋值临时map存储，用来存放路由组Group对应的详细Path类对象，生成路由路径类文件时遍历
     *
     * @param bean 路由详细信息，最终实体封装类
     */
    private void valueOfPathMap(ARouterBean bean) {
        if (checkOfPath(bean)) {
            messager.printMessage(Diagnostic.Kind.NOTE, "RouterBean >>> " + bean.toString());
            String groupName = bean.getGroupName();
            List<ARouterBean> routerBeans = tempPathMap.get(groupName);
            if (EmptyUtils.isEmpty(routerBeans)) {
                routerBeans = new ArrayList<>();
                routerBeans.add(bean);
                tempPathMap.put(groupName, routerBeans);
            } else {
                routerBeans.add(bean);
            }
        } else {
            messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter注解未按规范配置，如：/app/MainActivity");
        }
    }

    /**
     * 检查路径是否规范
     *
     * @param bean
     * @return
     */
    private boolean checkOfPath(ARouterBean bean) {
        String path = bean.getPath();
        String groupName = bean.getGroupName();
        //  eg: /app/MainActivity
        if (EmptyUtils.isEmpty(path) || !path.startsWith(Constant.SPLITTER)) {
            messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter注解中的path值，必须要以 / 开头");
            return false;
        }
        // 比如开发者代码为：path = "/MainActivity"，最后一个 / 符号必然在字符串第1位
        if (path.lastIndexOf(Constant.SPLITTER) == 0) {
            // 架构师定义规范，让开发者遵循
            messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter注解未按规范配置，如：/app/MainActivity");
            return false;
        }
        // 从第一个 / 到第二个 / 中间截取，如：/app/MainActivity 截取出 app 作为group
        String finalGroup = path.substring(1, path.indexOf(Constant.SPLITTER, 1));
        // @ARouter注解中的group有赋值情况
        if (!EmptyUtils.isEmpty(groupName) && !groupName.equals(moduleName)) {
            // 架构师定义规范，让开发者遵循
            messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter注解中的group值必须和子模块名一致！");
            return false;
        } else {
            bean.setGroupName(finalGroup);
            return true;
        }
    }

    /**
     * 创建path 路径
     *
     * @param element
     */
    private void createPath(TypeElement element) {
        if (!EmptyUtils.isEmpty(tempPathMap)) {
            Set<Map.Entry<String, List<ARouterBean>>> entries = tempPathMap.entrySet();
            ClassName map = ClassName.get(HashMap.class);
            ClassName string = ClassName.get(String.class);
            ClassName arouter = ClassName.get(ARouterBean.class);
            //  参数类型
            ParameterizedTypeName typeName = ParameterizedTypeName.
                    get(map,// map
                            string,//  map<String,
                            arouter);//    map<String,ARouterBean>

            //  遍历 每一个分组创建一个文件
            for (Map.Entry<String, List<ARouterBean>> entry : entries) {
                String key = entry.getKey();
                List<ARouterBean> value = entry.getValue();
                String finalClassName = Constant.APT_NAME_PREFIX_PATH + key;
                try {
                    //  重写接口方法，返回一个hashMap 参照\app\src\main\java\com\yin\component\test包下
                    MethodSpec.Builder method = MethodSpec.methodBuilder(Constant.PATH_METHOD_NAME)
                            .addAnnotation(Override.class)//    重写注解
                            .returns(typeName)//    方法返回值
                            .addModifiers(Modifier.PUBLIC);// public修饰符

                    method.addStatement("$T <$T,$T> $N = new $T<>()", map, string, arouter, Constant.PATH_PARAMETER_NAME, map);
                    if (value != null && value.size() > 0) {
//                        ARouterBean aroutBean = ARouterBean.create(ARouterBean.Type.ACTIVITY,
//                        ComponentA_MainActivity.class,
//                        "/componentA/ComponentA_MainActivity",
//                        "componentA");
//                        map.put("/componentA/ComponentA_MainActivity", aroutBean);

                        for (ARouterBean aRouterBean : value) {
                            String path = aRouterBean.getPath();
                            method.addStatement("$N.put($S,$T.create($T.$L,$T.class,$S,$S))",//    $T.$L 类型的自变量
                                    Constant.PATH_PARAMETER_NAME,//  map.put
                                    path,// componentA/ComponentA_MainActivity
                                    arouter,//  ARouterBean
                                    ClassName.get(ARouterBean.Type.class),//    ARouterBean.Type.ACTIVITY  $T.$L 类型的自变量
                                    aRouterBean.getType(),//    ARouterBean.Type.ACTIVITY  $T.$L 类型的自变量
                                    ClassName.get((TypeElement) aRouterBean.getElement()),// ComponentA_MainActivity.class
                                    path,// "/componentA/ComponentA_MainActivity",
                                    aRouterBean.getGroupName()// componentA
                            );
                        }
                    }

                    method.addStatement("return $N", Constant.PATH_PARAMETER_NAME);
                    messager.printMessage(Diagnostic.Kind.NOTE, "APT生成路由Path类文件：" + packageNameForApt + "." + finalClassName);
                    //  类型
                    TypeSpec clazz = TypeSpec.classBuilder(finalClassName)//    类名
                            .addSuperinterface(ClassName.get(element))// 实现ARouterLoadPath接口
                            .addModifiers(Modifier.PUBLIC)// public修饰符
                            .addMethod(method.build())// 方法的构建（方法参数 + 方法体）
                            .build();// 类构建完成
                    JavaFile file = JavaFile.builder(packageNameForApt, clazz).build();
                    file.writeTo(filer);// 文件生成器开始生成类文件
                    tempGroupMap.put(key, finalClassName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 创建group 路径
     */
    private void createGroup(TypeElement pathElement, TypeElement groupElement) {
        if (pathElement != null && groupElement != null && !EmptyUtils.isEmpty(tempGroupMap)) {
            Set<Map.Entry<String, String>> entries = tempGroupMap.entrySet();

//            HashMap<String, Class<? extends ARouterLoadPath>>
            ClassName map = ClassName.get(HashMap.class);
            ClassName string = ClassName.get(String.class);
            // 第二个参数：Class<? extends ARouterLoadPath>
            // 某某Class是否属于ARouterLoadPath接口的实现类
            ParameterizedTypeName clazzParame = ParameterizedTypeName.get(ClassName.get(Class.class),
                    WildcardTypeName.subtypeOf(ClassName.get(pathElement)));
            ParameterizedTypeName typeName = ParameterizedTypeName.get(map, // Map
                    string,  // Map<String
                    clazzParame
            );
            try {
                for (Map.Entry<String, String> entry : entries) {
                    String key = entry.getKey();
                    String classPathName = entry.getValue();
                    //  ARouter$$Group$$componentA
                    String finalClassName = Constant.APT_NAME_PREFIX_GROUP + key;
                    MethodSpec.Builder method = MethodSpec.methodBuilder(Constant.GROUP_METHOD_NAME)
                            .addAnnotation(Override.class)//    重写注解
                            .returns(typeName)//    方法返回值
                            .addModifiers(Modifier.PUBLIC);// public修饰符
                    //  HashMap<String, Class<? extends ARouterLoadPath>> map = new HashMap<>();
                    method.addStatement("$T <$T,$T> $N = new $T<>()", map, string, clazzParame, Constant.GROUP_PARAMETER_NAME, map);
                    //  map.put("componentA", ARouter$$Path$$componentA.class); 容易出错
                    method.addStatement("$N.put($S,$T.class)", Constant.GROUP_PARAMETER_NAME, key, ClassName.get(packageNameForApt, classPathName));
                    //  return map;
                    method.addStatement("return $N", Constant.GROUP_PARAMETER_NAME);
                    //  类型
                    TypeSpec clazz = TypeSpec.classBuilder(finalClassName)//    类名
                            .addSuperinterface(ClassName.get(groupElement))// 实现ARouterLoadGroup接口
                            .addModifiers(Modifier.PUBLIC)// public修饰符
                            .addMethod(method.build())// 方法的构建（方法参数 + 方法体）
                            .build();// 类构建完成
                    JavaFile file = JavaFile.builder(packageNameForApt, clazz).build();
                    file.writeTo(filer);// 文件生成器开始生成类文件
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
