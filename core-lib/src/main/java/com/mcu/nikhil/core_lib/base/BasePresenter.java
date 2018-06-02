package com.mcu.nikhil.core_lib.base;

public interface BasePresenter<T> {
    void bind(T view);
    void unbind();
}
