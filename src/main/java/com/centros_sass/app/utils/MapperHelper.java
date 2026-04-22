package com.centros_sass.app.utils;

import java.time.LocalDateTime;

import com.centros_sass.app.model.profiles.users.User;
import com.centros_sass.app.model.profiles.workers.Worker;

public final class MapperHelper {

    private MapperHelper() {
    }

    public static String buildFullName(Worker worker) {
        if (worker == null) {
            return null;
        }
        return doBuildFullName(worker.getFirstName(), worker.getSecondName(), worker.getFirstSurname(), worker.getSecondSurname());
    }

    public static String buildFullName(User user) {
        if (user == null) {
            return null;
        }
        return doBuildFullName(user.getFirstName(), user.getSecondName(), user.getFirstSurname(), user.getSecondSurname());
    }

    private static String doBuildFullName(String firstName, String secondName, String firstSurname, String secondSurname) {
        if (firstName == null) {
            return null;
        }
        StringBuilder fullName = new StringBuilder(firstName);
        if (secondName != null && !secondName.isEmpty()) {
            fullName.append(" ").append(secondName);
        }
        fullName.append(" ").append(firstSurname);
        if (secondSurname != null && !secondSurname.isEmpty()) {
            fullName.append(" ").append(secondSurname);
        }
        return fullName.toString();
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.toString();
    }
}
