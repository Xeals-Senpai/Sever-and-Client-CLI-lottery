/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 *
 * @author Jan
 */
public class Server {

    static ServerSocket serverSocket;
    static Socket socket;
    static DataInputStream in;
    static DataOutputStream out;
    static Random randGen = new Random();

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            //creating and initialising the socket and the input and output readers
            serverSocket = new ServerSocket(5000);
            socket = serverSocket.accept();
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            String space = "\n---------------------------------------------------------------------\n";
            String defeat = "\nYou lose! Winning Number is ";
            String victory = "\nYou won! The winning number is ";

            boolean done = false;

            while (!done) {
                System.out.println(space);
                System.out.print("\nConnection Established! Welcome to the lottery server!"); //acknowledgement message for verification

                try {
                    int rand = randGen.nextInt(50); //random number generator 
                    int test = 0;
                    for (int i = 0; i < 6; i++) {
                        if (rand == in.readInt()) {
                            test = rand; //if any of the random number is the same as the user
                            //input then it stores into a new variable
                        }
                    }

                    if (test != 0) {  //checks if the variable has been overwritten
                        out.writeUTF(victory + rand); //condition statement for a victory
                        System.out.println(space);
                        done = true;
                    } else { //condition statement for a lost
                        out.writeUTF(defeat + rand);
                        System.out.println(space);
                        done = true;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    done = true;
                }
            }
            System.out.println(space);
            socket.close();
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
