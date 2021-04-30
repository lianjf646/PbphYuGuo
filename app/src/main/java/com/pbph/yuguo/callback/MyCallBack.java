package com.pbph.yuguo.callback;

import java.io.IOException;

public interface MyCallBack<T> {
    void onNext(T response) throws IOException;
}
