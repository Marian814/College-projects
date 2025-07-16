package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public non-sealed class ComplexTask extends Task implements Serializable {
    private static Map<Integer, List<SimpleTask>> complexTaskLists = new HashMap<>();
    private int complexListId;

    public ComplexTask(int idTask, String statusTask, String title, int complexListId) {
        super(idTask, statusTask, title);
        this.complexListId = complexListId;
        if (!(complexTaskLists.containsKey(complexListId))) {
            complexTaskLists.put(complexListId, new ArrayList<>());
        }
    }

    public int getComplexListId() {
        return complexListId;
    }

    public void setComplexListId(int complexListId) {
        this.complexListId = complexListId;
    }

    public int estimateDuration() {
        int totalDuration = 0;
        for (List<SimpleTask> taskList : complexTaskLists.values()) {
            for (Task task : taskList) {
                totalDuration += task.estimateDuration();
            }
        }
        return totalDuration;
    }

    public int estimateDurationPerList(int complexListId) {
        int totalDuration = 0;
        List<SimpleTask> taskList = complexTaskLists.get(complexListId);
        for (Task task : taskList) {
            totalDuration += task.estimateDuration();
        }
        return totalDuration;
    }

    public static Map<Integer, List<SimpleTask>> getComplexTaskLists() {
        return complexTaskLists;
    }

    public static List<SimpleTask> getTasksByComplexListId(int complexListId) {
        return complexTaskLists.get(complexListId);
    }

    public static void addTask(int idTask, String statusTask, String title, int startHour, int endHour, int complexListId) {
        List<SimpleTask> complexTasks;

        if (complexTaskLists.containsKey(complexListId)) {
            complexTasks = complexTaskLists.get(complexListId);
        } else {
            complexTasks = new ArrayList<>();
            complexTaskLists.put(complexListId, complexTasks);
        }

        SimpleTask newTask = new SimpleTask(idTask, statusTask, title, startHour, endHour);
        complexTasks.add(newTask);
    }

    public static void deleteTask(int idTask, int complexListId) {
        List<SimpleTask> taskList = complexTaskLists.get(complexListId);
        for (Task task : taskList) {
            if (task.getIdTask() == idTask) {
                taskList.remove(task);
                break;
            }
        }
    }
}
