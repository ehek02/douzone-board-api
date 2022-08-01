package project.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface UserSessionService {
    void checkRefresh(HttpServletRequest request, HttpServletResponse response);

    void insertRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;


    void logout(String name);
}
