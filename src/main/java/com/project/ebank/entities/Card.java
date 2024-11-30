package com.project.ebank.entities;

import com.project.ebank.enums.CardType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NegativeOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Card {
    @Id
    private String id;
    @Column(nullable = false,unique = false)
    private Date expirationDate;
    @Enumerated(EnumType.STRING)
    private CardType type;
    @ManyToOne
    private BankAccount bankAccount;
}
