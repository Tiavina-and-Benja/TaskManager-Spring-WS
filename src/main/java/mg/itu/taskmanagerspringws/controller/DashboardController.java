package mg.itu.taskmanagerspringws.controller;

import mg.itu.taskmanagerspringws.dto.DashboardDto;
import mg.itu.taskmanagerspringws.service.DashboardService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<EntityModel<DashboardDto>> getDashboard() {
        DashboardDto dto = dashboardService.getDashboard();

        EntityModel<DashboardDto> model = EntityModel.of(dto);
        Link selfLink = linkTo(methodOn(DashboardController.class)
                .getDashboard())
                .withSelfRel();
        model.add(selfLink);
        Link usersLink = linkTo(methodOn(mg.itu.taskmanagerspringws.controller.UserController.class)
                .getAllUsers())
                .withRel("all-users");
        model.add(usersLink);

        return ResponseEntity.ok(model);
    }
}