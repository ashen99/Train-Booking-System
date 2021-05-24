public class PassengerQueue {
    private Passenger[] QueueArray = new Passenger[21];
    private int first = 0;
    private int last = 0;
    private int maxStayInQueue = 0;
    private int maxLength = 0;

    public void add(Passenger data){
        if (!isFull()){
            QueueArray[last] = data;
            last++;
            maxLength++;
        }
        else {
            System.out.println("Train queue is full.");
        }
    }

    public Passenger[] getQueueArray(){
        return QueueArray;
    }

    public void display(){
        for (int i= 0;i<maxLength;i++){
            Passenger cus = new Passenger();
            cus = QueueArray[i];
            System.out.println(cus.getSeatNumber() + " ");
        }
    }
    public void deletePassenger(int i){
        for (int j = i; j < getLength(); j++) {
            QueueArray[j] = QueueArray[j + 1];
        }
        maxLength--;
    }

    public Passenger remove(){
        Passenger data = QueueArray[first];
        if (!isEmpty()){
            first++;
            maxStayInQueue = maxStayInQueue+ data.getSecondsInQueue();
        }else {
            System.out.println("Train Queue is Empty");
        }
        return data;
    }

    public void Clear(){
        first = 0;
        last = 0;
        maxStayInQueue = 0;
        maxLength = 0;

    }

    public boolean isFull(){
        return getLength() == 21;
    }

    public boolean isEmpty(){
        return getLength() == 0;
    }

    public int getLength(){
        return maxLength;
    }

    public  int getMaxStayInQueue(){
        return maxStayInQueue;
    }

}