package ru.DailyProblemBot.telegram.action.signup;

import ru.DailyProblemBot.enums.UserPhase;
import ru.DailyProblemBot.main.Main;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.DailyProblemBot.telegram.MessageControl;
import ru.DailyProblemBot.telegram.UserProperties;
import ru.DailyProblemBot.telegram.action.Action;
import ru.DailyProblemBot.telegram.action.ActionEngine;
import ru.DailyProblemBot.util.config.Messages;

public class StudentId extends Action {

    public StudentId(UserPhase phase) {
        super(phase);
    }

    @Override
    public void alert(String chatId, UserProperties properties) {
        properties.setPhase(phase);
        Messages.signup_studentId.send(chatId);
    }

    @Override
    public void handle(String chatId, Message message, UserProperties properties) {

        String text = message.getText();

        if (!Main.getSettings().checkStudentIdByPattern(text)) {
            MessageControl.deleteLastMessage(chatId);
            Messages.signup_errorPattern.send(chatId);
        } else {
            properties.setEnteredStudentId(text);
            ActionEngine.getAction(UserPhase.SIGNUP_EMAIL).alert(chatId, properties);
        }
    }
}
