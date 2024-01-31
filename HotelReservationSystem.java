/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.codealpha;

/**
 *
 * @author ASUS
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Room {
    private int roomId;
    private String category;
    private boolean isAvailable;

    public Room(int roomId, String category) {
        this.roomId = roomId;
        this.category = category;
        this.isAvailable = true;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void bookRoom() {
        this.isAvailable = false;
    }

    public void releaseRoom() {
        this.isAvailable = true;
    }
}

class Reservation {
    private int reservationId;
    private String customerName;
    private Room bookedRoom;

    public Reservation(int reservationId, String customerName, Room bookedRoom) {
        this.reservationId = reservationId;
        this.customerName = customerName;
        this.bookedRoom = bookedRoom;
    }

    public int getReservationId() {
        return reservationId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Room getBookedRoom() {
        return bookedRoom;
    }
}

class Hotel {
    private Map<String, List<Room>> roomsByCategory;
    private List<Reservation> reservations;
    private int reservationCounter;

    public Hotel() {
        roomsByCategory = new HashMap<>();
        reservations = new ArrayList<>();
        reservationCounter = 1;
    }

    public void addRoom(int roomId, String category) {
        roomsByCategory.computeIfAbsent(category, k -> new ArrayList<>()).add(new Room(roomId, category));
    }

    public void displayAvailableRooms() {
        System.out.println("\nAvailable Rooms:");
        for (Map.Entry<String, List<Room>> entry : roomsByCategory.entrySet()) {
            String category = entry.getKey();
            List<Room> rooms = entry.getValue();

            System.out.println("Category: " + category);
            for (Room room : rooms) {
                if (room.isAvailable()) {
                    System.out.println("Room ID: " + room.getRoomId());
                }
            }
            System.out.println(); 
        }
    }

    public Reservation makeReservation(String customerName, String category) {
        List<Room> rooms = roomsByCategory.get(category);
        if (rooms != null) {
            for (Room room : rooms) {
                if (room.isAvailable()) {
                    room.bookRoom();
                    Reservation reservation = new Reservation(reservationCounter++, customerName, room);
                    reservations.add(reservation);
                    return reservation;
                }
            }
        }
        System.out.println("No available rooms in the specified category.");
        return null;
    }

    public void displayBookingDetails() {
        System.out.println("\nBooking Details:");
        for (Reservation reservation : reservations) {
            System.out.println("Reservation ID: " + reservation.getReservationId() +
                    ", Customer: " + reservation.getCustomerName() +
                    ", Room ID: " + reservation.getBookedRoom().getRoomId() +
                    ", Category: " + reservation.getBookedRoom().getCategory());
        }
    }
}

public class HotelReservationSystem {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("\nHotel Reservation System Menu:");
            System.out.println("1. Add Room");
            System.out.println("2. Display Available Rooms");
            System.out.println("3. Make a Reservation");
            System.out.println("4. Display Booking Details");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addRoom(hotel, scanner);
                    break;
                case 2:
                    hotel.displayAvailableRooms();
                    break;
                case 3:
                    makeReservation(hotel, scanner);
                    break;
                case 4:
                    hotel.displayBookingDetails();
                    break;
                case 5:
                    System.out.println("Exiting the program. Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private static void addRoom(Hotel hotel, Scanner scanner) {
        System.out.print("Enter Room ID: ");
        int roomId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character left by nextInt()

        System.out.print("Enter Room Category: ");
        String category = scanner.nextLine();

        hotel.addRoom(roomId, category);
        System.out.println("Room added successfully!");
    }

    private static void makeReservation(Hotel hotel, Scanner scanner) {
        scanner.nextLine(); 

        System.out.print("Enter your name: ");
        String customerName = scanner.nextLine();

        System.out.print("Enter the room category for reservation: ");
        String category = scanner.nextLine();

        Reservation reservation = hotel.makeReservation(customerName, category);
        if (reservation != null) {
            System.out.println("Reservation successful! Your reservation ID is: " + reservation.getReservationId());
        } else {
            System.out.println("Reservation failed. Please try again.");
        }
    }
}



