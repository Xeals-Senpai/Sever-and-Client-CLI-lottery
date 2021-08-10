
import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 *
 * @author Jan
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        Socket socket;
        DataInputStream in;
        DataOutputStream out;
        String space = "\n---------------------------------------------------------------------\n";

        Scanner userInput = new Scanner(System.in);
        try {
            //creating the socket
            socket = new Socket("127.0.0.1", 5000); //connecting to the loopback address and using a unused port
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            int num = 0;

            boolean done = false;

            while (!done) {
                System.out.println(space);
                System.out.println("Welcome to the Client Machine!\n");
                System.out.println("Enter number: ");

                try {
                    for (int i = 0; i < 6; i++) {
                        num = userInput.nextInt();
                        if (num <= 0 || num >= 50) {
                            System.err.println("Error! Please enter an Integer between 0 and 50!"); //catching error
                            socket.close(); 
                            in.close();
                            out.close();
                        } else {
                            out.writeInt(num); //sending the user input to the server
                            System.out.println("You have entered: Number " + num);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Please enter an Integer! and try again!");
                    e.printStackTrace();
                    socket.close();
                    in.close();
                    out.close();
                    System.exit(99);
                }
                System.out.println(in.readUTF()); //reading the results from the server
                System.out.println(space);
                done = true;
            }
            socket.close();
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
