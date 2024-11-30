package com.project.ebank.service;


import com.project.ebank.entities.Customer;
import com.project.ebank.entities.Permission;
import com.project.ebank.entities.Role;
import com.project.ebank.exceptions.CustomerNotFoundException;
import com.project.ebank.exceptions.PermissionNotFoundException;
import com.project.ebank.exceptions.RoleAlreadyExistsException;
import com.project.ebank.exceptions.RoleNotFoundException;
import com.project.ebank.repositories.CustomerRepository;
import com.project.ebank.repositories.PermissionRepository;
import com.project.ebank.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class SecurityServiceImpl implements SecurityService {
    private CustomerRepository customerRepository;
    private RoleRepository roleRepository ;
    private PasswordEncoder passwordEncoder;
    private PermissionRepository permissionRepository;
    @Override
    public Role saveRole(String roleName, String description) {
        Role appRole =roleRepository.findByRoleName (roleName);
        if(appRole!=null) throw new RoleAlreadyExistsException("Role "+roleName+" already exist");
        appRole=new Role();
        appRole.setRoleName (roleName);
        appRole.setDesc(description);

        return roleRepository.save(appRole);
    }

    @Override
    public Role updateRole(Long roleId,String roleName, String description) {

        Role role=roleRepository.findById(roleId).orElseThrow(()->new RoleNotFoundException("role not found"));
        role.setRoleName(roleName);
        role.setDesc(description);
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        Role role=roleRepository.findById(roleId).orElseThrow(()->new RoleNotFoundException("role not found"));
roleRepository.deleteById(roleId);
    }

    @Override
    public void addRoleToUser( String email,String roleName) {
        Customer customer=customerRepository.findCustomersByEmail(email);
        if(customer==null) throw new CustomerNotFoundException("Customer not found");
        Role appRole=roleRepository.findByRoleName (roleName);
        if(appRole==null) throw new RoleNotFoundException("Role not found");
        List<Customer> customers=appRole.getUsers();
        customers.add(customer);
        appRole.setUsers(customers);
        customer.setRole(appRole);
        customerRepository.save(customer);
    }

    @Override
    public void removeRoleFromUser(String email, String roleName) {
        Customer customer=customerRepository.findCustomersByEmail(email);
        if(customer==null) throw new CustomerNotFoundException("Customer not found");
        Role appRole=roleRepository.findByRoleName (roleName);
        if(appRole==null) throw new RoleNotFoundException("Role not found");
        List<Customer> customers=appRole.getUsers();
        customers.remove(customer);
        appRole.setUsers(customers);
        customer.setRole(null);
        customerRepository.save(customer);
    }

    @Override
    public void addPermissionToRole(String permissionName, String roleName) {
        Role appRole =roleRepository.findByRoleName (roleName);
        if(appRole==null) throw new RoleNotFoundException("Role: "+roleName+" not found");
        Permission permission=permissionRepository.findByName(permissionName);
        if(permission==null)throw new PermissionNotFoundException("permission not found");
        List<Permission> permissionList=appRole.getPermissions();
        List<Role> roleList=permission.getRoles();
        permissionList.add(permission);
        roleList.add(appRole);
        roleRepository.save(appRole);
    }

    @Override
    public void removePermissionFromRole(String permissionName, String roleName) {
        Role appRole =roleRepository.findByRoleName (roleName);
        if(appRole==null) throw new RoleNotFoundException("Role: "+roleName+" not found");
        Permission permission=permissionRepository.findByName(permissionName);
        if(permission==null)throw new PermissionNotFoundException("permission not found");
        List<Permission> permissionList=appRole.getPermissions();
        List<Role> roleList=permission.getRoles();
        permissionList.remove(permission);
        roleList.remove(appRole);
        roleRepository.save(appRole);
    }

    @Override
    public Permission addPermission(String name) {
        Permission permission=new Permission();
        permission.setName(name);
        return permissionRepository.save(permission);
    }


}
