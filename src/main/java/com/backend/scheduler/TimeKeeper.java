package com.backend.scheduler;

import java.time.LocalDate;

public class TimeKeeper {
    private static TimeKeeper instance = null;
    private LocalDate currentDate;

    private TimeKeeper() {
        this.currentDate = LocalDate.now();
    }

    public static TimeKeeper getInstance() {
        if (instance == null) {
            instance = new TimeKeeper();
        }
        return instance;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }
}
