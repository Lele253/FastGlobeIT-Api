package apis.Manga.API.Controller;

import apis.Manga.API.Entety.User;
import apis.Manga.API.Repository.UserRepository;
import apis.Manga.API.Security.JwtAuthentificationFilter;
import apis.Manga.API.Security.JwtTokenProvider;
import apis.Manga.API.Service.UserService;
import apis.Manga.API.request.AuthRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;


    public AuthController(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;


    }

    @CrossOrigin
    @PostMapping(value = "/regist")
    public ResponseEntity<User> register(@RequestBody AuthRequest authRequest) {

        User created = userService.createUser(authRequest);

        return ResponseEntity.ok(created);
    }

    @CrossOrigin
    @PutMapping(value = "/user/{id}")
    public ResponseEntity<String> updateUserById(@PathVariable(value = "id") Long id, AuthRequest authRequest) {
        Optional<User> selectedUser = userRepository.findById(id);

        if (selectedUser.isPresent()) {
            selectedUser.get().setName(authRequest.getUsername());
            selectedUser.get().setEmail(authRequest.getEmail());
            userRepository.save(selectedUser.get());
            return ResponseEntity.ok(selectedUser.toString());
        }

        return ResponseEntity.badRequest().body("User nicht gefunden");
    }


    @GetMapping("/user")
    public Optional<User> getUser() {
        return userRepository.findByEmail(jwtTokenProvider.getUserMailFromToken(JwtAuthentificationFilter.x));
    }


    @DeleteMapping("/user/all/{nutzerId}")
    public Boolean deleteOrder1(@PathVariable(value = "nutzerId") Long Id) {
        userRepository.deleteById(Id);
        return true;
    }


    @CrossOrigin
    @GetMapping("/user/all")
    public List<User> getUserAll() {
        return userRepository.findAll();
    }


    @CrossOrigin
    @PostMapping(value = "/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = jwtTokenProvider.generateToken(authentication);

            Optional<User> userDetails = userRepository.findByEmail(authRequest.getEmail());
            ;

            Map<String, Object> response = new HashMap<>();
            response.put("token", jwtToken);
            response.put("user", userDetails);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            // Handle authentication failure
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


    }


}




