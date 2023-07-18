package factory;

import java.util.HashMap;
import java.util.Map;

public class MyBeanFactory implements BeanFactory {
    private final Map<String, Object> beans = new HashMap<>();

    public void registerBean(String name, Object bean) {
        beans.put(name, bean);
    }

    @Override
    public Object getBean(String name) {
        return beans.get(name);
    }
}