package apis.Manga.API.Service;

import apis.Manga.API.Entety.User;
import apis.Manga.API.Repository.ProjectRepository;
import apis.Manga.API.Repository.UserRepository;
import apis.Manga.API.Security.JwtTokenProvider;
import apis.Manga.API.request.AuthRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private ProjectRepository projectRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository userRepository, ProjectRepository projectRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }



    public User createUser( AuthRequest authRequest){
        Optional<User> userOptional = userRepository.findByEmail(authRequest.getEmail());
        if (userOptional.isPresent()) {
            return null;
        }

        User user = new User();
        user.setEmail(authRequest.getEmail());
        user.setName(authRequest.getUsername());

        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        User created = userRepository.save(user);
        return created;
    }

}
