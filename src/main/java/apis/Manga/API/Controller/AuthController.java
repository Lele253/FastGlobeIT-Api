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

        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;

    }

    @CrossOrigin
    @PostMapping(value = "/regist")
    public ResponseEntity<User> register(@RequestBody User user) {
        if (!userService.isAdmin()) return ResponseEntity.badRequest().build();
        User created = userService.createUser(user);
        if (created == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(created);
    }

    @CrossOrigin
    @PutMapping(value = "/user/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable(value = "id") Long id, User user) {
        if (!userService.isAdmin()) return ResponseEntity.badRequest().build();
        User updatedUser = userService.updateUserById(id, user);
        if (updatedUser == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(updatedUser);
    }


    @GetMapping("/user")
    public Optional<User> getUser() {
        return userRepository.findByEmail(jwtTokenProvider.getUserMailFromToken(JwtAuthentificationFilter.x));
    }


    @DeleteMapping("/user/all/{nutzerId}")
    public Boolean deleteById(@PathVariable(value = "nutzerId") Long Id) {
        if (!userService.isAdmin()) return false;
        userRepository.deleteById(Id);
        return true;
    }


    @CrossOrigin
    @GetMapping("/user/all")
    public List<User> getUserAll() {
        if (!userService.isAdmin()) return null;
        return userRepository.findAll();
    }


    /* @CrossOrigin
     @PostMapping(value = "/login")
     public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
         Map<String, Object> response = userService.login(user);
         if (response == null) return ResponseEntity.badRequest().build();
         return ResponseEntity.ok(response);
     }*/

    @CrossOrigin
    @PostMapping(value = "/login")
    public ResponseEntity<Map<String, Object>>  login(@RequestBody AuthRequest authRequest) {
        System.out.println(authRequest.getPassword());

        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()
                    )
            );
            System.out.println("test1");

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = jwtTokenProvider.generateToken(authentication);

            Optional<User> userDetails = userRepository.findByEmail(authRequest.getEmail());;

            Map<String, Object> response = new HashMap<>();
            response.put("token", jwtToken);
            response.put("user", userDetails);

            System.out.println("test2");
            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }



    }
}




