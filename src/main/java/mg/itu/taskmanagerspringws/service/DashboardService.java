package mg.itu.taskmanagerspringws.service;

import mg.itu.taskmanagerspringws.dto.DashboardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    private final ProjectService projectService;
    private final UserService userService;

    @Autowired
    public DashboardService(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    public DashboardDto getDashboard() {
        Long projects = projectService.getProjectCount();
        Long users = userService.getTotalUsers();
        return new DashboardDto(projects, users);
    }
}
