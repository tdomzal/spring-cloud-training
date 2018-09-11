package pl.training.cloud.users.service;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.training.cloud.users.model.ResultPage;
import pl.training.cloud.users.model.User;
import pl.training.cloud.users.repository.UsersRepository;

@Service
public class UsersService {

    private UsersRepository usersRepository;
    private DepartmentsService departmentsService;
    @Setter
    @Value("${defaultDepartmentId}")
    private Long defaultDepartmentId;

    public UsersService(UsersRepository usersRepository, @Qualifier("feignDepartmentsService") DepartmentsService departmentsService) {
        this.usersRepository = usersRepository;
        this.departmentsService = departmentsService;
    }

    public void addUser(User user) {
        if (user.getDepartmentId() == null) {
            user.setDepartmentId(defaultDepartmentId);
        }
        usersRepository.saveAndFlush(user);
    }

    public ResultPage<User> getUsers(int pageNumber, int pageSize) {
        Page<User> usersPage = usersRepository.findAll(PageRequest.of(pageNumber, pageSize));
        fetchDepartments(usersPage);
        return new ResultPage<>(usersPage.getContent(), usersPage.getNumber(), usersPage.getTotalPages());
    }

    private void fetchDepartments(Page<User> usersPage) {
        usersPage.getContent().forEach(user -> departmentsService.getDepartmentName(user.getDepartmentId())
                    .ifPresent(user::setDepartmentName));
    }

}
