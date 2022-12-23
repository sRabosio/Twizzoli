package it.itsar.twizzoli.data;

public interface ResultHandler {
    <T> void success(T result);
    void failed(Exception e);
    void error(int code);
}
