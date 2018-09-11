package pl.training.cloud.departments.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Log
@Aspect
@Component
@RequiredArgsConstructor
public class DepartmentChangeNotifier {

    /*@Pointcut("bean(departmentsService) && !execution(* pl.training.cloud.departments.service.DepartmentsService.get*(..)) && args(department)")
    public void onChange(Department department) {
    }*/

    @NonNull
    private Source source;

    @AfterReturning("@annotation(Notify)")
    public void sendNotification() {
        log.info("Sending notification");
        source.output().send(MessageBuilder.withPayload("new-update").build());
    }

}
