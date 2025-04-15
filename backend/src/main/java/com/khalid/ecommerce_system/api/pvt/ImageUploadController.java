package com.khalid.ecommerce_system.api.pvt;

import com.khalid.ecommerce_system.constant.Constants;
import com.khalid.ecommerce_system.constant.RestResponseMessage;
import com.khalid.ecommerce_system.constant.RestResponseStatusCode;
import com.khalid.ecommerce_system.exception.FileUploadException;
import com.khalid.ecommerce_system.service.ImageUploadService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.khalid.ecommerce_system.constant.RestApiResponse.buildResponseWithDetails;

@RestController
@CrossOrigin
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageUploadController {
    public static final String API_PATH_PLAYER_UPLOAD_PLAYER_IMAGE = Constants.ApiPath.PRIVATE_API_PATH + "/upload-image";
    private final ImageUploadService imageUploadService;

    @Data
    public static class ImageUploadRequest {
        @NotNull(message = "Image file is required")
        private MultipartFile image;

        @NotBlank(message = "Category is required")
        private String category;

        @NotBlank(message = "Filename is required")
        private String fileName;
    }

    @PostMapping(path = API_PATH_PLAYER_UPLOAD_PLAYER_IMAGE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> uploadPlayerImage(
            @Valid @ModelAttribute ImageUploadRequest request
    ) throws IOException, FileUploadException {
        String filePath = imageUploadService.uploadImage(
                new ImageUploadService.UploadImageInputParameter(request.getImage(),request.getCategory(),
                        request.getFileName()));
        return buildResponseWithDetails(RestResponseStatusCode.OK_STATUS, RestResponseMessage.CREATE_OK, filePath);
    }
}
