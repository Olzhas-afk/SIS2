import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter server address (e.g., localhost):");
        String serverAddress = scanner.nextLine();

        try (Socket socket = new Socket(serverAddress, 12345);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

            System.out.println("Connected to the server.");

            while (true) {

                System.out.println("Enter 3d object name (Circle/Rectangle) or 'Q' to quit:");
                String shapeType = scanner.nextLine();

                if (shapeType.equalsIgnoreCase("Q")) {
                    output.writeObject(null);
                    break;
                }

                Shape shape = null;
                if (shapeType.equalsIgnoreCase("Circle")) {

                    System.out.println("Enter radius, please:");
                    double radius = scanner.nextDouble();
                    scanner.nextLine();
                    shape = new Circle(radius);
                } else if (shapeType.equalsIgnoreCase("Rectangle")) {

                    System.out.println("Enter width, please:");
                    double width = scanner.nextDouble();
                    System.out.println("Enter height, please:");
                    double height = scanner.nextDouble();
                    scanner.nextLine();
                    shape = new Rectangle(width, height);
                } else {
                    System.out.println("Invalid shape type. Try again.");
                    continue;
                }

                output.writeObject(shape);

                String response = (String) input.readObject();
                System.out.println("Server message:");
                System.out.println(response);
            }
        } catch (Exception e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
