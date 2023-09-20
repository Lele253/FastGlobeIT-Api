package apis.Manga.API.Controller;

import apis.Manga.API.Entety.User;
import apis.Manga.API.Repository.UserRepository;
import apis.Manga.API.Security.JwtAuthentificationFilter;
import apis.Manga.API.Security.JwtTokenProvider;
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


    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;


    }

    @CrossOrigin
    @PostMapping(value = "/regist")
    public ResponseEntity<User> register(@RequestBody AuthRequest authRequest) {
        Optional<User> userOptional = userRepository.findByEmail(authRequest.getEmail());
        if (userOptional.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        User user = new User();
        user.setEmail(authRequest.getEmail());
        user.setName(authRequest.getUsername());

        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        User created = userRepository.save(user);
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

    @GetMapping("/user/all/{nutzerId}")
    public User leseNutzerListe(@PathVariable long nutzerId) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(nutzerId));
        if (user.isPresent()) {
            return user.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
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




