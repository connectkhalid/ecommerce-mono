package com.khalid.ecommerce_system.service.impl;

import com.khalid.ecommerce_system.constant.ImageUploadCategory;
import com.khalid.ecommerce_system.constant.RestErrorMessageDetail;
import com.khalid.ecommerce_system.constant.RestResponseMessage;
import com.khalid.ecommerce_system.constant.RestResponseStatusCode;
import com.khalid.ecommerce_system.entity.UploadedFileInfo;
import com.khalid.ecommerce_system.exception.FileUploadException;
import com.khalid.ecommerce_system.form.validation.EnumUtility;
import com.khalid.ecommerce_system.repository.UploadedFileInfoRepository;
import com.khalid.ecommerce_system.service.ImageUploadService;
import com.khalid.ecommerce_system.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Service("ImageUploadService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageUploadServiceImpl implements ImageUploadService {
    @Value("${file.upload-dir}")
    private String rootDir;

    private final UploadedFileInfoRepository uploadedFileInfoRepository;

    @Override
    public String uploadImage(UploadImageInputParameter parameter) throws IOException, FileUploadException {
        // Validate input parameters
        validateInputParameters(parameter);

        // Validate image file
        validateImageFile(parameter.getFile());

        // Process file upload
        return processFileUpload(parameter);
    }

    private void validateInputParameters(UploadImageInputParameter parameter) throws FileUploadException {
        if (parameter.getFile() == null || parameter.getFile().isEmpty()) {
            throw new FileUploadException(
                    RestResponseMessage.FILE_UPLOAD_FAILURE,
                    RestResponseStatusCode.FORBIDDEN_STATUS,
                    RestErrorMessageDetail.FILE_UPLOAD_FAILURE_ERROR_MESSAGE
            );
        }

        if (parameter.getFileName() == null || parameter.getFileName().trim().isEmpty()) {
            throw new FileUploadException(
                    RestResponseMessage.INVALID_FILENAME,
                    RestResponseStatusCode.FORBIDDEN_STATUS,
                    RestErrorMessageDetail.INVALID_FILENAME_ERROR_MESSAGE
            );
        }

        if (parameter.getCategory() == null || parameter.getCategory().trim().isEmpty()) {
            throw new FileUploadException(
                    RestResponseMessage.INVALID_IMAGE_CATEGORY,
                    RestResponseStatusCode.FORBIDDEN_STATUS,
                    RestErrorMessageDetail.INVALID_CATEGORY_ERROR_MESSAGE
            );
        }
    }

    private void validateImageFile(MultipartFile file) throws FileUploadException {
        // Check file size (1MB limit)
        if (file.getSize() > 1_048_576) { // 1MB in bytes
            throw new FileUploadException(
                    RestResponseMessage.FILE_SIZE_EXCEEDED,
                    RestResponseStatusCode.FORBIDDEN_STATUS,
                    RestErrorMessageDetail.FILE_SIZE_EXCEEDED_ERROR_MESSAGE
            );
        }

        // Check file content type
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new FileUploadException(
                    RestResponseMessage.INVALID_FILE_TYPE,
                    RestResponseStatusCode.FORBIDDEN_STATUS,
                    RestErrorMessageDetail.INVALID_FILE_TYPE_ERROR_MESSAGE
            );
        }

        // Check file extension
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !isValidImageExtension(originalFilename)) {
            throw new FileUploadException(
                    RestResponseMessage.INVALID_FILE_EXTENSION,
                    RestResponseStatusCode.FORBIDDEN_STATUS,
                    RestErrorMessageDetail.INVALID_FILE_EXTENSION_ERROR_MESSAGE
            );
        }
    }

    private boolean isValidImageExtension(String filename) {
        String extension = getFileExtension(filename).toLowerCase();
        return extension.equals(".jpg") || extension.equals(".jpeg") ||
                extension.equals(".png") || extension.equals(".gif");
    }

    private String processFileUpload(UploadImageInputParameter parameter)
            throws IOException, FileUploadException {

        ImageUploadCategory category = resolveImageCategory(parameter.getCategory());
        Path uploadPath = prepareUploadDirectory(parameter.getCategory());
        Path filePath = prepareTargetFilePath(parameter, uploadPath);

        validateFileDoesNotExist(filePath, category);
        saveFileToDisk(parameter.getFile(), filePath);

        return persistFileMetadata(filePath, category).toString();
    }

    private ImageUploadCategory resolveImageCategory(String categoryStr) throws FileUploadException {
        try {
            return EnumUtility.getEnumValue(ImageUploadCategory.class, categoryStr);
        } catch (IllegalArgumentException e) {
            throw new FileUploadException(
                    RestResponseMessage.INVALID_CATEGORY,
                    RestResponseStatusCode.FORBIDDEN_STATUS,
                    RestErrorMessageDetail.INVALID_CATEGORY_ERROR_MESSAGE
            );
        }
    }

    private Path prepareUploadDirectory(String subDirectory) throws IOException {
        Path uploadPath = Paths.get(rootDir, subDirectory);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        return uploadPath;
    }

    private Path prepareTargetFilePath(UploadImageInputParameter parameter, Path uploadPath) {
        String fileExtension = getFileExtension(parameter.getFile().getOriginalFilename());
        return uploadPath.resolve(parameter.getCategory() + parameter.getFileName() + fileExtension);
    }

    private void validateFileDoesNotExist(Path filePath, ImageUploadCategory category)
            throws FileUploadException {

        if (Files.exists(filePath)) {
            throw new FileUploadException(
                    RestResponseMessage.FILE_ALREADY_EXISTS,
                    RestResponseStatusCode.ALREADY_EXISTS_STATUS,
                    RestErrorMessageDetail.FILE_ALREADY_EXISTS_ERROR_MESSAGE
            );
        }

        String fileName = filePath.getFileName().toString();
        if (uploadedFileInfoRepository.findByFileNameAndFileCategory(fileName, category).isPresent()) {
            throw new FileUploadException(
                    RestResponseMessage.FILE_ALREADY_EXISTS,
                    RestResponseStatusCode.ALREADY_EXISTS_STATUS,
                    RestErrorMessageDetail.FILE_ALREADY_EXISTS_ERROR_MESSAGE
            );
        }
    }

    private void saveFileToDisk(MultipartFile file, Path filePath) throws FileUploadException {
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("Failed to save file to disk", e);
            throw new FileUploadException(
                    RestResponseMessage.FILE_SAVE_FAILURE,
                    RestResponseStatusCode.INTERNAL_ERROR_STATUS,
                    RestErrorMessageDetail.FILE_SAVE_FAILURE_ERROR_MESSAGE
            );
        }
    }

    private Path persistFileMetadata(Path filePath, ImageUploadCategory category) {
        UploadedFileInfo fileInfo = new UploadedFileInfo();

        fileInfo.setFileCategory(category);
        fileInfo.setFileName(filePath.getFileName().toString());
        fileInfo.setFilePath(filePath.toString());
        fileInfo.setDeleteFlag(false);
        fileInfo.setCreatedDt(DateUtil.currentTime());
        fileInfo.setUpdatedDt(DateUtil.currentTime());

        uploadedFileInfoRepository.save(fileInfo);
        return Path.of(fileInfo.getFilePath());
    }

    private String getFileExtension(String originalFileName) {
        int lastIndex = originalFileName.lastIndexOf('.');
        return lastIndex != -1 ? originalFileName.substring(lastIndex) : "";
    }
}
