package com.lks.core.enums;

/**
 * Created by lokkur on 7/24/2015.
 */
public enum UserRoles {

    ROLE_DO("Data Operator"),
    ROLE_SCANNER("Scanner"),
    ROLE_RESOLVER("Resolver"),
    ROLE_APPROVER("Approver"),
    ROLE_ADMIN("Administrator");

    private String roleName;

    UserRoles(String roleName) {
        this.roleName = roleName;
    }

}
