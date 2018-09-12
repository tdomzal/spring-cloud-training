package pl.training.cloud.users.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.training.cloud.users.config.DepartmentsConfig;
import pl.training.cloud.users.model.ResultPage;
import pl.training.cloud.users.model.User;
import pl.training.cloud.users.repository.UsersRepository;

@Service
public class UsersService {

    private UsersRepository usersRepository;
    private DepartmentsService departmentsService;
    private DepartmentsConfig departmentsConfig;

    public UsersService(UsersRepository usersRepository,
                        @Qualifier("feignDepartmentsService") DepartmentsService departmentsService,
                        DepartmentsConfig departmentsConfig) {
        this.usersRepository = usersRepository;
        this.departmentsService = departmentsService;
        this.departmentsConfig = departmentsConfig;
    }

    public void addUser(User user) {
        if (user.getDepartmentId() == null) {
            user.setDepartmentId(departmentsConfig.getDefaultDepartmentId());
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
