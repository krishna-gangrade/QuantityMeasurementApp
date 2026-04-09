package com.app.quantitymeasurement.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.quantitymeasurement.entity.PasswordResetOtp;
import com.app.quantitymeasurement.entity.User;
import com.app.quantitymeasurement.repository.PasswordResetOtpRepository;
import com.app.quantitymeasurement.repository.UserRepository;

@Service
public class PasswordResetService {

    @Autowired
    private PasswordResetOtpRepository otpRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void sendOtp(String email) {

        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);

        PasswordResetOtp resetOtp = new PasswordResetOtp();
        resetOtp.setEmail(email);
        resetOtp.setOtp(otp);
        resetOtp.setExpiryTime(LocalDateTime.now().plusMinutes(5));

        otpRepository.save(resetOtp);

        emailService.sendPasswordResetEmail(email, otp);
    }

    public void resetPassword(String email, String otp, String newPassword) {

        PasswordResetOtp stored = otpRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (!stored.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP");
        }

        if (stored.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        otpRepository.delete(stored);
    }
}