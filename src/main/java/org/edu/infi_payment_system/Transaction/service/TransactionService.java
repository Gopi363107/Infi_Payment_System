package org.edu.infi_payment_system.Transaction.service;


import org.edu.infi_payment_system.Transaction.dto.TransactionRequestDto;
import org.edu.infi_payment_system.Transaction.dto.TransactionResponseDto;
import org.edu.infi_payment_system.Transaction.enums.TransactionStatus;


import java.util.List;
import java.util.UUID;

public interface TransactionService {

    TransactionResponseDto processTransaction(TransactionRequestDto transactionRequest);
    TransactionResponseDto getByPaymentId(UUID paymentId);
    TransactionResponseDto getByTransactionId(UUID transactionId);
    List<TransactionResponseDto> getByTransactionStatus(TransactionStatus status);
    List<TransactionResponseDto> getAllTransaction();

}
/*

Controller Layer
│
├── PaymentController
│
▼
Service Layer
│
├── PaymentService
│       │
│       ├── User Validation
│       ├── Idempotency Check
│       ├── Fraud Check
│       ├── Create Payment(PENDING)
│       │
│       ├── TransactionService
│       │       │
│       │       ├── Lock Sender Account
│       │       ├── Check Balance
│       │       ├── Debit Sender
│       │       ├── Credit Receiver
│       │       ├── Save Accounts
│       │       ├── Create Transaction Record
│       │       └── Create Ledger Entries
│       │
│       ├── Mark Payment SUCCESS
│       ├── Audit Log
│       └── NotificationService
│
▼
Repository Layer
│
├── UserRepository
├── AccountRepository
├── PaymentRepository
├── TransactionRepository
├── LedgerRepository
└── AuditRepository
│
▼
Database


 */
/*
1. Check sender balance
2. Debit sender account
3. Credit receiver account
4. Save updated accounts
5. Create transaction entity
6. Create ledger entries
7. Return success


Payment Package
    -> Create payment request
    -> Track payment status

Transaction Package
    -> Move money
    -> Create transaction record

Ledgers Package
    -> Create debit/credit entries
 */
