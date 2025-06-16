package com.example.authify.service;

import org.springframework.context.annotation.Profile;

import com.example.authify.io.ProfileRequest;
import com.example.authify.io.ProfileResponse;

public interface ProfileService {

    ProfileResponse createProfile(ProfileRequest request);
}
