package org.edu.infi_payment_system.User.mapper;

import lombok.Getter;
import org.edu.infi_payment_system.User.dto.request.RegisterRequestDto;
import org.edu.infi_payment_system.User.dto.response.UserResponseDto;
import org.edu.infi_payment_system.User.entity.AppUser;
import org.springframework.stereotype.Component;

@Component
@Getter
public class UserMapper {

    public AppUser toEntity(RegisterRequestDto dto){

        if(dto == null)return null;

        AppUser user = new AppUser();
        user.setName(dto.getName().trim());
        user.setEmail(dto.getEmail().trim().toLowerCase());
        user.setMobileNumber(dto.getMobileNumber().trim());
        user.setPassword(dto.getPassword());

        return user;
    }

    public UserResponseDto toResponseDto(AppUser user){
        if(user == null)return null;

        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setMobileNumber(user.getMobileNumber());
        dto.setAccountStatus(user.getAccountStatus() != null ? user.getAccountStatus().name() : null);
        dto.setVerified(user.isVerified());

        return dto;


    }
}
