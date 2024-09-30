package com.project.ebank.repositories;

import com.project.ebank.entities.Card;
import com.project.ebank.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends JpaRepository<Card,String> {

    List<Card> findByBankAccountId(String id);
}
