package BusinessLogic;

import Model.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utility {
    private TasksManagement taskManagement;

    public Utility(TasksManagement taskManagement) {
        this.taskManagement = taskManagement;
    }

    public String filters(TasksManagement taskManagement) {
        Map<Employee, Integer> employeeWorkHours = new HashMap<>();
        StringBuilder result = new StringBuilder("Employees with work duration greater than 40 hours:\n");

        for (Employee employee : taskManagement.getAllEmployees()) {
            int totalHours = taskManagement.calculateEmployeeWorkDuration(employee.getIdEmployee());
            employeeWorkHours.put(employee, totalHours);
        }

        List<Map.Entry<Employee, Integer>> filteredEmployees = new ArrayList<>();

        for (Map.Entry<Employee, Integer> entry : employeeWorkHours.entrySet()) {
            if (entry.getValue() > 40) {
                filteredEmployees.add(entry);
            }
        }

        for (int i = 0; i < filteredEmployees.size() - 1; i++) {
            for (int j = i + 1; j < filteredEmployees.size(); j++) {
                if (filteredEmployees.get(i).getValue() > filteredEmployees.get(j).getValue()) {
                    Map.Entry<Employee, Integer> temp = filteredEmployees.get(i);
                    filteredEmployees.set(i, filteredEmployees.get(j));
                    filteredEmployees.set(j, temp);
                }
            }
        }

        for (Map.Entry<Employee, Integer> entry : filteredEmployees) {
            result.append(entry.getKey().getName()).append(" - ").append(entry.getValue()).append(" ore\n");
        }

        return result.toString();
    }

    public String numberOfStatus(TasksManagement taskManagement) {
        Map<String, Map<String, Integer>> nrOfTasks = new HashMap<>();
        StringBuilder result = new StringBuilder("Number of Completed and Uncompleted Tasks:\n");

        for (Employee employee : taskManagement.getAllEmployees()) {
            Map<String, Integer> nrOfTasksPerCategory = new HashMap<>();

            int completedTasks = taskManagement.getCompletedTasks(employee);
            int uncompletedTasks = taskManagement.getUncompletedTasks(employee);

            nrOfTasksPerCategory.put("Completed", completedTasks);
            nrOfTasksPerCategory.put("Uncompleted", uncompletedTasks);

            nrOfTasks.put(employee.getName(), nrOfTasksPerCategory);
        }

        for(Map.Entry<String, Map<String, Integer>> entry : nrOfTasks.entrySet()) {
            String category = entry.getKey();
            Map<String, Integer> nrOfCategory = entry.getValue();
            result.append("Employee: ").append(category).append("\n");
            for (Map.Entry<String, Integer> categoryEntry : nrOfCategory.entrySet()) {
                String categoryName = categoryEntry.getKey();
                int categoryTasks = categoryEntry.getValue();
                result.append("     ").append(categoryName).append(" - ").append(categoryTasks).append("\n");
            }
        }

        return result.toString();
    }
}
