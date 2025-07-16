package Model;

import java.io.Serializable;

public sealed abstract class Task implements Serializable permits SimpleTask, ComplexTask {
    private int idTask;
    private String statusTask;
    private String title;

    public Task(int idTask, String statusTask, String title) {
        this.idTask = idTask;
        this.statusTask = statusTask;
        this.title = title;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public String getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(String statusTask) {
        this.statusTask = statusTask;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public abstract int estimateDuration();

    @Override
    public String toString() {
        return "Task{" + "idTask=" + idTask + ", statusTask=" + statusTask + ", title='" + title + "'}";
    }
}
