package com.khalid.ecommerce_system.constant;

public class Constants {
    private Constants() {}

    public static final class ApiPath {
        private ApiPath() {}

        public static final String COMMON_API_PATH = "/api";
        public static final String PRIVATE_API_PATH = COMMON_API_PATH + "/private";
        public static final String PUBLIC_API_PATH = COMMON_API_PATH + "/pub";
    }

    public static final class HttpHeaders {
        private HttpHeaders() {}

        public static final String HEADER_KEY_CLIENT_AUTH = "X-YY-Signature";
    }

    public static final class Security {
        private Security() {
        }

        public static final String TOKEN_TYPE = "Ecommerce-Token ";
        public static final int TOKEN_TYPE_LEN = 16;
        public static final int ACCOUNT_LOCK_TIME = 30;
        public static final int ACCOUNT_LOCK_ATTEMPT = 3;
    }
}
