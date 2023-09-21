package apis.Manga.API.Service;

import apis.Manga.API.Entety.User;
import apis.Manga.API.Repository.ProjectRepository;
import apis.Manga.API.Repository.UserRepository;
import apis.Manga.API.Security.JwtAuthentificationFilter;
import apis.Manga.API.Security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
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


    public User createUser(User user) {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            return null;
        }

        User selectedUser = new User();
        selectedUser.setEmail(user.getEmail());
        selectedUser.setName(user.getName());
        selectedUser.setProject(user.getProject());

        selectedUser.setPassword(passwordEncoder.encode(selectedUser.getPassword()));
        User created = userRepository.save(selectedUser);
        return created;
    }

    public User updateUserById(Long id, User user) {
        Optional<User> selectedUser = userRepository.findById(id);


        if (selectedUser.isPresent()) {

            selectedUser.get().setName(user.getName());
            selectedUser.get().setEmail(user.getEmail());
            selectedUser.get().setProject(user.getProject());

            if (selectedUser.get().getPassword() != passwordEncoder.encode(user.getPassword())) {
                selectedUser.get().setPassword(passwordEncoder.encode(user.getPassword()));
            }

            userRepository.save(selectedUser.get());
            return selectedUser.get();
        }
        return null;
    }


    public boolean isAdmin() {
        Optional<User> selectedUser = userRepository.findByEmail(jwtTokenProvider.getUserMailFromToken(JwtAuthentificationFilter.x));
        if (selectedUser.isPresent() && selectedUser.get().getStatus().equals("Admin")) return true;
        return false;
    }

}
