package com.project.ebank.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@DiscriminatorValue("SAV")
public class SavingAccount extends BankAccount{
    @Column(nullable = false)
    private double interestRate;
}
