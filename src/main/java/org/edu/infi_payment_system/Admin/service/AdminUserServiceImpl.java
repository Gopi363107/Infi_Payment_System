package org.edu.infi_payment_system.Admin.service;

import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Admin.dto.UserDetailsResponse;
import org.edu.infi_payment_system.Admin.dto.UserSearchResponse;
import org.edu.infi_payment_system.Admin.dto.UserStatsResponse;
import org.edu.infi_payment_system.Admin.dto.UserTransactionResponse;
import org.edu.infi_payment_system.Auth.exception.custom.UserNotFoundException;
import org.edu.infi_payment_system.Transaction.entity.Transactions;
import org.edu.infi_payment_system.Transaction.repository.TransactionRepository;
import org.edu.infi_payment_system.User.entity.Users;
import org.edu.infi_payment_system.User.enums.AccountStatus;
import org.edu.infi_payment_system.User.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements  AdminUserService{

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public UserDetailsResponse getUser(UUID userId) {

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not found"));

        return mapToUserDetailsResponse(user);
    }

    @Override
    public List<UserSearchResponse> searchUser(String name, String email, AccountStatus status) {

        List<Users> users;

        if(name != null && !name.isBlank()){
            users = userRepository.findByNameContainingIgnoreCase(name);
        }
        else if(email != null && !email.isBlank()){
            users = userRepository.findByEmailContainingIgnoreCase(email);
        }
        else if(status != null && !status.name().isBlank()){
            users = userRepository.findByAccountStatus(status);
        }
        else{
            users = userRepository.findAll();
        }

        return users.stream()
                .map(this :: mapToUserSearchResponse)
                .toList();
    }

    @Override
    public List<UserTransactionResponse> getUserTransactions(UUID senderId ,UUID receiverId) {
        List<Transactions> transactions = transactionRepository
                .findBySenderIdOrReceiverId(senderId,receiverId);

        return transactions.stream()
                .map(this :: mapToTransactionResponse)
                .toList();
    }

    @Override
    public void freezeUser(UUID userId) {

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not found"));

        if(user.getAccountStatus() == AccountStatus.BLOCKED){
            throw new IllegalStateException(
                    "User already blocked"
            );
        }

        user.setAccountStatus(AccountStatus.BLOCKED);

        userRepository.save(user);
    }

    @Override
    public void unFreezeUser(UUID userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not found"));

        if(user.getAccountStatus() == AccountStatus.ACTIVE){
            throw new IllegalStateException(
                    "user already active"
            );
        }

        user.setAccountStatus(AccountStatus.ACTIVE);

        userRepository.save(user);
    }

    @Override
    public UserStatsResponse getUserStats() {

        UserStatsResponse response = new UserStatsResponse();

        response.setTotalUsers(userRepository.count());

        response.setActiveUsers(
                userRepository.countByAccountStatus(AccountStatus.ACTIVE));

        response.setSuspendedUsers(
                userRepository.countByAccountStatus(AccountStatus.SUSPENDED));

        return response;
    }

    private UserDetailsResponse mapToUserDetailsResponse(
            Users user) {

        UserDetailsResponse response =
                new UserDetailsResponse();

        response.setUserId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setMobileNumber(user.getMobileNumber());
        response.setVerified(user.getVerified());
        response.setAccountStatus(user.getAccountStatus());
        response.setCreatedAt(user.getCreatedAt());

        return response;
    }

    public  UserSearchResponse mapToUserSearchResponse(
            Users user) {

        UserSearchResponse response =
                new UserSearchResponse();

        response.setUserId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setAccountStatus(user.getAccountStatus());

        return response;
    }

    private UserTransactionResponse mapToTransactionResponse(
            Transactions transaction) {

        UserTransactionResponse response =
                new UserTransactionResponse();

        response.setTransactionId(
                transaction.getTransactionId());

        response.setPaymentId(
                transaction.getPaymentId());

        response.setSenderId(
                transaction.getSenderId());

        response.setReceiverId(
                transaction.getReceiverId());

        response.setAmount(
                transaction.getAmount());

        response.setTransactionStatus(
                transaction.getTransactionStatus());

        response.setCreatedAt(
                transaction.getCreatedAt());

        return response;
    }
}