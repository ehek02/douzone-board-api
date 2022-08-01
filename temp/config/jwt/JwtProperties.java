package project.config.jwt;

public interface JwtProperties {
    String SECRET = "douzone"; // 우리 서버만 알고 있는 비밀값
    Integer ACCESS_TOKEN_EXPIRATION_TIME = 3600 * 1000; // 1000 = 1초
    Integer REFRESH_TOKEN_EXPIRATION_TIME = 864000000; // 10일 (1/1000초)
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
