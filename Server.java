import java.io.*;
import java.net.*;

abstract class Shape implements Serializable {
    public abstract double getArea();
    public abstract String getDetails();
}

class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public String getDetails() {
        return "Circle with radius " + radius;
    }
}

class Rectangle extends Shape {
    private double width, height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public String getDetails() {
        return "Rectangle with width " + width + " and height " + height;
    }
}

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server started. Waiting for a client...");

            try (Socket clientSocket = serverSocket.accept();
                 ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
                 ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream())) {

                System.out.println("Client connected: " + clientSocket.getInetAddress());

                while (true) {
                    Object obj = input.readObject();
                    if (obj == null) {
                        System.out.println("Client has disconnected.");
                        break;
                    }

                    Shape shape = (Shape) obj;
                    System.out.println("Client requested the area of a " + shape.getDetails());

                    double area = shape.getArea();
                    System.out.println("Answer is: " + area);

                    output.writeObject("Answer is: " + area);
                }
            }
        } catch (Exception e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}

