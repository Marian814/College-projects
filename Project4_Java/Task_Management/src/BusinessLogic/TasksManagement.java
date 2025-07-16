package BusinessLogic;

import Model.Employee;
import Model.Task;
import Model.SimpleTask;

import java.io.Serializable;
import java.util.*;

public class TasksManagement implements Serializable {
    private Map<Employee, List<Task>> employeeTasks;
    private Map<Integer, Employee> idToEmployee;
    private List<SimpleTask> simpleTasks;
    private Map<Integer, List<SimpleTask>> complexTasks;

    public TasksManagement() {
        this.employeeTasks = new HashMap<>();
        this.idToEmployee = new HashMap<>();
        this.simpleTasks = new ArrayList<>();
        this.complexTasks = new HashMap<>();
    }

    public Map<Employee, List<Task>> getEmployeeTasks() {
        return employeeTasks;
    }

    public List<SimpleTask> getSimpleTasks() {
        return simpleTasks;
    }

    public Map<Integer, List<SimpleTask>> getComplexTasks() {
        return complexTasks;
    }

    public void addEmployee(int idEmployee, String name) {
        Employee employee = new Employee(idEmployee, name);
        idToEmployee.put(idEmployee, employee);
        employeeTasks.putIfAbsent(employee, new ArrayList<>());
    }

    public void removeEmployee(int idEmployee) {
        Employee employee = idToEmployee.remove(idEmployee);
        if (employee != null) {
            employeeTasks.remove(employee);
        }
    }

    public void assignTaskToEmployee(int idEmployee, Task task) {
        Employee employee = idToEmployee.get(idEmployee);
        employeeTasks.computeIfAbsent(employee, k -> new ArrayList<>()).add(task);
    }

    public void removeTaskFromEmployee(int idEmployee, Task task) {
        Employee employee = idToEmployee.get(idEmployee);
        employeeTasks.computeIfAbsent(employee, k -> new ArrayList<>()).remove(task);
    }

    public List<Task> getTasksForAnEmployee(int idEmployee) {
        Employee employee = idToEmployee.get(idEmployee);
        return employeeTasks.get(employee);
    }

    public int calculateEmployeeWorkDuration(int idEmployee) {
        Employee employee = idToEmployee.get(idEmployee);
        List<Task> tasks = employeeTasks.get(employee);
        int workDuration = 0;
        for (Task task : tasks) {
            if(task.getStatusTask().equals("Completed")) {
                workDuration += task.estimateDuration();
            }
        }
        return workDuration;
    }

    public int calculateAllEmployeeWorkDuration(int idEmployee) {
        Employee employee = idToEmployee.get(idEmployee);
        List<Task> tasks = employeeTasks.get(employee);
        int workDuration = 0;
        for (Task task : tasks) {
            workDuration += task.estimateDuration();
        }
        return workDuration;
    }

    public void modifyTaskStatus(int idEmployee, int idTask){
        Employee employee = idToEmployee.get(idEmployee);
        List<Task> tasks = employeeTasks.get(employee);
        for (Task task : tasks) {
            if(task.getIdTask() == idTask) {
                if(task.getStatusTask().equals("Completed")) {
                    task.setStatusTask("Uncompleted");
                }
                else {
                    task.setStatusTask("Completed");
                }
            }
        }
    }

    public Collection<Employee> getAllEmployees() {
        return idToEmployee.values();
    }

    public int getCompletedTasks(Employee employee) {
        List<Task> tasks = employeeTasks.get(employee);
        int completedTasks = 0;
        for (Task task : tasks) {
            if(task.getStatusTask().equals("Completed")) {
                completedTasks++;
            }
        }
        return completedTasks;
    }

    public int getUncompletedTasks(Employee employee) {
        List<Task> tasks = employeeTasks.get(employee);
        int UncompletedTasks = 0;
        for (Task task : tasks) {
            if(task.getStatusTask().equals("Uncompleted")) {
                UncompletedTasks++;
            }
        }
        return UncompletedTasks;
    }
}
