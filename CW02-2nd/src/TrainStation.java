import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Random;
import java.util.Scanner;

import static javafx.geometry.Pos.CENTER;

public class TrainStation extends Application {
    final static int seat_num = 42;
    //Integer tripway = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ArrayList<String> seatque = new ArrayList<String>();  //loading people from train.


        ArrayList<String> aList = new ArrayList<String>(); //to add selected people from colombo to badulla train.


        Passenger[] waitingRoom = new Passenger[42];
        PassengerQueue trainQueue = new PassengerQueue();

        ArrayList<Passenger> Train = new ArrayList<Passenger>();

        Destination();      //selecting destination method
        CW1load(seatque);
        PassengerCheck(seatque, aList);
        AddToWaitingRoom(aList, waitingRoom);

        Scanner sc = new Scanner(System.in);

        menu:
        while (true) {
            System.out.println("=======================");
            System.out.println("Train Booking System for Denuwara Menike Train:");
            System.out.println("=======================");
            System.out.println("‘A’ to add a passenger to the trainQueue:");
            System.out.println("‘V’ to view the trainQueue:");
            System.out.println("‘D’ to Delete passenger from the trainQueue:");
            System.out.println("‘S’ to Store trainQueue data into a plain text file");
            System.out.println("‘L’ to Load data back from the file into the trainQueue");
            System.out.println("‘R’ to Run the simulation and produce report.");
            System.out.println("‘C’ to change the destination.");
            System.out.println("‘Q’ to Exit.");
            System.out.println("Select your option:");
            String option = sc.next().toUpperCase();
            switch (option) {
                case ("A"):
                    if (direction.equals("")){
                        System.out.println("please select a destination");
                    }else {
                        addPassenger(waitingRoom, trainQueue);
                    }
                    break;
                case ("V"):
                    if (direction.equals("")){
                        System.out.println("please select a destination");
                    }else {
                        viewseats(trainQueue);
                    }
                    break;
                case ("D"):
                    if (direction.equals("")){
                        System.out.println("please select a destination");
                    }else {
                        deletePassengerInQueue(trainQueue);
                    }
                    break;
                case ("S"):
                    if (direction.equals("")){
                        System.out.println("please select a destination");
                    }else {
                        Store(trainQueue, waitingRoom);
                    }
                    break;
                case ("L"):
                    if (direction.equals("")){
                        System.out.println("please select a destination");
                    }else {
                        loadfromfile(waitingRoom, trainQueue);
                    }
                    break;
                case ("R"):
                    if (direction.equals("")){
                        System.out.println("please select a destination");
                    }else {
                        runsimulater(trainQueue, Train);
                    }
                    break;
                case ("Q"):
                    break menu;
                case ("C"):
                    aList.clear();
                    waitingRoomLength = 0;
                    trainQueue.Clear();
                    Train.clear();
                    passengersintrainlength = 0;
                    Destination();
                    CW1load(seatque);
                    PassengerCheck(seatque, aList);
                    AddToWaitingRoom(aList, waitingRoom);
                    break;
                default:
                    System.out.println("Invalid option. Try agin.");
                    continue;
            }

        }
    }


    private void PassengerCheck(ArrayList<String> seat, ArrayList aList) {
        //==================== selecting the passengers who came for the train===============================
        Stage stage1 = new Stage();
        stage1.setTitle("Train booking system");

        FlowPane root1 = new FlowPane();
        root1.setStyle("-fx-background-color: #2f4f4f;");

        root1.setVgap(20);
        root1.setHgap(30);

        Label head = new Label("train booking system");
        head.setFont(new Font("Arial Unicode MS", 20));
        head.setStyle("-fx-background-color:POWDERBLUE");
        head.setPadding(new Insets(40, 118, 40, 100));
        root1.getChildren().add(head);

        //creating buttons
        for (int i = 1; i <= seat_num; i++) {
            Button btn = new Button(String.valueOf(i));
            btn.setId(Integer.toString(i));
            btn.setStyle("-fx-pref-height: 28px;-fx-pref-width: 50px");
            root1.getChildren().add(btn);
            if (seat.contains(btn.getId())) {           //checking if the seat is already booked or not
                btn.setStyle("-fx-background-color : #ff0000;-fx-pref-height: 28px;-fx-pref-width: 50px");
            } else {
                btn.setStyle("-fx-background-color : #d8d8d8;-fx-pref-height: 28px;-fx-pref-width: 50px");
            }
            btn.setOnAction(e -> {
                if (seat.contains(btn.getId())) {       //checking if the seat is already booked or not
                    if (aList.contains(btn.getId())) {
                        System.out.println("Already added to arrive list");
                    } else {
                        btn.setStyle("-fx-background-color : #ff6666;-fx-pref-height: 28px;-fx-pref-width: 50px");
                        int index = seat.indexOf(btn.getId());
                        aList.add(seat.get(index - 1));
                        aList.add(seat.get(index));
                    }
                } else {
                    btn.setDisable(true);
                    System.out.println("this seat hasen't been booked");
                }
            });
        }
        Scene scene1 = new Scene(root1, 400, 600);
        stage1.setScene(scene1);
        stage1.showAndWait();
    }

    public int waitingRoomLength = 0;

    public void AddToWaitingRoom(ArrayList<String> aList, Passenger[] wRoom) {
        ArrayList<String> a = new ArrayList<String>();          //to get names to a different arraylist.
        ArrayList<String> b = new ArrayList<String>();          //to get seat numbers to a different arraylist.
        for (int i = 0; i < aList.size(); i++) {
            if (i % 2 == 0) {
                a.add(aList.get(i));
            } else {
                b.add(aList.get(i));
            }
        }

        for (int index = waitingRoomLength; index < a.size(); index++) {
            String seatNumber = b.get(index);
            String arrivedCustomerName = a.get(index);
            String firstName = arrivedCustomerName.substring(0, arrivedCustomerName.indexOf("_"));
            String surname = arrivedCustomerName.substring(arrivedCustomerName.indexOf("_") + 1, arrivedCustomerName.length());

            Passenger passenger = new Passenger();
            passenger.setCustomerName(firstName, surname);
            passenger.setSeatNumber(seatNumber);
            wRoom[waitingRoomLength] = passenger;
            waitingRoomLength++;
        }
    }

    public class read {    //this will read the file we saved first arraylist
        private Scanner load;

        private void openfile(String Filename) {
            try {
                load = new Scanner(new File(Filename));
            } catch (Exception e) {
                System.out.println("Error");
            }
        }

        public void readfile(ArrayList<String> seatque) {
            while (load.hasNext()) {
                String A = load.next();
                seatque.add(A);
            }
        }

        public void closefile() {
            load.close();
        }
    }

    public void loadfile(ArrayList<String> seatque,String Filename) {
        File file = new File(Filename);
        if (file.exists()) {
            seatque.clear();
            TrainStation.read load = new read();
            load.openfile(Filename);
            load.readfile(seatque);
            load.closefile();
            System.out.println("\nTrain Booking - Load from file");
            System.out.println(seatque);
        } else {
            System.out.println("\nCan\'t load\nNo stored file");
        }
    }
    public String direction;
    public void CW1load(ArrayList<String> seatque){
        if (direction == "Colombo to Badulla"){         //loading people acording to their destinations
            String Filename = "E:\\IIT\\1 st YEAR\\java\\SEMESTER 02 CW'S\\CWK-01-COURSEWORK\\CW01-2019416\\seatdata.txt";
            loadfile(seatque,Filename);
        }
        else {
            String Filename = "E:\\IIT\\1 st YEAR\\java\\SEMESTER 02 CW'S\\CWK-01-COURSEWORK\\CW01-2019416\\seatdata2.txt";
            loadfile(seatque, Filename);
        }
    }

    private void Destination() {
        direction="";
        Stage Stagedestination = new Stage();       //creating a new stage
        GridPane menu = new GridPane();             //creating a grid pane
        menu.setStyle("-fx-background-color: #ade6e6;");
        menu.setPadding(new Insets(20));
        menu.setVgap(6);
        menu.setHgap(6);

        Label label2 = new Label("select your destination :");
        label2.setFont(new Font("Arial Unicode MS",18));
        menu.add(label2,0,0);
        ComboBox<String> choicebox = new ComboBox<>();          //creating a dropdown meanu to select the destination
        choicebox.getItems().add("Colombo to Badulla");
        choicebox.getItems().add("Badulla to Colombo");

        choicebox.setPromptText("select your destination");    //setting a default text to dropdown menu when gui opens
        menu.add(choicebox,1,0);

        Button book = new Button("Enter");
        book.setFont(new Font("Arial Unicode MS",15));
        menu.add(book,1,4);


        book.setOnAction(event -> {
            direction = choicebox.getValue();
            Stagedestination.close();
        });
        Scene scene = new Scene(menu,440,350);
        Stagedestination.setTitle("select your destination");
        Stagedestination.setScene(scene);
        Stagedestination.showAndWait();

    }

    public void addPassenger(Passenger[] wRoom, PassengerQueue trainQueue) {
        Random rand = new Random();
        int randomNum = rand.nextInt(6) + 1;
        if (randomNum <= waitingRoomLength) {
            for (int i = 0; i < randomNum; i++) {
                trainQueue.add(wRoom[0]);
                for (int j = 0; j < waitingRoomLength; j++) {
                    wRoom[j] = wRoom[j + 1];
                }
                waitingRoomLength--;
            }
        } else {
            if (waitingRoomLength == 0) {
                System.out.println("Waiting room is empty");
            } else {
                System.out.println("Genarated random number was " + randomNum);
                System.out.println("But there are only " + waitingRoomLength + " in waiting room");
                System.out.println("So only " + waitingRoomLength + " can added to train queue");
                int x = waitingRoomLength;
                for (int i = 0; i < x; i++) {
                    trainQueue.add(wRoom[0]);
                    for (int j = 0; j < waitingRoomLength; j++) {
                        wRoom[j] = wRoom[j + 1];
                    }
                    waitingRoomLength--;
                }
            }
        }
        System.out.println("Queue :");
        for (int i = 0; i < trainQueue.getLength(); i++) {   //trainque
            System.out.println(trainQueue.getQueueArray()[i].getSeatNumber() + " " + trainQueue.getQueueArray()[i].getCustomerName());
        }
        Stage waitingroomstage = new Stage();
        GridPane gp = new GridPane();
        waitingroomstage.setTitle("PASSENGER INFORMATION");
        gp.setHgap(22);
        gp.setVgap(22);

        Label head = new Label("Passengers in waiting room");
        head.setFont(new Font("Arial Unicode MS", 24));
        head.setStyle("-fx-text-fill: #ff8000");
        gp.getChildren().add(head);
        GridPane.setConstraints(head, 1, 1, 1, 1);

        for (int x = 0; x < waitingRoomLength; x++) {
            Label CusInWaitingRoom = new Label();
            CusInWaitingRoom.setText("customer name : " + wRoom[x].getCustomerName() + "  ||  Seat number : " + wRoom[x].getSeatNumber());
            gp.getChildren().add(CusInWaitingRoom);
            GridPane.setConstraints(CusInWaitingRoom, 1, x + 3, 1, 1);
        }

        Label head2 = new Label("Passengers in Queue");
        head2.setFont(new Font("Arial Unicode MS", 24));
        head2.setStyle("-fx-text-fill: #b76fff");
        gp.getChildren().add(head2);
        GridPane.setConstraints(head2, 5, 1, 1, 1);

        for (int y = 0; y < trainQueue.getLength(); y++) {
            Label CusInQue = new Label();
            CusInQue.setText("customer name : " + trainQueue.getQueueArray()[y].getCustomerName() + "  ||  Seat number : " + trainQueue.getQueueArray()[y].getSeatNumber());
            gp.getChildren().add(CusInQue);
            GridPane.setConstraints(CusInQue, 5, y + 3, 1, 1);
        }

        Scene waitingscene = new Scene(gp, 850, 700);
        waitingroomstage.setScene(waitingscene);
        waitingroomstage.showAndWait();
    }

    public void viewseats(PassengerQueue trainQueue) {
        ArrayList<String> trainQueueCNL = new ArrayList<String>();     //creating anew arraylist to get customer names who are in trainqueue
        ArrayList<String> trainQueueSNL = new ArrayList<String>();      //creating a new arraylist to get seat numbers of the people in trainwueue.
        for (int a = 0; a < trainQueue.getLength(); a++) {
            trainQueueSNL.add(trainQueue.getQueueArray()[a].getSeatNumber());
            trainQueueCNL.add(trainQueue.getQueueArray()[a].getCustomerName());
        }

        Stage viewseat = new Stage();
        viewseat.setTitle("SEAT DEATAILS");
        FlowPane fp2 = new FlowPane();
        fp2.setStyle("-fx-background-color: #2f4f4f;");

        fp2.setHgap(20);
        fp2.setVgap(20);

        for (int i = 1; i <= 42; i++) {
            Button btn = new Button();
            btn.setText(String.valueOf(i));
            String snum = String.format("%d", i);
            Label cusname = new Label();
            cusname.setStyle("-fx-pref-height: 28px;-fx-pref-width: 110;-fx-text-fill: #000000");
            if (trainQueueSNL.contains(snum)) {                 //checking if the passenger is in trainqueue. if yes the seat will be shown in red color.
                int index = trainQueueSNL.indexOf(snum);
                btn.setStyle("-fx-background-color : #ff0000;-fx-pref-height: 28px;-fx-pref-width: 50px");
                cusname.setText(trainQueueCNL.get(index).replace("_", " "));
            } else {
                btn.setStyle("-fx-background-color : #d8d8d8;-fx-pref-height: 28px;-fx-pref-width: 50px");
                cusname.setText("NO NAME");
            }
            fp2.getChildren().addAll(btn, cusname);
        }
        Scene viewscene = new Scene(fp2, 790, 670);
        viewseat.setScene(viewscene);
        viewseat.showAndWait();
    }

    public void deletePassengerInQueue(PassengerQueue trainQueue) {
        Scanner input = new Scanner(System.in);
        System.out.println("enter the name of the customer to delete the seat:");       //getting the customer name
        String name = input.next().toLowerCase();
        System.out.println("enter the surname of the customer to delete the seat:");    //getting the surname
        String surname = input.next().toLowerCase();
        String customerName = name + " " + surname;

        int x = trainQueue.getLength();                         //checking if the passenger name is in the trainqueue
        for (int i = 0; i < trainQueue.getLength(); i++) {
            if (trainQueue.getQueueArray()[i].getCustomerName().equals(customerName)) {
                trainQueue.deletePassenger(i);                  //removing passenger name from trainqueue
                System.out.println(customerName + " was deleted from the train queue");
                break;
            }
            if (x == trainQueue.getLength()) {
                System.out.println("there is no one as " + customerName + " in train queue");
            }
        }
    }

    public void Store(PassengerQueue trainQueue, Passenger[] wRoom) {
        ArrayList<String> queuePassengername = new ArrayList<String>();     //creating a nother arraylist to get names of the passengers in the trainqueue
        ArrayList<String> queueSeatnumber = new ArrayList<String>();        //creting a nother arraylist to get the seat number of the people who are in trainqueue

        ArrayList<String> waitingroomPassengername = new ArrayList<String>();   //creating a nother arraylist to get names of the passengers in the waitingroom
        ArrayList<String> waitingroomseatnumber = new ArrayList<String>();      //creating a nother arraylist to get the seat numbers of the people who are in the waitingroom


        if (trainQueue.getLength() != 0) {      //checking if he trainqueue is empty or not.if empty no will be added
            for (int i = 0; i < trainQueue.getLength(); i++) {
                queuePassengername.add(trainQueue.getQueueArray()[i].getCustomerName().replace(" ", "_"));
                queueSeatnumber.add(trainQueue.getQueueArray()[i].getSeatNumber());
            }
        } else {
            System.out.println("no Passengers in train queue.");
        }

        if (waitingRoomLength != 0) {           //checking if the waitingroom is empty.
            for (int l = 0; l < waitingRoomLength; l++) {
                waitingroomPassengername.add(wRoom[l].getCustomerName().replace(" ", "_"));
                waitingroomseatnumber.add(wRoom[l].getSeatNumber());
            }
        } else {
            System.out.println("there are no passengers in waiting room");
        }

        TrainStation.createfile waitingroom = new TrainStation.createfile();
        waitingroom.openfile();
        waitingroom.addRecords(waitingroomPassengername, waitingroomseatnumber);
        waitingroom.closeFile();

        TrainStation.createfile2 trainqueue = new TrainStation.createfile2();
        trainqueue.openfile();
        trainqueue.addRecords(queuePassengername, queueSeatnumber);
        trainqueue.closeFile();

        System.out.println("DONE");
    }

    public class createfile {
        private Formatter x;

        public void openfile() {
            try {
                x = new Formatter("waitingroom-"+ direction + ".txt");      //giving direction to create different save file acording to destination
            } catch (Exception e) {
                System.out.println("Error");
            }
        }

        public void addRecords(ArrayList<String> waitingroomPassengername, ArrayList<String> waitingroomseatnumber) {
            if (waitingroomPassengername.size() != 0) {
                for (int i = 0; i < waitingroomPassengername.size(); i++) {
                    String cusname = waitingroomPassengername.get(i);
                    String seatnum = waitingroomseatnumber.get(i);
                    x.format("%s\n%s\n\n", cusname, seatnum);
                }
            }
        }

        public void closeFile() {
            x.close();
        }
    }

    public class createfile2 {
        private Formatter x;

        public void openfile() {
            try {
                x = new Formatter("trainqueue-"+ direction + ".txt");
            } catch (Exception e) {
                System.out.println("Error");
            }
        }

        public void addRecords(ArrayList<String> queuePassengername, ArrayList<String> queueSeatnumber) {
            if (queuePassengername.size() != 0) {
                for (int i = 0; i < queuePassengername.size(); i++) {
                    String cusname = queuePassengername.get(i);
                    String seatnum = queueSeatnumber.get(i);
                    x.format("%s\n%s\n\n", cusname, seatnum);
                }
            }
        }

        public void closeFile() {
            x.close();
        }
    }

    public class readfileWaitingRoom {    //this will read the file we saved first arraylist
        private Scanner loadCTB;

        private void openfile() {
            try {
                loadCTB = new Scanner(new File("waitingroom-"+ direction + ".txt"));
            } catch (Exception e) {
                System.out.println("Error");
            }
        }

        public void readfileWR(ArrayList<String> savedwaitingroom) {
            while (loadCTB.hasNext()) {
                String A = loadCTB.next();
                savedwaitingroom.add(A);
            }
        }

        public void closefileWR() {
            loadCTB.close();
        }
    }

    public void loadfromfile(Passenger[] waitingRoom, PassengerQueue trainQueue) {
        File file = new File("waitingroom-"+ direction + ".txt");
        if (file.exists()) {
            waitingRoomLength = 0;
            ArrayList<String> savedwaitingroom = new ArrayList<String>();
            TrainStation.readfileWaitingRoom loadWR = new readfileWaitingRoom();
            loadWR.openfile();
            loadWR.readfileWR(savedwaitingroom);
            loadWR.closefileWR();

            AddToWaitingRoom(savedwaitingroom, waitingRoom);

            System.out.println("\nTrain Booking - Load from file");
        } else {
            System.out.println("\nCan\'t load\nNo stored file");
        }

        File file2 = new File("trainqueue-"+ direction + ".txt");
        if (file2.exists()) {
            trainQueue.Clear();
            ArrayList<String> savedtrainqueue = new ArrayList<String>();
            Passenger[] trainqueuearray = new Passenger[21];
            TrainStation.readfiletrainqueue loadTQ = new readfiletrainqueue();
            loadTQ.openfile();
            loadTQ.readfileTQ(savedtrainqueue);
            loadTQ.closefileTQ();
            System.out.println("\nTrain Booking - Load from file");

            AddToArray(savedtrainqueue, trainqueuearray);
            for (int i = 0; i < savedtrainqueue.size() / 2; i++) {
                trainQueue.add(trainqueuearray[i]);
                System.out.println(trainQueue.getQueueArray()[i].getCustomerName());
            }

        } else {
            System.out.println("\nCan\'t load\nNo stored file");
        }
    }

    public void AddToArray(ArrayList<String> savedtotrainqueueList, Passenger[] trainqueuearray) {
        ArrayList<String> a = new ArrayList<String>(); //to get names to a different arraylist.
        ArrayList<String> b = new ArrayList<String>(); //to get seat numbers to a different arraylist.
        for (int i = 0; i < savedtotrainqueueList.size(); i++) {
            if (i % 2 == 0) {
                a.add(savedtotrainqueueList.get(i));
            } else {
                b.add(savedtotrainqueueList.get(i));
            }
        }

        for (int index = 0; index < a.size(); index++) {
            String seatNumber = b.get(index);
            String arrivedCustomerName = a.get(index);
            String firstName = arrivedCustomerName.substring(0, arrivedCustomerName.indexOf("_"));
            String surname = arrivedCustomerName.substring(arrivedCustomerName.indexOf("_") + 1, arrivedCustomerName.length());

            Passenger passenger = new Passenger();
            passenger.setCustomerName(firstName, surname);
            passenger.setSeatNumber(seatNumber);
            trainqueuearray[index] = passenger;
        }
    }

    public class readfiletrainqueue {
        private Scanner loadCTB;

        private void openfile() {
            try {
                loadCTB = new Scanner(new File("trainqueue-"+ direction + ".txt"));
            } catch (Exception e) {
                System.out.println("Error");
            }
        }

        public void readfileTQ(ArrayList<String> savedtrainqueue) {
            while (loadCTB.hasNext()) {
                String A = loadCTB.next();
                savedtrainqueue.add(A);
            }
        }

        public void closefileTQ() {
            loadCTB.close();
        }
    }


    public int passengersintrainlength;

    public void runsimulater(PassengerQueue trainQueue, ArrayList<Passenger> Train) {
        if (trainQueue.isEmpty()) {
            System.out.println("train queue is empty.");
        } else {
            int passengersintrain = passengersintrainlength;
            for (int i = 0; i < trainQueue.getLength(); i++) {
                Random number = new Random();
                int num1 = number.nextInt(6) + 1;
                int num2 = number.nextInt(6) + 1;
                int num3 = number.nextInt(6) + 1;
                int timeinqueue = num1 + num2 + num3;
                trainQueue.getQueueArray()[i].setSecondsInQueue(timeinqueue);
                Train.add(trainQueue.remove());
                passengersintrainlength++;
            }

            Stage report = new Stage();
            GridPane gp = new GridPane();
            gp.setStyle("-fx-background-color: #2f4f4f;");
            gp.setHgap(20);
            gp.setVgap(20);

            int i =passengersintrain;
            for (int row =2;row <=7; row++){
                for (int col = 0; col <=3 ; col++){
                    Button btn = new Button();
                    if (i < Train.size()){

                        btn.setText(Train.get(i).getCustomerName() + "\n" + "seat" +Train.get(i).getSeatNumber() +"\n"+"Seconds in queue" +Train.get(i).getSecondsInQueue());
                    }
                    else {
                        btn.setVisible(false);
                    }
                    GridPane.setConstraints(btn,col,row);
                    btn.setStyle("-fx-pref-width: 200px;-fx-background-color: #82E0AA;-fx-border-radius: 200px");
                    gp.getChildren().add(btn);
                    i++;
                }
            }

            Label lb2 = new Label();
            lb2.setText("Max lenghth:"+trainQueue.getLength());
            lb2.setStyle("-fx-pref-width: 200px;-fx-text-fill: #dcf7e7");
            GridPane.setConstraints(lb2,0,8);

            Label lb3 = new Label();
            lb3.setText("Max waiting time:"+trainQueue.getMaxStayInQueue());
            lb3.setStyle("-fx-pref-width: 200px;-fx-text-fill: #dcf7e7");
            GridPane.setConstraints(lb3,1,8);

            Label lb4 = new Label();
            lb4.setText("Min waiting time:"+Train.get(passengersintrain).getSecondsInQueue());
            lb4.setStyle("-fx-pref-width: 200px;-fx-text-fill: #dcf7e7");
            GridPane.setConstraints(lb4,2,8);

            Label lb5 = new Label();
            lb5.setText("Average waiting time:"+trainQueue.getMaxStayInQueue()/trainQueue.getLength());
            lb5.setStyle("-fx-pref-width: 200px;-fx-text-fill: #dcf7e7");
            GridPane.setConstraints(lb5,3,8);

            gp.getChildren().addAll(lb2,lb3,lb4,lb5);

            Scene reportsc = new Scene(gp,1200,500);
            report.setTitle("REPORT");
            report.setScene(reportsc);
            report.showAndWait();

            //saving report
            StoreReport(trainQueue,Train,passengersintrain);

            //clearing the trainqueue
            trainQueue.Clear();
        }

    }

    public class ReportStore {   //saving the report to a text file
        private Formatter storereport;

        public void openfile() {
            try {
                storereport = new Formatter("Report-"+ direction + ".txt");
            } catch (Exception e) {
                System.out.println("Error");
            }
        }

        public void addRecords(PassengerQueue trainQueue, ArrayList<Passenger> Train,int passengersintrain) {
            for (int i = passengersintrain ; i<Train.size();i++){
                storereport.format("\nName :%s|| Seat :%s|| Seconds In Queue :%s",Train.get(i).getCustomerName(),Train.get(i).getSeatNumber(),Train.get(i).getSecondsInQueue());
            }
            storereport.format("\n");
            storereport.format("\nMax lenghth : %s",trainQueue.getLength());
            storereport.format("\nMax Stay time : %s",trainQueue.getMaxStayInQueue()+"s");
            storereport.format("\nMinimum stay time : %s",Train.get(passengersintrain).getSecondsInQueue()+"s");
            storereport.format("\nAverage time : %s",trainQueue.getMaxStayInQueue()/trainQueue.getLength()+"s");
        }

        public void closeFile() {
            storereport.close();
        }
    }
    public void StoreReport(PassengerQueue trainQueue, ArrayList<Passenger> Train,int passengersintrain){
        ReportStore storedData = new ReportStore();
        storedData.openfile();
        storedData.addRecords(trainQueue,Train,passengersintrain);
        storedData.closeFile();
        System.out.println("Saved");
    }
}