package context;

import annotation.Autowired;
import annotation.Repository;
import annotation.Service;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyApplicationContext implements ApplicationContext {
    private final Map<String, Object> beanNameMap;
    private final Map<Class<?>, Object> beanClazzMap;


    public MyApplicationContext() {
        beanNameMap = new HashMap<>();
        beanClazzMap = new HashMap<>();
        scanAndRegisterBeans();
        injectDependencies();
        initializeBeans();
        destroyBeans();
    }


    @Override
    public void registerBean(Object bean, String beanName) {
        beanNameMap.put(beanName, bean);
    }

    @Override
    public void registerBean(Object bean, Class<?> clazz) {
        beanClazzMap.put(clazz, bean);
    }


    @Override
    public Object getBean(String beanName) {
        return beanNameMap.get(beanName);
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        for (Object bean : beanClazzMap.values()) {
            if (clazz.isAssignableFrom(bean.getClass())) {
                return clazz.cast(bean);
            }
        }
        return null;
    }

    private Object createBean(Class<?> clazz) {
        try {
            Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            for (Constructor<?> constructor : constructors) {
                if (isParameterizedConstructor(constructor.getParameterCount())) {
                    Class<?>[] parameterTypes = constructor.getParameterTypes();
                    Object[] parameters = new Object[parameterTypes.length];
                    for (int i = 0; i < parameterTypes.length; i++) {
                        parameters[i] = getBean(parameterTypes[i].getSimpleName());
                    }
                    return constructor.newInstance(parameters);
                }
            }
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void scanAndRegisterBeans() {
        List<Class<? extends Annotation>> annotationClasses = List.of(Service.class, Repository.class);
        List<String> classNames = scanClassesWithBeanAnnotations(annotationClasses);
        List<Class<?>> parameterlessConstructorClasses = new ArrayList<>();
        List<Class<?>> parameterizedConstructorsClasses = new ArrayList<>();
        for (String className : classNames) {
            try {
                Class<?> clazz = Class.forName(className);
                Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
                for (Constructor<?> declaredConstructor : declaredConstructors) {
                    if (isParameterizedConstructor(declaredConstructor.getParameterCount())) {
                        parameterizedConstructorsClasses.add(clazz);
                    } else {
                        parameterlessConstructorClasses.add(clazz);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Class<?> clazz : parameterlessConstructorClasses) {
            Object bean = createBean(clazz);
            String beanName = clazz.getSimpleName();
            registerBean(bean, beanName);
            registerBean(bean, clazz);
        }
        for (Class<?> clazz : parameterizedConstructorsClasses) {
            Object bean = createBean(clazz);
            String beanName = clazz.getSimpleName();
            registerBean(bean, beanName);
            registerBean(bean, clazz);
        }
    }

    private static boolean isParameterizedConstructor(int declaredConstructor) {
        return declaredConstructor > 0;
    }

    private List<String> scanClassesWithBeanAnnotations(List<Class<? extends Annotation>> annotationClasses) {
        ArrayList<String> classNames = new ArrayList<>();
        try {
            URL resource = Thread.currentThread().getContextClassLoader().getResource("");
            if (resource != null) {
                File directory = new File(resource.getFile());
                if (directory.exists() && directory.isDirectory()) {
                    scanClassesInDirectory(directory, "", classNames, annotationClasses);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classNames;
    }

    private void scanClassesInDirectory(File directory, String packageName, List<String> classNames, List<Class<? extends Annotation>> annotationClasses) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".class")) {
                    String className = packageName + file.getName().replace(".class", "");
                    try {
                        Class<?> clazz = Class.forName(className);
                        for (Class<? extends Annotation> annotationClass : annotationClasses) {
                            if (clazz.isAnnotationPresent(annotationClass)) {
                                classNames.add(className);
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } else if (file.isDirectory()) {
                    String subPackageName = packageName + file.getName() + ".";
                    scanClassesInDirectory(file, subPackageName, classNames, annotationClasses);
                }
            }
        }
    }

    private void injectDependencies() {
        for (Object bean : beanNameMap.values()) {
            injectDependencies(bean, List.of(Autowired.class));
        }
    }

    private void injectDependencies(Object bean, List<Class<? extends Annotation>> annotationClasses) {
        Class<?> clazz = bean.getClass();
        for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
            for (Class<? extends Annotation> annotationClass : annotationClasses) {
                if (field.isAnnotationPresent(annotationClass)) {
                    String beanName = field.getType().getSimpleName();
                    Object dependency = getBean(beanName);
                    if (dependency != null) {
                        try {
                            field.setAccessible(true);
                            field.set(bean, dependency);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void initializeBeans() {
        for (Object bean : beanNameMap.values()) {
            if (bean instanceof InitializingBean) {
                try {
                    ((InitializingBean) bean).afterPropertiesSet();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void destroyBeans() {
        for (Object bean : beanNameMap.values()) {
            if (bean instanceof DisposableBean) {
                try {
                    ((DisposableBean) bean).destroy();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
