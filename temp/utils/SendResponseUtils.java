package project.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class SendResponseUtils {

    public SendResponseUtils() {
        throw new UnsupportedOperationException("utils class 는 생성할 수 없습니다.");
    }

    @SneakyThrows
    public static void sendBody(int statusCode, String errorMsg, HttpServletResponse response) {
        response.setStatus(statusCode);
        Map<String, String> error = new HashMap<>();
        error.put("error_message", errorMsg);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}