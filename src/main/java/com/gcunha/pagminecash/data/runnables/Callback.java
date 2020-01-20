package com.gcunha.pagminecash.data.runnables;

public interface Callback<V extends Object> {
    public void call(V result);
}
