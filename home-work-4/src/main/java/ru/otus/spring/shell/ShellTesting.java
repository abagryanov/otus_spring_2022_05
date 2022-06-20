package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.spring.domain.Testing;
import ru.otus.spring.service.processor.TestingProcessorService;
import ru.otus.spring.service.question.QuestionService;

@ShellComponent
@RequiredArgsConstructor
public class ShellTesting {
    private final QuestionService questionService;

    private final TestingProcessorService testingProcessorService;

    private String userName;

    @ShellMethod(value = "Register command", key = {"r", "register"})
    public String registerUserName(String userName) {
        this.userName = userName;
        return String.format("Welcome, %s", userName);
    }

    @ShellMethod(value = "Start testing command", key = {"s", "start"})
    @ShellMethodAvailability("isStartTestingCommandAvailable")
    public String startTesting() {
        Testing testing = new Testing(questionService.getQuestions());
        testing.setUserName(userName);
        testingProcessorService.processTesting(testing);
        return String.format("Testing is finished for: %s", userName);
    }

    private Availability isStartTestingCommandAvailable() {
        return userName == null ?
                Availability.unavailable("Enter user name") :
                Availability.available();
    }
}
