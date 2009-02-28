package com.mycila.license.core.task;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public interface Task<T> {
    T execute();
}
