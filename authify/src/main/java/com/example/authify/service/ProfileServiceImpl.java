package com.example.authify.service;

import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.authify.entity.UserEntity;
import com.example.authify.io.ProfileRequest;
import com.example.authify.io.ProfileResponse;
import com.example.authify.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

    private final UserRepository userRepository;


    @Override
    public ProfileResponse createProfile(ProfileRequest request) {

        UserEntity newProfile = convertToUserEntity(request);
        if (!userRepository.existsByEmail(request.getEmail())) {
             newProfile =userRepository.save(newProfile);
             return convertToProfileResponse(newProfile);
        }
       throw new ResponseStatusException(HttpStatus.CONFLICT,"Email already exists");
    }


    private ProfileResponse convertToProfileResponse(UserEntity newProfile) {
        return ProfileResponse.builder()
                      .email(newProfile.getEmail())
                      .name(newProfile.getName())
                      .userId(newProfile.getUserId())
                      .isAccountBoolean(newProfile.getIsAccountVerified())
                      .build();
    }


    private UserEntity convertToUserEntity(ProfileRequest request) {
        return UserEntity.builder()
                  .email(request.getEmail())
                  .userId(UUID.randomUUID().toString())
                  .name(request.getName())
                  .password(request.getPassword())
                  .isAccountVerified(false)
                  .resetOtpExpireAt(0L)
                  .verifyOtp(null)
                  .verifyOtpExpireAt(0L)
                  .resetOtp(null)
                  .build();    

    }

}
