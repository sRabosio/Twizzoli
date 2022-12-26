package it.itsar.twizzoli.data;

import it.itsar.twizzoli.models.User;

public interface ResultHandler {
    <T> void success(T result);
    void failed(int code, String message);
}
