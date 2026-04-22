package org.edu.infi_payment_system.User.dto.response;

import lombok.Data;

@Data // it includes all getter.setter.tostring.equals.hashcode
public class UserResponseDto {

    private Long id;
    private String name;
    private String email;
    private String mobileNumber;
}
