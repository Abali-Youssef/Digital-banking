package com.project.ebank.service;


import com.project.ebank.entities.Permission;
import com.project.ebank.entities.Role;
import org.springframework.validation.method.ParameterErrors;

public interface SecurityService {

    Role saveRole(String roleName, String description);
    Role updateRole(Long roleId, String roleName, String description);
    void deleteRole(Long roleId);
    void addRoleToUser(String email, String roleName);
    void removeRoleFromUser (String email, String roleName);
    void addPermissionToRole(String permission,String role);
    void removePermissionFromRole(String permission,String role);
    Permission addPermission(String name);

}
