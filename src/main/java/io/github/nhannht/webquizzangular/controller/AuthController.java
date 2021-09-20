package io.github.nhannht.webquizzangular.controller;

import io.github.nhannht.webquizzangular.dto.UserDTO;
import io.github.nhannht.webquizzangular.entity.User;
import io.github.nhannht.webquizzangular.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class AuthController {
    final
    UserRepository userRepository;
    final
    BCryptPasswordEncoder encoder;

   @Autowired
    public AuthController(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Operation(description = "register new user")
    @PostMapping("/api/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid UserDTO userDTO) {
        logger.error("We are using register user");
        logger.error("The user is registing is " + userDTO.getEmail() + userDTO.getPassword());
        Optional<User> testUser = userRepository.findByEmail(userDTO.getEmail());
        if (testUser.isPresent()) {
            logger.error("Bug here? ");
            return ResponseEntity.badRequest().build();
        }
        ModelMapper mapper = new ModelMapper();
        User user = mapper.map(userDTO, User.class);
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(user);

    }

}
