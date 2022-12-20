package it.itsar.twizzoli.data;

public interface AsyncResult {
    void success(Object result);
    void failed(Exception e);
}
