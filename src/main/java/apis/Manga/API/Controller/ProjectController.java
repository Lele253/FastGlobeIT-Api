package apis.Manga.API.Controller;

import apis.Manga.API.Entety.Project;
import apis.Manga.API.Repository.ProjectRepository;
import apis.Manga.API.Repository.UserRepository;
import apis.Manga.API.Service.ProjectService;
import apis.Manga.API.Service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class ProjectController {
    private UserRepository userRepository;
    private ProjectService projectService;
    private ProjectRepository projectRepository;
    private UserService userService;

    public ProjectController(UserRepository userRepository, ProjectService projectService, ProjectRepository projectRepository, UserService userService) {
        this.userRepository = userRepository;
        this.projectService = projectService;
        this.projectRepository = projectRepository;
        this.userService = userService;
    }




    @GetMapping("/project")
    public List<Project> getAllProject(){
        if (userService.isAdmin()){
        return projectRepository.findAll() ;
        }
        return null;
    }
}
