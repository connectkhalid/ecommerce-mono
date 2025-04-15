package com.khalid.ecommerce_system.constant;

public class RestResponseMessage {
    // Operation Error
    public static final String VALIDATION_ERROR = "WARN_INPUT_PARAM";
    public static final String SQL_ERROR = "DATABASE_ERROR";
    public static final String REQUEST_ERROR = "ERR_REQUEST";

    public static final String UNKNOWN_ERROR = "ERR_UNKNOWN";
    public static final String TIMEOUT_ERROR = "REQUEST_TIMEOUT_ERROR";
    public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";

    // Entity Not Found
    public static final String USER_NOT_FOUND = "INFO_NO_USER";
    public static final String ROLE_NOT_FOUND = "INFO_NO_ROLE";
    public static final String AUTH_DATA_NOT_FOUND = "INFO_NO_AUTH_DATA";

    // Operation Success
    public static final String CREATE_OK = "SUCCESSFULLY_CREATED";
    public static final String UPDATE_OK = "SUCCESSFULLY_UPDATED";
    public static final String DELETE_OK = "SUCCESSFULLY_DELETED";
    public static final String FETCH_OK = "SUCCESSFULLY_FETCHED";
    public static final String SEND_MAIL_OK = "SUCCESSFULLY_SEND_MAIL";
    public static final String FILE_UPLOAD_OK = "SUCCESSFULLY_UPLOAD_FILE";

    // User Service
    public static final String USER_ALREADY_EXISTS = "INFO_USER_ALREADY_EXISTS";
    public static final String ADMIN_ACCOUNT_CREATION_ERROR_MESSAGE = "ADMIN_ACCOUNT_CREATION_ERROR";

    // Auth Service
    public static final String INVALID_TOKEN = "INVALID_TOKEN";
    public static final String EXPIRED_TOKEN = "EXPIRED_TOKEN";
    public static final String WRONG_CREDENTIALS = "WRONG_CREDENTIALS";
    public static final String LOGIN_SUCCESS = "LOGIN_SUCCESS";
    public static final String REFRESH_SUCCESS = "REFRESH_SUCCESS";
    public static final String LOGOUT_SUCCESS = "LOGOUT_SUCCESS";
    public static final String NOT_LOGGED_IN = "NOT_LOGGED_IN";

    public static final String ACCESS_DENIED = "Access is denied";
    public static final String ROLE_MISMATCH = "USER_ROLE_CHANGED";
    public static final String SEND_MAIL_FAILURE = "MAIL_SENDING_FAILED";
    public static final String TIMEOUT_MESSAGE = "transaction timeout expired";

    public static final String FILE_UPLOAD_FAILURE = "FILE_UPLOAD_FAILED";

    //Product Service
    public static final String ERROR_IN_ADDING_PRODUCT = "ERROR_IN_ADDING_PRODUCT";
    public static final String PRODUCT_NOT_FOUND = "PRODUCT_NOT_FOUND";
    public static final String ERROR_IN_UPDATING_PRODUCT = "ERROR_IN_UPDATING_PRODUCT";
    public static final String ERROR_IN_DELETING_PRODUCT = "ERROR_IN_DELETING_PRODUCT";
    public static final String ERROR_IN_FETCHING_PRODUCTS = "ERROR_IN_FETCHING_PRODUCTS";
    public static final String PRODUCT_ALREADY_EXISTS = "PRODUCT_ALREADY_EXISTS";
    public static final String INVALID_SORT_FIELD = "INVALID_SORT_FIELD";

    //File Upload Service
    public static final String FILE_ALREADY_EXISTS = "FILE_ALREADY_EXISTS";
    public static final String INVALID_FILENAME = "FILENAME_INVALID";
    public static final String INVALID_IMAGE_CATEGORY = "IMAGE_CATEGORY_INVALID";
    public static final String FILE_SAVE_FAILURE = "FILE_SAVE_FAILURE";
    public static final String FILE_SIZE_EXCEEDED = "FILE_SIZE_EXCEEDED";
    public static final String INVALID_FILE_TYPE = "INVALID_FILE_TYPE";
    public static final String INVALID_FILE_EXTENSION = "INVALID_FILE_EXTENSION";
    public static final String INVALID_CATEGORY = "INVALID_CATEGORY";

    //Order Service
    public static final String ORDER_QUANTITY_EXCEED_STOCK = "ORDER_QUANTITY_EXCEED_AVAILABLE_STOCK";
    public static final String ORDER_NOT_FOUND = "ORDER_NOT_FOUND";
    public static final String ORDER_INFO_NOT_EDITABLE = "ORDER_INFO_NOT_EDITABLE";
}
