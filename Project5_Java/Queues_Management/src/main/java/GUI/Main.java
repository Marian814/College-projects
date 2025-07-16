package GUI;

import BusinessLogic.SelectionPolicy;
import BusinessLogic.SimulationManager;
import DataAccess.LogEvents;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        String asciiArt = """
                _`._ `-.__`-.   \\ `- ',_,--' _,- ,-
                `. `._   `-.`-._`  '  _,.--'_,-'  ___
                  `-._`-._  `-._   -''_, '__ ,---_,--.\\
                      `-._'-._ '`   -'_,-'___,--'
                          `-._`-  '  __,-'
         ____                `.   :  |              ___
  %%%%%_/____\\_               :   '  |             (___)_          88888
 /_' %% |',' &                : ;    :    66666,   /' ;;   ,---.  O,O-888
  |=  % \\_-_ /                : ,  . ;   6-,- 666   >  |   ',' &&  --  88 ,,,,
   )_(    )_(                 : ;  ' |   6 =  666   )_(    \\-  &&  _)_(_ .,. \\\\
  /.-.|  /.-.\\                | '    |   66)_6666  /.-.\\    )__(  /   .-| o  ||
 | | || |#|#|#|               ;   ;  |   6/ .-666 | | | |  / .-.\\(_(_ | |  )_(
 | | || |#|#|#|     _____     ;   '  ;   (( |_||6 | |_| | /  | || |   | | / ..|
 |/ / | |#|#|#|   ,'`. \\ |    ;  , ' |  __|_| ||  | | | | |  | || |   | |_|| ||
 /_/__| |#|#|#|  / `-.`.\\|    ; '   ,-_(_|___/ |  |-/ /-| |  | || |___|_(_)__||
(_/###)_|,(_).|_ |`_-_`-_|____'___ //     |____|  |`-'  ) \\__|_|| |===(_| |___|
 ||##||:::::::::||                |/ :    /####\\  |    |   ||(_)) |==/___\\||  )
 ||##||::'''    |\\________________/  |   /######\\ |____|   ||##|  |=(_____)| |
 ||##||_________|      //   \\\\    ' `.    || |    || |    ||##|  |=_==_| || |
_||##|__||   | ______ // ___ \\\\ __________ || |___ || | __ ||##| __ || |__|| |
 ||##|  ||___|     ,--. _____ ,--.         || |    ||_|    ||##|    ||_|  ||_|
((___| <<_,-.|    ((_ ))-----((_ ))        ||_|  ((___|    ;;##|   <<.-! ((__|
                   `--'       `--'        <<.-!           ((___|
""";

        TextArea textArea = new TextArea(asciiArt);
        textArea.setEditable(false);
        textArea.setStyle("-fx-font-family: 'monospaced'; -fx-font-size: 10;");
        textArea.setWrapText(false);
        textArea.setPrefHeight(300);

        VBox formLayout = new VBox(10);
        formLayout.setStyle("-fx-padding: 20;");

        HBox hboxN = new HBox(21);
        hboxN.setAlignment(Pos.CENTER);
        Label labelN = new Label("N (numar de clienti):");
        TextField textFieldN = new TextField();
        textFieldN.setPrefWidth(150);
        hboxN.getChildren().addAll(labelN, textFieldN);

        HBox hboxQ = new HBox(30);
        hboxQ.setAlignment(Pos.CENTER);
        Label labelQ = new Label("Q (numar de cozi):");
        TextField textFieldQ = new TextField();
        textFieldQ.setPrefWidth(150);
        hboxQ.getChildren().addAll(labelQ, textFieldQ);

        HBox hboxTMaxSimulation = new HBox(35);
        hboxTMaxSimulation.setAlignment(Pos.CENTER);
        Label labelTMaxSimulation = new Label("t_max_simulation:");
        TextField textFieldTMaxSimulation = new TextField();
        textFieldTMaxSimulation.setPrefWidth(150);
        hboxTMaxSimulation.getChildren().addAll(labelTMaxSimulation, textFieldTMaxSimulation);

        HBox hboxTArrivalMin = new HBox(60);
        hboxTArrivalMin.setAlignment(Pos.CENTER);
        Label labelTArrivalMin = new Label("t_arrival_min:");
        TextField textFieldTArrivalMin = new TextField();
        textFieldTArrivalMin.setPrefWidth(150);
        hboxTArrivalMin.getChildren().addAll(labelTArrivalMin, textFieldTArrivalMin);

        HBox hboxTArrivalMax = new HBox(60);
        hboxTArrivalMax.setAlignment(Pos.CENTER);
        Label labelTArrivalMax = new Label("t_arrival_max:");
        TextField textFieldTArrivalMax = new TextField();
        textFieldTArrivalMax.setPrefWidth(150);
        hboxTArrivalMax.getChildren().addAll(labelTArrivalMax, textFieldTArrivalMax);

        HBox hboxTServiceMin = new HBox(60);
        hboxTServiceMin.setAlignment(Pos.CENTER);
        Label labelTServiceMin = new Label("t_service_min:");
        TextField textFieldTServiceMin = new TextField();
        textFieldTServiceMin.setPrefWidth(150);
        hboxTServiceMin.getChildren().addAll(labelTServiceMin, textFieldTServiceMin);

        HBox hboxTServiceMax = new HBox(60);
        hboxTServiceMax.setAlignment(Pos.CENTER);
        Label labelTServiceMax = new Label("t_service_max:");
        TextField textFieldTServiceMax = new TextField();
        textFieldTServiceMax.setPrefWidth(150);
        hboxTServiceMax.getChildren().addAll(labelTServiceMax, textFieldTServiceMax);

        HBox hboxStrategy = new HBox(85);
        hboxStrategy.setAlignment(Pos.CENTER);
        Label labelStrategy = new Label("Strategie:");
        ComboBox<String> comboBoxStrategy = new ComboBox<>();
        comboBoxStrategy.setPrefWidth(150);
        comboBoxStrategy.getItems().addAll("SHORTEST_TIME", "SHORTEST_QUEUE");
        hboxStrategy.getChildren().addAll(labelStrategy, comboBoxStrategy);

        formLayout.getChildren().addAll(hboxN, hboxQ, hboxTMaxSimulation, hboxTArrivalMin, hboxTArrivalMax, hboxTServiceMin, hboxTServiceMax, hboxStrategy);

        Button startButton = new Button("START SIMULATION");
        startButton.setPrefWidth(200);

        startButton.setOnAction(e -> {
            int numClients = Integer.parseInt(textFieldN.getText());
            int numQueues = Integer.parseInt(textFieldQ.getText());
            int maxSimulationTime = Integer.parseInt(textFieldTMaxSimulation.getText());
            int arrivalMin = Integer.parseInt(textFieldTArrivalMin.getText());
            int arrivalMax = Integer.parseInt(textFieldTArrivalMax.getText());
            int serviceMin = Integer.parseInt(textFieldTServiceMin.getText());
            int serviceMax = Integer.parseInt(textFieldTServiceMax.getText());

            String str = comboBoxStrategy.getValue();
            SelectionPolicy strategy;
            if (str.equals("SHORTEST_TIME")) {
                strategy = SelectionPolicy.SHORTEST_TIME;
            }
            else {
                strategy = SelectionPolicy.SHORTEST_QUEUE;
            }

            SimulationManager simulationManager = new SimulationManager(numClients, numQueues, maxSimulationTime,
                    arrivalMin, arrivalMax, serviceMin, serviceMax, strategy);

            Scene simulationScene = createSimulationScene(stage, simulationManager);

            stage.setScene(simulationScene);

            Thread simulationThread = new Thread(simulationManager);
            simulationThread.start();
        });

        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(startButton);

        Button test1Button = new Button("TEST 1");
        test1Button.setPrefWidth(100);
        test1Button.setOnAction(e -> {
            textFieldN.setText("4");
            textFieldQ.setText("2");
            textFieldTMaxSimulation.setText("60");
            textFieldTArrivalMin.setText("2");
            textFieldTArrivalMax.setText("30");
            textFieldTServiceMin.setText("2");
            textFieldTServiceMax.setText("4");
        });

        Button test2Button = new Button("TEST 2");
        test2Button.setPrefWidth(100);
        test2Button.setOnAction(e -> {
            textFieldN.setText("50");
            textFieldQ.setText("5");
            textFieldTMaxSimulation.setText("60");
            textFieldTArrivalMin.setText("2");
            textFieldTArrivalMax.setText("40");
            textFieldTServiceMin.setText("1");
            textFieldTServiceMax.setText("7");
        });

        Button test3Button = new Button("TEST 3");
        test3Button.setPrefWidth(100);
        test3Button.setOnAction(e -> {
            textFieldN.setText("1000");
            textFieldQ.setText("20");
            textFieldTMaxSimulation.setText("200");
            textFieldTArrivalMin.setText("10");
            textFieldTArrivalMax.setText("100");
            textFieldTServiceMin.setText("3");
            textFieldTServiceMax.setText("9");
        });

        HBox buttonBox2 = new HBox();
        buttonBox2.setAlignment(Pos.CENTER);
        buttonBox2.setSpacing(20);
        buttonBox2.getChildren().addAll(test1Button, test2Button, test3Button);

        VBox mainLayout = new VBox(20);
        mainLayout.getChildren().addAll(textArea, formLayout, buttonBox, buttonBox2);

        Scene scene = new Scene(mainLayout, 490, 750);
        stage.setTitle("Queue Simulation");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private Scene createSimulationScene(Stage stage, SimulationManager simulationManager) {
        Label waitingListLabel = new Label("Waiting List:");
        TextArea waitingListArea = new TextArea();
        waitingListArea.setEditable(false);
        waitingListArea.setPrefRowCount(6);
        waitingListArea.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10;");

        ScrollPane waitingScrollPane = new ScrollPane(waitingListArea);
        waitingScrollPane.setFitToWidth(true);

        Label timeLabel = new Label();
        timeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        HBox timeBox = new HBox(timeLabel);
        timeBox.setAlignment(Pos.CENTER);

        Label queuesLabel = new Label("Queues:");
        VBox queuesBox = new VBox(10);
        queuesBox.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10;");
        ScrollPane queueScrollPane = new ScrollPane(queuesBox);
        queueScrollPane.setFitToWidth(true);
        queueScrollPane.setPrefHeight(300);

        Label avgWaitLabel = new Label("Average Waiting Time: ");
        Label avgServiceLabel = new Label("Average Service Time: ");
        Label peakHourLabel = new Label("Peak Hour: ");

        Thread uiUpdateThread = new Thread(() -> {
            while (simulationManager.getCurrentTime() < simulationManager.timeLimit && !simulationManager.isSimulationDone()) {
                int finalCurrentTime = simulationManager.getCurrentTime();

                Platform.runLater(() -> {
                    timeLabel.setText("Current Time: " + finalCurrentTime);
                    waitingListArea.setText(simulationManager.getWaitingList());
                    queuesBox.getChildren().clear();
                    Label queueLabel = new Label(simulationManager.getScheduler().getQueuesStatus());
                    queuesBox.getChildren().add(queueLabel);
                });

                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            Platform.runLater(() -> {
                timeLabel.setText("Current Time: " + simulationManager.getCurrentTime());
                waitingListArea.setText(simulationManager.getWaitingList());
                queuesBox.getChildren().clear();
                Label queueLabel = new Label(simulationManager.getScheduler().getQueuesStatus());
                queuesBox.getChildren().add(queueLabel);
                avgWaitLabel.setText("Average Waiting Time: \n" + simulationManager.getAverageWaitingTime());
                avgServiceLabel.setText("Average Service Time: \n" + simulationManager.getAverageServiceTime());
                peakHourLabel.setText("Peak Hour: \n" + simulationManager.getPeakHour());
            });
        });

        uiUpdateThread.start();

        VBox stat1 = new VBox(avgWaitLabel);
        VBox stat2 = new VBox(avgServiceLabel);
        VBox stat3 = new VBox(peakHourLabel);

        stat1.setAlignment(Pos.CENTER);
        stat2.setAlignment(Pos.CENTER);
        stat3.setAlignment(Pos.CENTER);

        HBox statsBox = new HBox(50, stat1, stat2, stat3);
        statsBox.setAlignment(Pos.CENTER);
        statsBox.setStyle("-fx-padding: 20;");

        Button backButton = new Button("GO BACK");
        backButton.setOnAction(e -> {
            simulationManager.setRunning(false);
            uiUpdateThread.interrupt();
            start(stage);
        });

        Button stopButton = new Button("STOP SIMULATION");
        stopButton.setOnAction(e -> {
                    simulationManager.setRunning(false);
                    uiUpdateThread.interrupt();
        });

        HBox backButtonBox = new HBox(backButton, stopButton);
        backButtonBox.setSpacing(20);
        backButtonBox.setAlignment(Pos.CENTER);

        VBox simulationLayout = new VBox(20, waitingListLabel, waitingScrollPane, timeBox, queuesLabel, queueScrollPane, statsBox, backButtonBox);
        simulationLayout.setStyle("-fx-padding: 20;");
        return new Scene(simulationLayout, 700, 750);
    }

    public static void main(String[] args) {
        LogEvents.startLogging("simulationOutput.txt");
        launch();
        LogEvents.stopLogging();
    }
}
