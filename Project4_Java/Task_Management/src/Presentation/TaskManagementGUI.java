package Presentation;

import BusinessLogic.*;
import Model.*;
import DataAccess.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskManagementGUI {
    private TasksManagement taskManager;
    private Utility utility;
    private JFrame frame;
    private JTextField employeeNameField, employeeIdField, taskIdField, taskTitleField, taskStartHourField, taskEndHourField;
    private JCheckBox complexTaskCheckBox;
    private JComboBox<String> statusComboBox;

    public TaskManagementGUI() {
        taskManager = new TasksManagement();
        utility = new Utility(taskManager);
        initializeUI();
    }

    private void initializeUI() {
        JFrame frame = new JFrame("Task Management");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.getContentPane().setBackground(new Color(245, 222, 179));
        Color buttonColor = new Color(205, 92, 45);

        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        employeeNameField = new JTextField(10);
        employeeIdField = new JTextField(10);
        taskIdField = new JTextField(10);
        taskTitleField = new JTextField(10);
        taskStartHourField = new JTextField(10);
        taskEndHourField = new JTextField(10);
        complexTaskCheckBox = new JCheckBox("Complex Task");
        complexTaskCheckBox.setBackground(new Color(245, 222, 179));
        statusComboBox = new JComboBox<>(new String[]{"Completed", "Uncompleted"});
        statusComboBox.setBackground(new Color(245, 222, 179));

        JButton addEmployeeButton = new JButton("Add Employee");
        addEmployeeButton.setBackground(buttonColor);
        addEmployeeButton.setForeground(Color.WHITE);
        JButton removeEmployeeButton = new JButton("Remove Employee");
        removeEmployeeButton.setBackground(buttonColor);
        removeEmployeeButton.setForeground(Color.WHITE);
        JButton addTaskButton = new JButton("Add Task");
        addTaskButton.setBackground(buttonColor);
        addTaskButton.setForeground(Color.WHITE);
        JButton removeTaskButton = new JButton("Remove Task");
        removeTaskButton.setBackground(buttonColor);
        removeTaskButton.setForeground(Color.WHITE);
        JButton showEmployeesButton = new JButton("Show Employees");
        showEmployeesButton.setBackground(buttonColor);
        showEmployeesButton.setForeground(Color.WHITE);
        JButton showEmployeeTasksButton = new JButton("Show Employee Tasks");
        showEmployeeTasksButton.setBackground(buttonColor);
        showEmployeeTasksButton.setForeground(Color.WHITE);
        JButton showSimpleTasksButton = new JButton("Show Simple Tasks");
        showSimpleTasksButton.setBackground(buttonColor);
        showSimpleTasksButton.setForeground(Color.WHITE);
        JButton showComplexTasksButton = new JButton("Show Complex Tasks");
        showComplexTasksButton.setBackground(buttonColor);
        showComplexTasksButton.setForeground(Color.WHITE);
        JButton assignTaskButton = new JButton("Assign Task");
        assignTaskButton.setBackground(buttonColor);
        assignTaskButton.setForeground(Color.WHITE);
        JButton modifyTaskButton = new JButton("Modify Task");
        modifyTaskButton.setBackground(buttonColor);
        modifyTaskButton.setForeground(Color.WHITE);
        JButton showStatisticsNr1Button = new JButton("Work duration > 40 hours");
        showStatisticsNr1Button.setBackground(buttonColor);
        showStatisticsNr1Button.setForeground(Color.WHITE);
        JButton showStatisticsNr2Button = new JButton("Completed/Uncompleted tasks");
        showStatisticsNr2Button.setBackground(buttonColor);
        showStatisticsNr2Button.setForeground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(new JLabel("Employee Name:"), gbc);
        gbc.gridx = 1;
        frame.add(employeeNameField, gbc);
        gbc.gridx = 2;
        frame.add(new JLabel("Employee ID:"), gbc);
        gbc.gridx = 3;
        frame.add(employeeIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(addEmployeeButton, gbc);
        gbc.gridx = 1;
        frame.add(removeEmployeeButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(new JLabel("\n"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(new JLabel("Task ID:"), gbc);
        gbc.gridx = 1;
        frame.add(taskIdField, gbc);
        gbc.gridx = 2;
        frame.add(new JLabel("Task Title:"), gbc);
        gbc.gridx = 3;
        frame.add(taskTitleField, gbc);
        gbc.gridx = 4;
        frame.add(new JLabel("Task Status:"), gbc);
        gbc.gridx = 5;
        frame.add(statusComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(new JLabel("Task Start Hour:"), gbc);
        gbc.gridx = 1;
        frame.add(taskStartHourField, gbc);
        gbc.gridx = 2;
        frame.add(new JLabel("Task End Hour:"), gbc);
        gbc.gridx = 3;
        frame.add(taskEndHourField, gbc);
        gbc.gridx = 4;
        frame.add(complexTaskCheckBox, gbc);
        gbc.gridx = 5;

        gbc.gridx = 0;
        gbc.gridy = 5;
        frame.add(addTaskButton, gbc);
        gbc.gridx = 1;
        frame.add(removeTaskButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        frame.add(new JLabel("\n"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        frame.add(showEmployeesButton, gbc);
        gbc.gridx = 1;
        frame.add(showEmployeeTasksButton, gbc);
        gbc.gridx = 2;
        frame.add(showSimpleTasksButton, gbc);
        gbc.gridx = 3;
        frame.add(showComplexTasksButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        frame.add(assignTaskButton, gbc);
        gbc.gridx = 1;
        frame.add(modifyTaskButton, gbc);
        gbc.gridx = 2;
        frame.add(showStatisticsNr1Button, gbc);
        gbc.gridx = 3;
        frame.add(showStatisticsNr2Button, gbc);

        addEmployeeButton.addActionListener(this::addEmployee);
        removeEmployeeButton.addActionListener(this::removeEmployee);
        addTaskButton.addActionListener(this::addTask);
        removeTaskButton.addActionListener(this::removeTask);
        showEmployeesButton.addActionListener(this::showEmployees);
        showEmployeeTasksButton.addActionListener(this::showEmployeeTasks);
        showSimpleTasksButton.addActionListener(this::showSimpleTasks);
        showComplexTasksButton.addActionListener(this::showComplexTasks);
        assignTaskButton.addActionListener(this::assignTask);
        modifyTaskButton.addActionListener(this::modifyTask);
        showStatisticsNr1Button.addActionListener(this::statisticsNr1);
        showStatisticsNr2Button.addActionListener(this::statisticsNr2);

        frame.setVisible(true);
    }

    private void addEmployee(ActionEvent e) {
        int id = Integer.parseInt(employeeIdField.getText());
        String name = employeeNameField.getText();
        taskManager = SerializationOperations.Deserialization();
        taskManager.addEmployee(id, name);
        SerializationOperations.Serialization(taskManager);
        JOptionPane.showMessageDialog(frame, "Employee Added");
        employeeNameField.setText("");
        employeeIdField.setText("");
    }

    private void removeEmployee(ActionEvent e) {
        int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Employee ID:"));
        taskManager = SerializationOperations.Deserialization();
        taskManager.removeEmployee(id);
        SerializationOperations.Serialization(taskManager);
        JOptionPane.showMessageDialog(frame, "Employee Removed");
        employeeNameField.setText("");
        employeeIdField.setText("");
    }

    private void showEmployees(ActionEvent e) {
        StringBuilder employees = new StringBuilder("Employees:\n");
        taskManager = SerializationOperations.Deserialization();
        for (Employee emp : taskManager.getAllEmployees()) {
            employees.append(emp.getIdEmployee()).append(" - ").append(emp.getName()).append(" : ")
                    .append("Work duration ")
                    .append(taskManager.calculateEmployeeWorkDuration(emp.getIdEmployee()))
                    .append("\n");
        }
        JOptionPane.showMessageDialog(frame, employees.toString());
    }

    private void addTask(ActionEvent e) {
        int idTask = Integer.parseInt(taskIdField.getText());
        String title = taskTitleField.getText();
        String status = (String) statusComboBox.getSelectedItem();
        int startHour = Integer.parseInt(taskStartHourField.getText());
        int endHour = Integer.parseInt(taskEndHourField.getText());
        taskManager = SerializationOperations.Deserialization();
        if (complexTaskCheckBox.isSelected()) {
            int complexListId = Integer.parseInt(JOptionPane.showInputDialog("Enter Complex List ID:"));
            SimpleTask complexTask = new SimpleTask(idTask, status, title, startHour, endHour);
            taskManager.getComplexTasks().computeIfAbsent(complexListId, k -> new ArrayList<>()).add(complexTask);
        }
        else {
            SimpleTask simpleTask = new SimpleTask(idTask, status, title, startHour, endHour);
            taskManager.getSimpleTasks().add(simpleTask);
        }
        SerializationOperations.Serialization(taskManager);
        JOptionPane.showMessageDialog(frame, "Task Added");
        taskIdField.setText("");
        taskTitleField.setText("");
        taskStartHourField.setText("");
        taskEndHourField.setText("");
    }

    private void removeTask(ActionEvent e) {
        int idTask = Integer.parseInt(JOptionPane.showInputDialog("Enter Task ID:"));
        taskManager = SerializationOperations.Deserialization();
        for (Task task : taskManager.getSimpleTasks()) {
            if (task.getIdTask() == idTask) {
                taskManager.getSimpleTasks().remove(task);
                taskManager.removeTaskFromEmployee(idTask, task);
                SerializationOperations.Serialization(taskManager);
                JOptionPane.showMessageDialog(frame, "Task Removed");
                return;
            }
        }
        for (Map.Entry<Integer, List<SimpleTask>> entry : taskManager.getComplexTasks().entrySet()) {
            List<SimpleTask> tasks = entry.getValue();
            for (SimpleTask task : tasks) {
                if (task.getIdTask() == idTask) {
                    taskManager.getComplexTasks().remove(task);
                    taskManager.removeTaskFromEmployee(idTask, task);
                    SerializationOperations.Serialization(taskManager);
                    JOptionPane.showMessageDialog(frame, "Task Removed");
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(frame, "Task not found");
    }

    private void assignTask(ActionEvent e) {
        int employeeId = Integer.parseInt(JOptionPane.showInputDialog("Enter Employee ID:"));
        int taskId = Integer.parseInt(JOptionPane.showInputDialog("Enter Task ID:"));
        taskManager = SerializationOperations.Deserialization();
        for (Task task : taskManager.getSimpleTasks()) {
            if (task.getIdTask() == taskId) {
                taskManager.assignTaskToEmployee(employeeId, task);
                SerializationOperations.Serialization(taskManager);
                JOptionPane.showMessageDialog(frame, "Task Assigned");
                return;
            }
        }
        for (Map.Entry<Integer, List<SimpleTask>> entry : taskManager.getComplexTasks().entrySet()) {
            List<SimpleTask> tasks = entry.getValue();
            for (SimpleTask task : tasks) {
                if (task.getIdTask() == taskId) {
                    taskManager.assignTaskToEmployee(employeeId, task);
                    SerializationOperations.Serialization(taskManager);
                    JOptionPane.showMessageDialog(frame, "Task Assigned");
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(frame, "Task not found");
    }

    private void modifyTask(ActionEvent e) {
        int employeeId = Integer.parseInt(JOptionPane.showInputDialog("Enter Employee ID:"));
        int taskId = Integer.parseInt(JOptionPane.showInputDialog("Enter Task ID:"));
        taskManager = SerializationOperations.Deserialization();
        taskManager.modifyTaskStatus(employeeId, taskId);
        SerializationOperations.Serialization(taskManager);
        JOptionPane.showMessageDialog(frame, "Task Status Modified!");
    }

    private void showEmployeeTasks(ActionEvent e) {
        int idEmployee = Integer.parseInt(JOptionPane.showInputDialog("Enter Employee ID:"));
        taskManager = SerializationOperations.Deserialization();
        List<Task> tasks = taskManager.getTasksForAnEmployee(idEmployee);
        StringBuilder taskList = new StringBuilder("Tasks:\n");
        for (Task task : tasks) {
            taskList.append(task.getIdTask()).append(" - ").append(task.getTitle()).append(" (Status: ")
                    .append(task.getStatusTask()).append(", Estimated Duration: ")
                    .append(task.estimateDuration()).append(")\n");
        }
        JOptionPane.showMessageDialog(frame, taskList.toString());
    }

    private void showSimpleTasks(ActionEvent e) {
        StringBuilder simpleTasks = new StringBuilder("Simple Tasks:\n");
        taskManager = SerializationOperations.Deserialization();
        for (SimpleTask task : taskManager.getSimpleTasks()) {
            simpleTasks.append("- Task ID: ").append(task.getIdTask())
                    .append(", Title: ").append(task.getTitle())
                    .append(", Status: ").append(task.getStatusTask())
                    .append(", Estimated Duration: ").append(task.estimateDuration()).append(" hours\n");
        }
        JOptionPane.showMessageDialog(frame, simpleTasks.toString());
    }

    private void showComplexTasks(ActionEvent e) {
        StringBuilder complexTasks = new StringBuilder("Complex Task Lists:\n");
        taskManager = SerializationOperations.Deserialization();
        for (Map.Entry<Integer, List<SimpleTask>> entry : taskManager.getComplexTasks().entrySet()) {
            complexTasks.append("List ID: ").append(entry.getKey()).append("\n");
            List<SimpleTask> tasks = entry.getValue();
            for (SimpleTask task : tasks) {
                complexTasks.append("  - Task ID: ").append(task.getIdTask())
                        .append(", Title: ").append(task.getTitle())
                        .append(", Status: ").append(task.getStatusTask())
                        .append(", Estimated Duration: ").append(task.estimateDuration()).append(" hours\n");
            }
        }
        JOptionPane.showMessageDialog(frame, complexTasks.toString());
    }

    private void statisticsNr1(ActionEvent e) {
        taskManager = SerializationOperations.Deserialization();
        String stats = utility.filters(taskManager);
        JOptionPane.showMessageDialog(frame, stats, "StatisticsNr2", JOptionPane.INFORMATION_MESSAGE);
    }

    private void statisticsNr2(ActionEvent e) {
        taskManager = SerializationOperations.Deserialization();
        String stats = utility.numberOfStatus(taskManager);
        JOptionPane.showMessageDialog(frame, stats, "StatisticsNr1", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new TaskManagementGUI();
    }
}
