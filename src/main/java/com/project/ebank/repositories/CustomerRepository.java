package com.project.ebank.repositories;

import com.project.ebank.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    //List<Customer> findByNameContains(String keyword);
    @Query("select c from Customer c where c.name like :kw or c.email like :kw")
    Page<Customer> findCustomersByKeyword(@Param(value="kw") String keyword, Pageable pageable);
    @Query("select c from Customer c where c.email = :kw")
    Customer findCustomersByEmail(@Param(value="kw") String email);
    @Query("select c from Customer c where c.cin like :kw")
    List<Customer> findCustomersByCin(@Param(value="kw") String keyword);
}
