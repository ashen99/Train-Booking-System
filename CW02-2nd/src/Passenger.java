public class Passenger {
    private String firstname;
    private String surname;
    private String seatNumber;
    private int secondsInQueue;

    public void setCustomerName(String fname , String sname){
        firstname = fname;
        surname = sname;
    }

    public void setSeatNumber(String snum){
        this.seatNumber = snum;
    }
    public void setSecondsInQueue(int secs){
        this.secondsInQueue = secs;
    }
    public String getCustomerName(){
        return firstname + " " + surname;
    }
    public String getSeatNumber(){
        return seatNumber;
    }

    public int getSecondsInQueue(){
        return secondsInQueue;
    }

    public void display(){
        System.out.println("\nCustomer Name\t: " + getCustomerName());
        System.out.println("Seat Number\t\t: " + getSeatNumber());
    }
}