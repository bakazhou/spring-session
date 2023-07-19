package factory;

public interface BeanFactory {
    Object getBean(String name);
    void registerBean(Object bean, String beanName);
}