package org.edu.infi_payment_system.Payment.service;

import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Account.entity.BankAccount;
import org.edu.infi_payment_system.Account.repository.AccountRepository;
import org.edu.infi_payment_system.Payment.dto.PaymentRequestDto;
import org.edu.infi_payment_system.Payment.dto.PaymentResponseDto;
import org.edu.infi_payment_system.Payment.entity.BankPayment;
import org.edu.infi_payment_system.Payment.enums.PaymentStatus;
import org.edu.infi_payment_system.Payment.mapper.PaymentMapper;
import org.edu.infi_payment_system.Payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;
    private final PaymentMapper paymentMapper;

    @Override
    @Transactional
    public PaymentResponseDto processPayment(PaymentRequestDto dto){

        // 1.fetch sender account
        BankAccount sender  = accountRepository.findById(dto.getSenderAccountId())
                .orElseThrow(()->new AccountNotFoundException("sender account not found"));

        // 2.fetch receiver account
        BankAccount receiver = accountRepository.findById(dto.getReceiverAccountId())
                .orElseThrow(() -> new AccountNotFoundException("receiver account not found"));

        // 3. create  payment entity (pending)
        BankPayment payment = paymentMapper.toEntity(dto);

        try{

            // 4. check balance
            if(sender.getBalance() < dto.getAmount()){
                throw new InsufficientBalanceException("Insufficient Balance");
            }

            // 5. debit money
            sender.setBalance(sender.getBalance() - dto.getAmount());

            // 6. credit money
            receiver.setBalance(receiver.getBalance() + dto.getAmount());

            // 7. save updated accounts money
            accountRepository.save(sender);
            accountRepository.save(receiver);

            // 8. Make payments status success
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setCompletedAt(LocalDateTime.now());
        }
        catch(Exception e){

            // 9. Mark Payment FAILED
            payment.setStatus(PaymentStatus.FAILED);
            payment.setCompletedAt(LocalDateTime.now());

            // 10. save failed transaction
            paymentRepository.save(payment);

            throw e;
        }

        // 11.save successful payment
        paymentRepository.save(payment);

        return paymentMapper.toResponseDto(payment);
    }

    @Override
    public PaymentResponseDto getPaymentById(Long id){

        BankPayment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentIdNotFoundException("Payment ID not found"));

        return paymentMapper.toResponseDto(payment);
    }

    @Override
    public List<PaymentResponseDto> getPaymentByAccount(Long accountId){
        return paymentRepository.findBySenderAccountIdOrReceiverAccountId(accountId, accountId)
                .stream()
                .map(paymentMapper :: toResponseDto)
                .toList();
    }

    @Override
    public List<PaymentResponseDto> getAllPayments(){
        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper  :: toResponseDto)
                .toList();
    }

    @Override
    public List<PaymentResponseDto> getPaymentByStatus(PaymentStatus status){
        return paymentRepository.findByPaymentStatus(status)
                .stream()
                .map(paymentMapper :: toResponseDto)
                .toList();
    }
}
