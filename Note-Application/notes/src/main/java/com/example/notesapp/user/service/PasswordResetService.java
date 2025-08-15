package com.example.notesapp.user.service;


import com.example.notesapp.user.dto.PasswordDTO;
import com.example.notesapp.user.dto.UserDTO;
import com.example.notesapp.user.models.entities.PasswordResetToken;
import com.example.notesapp.user.models.entities.UserEntity;
import com.example.notesapp.user.repository.TokenRepository;
import com.example.notesapp.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
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

    public boolean isUserFound(UserDTO userDTO,HttpSession session) {
       Optional<UserEntity> user = userRepository.findByEmail(userDTO.getEmail());
       if (user.isPresent()) {
           session.setAttribute("userId", user.get().getUserid());

        return true;
       }
       return false;

    }

    public  String token=generateToken();

    @Transactional
    public void saveTokenInDB(UserDTO userDTO) {
        try {

            LocalDateTime currentDateTime = LocalDateTime.now();
            LocalDateTime expiryDateTime = currentDateTime.plusMinutes(15);// time which the token will end

            PasswordResetToken resetToken = new PasswordResetToken();

            Optional<UserEntity> user=userRepository.findByEmail(userDTO.getEmail());

            resetToken.setUser(user.get());
            resetToken.setExpiryDateTime(expiryDateTime);
            resetToken.setToken(token);

            tokenRepository.save(resetToken);
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
    public void sendEmail(UserDTO userDTO) {
        try {

            Optional<UserEntity> user = userRepository.findByEmail(userDTO.getEmail());
            String resetLink = resetPasswordBaseUrl + "/" + token;

            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(fromEmail);
            msg.setTo(user.get().getEmail());
            msg.setSubject("Reset Your Password");
            msg.setText("Hello " + user.get().getName() + ",\n\n"
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


    public boolean verifyToken(String token, HttpSession session, PasswordDTO passwordDTO) {
        if (token==null){
            return false;
        }
        PasswordResetToken resetToken=tokenRepository.findByToken(token);
        if (resetToken==null){
            return false;
        }else {
            int  userid= (int) session.getAttribute("userId");

            Optional<UserEntity> user=userRepository.findById(userid);
            if (user.isEmpty()){
                return false;
            }
            if (resetToken.getUser().getUserid()== user.get().getUserid() &&
                    NotExpired(resetToken.getExpiryDateTime())&&
                    !resetToken.isUsed()){
                user.get().setPassword(passwordEncoder.encode(passwordDTO.getPassword()));
                userRepository.save(user.get());
                resetToken.setUsed(true);
                tokenRepository.delete(resetToken);
                return true;
            }else {
//                throw new RuntimeException("Error while updating your new password");
                return false;

            }

        }
    }
/*
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
 */
}

