package Model;

import java.io.Serializable;

public non-sealed class SimpleTask extends Task implements Serializable {
    private int startHour;
    private int endHour;

    public SimpleTask(int idTask, String statusTask, String title, int startHour, int endHour) {
        super(idTask, statusTask, title);
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int estimateDuration() {
        if (endHour < startHour) {
            endHour += 24;
        }
        return endHour - startHour;
    }
}
