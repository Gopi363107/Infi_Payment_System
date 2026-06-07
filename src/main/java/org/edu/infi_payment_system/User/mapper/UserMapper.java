package org.edu.infi_payment_system.User.mapper;

import lombok.Getter;
import org.edu.infi_payment_system.User.dto.request.RegisterRequestDto;
import org.edu.infi_payment_system.User.dto.response.UserResponseDto;
import org.edu.infi_payment_system.User.entity.Users;
import org.springframework.stereotype.Component;

@Component
@Getter
public class UserMapper {

    public Users toEntity(RegisterRequestDto dto){

        if(dto == null)return null;

        Users user = new Users();
        user.setName(dto.getName().trim());
        user.setEmail(dto.getEmail().trim().toLowerCase());
        user.setMobileNumber(dto.getMobileNumber());
        user.setPassword(dto.getPassword());

        return user;
    }

    public UserResponseDto toResponseDto(Users user){
        if(user == null)return null;

        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setMobileNumber(user.getMobileNumber());
        dto.setAccountStatus(user.getAccountStatus() != null ? user.getAccountStatus().name() : null);
        dto.setVerified(user.getVerified());

        return dto;


    }
}
