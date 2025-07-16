package DataAccess;

import BusinessLogic.TasksManagement;

import java.io.*;

public class SerializationOperations {

    public static void Serialization(TasksManagement tasksManagement) {
        try(FileOutputStream fileOutputStream = new FileOutputStream("binaryFile.ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){
            objectOutputStream.writeObject(tasksManagement);
            objectOutputStream.close();
        }
        catch(IOException e){
            System.out.println("Serialization failed");
        }
    }

    public static TasksManagement Deserialization() {
        try(FileInputStream fileInputStream = new FileInputStream("binaryFile.ser");
            ObjectInputStream objectInputStream1 = new ObjectInputStream(fileInputStream)){
            TasksManagement tasksManagement = (TasksManagement) objectInputStream1.readObject();
            objectInputStream1.close();
            return tasksManagement;
        }
        catch(IOException | ClassNotFoundException e){
            System.out.println("Deserialization failed");
        }
        return null;
    }
}
