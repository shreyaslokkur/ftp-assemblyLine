package com.lks.core.enums;

/**
 * Created by lokkur on 7/24/2015.
 */
public enum UserRoles {

    DATAOPERATOR("ROLE_DO"),
    SCANNER("ROLE_SCANNER"),
    APPROVER("ROLE_APPROVER"),
    ADMIN("ROLE_ADMIN");

    private String roleName;

    UserRoles(String roleName) {
        this.roleName = roleName;
    }

}
