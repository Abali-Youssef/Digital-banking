package com.project.ebank.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)

    private Long id;
    @Column(name = "DESCRIPTION")
    private String desc;
    @Column(unique = true,length = 20)
    private String roleName;
    //@JoinTable(name="users_roles")
    @OneToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    private List<Customer> users=new ArrayList<>();
    @ManyToMany(mappedBy = "roles")
    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    private List<Permission> permissions=new ArrayList<>();

}
