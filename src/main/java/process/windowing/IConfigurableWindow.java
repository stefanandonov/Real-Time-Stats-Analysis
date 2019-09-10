package process.windowing;

public interface IConfigurableWindow<T> {
    T getElement();
    Long getWindowSize();
    String getWindowType();
    Long getWindowSlide();
}
