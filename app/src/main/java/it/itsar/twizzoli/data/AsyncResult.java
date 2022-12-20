package it.itsar.twizzoli.data;

import android.util.ArraySet;

import java.util.ArrayList;

public interface AsyncResult {
    <T> void success(ArraySet<T> result);
    void failed(Exception e);
    void error(int code);
}
