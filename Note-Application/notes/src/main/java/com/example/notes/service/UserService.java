package com.example.notes.service;


import com.example.notes.dto.UserDTO;
import com.example.notes.model.UserEntity;
import com.example.notes.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


   /* public List<NoteEntity> userNote(int user_id){
   Get the user's notes
      Optional<UserEntity> user=userRepository.findById(user_id);
        if (user.isPresent()){
            return noteRepository.findByUserEntity_UserId(user_id);
        }
        return List.of();
    }*/

    public void register(UserDTO userDTO){
        try {
            UserEntity user = new UserEntity();
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userRepository.save(user);

        }catch (Exception e){
            throw new RuntimeException("Error while your registration");
        }
    }

    public void changeProfile( UserDTO user, Authentication authentication) {
        try {
            String currentEmailUser = authentication.getName();
            log.info("User name is: {}", currentEmailUser);

            UserEntity user1 = userRepository.findByEmail(currentEmailUser);
            if (user.getName() != null) {
                user1.setName(user.getName());
            }
            if (user.getEmail() != null) {
                user1.setEmail(user.getEmail());
            }
            if (user.getPassword() != null) {
                user1.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            userRepository.save(user1);
        }catch (Exception ex){
            log.error("Error while updating profile", ex);
            throw new RuntimeException("Something went wrong while updating the profile");
        }
    }


}
