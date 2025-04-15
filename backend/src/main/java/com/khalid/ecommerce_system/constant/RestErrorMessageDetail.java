package com.khalid.ecommerce_system.constant;

public class RestErrorMessageDetail {
    // Common
    public static final String HTTP_REQUEST_METHOD_NOT_SUPPORTED_ERROR_MESSAGE = "Sorry, the requested HTTP method is not supported.";
    public static final String HTTP_MEDIA_TYPE_NOT_SUPPORTED_ERROR_MESSAGE = "Unsupported media type in the request.";
    public static final String HTTP_MEDIA_TYPE_NOT_ACCEPTABLE_ERROR_MESSAGE = "None of the requested media types are acceptable.";
    public static final String CONVERSION_NOT_SUPPORTED_ERROR_MESSAGE = "Conversion not supported for the given data type.";
    public static final String HTTP_MESSAGE_NOT_READABLE_ERROR_MESSAGE = "Unable to read the HTTP message.";
    public static final String HTTP_MESSAGE_NOT_WRITABLE_ERROR_MESSAGE = "Unable to write the HTTP message.";
    public static final String NO_HANDLER_FOUND_ERROR_MESSAGE = "No handler found for the request.";
    public static final String ASYNC_REQUEST_TIMEOUT_ERROR_MESSAGE = "Async request timed out.";
    public static final String MISSING_PATH_VARIABLE_ERROR_MESSAGE = "Missing path variable in the request.";
    public static final String MISSING_SERVLET_REQUEST_PARAMETER_ERROR_MESSAGE = "Missing servlet request parameter.";
    public static final String TYPE_MISMATCH_ERROR_MESSAGE = "Type mismatch in request parameters.";
    public static final String MISSING_SERVLET_REQUEST_PART_ERROR_MESSAGE = "Missing servlet request part.";
    public static final String SERVLET_REQUEST_BINDING_ERROR_MESSAGE = "ServletRequest binding error.";
    public static final String METHOD_ARGUMENT_TYPE_MISMATCH_ERROR_MESSAGE = "Method argument type mismatch.";
    public static final String BINDING_ERROR_MESSAGE = "Can not bind input due to invalid format.";
    public static final String INVALID_FORMAT_ERROR_MESSAGE = "Invalid data format in the request.";
    public static final String MAX_UPLOAD_SIZE_EXCEEDED_ERROR_MESSAGE = "Uploaded file size exceeds the maximum limit.";
    public static final String BAD_CREDENTIALS_ERROR_MESSAGE = "Username or password is invalid.";
    public static final String SQL_ERROR_MESSAGE = "An error occurred while processing database.";
    public static final String STACK_OVERFLOW_ERROR_MESSAGE = "Please reduce the number of Wi-Fi settings or devices under this group.";
    public static final String TIMEOUT_ERROR_MESSAGE = "Request processing time exceeds the maximum limit.";
    public static final String NO_PERMISSION_ERROR_MESSAGE = "You do not have the permission to perform the task.";
    public static final String UNKNOWN_ERROR_MESSAGE = "Unknown exception occurred, please check log for details.";
    public static final String INVALID_USER_TOKEN_ERROR_MESSAGE = "Token is not registered for a valid user.";
    public static final String INVALID_TOKEN_ERROR_MESSAGE = "Token is not valid.";
    public static final String EXPIRED_TOKEN_ERROR_MESSAGE = "Token has expired.";
    public static final String ACCESS_KEY_NOT_FOUND_ERROR_MESSAGE = "Access key does not exist.";
    public static final String FILE_UPLOAD_FAILURE_ERROR_MESSAGE = "File upload failed. No file is found or File name null";

    // User
    public static final String USER_NOT_FOUND_ERROR_MESSAGE = "User does not exist.";
    public static final String USER_ALREADY_LOGGED_OUT_ERROR_MESSAGE = "User Already logged out.";
    public static final String USER_ALREADY_EXISTS_ERROR_DETAILS = "Player already exists with the given player details";
    public static final String ADMIN_ACCOUNT_CREATION_ERROR_MESSAGE = "Admin account can not be created.";
    public static final String ACCOUNT_INFORMATION_MISMATCH_ERROR_MESSAGE = "Account information mismatch.";

    // Role
    public static final String ROLE_NOT_FOUND_ERROR_MESSAGE = "Role does not exist.";

    // Product
    public static final String ERROR_IN_ADDING_PRODUCT_MESSAGE = "ERROR_IN_ADDING_PRODUCT";
    public static final String PRODUCT_NOT_FOUND_ERROR_MESSAGE = "Product not found. Please Enter Valid product id.";
    public static final String PRODUCT_NOT_UPDATED_ERROR_MESSAGE = "Unable to update product. Try again.";
    public static final String ERROR_IN_DELETING_PRODUCT_MESSAGE = "Unable to delete product. Try again.";
    public static final String ERROR_IN_FETCHING_PRODUCTS_MESSAGE = "Unable to fetch products. Try again.";
    public static final String PRODUCT_ALREADY_EXISTS_ERROR_MESSAGE = "Product already exists. Please Enter different product name.";
    public static final String NO_PRODUCT_FOUND_ERROR_MESSAGE = "No product found. Please Enter Valid product id.";
    public static final String NO_PRODUCT_FOUND_WITH_KEYWORD_ERROR_MESSAGE = "No product found with given keyword.";
    public static final String NO_PRODUCT_AVAILABLE_ERROR_MESSAGE = "Unable to fetch products. Try again.";
    public static final String INVALID_SORT_FIELD_ERROR_MESSAGE = "Sort field is not valid. Please enter valid sort field like name, Price, qty.";

    //File Management
    public static final String FILE_ALREADY_EXISTS_ERROR_MESSAGE = "File already exists with same path. Please enter different file name.";
    public static final String INVALID_FILENAME_ERROR_MESSAGE = "File name can not be null or empty.";
    public static final String INVALID_CATEGORY_ERROR_MESSAGE = "Category can not be null or empty.";
    public static final String FILE_SAVE_FAILURE_ERROR_MESSAGE = "File save failed. Try again.";
    public static final String FILE_SIZE_EXCEEDED_ERROR_MESSAGE = "File size exceeded. Try another file.";
    public static final String INVALID_FILE_TYPE_ERROR_MESSAGE = "File type not supported. Try another image.";
    public static final String INVALID_FILE_EXTENSION_ERROR_MESSAGE = "File extension not supported. Upload image file.";

    // Order
    public static final String ORDER_QUANTITY_EXCEED_STOCK_ERROR_MESSAGE = "Order quantity exceeds stock quantity.PLease enter valid quantity.";
    public static final String ORDER_NOT_FOUND_ERROR_MESSAGE = "Order not found. Please Enter Valid order id.";
    public static final String ORDER_INFO_NOT_EDITABLE_ERROR_MESSAGE = "Order info can not be edited.";
}
