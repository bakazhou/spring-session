package context;

public interface DisposableBean {
    void destroy() throws Exception;
}