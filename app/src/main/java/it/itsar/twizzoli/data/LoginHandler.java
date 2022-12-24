package it.itsar.twizzoli.data;

import it.itsar.twizzoli.models.User;

public interface LoginHandler {
    void success(User loggedUser);
    void failed(int code, String message);
}
