package context;

public interface ApplicationContext {
    Object getBean(String beanName);
    <T> T getBean(Class<T> clazz);

    void registerBean(Object bean, String beanName);

    void registerBean(Object bean, Class<?> clazz);
}