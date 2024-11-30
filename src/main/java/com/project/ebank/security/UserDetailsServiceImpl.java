package com.project.ebank.security;


import com.project.ebank.entities.Customer;
import com.project.ebank.entities.Role;
import com.project.ebank.service.CustomerService;
import com.project.ebank.service.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private CustomerService customerService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //System.out.println("email (userdetailimpl) ==> "+email);
        Customer user = customerService.loadCustomerByEmail(email);
         Collection<GrantedAuthority> authorities = new ArrayList<>();


         Role role =user.getRole();
         role.getPermissions().forEach(p->{
             SimpleGrantedAuthority authority = new SimpleGrantedAuthority(p.getName());
             authorities.add(authority);
         });


                org.springframework.security.core.userdetails.User springScurityuser=new org.springframework.security.core.userdetails.User(user.getName(),user.getPassword(),authorities);
        return springScurityuser;
    }
}
