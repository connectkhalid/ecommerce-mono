package com.khalid.ecommerce_system.constant;


import com.khalid.ecommerce_system.service.FeaturePermissionService;
import com.khalid.ecommerce_system.service.impl.FeaturePermissionServiceImpl;
import org.springframework.stereotype.Service;

@Service("apiPermission")
public class FeaturePermissionList {

    // ACCOUNT MANAGEMENT
    public static FeaturePermissionService.FeaturePermissionObject getUserListViewPermission() {
        return FeaturePermissionServiceImpl.getFeaturePermissionObject(FeaturePermissionService.MainFeature.ACCOUNT_MANAGEMENT, FeaturePermissionService.SubFeature.ADMIN_BASIC, FeaturePermissionService.Permission.VIEW_LIST);
    }

    public static FeaturePermissionService.FeaturePermissionObject getUserProfileViewPermission() {
        return FeaturePermissionServiceImpl.getFeaturePermissionObject(FeaturePermissionService.MainFeature.ACCOUNT_MANAGEMENT, FeaturePermissionService.SubFeature.USER_BASIC, FeaturePermissionService.Permission.VIEW_DETAILS);
    }

    public static FeaturePermissionService.FeaturePermissionObject getAdminProfileViewPermission() {
        return FeaturePermissionServiceImpl.getFeaturePermissionObject(FeaturePermissionService.MainFeature.ACCOUNT_MANAGEMENT, FeaturePermissionService.SubFeature.ADMIN_BASIC, FeaturePermissionService.Permission.VIEW_DETAILS);
    }

    public static FeaturePermissionService.FeaturePermissionObject getUserProfileEditPermission() {
        return FeaturePermissionServiceImpl.getFeaturePermissionObject(FeaturePermissionService.MainFeature.ACCOUNT_MANAGEMENT, FeaturePermissionService.SubFeature.USER_BASIC, FeaturePermissionService.Permission.EDIT);
    }

    public static FeaturePermissionService.FeaturePermissionObject getAdminProfileEditPermission() {
        return FeaturePermissionServiceImpl.getFeaturePermissionObject(FeaturePermissionService.MainFeature.ACCOUNT_MANAGEMENT, FeaturePermissionService.SubFeature.ADMIN_BASIC, FeaturePermissionService.Permission.EDIT);
    }

    // PRODUCT MANAGEMENT
    public static FeaturePermissionService.FeaturePermissionObject getProductCreatePermission() {
        return FeaturePermissionServiceImpl.getFeaturePermissionObject(FeaturePermissionService.MainFeature.PRODUCT_MANAGEMENT, FeaturePermissionService.SubFeature.PRODUCT_BASIC, FeaturePermissionService.Permission.ADD);
    }
    public static FeaturePermissionService.FeaturePermissionObject getProductDetailsPermission() {
        return FeaturePermissionServiceImpl.getFeaturePermissionObject(FeaturePermissionService.MainFeature.PRODUCT_MANAGEMENT, FeaturePermissionService.SubFeature.PRODUCT_BASIC, FeaturePermissionService.Permission.VIEW_DETAILS);
    }
    public static FeaturePermissionService.FeaturePermissionObject getProductUpdatePermission() {
        return FeaturePermissionServiceImpl.getFeaturePermissionObject(FeaturePermissionService.MainFeature.PRODUCT_MANAGEMENT, FeaturePermissionService.SubFeature.PRODUCT_BASIC, FeaturePermissionService.Permission.EDIT);
    }
    public static FeaturePermissionService.FeaturePermissionObject getProductDeletePermission() {
        return FeaturePermissionServiceImpl.getFeaturePermissionObject(FeaturePermissionService.MainFeature.PRODUCT_MANAGEMENT, FeaturePermissionService.SubFeature.PRODUCT_BASIC, FeaturePermissionService.Permission.DELETE);
    }
    public static FeaturePermissionService.FeaturePermissionObject getProductListPermission() {
        return FeaturePermissionServiceImpl.getFeaturePermissionObject(FeaturePermissionService.MainFeature.PRODUCT_MANAGEMENT, FeaturePermissionService.SubFeature.PRODUCT_BASIC, FeaturePermissionService.Permission.VIEW_LIST);
    }
    //ORDER MANAGEMENT
    public static FeaturePermissionService.FeaturePermissionObject getOrderCreatePermission() {
        return FeaturePermissionServiceImpl.getFeaturePermissionObject(FeaturePermissionService.MainFeature.ORDER_MANAGEMENT, FeaturePermissionService.SubFeature.ORDER_BASIC, FeaturePermissionService.Permission.ADD);
    }
    public static FeaturePermissionService.FeaturePermissionObject getOrderDetailsPermission() {
        return FeaturePermissionServiceImpl.getFeaturePermissionObject(FeaturePermissionService.MainFeature.ORDER_MANAGEMENT, FeaturePermissionService.SubFeature.ORDER_BASIC, FeaturePermissionService.Permission.VIEW_DETAILS);
    }
    public static FeaturePermissionService.FeaturePermissionObject getOrderUpdatePermission() {
        return FeaturePermissionServiceImpl.getFeaturePermissionObject(FeaturePermissionService.MainFeature.ORDER_MANAGEMENT, FeaturePermissionService.SubFeature.ORDER_BASIC, FeaturePermissionService.Permission.EDIT);
    }
    public static FeaturePermissionService.FeaturePermissionObject getOrderDeletePermission() {
        return FeaturePermissionServiceImpl.getFeaturePermissionObject(FeaturePermissionService.MainFeature.ORDER_MANAGEMENT, FeaturePermissionService.SubFeature.ORDER_BASIC, FeaturePermissionService.Permission.DELETE);
    }
    public static FeaturePermissionService.FeaturePermissionObject getOrderListPermission() {
        return FeaturePermissionServiceImpl.getFeaturePermissionObject(FeaturePermissionService.MainFeature.ORDER_MANAGEMENT, FeaturePermissionService.SubFeature.ORDER_BASIC, FeaturePermissionService.Permission.VIEW_LIST);
    }
    //PROFILE MANAGEMENT
}
