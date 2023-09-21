package apis.Manga.API.Service;

import apis.Manga.API.Entety.Paket;
import apis.Manga.API.Entety.Project;
import apis.Manga.API.Entety.User;
import apis.Manga.API.Repository.PaketRepository;
import apis.Manga.API.Repository.ProjectRepository;
import apis.Manga.API.Repository.ToDoRepository;
import apis.Manga.API.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private UserRepository userRepository;
    private ProjectRepository projectRepository;
    private ToDoRepository toDoRepository;
    private PaketRepository paketRepository;
    private UserService userService;

    public ProjectService(UserRepository userRepository, ProjectRepository projectRepository, ToDoRepository toDoRepository, PaketRepository paketRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.toDoRepository = toDoRepository;
        this.paketRepository = paketRepository;
    }

    public Project createProject(Project projekt){
        Project newProjekt = new Project();
        newProjekt.setName(projekt.getName());
        newProjekt.setUrl(projekt.getUrl());
        projectRepository.save(newProjekt);
        return  newProjekt;
    }

    public Project updateProjectbyId(Long id, List<Paket> paket ){
        Optional<Project> projectInstance = projectRepository.findById(id);
        if (projectInstance.isPresent()){
            projectInstance.get().setPakete(paket);
        }
        return null;
    }

}
