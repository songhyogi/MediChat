package kr.spring.util;

public interface Parser<T> {
    T parse(String str);
}
