package com.example.notes.service;

import com.example.notes.model.PasswordResetToken;
import com.example.notes.model.UserEntity;
import com.example.notes.repository.TokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {


    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    TokenRepository tokenRepository;

    @Value("${app.reset.password.url}")
     private  String resetPasswordBaseUrl;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Transactional
    public String sendEmail(UserEntity user) {
        try {
            String resetLink = generateToken(user);
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
            return "Success";
        }catch (Exception ex){
            ex.printStackTrace();
            return "Error";
        }
    }

    @Transactional
    public String generateToken(UserEntity user){
        UUID uuid=UUID.randomUUID(); // the token which is used.
        LocalDateTime currentDateTime=LocalDateTime.now();
        LocalDateTime expiryDateTime=LocalDateTime.now().plusMinutes(15);// time which the token will end

        PasswordResetToken resetToken=new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setExpiryDateTime(expiryDateTime);
        resetToken.setToken(uuid.toString());
        tokenRepository.save(resetToken);
        return resetPasswordBaseUrl+"/"+resetToken.getToken();

    }

    public boolean hasExpired(LocalDateTime  expiryDateTime){

        LocalDateTime currentDateTime=LocalDateTime.now();
        return expiryDateTime.isBefore(currentDateTime);

    }








}
