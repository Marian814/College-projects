package Model;

public class Client {
    private int ID;
    private int arrivalTime;
    private int serviceTime;
    private int timeEnteredQueue;
    private int timeStartedService;
    private int timeFinishedService;

    public Client(int ID, int arrivalTime, int serviceTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getTimeEnteredQueue() {
        return timeEnteredQueue;
    }
    public void setTimeEnteredQueue(int time) {
        this.timeEnteredQueue = time;
    }

    public int getTimeStartedService() {
        return timeStartedService;
    }
    public void setTimeStartedService(int time) {
        this.timeStartedService = time;
    }

    public int getTimeFinishedService() {
        return timeFinishedService;
    }
    public void setTimeFinishedService(int time) {
        this.timeFinishedService = time;
    }
}
