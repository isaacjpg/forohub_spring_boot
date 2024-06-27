package com.forohub.infra.security;

import com.forohub.domain.auth.RegisterRequest;
import com.forohub.domain.auth.RegisterResponse;
import com.forohub.infra.errores.UserAlreadyExistsException;
import com.forohub.profile.Profile;
import com.forohub.profile.ProfileRepository;
import com.forohub.domain.users.User;
import com.forohub.domain.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private ProfileRepository profileRepository;

        @Autowired
        private BCryptPasswordEncoder passwordEncoder;


        public RegisterResponse register(RegisterRequest registerRequest) {
            //Validar si el usuario ya existe
            User userFinded  = userRepository.findByEmail(registerRequest.email());
            if(userFinded != null){
                throw new UserAlreadyExistsException("User already exists");
            }

            //Encode Password
            String passwordEncoded = passwordEncoder.encode(registerRequest.password());

            //Buscar perfil
            Profile profile = profileRepository.findByName("USER");
            if(profile == null){
               throw new RuntimeException("Profile not found");
            }


            //Crear usuario
            User user = new User(
                    null,
                    registerRequest.name(),
                    registerRequest.email(),
                    passwordEncoded,
                    profile
            );
            var userCreated = userRepository.save(user);

            return new RegisterResponse("User registered successfully");

        }
}
