package com.khalid.ecommerce_system.repository;

import com.khalid.ecommerce_system.constant.ImageUploadCategory;
import com.khalid.ecommerce_system.entity.UploadedFileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository("UploadedFileInfoRepository")
@Transactional(rollbackFor = Exception.class)
public interface UploadedFileInfoRepository extends JpaRepository<UploadedFileInfo, Long> {

    Optional<UploadedFileInfo> findByFileNameAndFileCategory(String newFileName, ImageUploadCategory category);
}
