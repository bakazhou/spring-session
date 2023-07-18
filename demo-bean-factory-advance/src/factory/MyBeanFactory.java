package factory;

import annotation.Bean;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBeanFactory {
    private final Map<String, Object> beanMap;

    public MyBeanFactory() {
        beanMap = new HashMap<>();
        scanAndRegisterBeans();
    }

    public void registerBean(Object bean, String beanName) {
        beanMap.put(beanName, bean);
    }


    public Object getBean(String beanName) {
        return beanMap.get(beanName);
    }

    private void scanAndRegisterBeans() {
        List<Class<? extends Annotation>> annotationClasses = List.of(Bean.class);
        List<String> classNames = scanClassesWithBeanAnnotation(annotationClasses);

        for (String className : classNames) {
            try {
                Class<?> clazz = Class.forName(className);
                Object bean = clazz.getDeclaredConstructor().newInstance();
                String beanName = clazz.getSimpleName();
                registerBean(bean, beanName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private List<String> scanClassesWithBeanAnnotation(List<Class<? extends Annotation>> annotationClasses) {
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

}