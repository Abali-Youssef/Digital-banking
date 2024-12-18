package com.project.ebank.entities;

import com.project.ebank.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "Type", length = 4,discriminatorType = DiscriminatorType.STRING)
public class BankAccount {
    @Id
    private String id;
    @Column(nullable = false)
    private double balance;
    @Column(nullable = false)
    private Date createdAt;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private AccountStatus status;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "bankAccount",fetch = FetchType.LAZY)
    private List<BankAccountOperation> bankAccountOperations;
    @OneToMany(mappedBy ="bankAccount",fetch = FetchType.LAZY)
    private List<Card> cards;
}
