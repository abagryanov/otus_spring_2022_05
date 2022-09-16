package ru.otus.spring.permission;

import org.springframework.security.acls.model.Permission;

public interface PermissionService {
    void addPermissionForUser(Class<?> objectClass,
                              long objectId,
                              Permission permission,
                              String userLogin);

    void addPermissionForAuthority(Class<?> objectClass,
                                   long objectId,
                                   Permission permission,
                                   String authority);
}
