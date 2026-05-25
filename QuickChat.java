package com.mycompany.loginandregistration;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.*;
import java.io.*;
/**
 *
 * @author Kingsley
 */
public class QuickChat {
    

    static List<String[]> sentMessages = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    // ════════════════════════════════════════
    //  MESSAGE CLASS
    // ════════════════════════════════════════
    static class Message {
        String messageID;
        String recipient;
        String messageText;
        int msgNumber;
        String messageHash;

        // Constructor
        Message(String messageID, String recipient, String messageText, int msgNumber) {
            this.messageID    = messageID;
            this.recipient    = recipient;
            this.messageText  = messageText;
            this.msgNumber    = msgNumber;
            this.messageHash  = createMessageHash();
        }

        // Check message ID is max 10 chars
        boolean checkMessageID() {
            return messageID.length() <= 10;
        }

        // Check recipient cell number
        String checkRecipientCell() {
            String errorMsg = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
            if (!recipient.startsWith("+")) {
                return errorMsg;
            }
            String digits = recipient.substring(1);
            if (digits.length() > 10) {
                return errorMsg;
            }
            return recipient;
        }

        // Check message length
        String checkMessageLength() {
            if (messageText.length() <= 250) {
                return "Message ready to send.";
            } else {
                int excess = messageText.length() - 250;
                return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
            }
        }

        // Create message hash: XX:N:FIRSTLAST (all caps)
        String createMessageHash() {
            String idPrefix  = messageID.substring(0, 2);
            String[] words   = messageText.trim().split("\\s+");
            String firstWord = words[0];
            String lastWord  = words[words.length - 1];
            return (idPrefix + ":" + msgNumber + ":" + firstWord + lastWord).toUpperCase();
        }

        // Testable overload - takes choice directly (no scanner needed)
        String sentMessage(String choice) {
            switch (choice) {
                case "1": return "Message successfully sent.";
                case "2": return "Press 0 to delete the message.";
                case "3": return storeMessage();
                default:  return "Invalid choice – message not actioned.";
            }
        }

        // Interactive version - reads from scanner
        String sentMessage() {
            System.out.println("\nWhat would you like to do with this message?");
            System.out.println("1) Send Message");
            System.out.println("2) Disregard Message");
            System.out.println("3) Store Message to send later");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();
            return sentMessage(choice);
        }

        // Store message to JSON file
        String storeMessage() {
            String filename = "stored_messages.json";
            try (FileWriter fw = new FileWriter(filename, true)) {
                fw.write("{\n");
                fw.write("  \"message_id\": \""   + messageID   + "\",\n");
                fw.write("  \"message_hash\": \"" + messageHash + "\",\n");
                fw.write("  \"recipient\": \""    + recipient   + "\",\n");
                fw.write("  \"message\": \""      + messageText + "\"\n");
                fw.write("},\n");
                return "Message successfully stored.";
            } catch (IOException e) {
                return "Error storing message: " + e.getMessage();
            }
        }

        // Print full message details
        String printMessages() {
            return "\n----------------------------------------" +
                   "\nMessage ID   : " + messageID   +
                   "\nMessage Hash : " + messageHash +
                   "\nRecipient    : " + recipient   +
                   "\nMessage      : " + messageText +
                   "\n----------------------------------------";
        }

    } // end Message class

    // ════════════════════════════════════════
    //  RETURN TOTAL MESSAGES
    // ════════════════════════════════════════
    static int returnTotalMessages() {
        return sentMessages.size();
    }

    // ════════════════════════════════════════
    //  GENERATE RANDOM 10-DIGIT MESSAGE ID
    // ════════════════════════════════════════
    static String generateMessageID() {
        Random rand = new Random();
        long id = (long)(rand.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
        return String.valueOf(id);
    }

    // ════════════════════════════════════════
    //  LOGIN
    // ════════════════════════════════════════
    static boolean login() {
        Map<String, String> users = new HashMap<>();
        users.put("admin", "password123");
        users.put("user1", "pass1");

        System.out.println("\n── Login ──────────────────────────────");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        if (users.containsKey(username) && users.get(username).equals(password)) {
            System.out.println("Login successful! Welcome, " + username + ".");
            return true;
        }
        System.out.println("Invalid username or password.");
        return false;
    }

    // ════════════════════════════════════════
    //  SEND MESSAGES FLOW
    // ════════════════════════════════════════
    static void sendMessagesFlow(int limit) {
        int remaining = limit - returnTotalMessages();

        if (remaining <= 0) {
            System.out.println("You have reached your message limit of " + limit + ".");
            return;
        }

        for (int i = 0; i < remaining; i++) {
            System.out.println("\n── Message " + (returnTotalMessages() + 1) + " of " + limit + " ────────────────────");

            // Recipient
            String recipient;
            while (true) {
                System.out.print("Enter recipient cell number (e.g. +2771869300): ");
                recipient = scanner.nextLine().trim();
                Message temp = new Message("0000000000", recipient, "test message", 0);
                String check = temp.checkRecipientCell();
                if (check.equals(recipient)) {
                    break;
                }
                System.out.println("Error: " + check);
            }

            // Message body
            String messageText;
            while (true) {
                System.out.print("Enter your message (max 250 characters): ");
                messageText = scanner.nextLine().trim();
                if (messageText.isEmpty()) {
                    System.out.println("Message cannot be empty.");
                } else if (messageText.length() > 250) {
                    int excess = messageText.length() - 250;
                    System.out.println("Message exceeds 250 characters by " + excess + "; please reduce the size.");
                } else {
                    System.out.println("Message ready to send.");
                    break;
                }
            }

            // Build Message object
            String msgID  = generateMessageID();
            int    msgNum = returnTotalMessages();
            Message msg   = new Message(msgID, recipient, messageText, msgNum);

            // Show full details
            System.out.println(msg.printMessages());

            // Send / Store / Disregard
            String status = msg.sentMessage();
            System.out.println("Status: " + status);

            if (status.contains("sent") || status.contains("stored")) {
                sentMessages.add(new String[]{msgID, msg.messageHash, recipient, messageText});
            }
        }
    }

    // ════════════════════════════════════════
    //  MAIN
    // ════════════════════════════════════════
    public static void main(String[] args) {

        System.out.println("=============================================");
        System.out.println("         Welcome to QuickChat.");
        System.out.println("=============================================");

        // Login - max 3 attempts
        boolean loggedIn = false;
        for (int attempt = 0; attempt < 3; attempt++) {
            if (login()) {
                loggedIn = true;
                break;
            }
            int left = 2 - attempt;
            if (left > 0) System.out.println("Attempts remaining: " + left);
        }
        if (!loggedIn) {
            System.out.println("Too many failed attempts. Exiting.");
            return;
        }

        // How many messages?
        int numMessages = 0;
        while (true) {
            try {
                System.out.print("\nHow many messages would you like to send this session? ");
                numMessages = Integer.parseInt(scanner.nextLine().trim());
                if (numMessages < 1) {
                    System.out.println("Please enter a number greater than 0.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        // Main menu loop
        while (true) {
            System.out.println("\n── Menu ────────────────────────────────");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                sendMessagesFlow(numMessages);
            } else if (choice.equals("2")) {
                System.out.println("\nComing Soon.");
            } else if (choice.equals("3")) {
                break;
            } else {
                System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }

        // Session summary
        System.out.println("\nTotal messages sent this session: " + returnTotalMessages());
        System.out.println("Thank you for using QuickChat. Goodbye!");
        scanner.close();
    }

} // end QuickChat class
     

