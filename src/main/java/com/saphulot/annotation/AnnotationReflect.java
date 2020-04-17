package com.saphulot.annotation;

import java.lang.reflect.Method;

/**
 * 注解使用
 *      注解定义
 *          注解接口内部抽象方法定义
 *          注解的元注解
 *              Target(注解作用的位置，类、方法、成员变量)
 *              Retention（注解作用的时机，源码字节码期、编译期、运行期）
 *              Documented（注释文档doc）
 *              Inherited（注解继承）
 *      注解解析
 *          反射获取注解属性
 */
@Property(className = "com.saphulot.annotation.Person",methodName = "show")
public class AnnotationReflect {
    public static void main(String[] args) throws Exception {
        // 解析注解 获取注解定义的位置的（class对象/metho对象/field对象）
        Class<AnnotationReflect> annotationReflectClass = AnnotationReflect.class;
        // 反射AnnotationReflect获取到Property注解接口的一个实现类的对象
        /*
            public class PropertyImpl implements Property{
                    public String className(){
                          return "com.saphulot.annotation.Person";
                    }

                    public String methodName(){
                          return "show";
                    }

            }
         */
        //  调用getAnnotation(Class)获取注解接口的一个实现类的对象，此对象实现的是注解使用时的属性
        Property an = annotationReflectClass.getAnnotation(Property.class);
        // 调用注解接口的抽象方法获取对应的属性值
        String className = an.className();
        String methodName = an.methodName();

        Class<?> aClass = Class.forName(className);

        Object obj = aClass.newInstance();

        Method method = aClass.getMethod(methodName);

        method.invoke(obj);
    }
}
