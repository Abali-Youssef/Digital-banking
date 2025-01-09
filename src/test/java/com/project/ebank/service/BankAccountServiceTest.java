package com.project.ebank.service;

import com.project.ebank.entities.BankAccount;
import com.project.ebank.entities.BankAccountOperation;
import com.project.ebank.enums.OperationType;
import com.project.ebank.exceptions.BalanceNotSufficientException;
import com.project.ebank.repositories.BankAccountRepository;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankAccountServiceTest {
    private final String ACC_ID="ACC_345";
    private final double AMOUNT=100;
    private final double ACC_BALANCE=1234;
    @InjectMocks
    private BankAccountServiceImpl underTest;
    @Mock
    private BankAccountOperationService bankAccountOperationService;
    @Mock
    BankAccountRepository bankAccountRepository;
    @BeforeEach
    void setUp() {
        underTest=new BankAccountServiceImpl();
        underTest.setBankAccountOperationService(bankAccountOperationService);
        underTest.setBankAccountRepository(bankAccountRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    @DisplayName("test a debit operation")
    void debitAmount() {
        BankAccount bankAccount=BankAccount.builder()
                .id(ACC_ID)
                .balance(ACC_BALANCE)
                .createdAt(new Date())
                .build();
        given(bankAccountRepository.findById(anyString())).willReturn(Optional.of(bankAccount));  //BDD test writing style
        //when(bankAccountRepository.findById(ACC_ID)).thenReturn(Optional.of(bankAccount));  //general unit test style
        underTest.debit(ACC_ID,AMOUNT,"Debit amount");
        verify(bankAccountOperationService).saveOperation(eq(OperationType.DEBIT), eq(AMOUNT), eq("Debit amount"), any(Date.class), eq(bankAccount));
        ArgumentCaptor<BankAccount> bankAccountArgumentCaptor =
                ArgumentCaptor.forClass(BankAccount.class);

        verify(bankAccountRepository)
                .save(bankAccountArgumentCaptor.capture());

        BankAccount capturedAccount = bankAccountArgumentCaptor.getValue();

        assertThat(capturedAccount.getBalance()).isEqualTo(ACC_BALANCE-AMOUNT);
    }
@Test
@DisplayName("test a debit operation when amount is greater that the sold")
void debitWillThrowBalanaceNotSufficientWhenAmountIsGreaterThanBalance(){
    BankAccount bankAccount=BankAccount.builder()
            .id(ACC_ID)
            .balance(ACC_BALANCE)
            .createdAt(new Date())
            .build();

    when(bankAccountRepository.findById(ACC_ID)).thenReturn(Optional.of(bankAccount));
    assertThatThrownBy(() -> underTest.debit(ACC_ID,AMOUNT*1000,"Debit amount"))
            .isInstanceOf(BalanceNotSufficientException.class)
            .hasMessageContaining("balance not sufficient");
}
    @Test
    @DisplayName("Test a credit operation")
    void creditAmount() {
        BankAccount bankAccount=BankAccount.builder()
                .id(ACC_ID)
                .balance(ACC_BALANCE)
                .createdAt(new Date())
                .build();
        when(bankAccountRepository.findById(ACC_ID)).thenReturn(Optional.of(bankAccount));
        underTest.credit(ACC_ID,AMOUNT,"Credit amount");
        verify(bankAccountOperationService).saveOperation(eq(OperationType.CREDIT), eq(AMOUNT), eq("Credit amount"), any(Date.class), eq(bankAccount));
        ArgumentCaptor<BankAccount> bankAccountArgumentCaptor =
                ArgumentCaptor.forClass(BankAccount.class);

        verify(bankAccountRepository)
                .save(bankAccountArgumentCaptor.capture());

        BankAccount capturedAccount = bankAccountArgumentCaptor.getValue();

        assertThat(capturedAccount.getBalance()).isEqualTo(ACC_BALANCE+AMOUNT);
    }
}