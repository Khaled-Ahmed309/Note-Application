package com.example.notes.service;

import com.example.notes.dto.PasswordDTO;
import com.example.notes.dto.UserDTO;
import com.example.notes.model.PasswordResetToken;
import com.example.notes.model.UserEntity;
import com.example.notes.repository.TokenRepository;
import com.example.notes.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {


    private final JavaMailSender javaMailSender;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetService(JavaMailSender javaMailSender, TokenRepository tokenRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.javaMailSender = javaMailSender;
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Value("${app.reset.password.url}")
    private String resetPasswordBaseUrl;
    @Value("${spring.mail.username}")
    private String fromEmail;

//    PasswordResetToken resetToken = new PasswordResetToken();

    public boolean isUserFound(UserDTO userDTO, HttpSession httpSession) {
        UserEntity user = userRepository.findByEmail(userDTO.getEmail());
        httpSession.setAttribute("userInformation", user);

        return user != null;

    }

    @Transactional
    public void saveTokenInDB(HttpSession httpSession) {
        try {

            String token = generateToken();
            UserEntity user = (UserEntity) httpSession.getAttribute("userInformation");
            LocalDateTime currentDateTime = LocalDateTime.now();
            LocalDateTime expiryDateTime = currentDateTime.plusMinutes(15);// time which the token will end

            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setUser(user);
            resetToken.setExpiryDateTime(expiryDateTime);
            resetToken.setToken(token);
            tokenRepository.save(resetToken);
            httpSession.setAttribute("TokenInformation",resetToken);
        } catch (Exception e) {
            throw new RuntimeException("Error while creating the code.");
        }
    }


    @Transactional
    public String generateToken() {
        UUID uuid = UUID.randomUUID(); // the token which is used.
        return uuid.toString();

    }

    @Transactional
    public void sendEmail(HttpSession httpSession) {
        try {

            PasswordResetToken resetToken =(PasswordResetToken) httpSession.getAttribute("TokenInformation");
            UserEntity user = (UserEntity) httpSession.getAttribute("userInformation");

            if (user==null||resetToken==null){
                throw new IllegalStateException("Missing user or token information in session");

            }
            String resetLink = resetPasswordBaseUrl + "/" + resetToken.getToken();

            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(fromEmail);
            msg.setTo(user.getEmail());
            msg.setSubject("Reset Your Password");
            msg.setText("Hello " + user.getName() + ",\n\n"
                    + "Please click the link below to reset your password:\n"
                    + resetLink + "\n\n"
                    + "If you didn't request this, you can ignore this email.\n\n"
                    + "Best regards,\nYour App Team");
            javaMailSender.send(msg);
        } catch (Exception ex) {
            throw new RuntimeException("Error while sending to your email");
        }
    }


    public boolean NotExpired(LocalDateTime expiryDateTime) {

        LocalDateTime currentDateTime = LocalDateTime.now();
        return expiryDateTime.isAfter(currentDateTime);

    }


    public boolean verifyToken(String token, HttpSession httpSession) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token);
        if (resetToken==null){
            return false;
        }
        UserEntity user = resetToken.getUser();
        httpSession.setAttribute("userInformation", user);
        httpSession.setAttribute("TokenInformation", resetToken);
        return true;
    }

    public void saveNewPassword(PasswordDTO passwordDTO, HttpSession httpSession) {
        try {
            UserEntity user = (UserEntity) httpSession.getAttribute("userInformation");
            PasswordResetToken resetToken =(PasswordResetToken) httpSession.getAttribute("TokenInformation");
            if (user == null||resetToken==null) {
                throw new IllegalStateException("Session expired or invalid");
            }
            user.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));
            userRepository.save(user);
            resetToken.setUsed(true);
            tokenRepository.save(resetToken);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error while updating your new password");
        }
    }
}

