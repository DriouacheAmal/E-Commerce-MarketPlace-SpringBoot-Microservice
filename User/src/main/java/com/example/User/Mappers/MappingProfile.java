package com.example.User.Mappers;

import com.example.User.Entity.DTO.UserDto;
import com.example.User.enums.UserRole;
import com.example.User.Entity.User;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MappingProfile {
    private static final ModelMapper modelMapper = new ModelMapper();
    public static User mapToUserEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public static UserDto mapToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
    static {
        Converter<User, UserDto> userToUserResponseDtoConverter = new Converter<User, UserDto>() {
            public UserDto convert(MappingContext<User, UserDto> context) {
                User source = context.getSource();
                UserDto destination = new UserDto();
                destination.setId(source.getId());
                destination.setEmail(source.getEmail());
                destination.setUsername(source.getUsername());
                destination.setFirstname(source.getFirstname());
                destination.setLastname(source.getLastname());
                destination.setPhoneNumber(source.getPhoneNumber());
                destination.setPassword(source.getPassword());
                destination.setUserRole(source.getUserRole());
                destination.setActive(source.getActive());
                return destination;
            }
        };
        modelMapper.addConverter(userToUserResponseDtoConverter);
    }
   /* public static UserDto toDTO(UserRole role){
        if(role == null) return null;
        UserDto userDto = new UserDto();
        userDto.setId(role.getId());
        userDto.setUserRole(role.getUserRole());
        return userDto;
    }

    public static UserDto userToDto(User user) {
        if(user == null) return null;
        UserDto userDTO = new UserDto();
        userDTO.setUserId(user.getUserId());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setEmail(user.getEmail());
        userDTO.setRoles(MappingProfile.roleDTOList(user.getRoles().stream().toList()));
        userDTO.setPassword(user.getPassword());
        userDTO.setIsActive(user.getActive());
        userDTO.setEnabled(user.isEnabled());
        userDTO.setConfirmationToken(user.getConfirmationToken());
        userDTO.setResetPasswordToken(user.getResetPasswordToken());
        return userDTO;
    }
    public static Set<UserRole> roleDTOList(List<UserRole> roleList){
        if(roleList == null || roleList.isEmpty()) return null;
        return roleList.stream()
                .map(MappingProfile::toDTO)
                .collect(Collectors.toList());
    }*/

    /*public static UserDto toUserDto(User user){
        if(user == null) return null;
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setEmail(user.getEmail());
        userDto.setUserRole(user.getRoles().stream().map(UserRole::name).collect(Collectors.toList()));
        userDto.setPassword(user.getPassword());
        userDto.setIsActive(user.getActive());
        userDto.setEnabled(user.isEnabled());
        userDto.setConfirmationToken(user.getConfirmationToken());
        userDto.setResetPasswordToken(user.getResetPasswordToken());
        return userDto;
    }*/



}
