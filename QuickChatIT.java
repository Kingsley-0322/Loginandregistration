package com.mycompany.loginandregistration;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import com.mycompany.loginandregistration.QuickChat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;


/**
 *
 * @author Kingsley
 */
public class QuickChatIT {

    QuickChat.Message message1;
    QuickChat.Message message2;

    @BeforeEach
    void setUp() {
        QuickChat.sentMessages.clear();

        // Message 1 – valid (Test Case 1 data)
        message1 = new QuickChat.Message(
            QuickChat.generateMessageID(),
            "+2771869300",
            "Hi Mike, can you join us for dinner tonight?",
            0
        );

        // Message 2 – invalid recipient (no '+')
        message2 = new QuickChat.Message(
            QuickChat.generateMessageID(),
            "08575975889",
            "Hi Keegan, did you receive the payment?",
            1
        );
    }

    // ════════════════════════════════════════════════════════
    //  1. Message length – success and failure  (Req 9, image 1)
    // ════════════════════════════════════════════════════════

    @Test
    @SuppressWarnings("unused")
    void testMessageLength_success_returnsReadyToSend() {
        // Message under 250 chars → "Message ready to send."
        assertEquals("Message ready to send.", message1.checkMessageLength(),
            "Short message should return 'Message ready to send.'");
    }

    @Test
    void testMessageLength_failure_returnsExceedsError() {
        // Build a 260-char message (exceeds by 10)
        String longText = "A".repeat(260);
        QuickChat.Message longMsg = new QuickChat.Message(
            QuickChat.generateMessageID(), "+2771869300", longText, 0
        );
        String result = longMsg.checkMessageLength();
        assertEquals(
            "Message exceeds 250 characters by 10; please reduce the size.",
            result,
            "260-char message should report it exceeds by 10."
        );
    }

    @Test
    void testMessageLength_exactly250_succeeds() {
        String text = "A".repeat(250);
        QuickChat.Message msg = new QuickChat.Message(
            QuickChat.generateMessageID(), "+2771869300", text, 0
        );
        assertEquals("Message ready to send.", msg.checkMessageLength(),
            "Exactly 250 characters should still pass.");
    }

    @Test
    void testMessageLength_251chars_fails() {
        String text = "A".repeat(251);
        QuickChat.Message msg = new QuickChat.Message(
            QuickChat.generateMessageID(), "+2771869300", text, 0
        );
        String result = msg.checkMessageLength();
        assertTrue(result.contains("exceeds 250 characters by 1"),
            "251-char message should say it exceeds by 1.");
    }

    // ════════════════════════════════════════════════════════
    //  2. Recipient number – success and failure  (image 2)
    // ════════════════════════════════════════════════════════

    @Test
    void testRecipient_valid_returnsSuccessMessage() {
        String result = message1.checkRecipientCell();
        assertEquals("+2771869300", result,
            "Valid number should be returned as-is (Cell phone number successfully captured).");
    }

    @Test
    void testRecipient_noInternationalCode_returnsFailureMessage() {
        String result = message2.checkRecipientCell();
        assertEquals(
            "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
            result,
            "Number without '+' should return the exact failure message."
        );
    }

    @Test
    void testRecipient_tooLong_returnsFailureMessage() {
        QuickChat.Message msg = new QuickChat.Message(
            QuickChat.generateMessageID(), "+27718693002", "Test", 0
        );
        String result = msg.checkRecipientCell();
        assertEquals(
            "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
            result,
            "Number with more than 10 digits after '+' should return failure message."
        );
    }

    // ════════════════════════════════════════════════════════
    //  3. Message Hash is correct  (image 2)
    // ════════════════════════════════════════════════════════

    @Test
    void testMessageHash_testCase1_correct() {
        // "Hi Mike, can you join us for dinner tonight?"
        // ID starts with auto-generated digits; msgNumber = 0
        // Expected pattern: XX:0:HITONIGHT?
        String hash = message1.messageHash;
        String[] parts = hash.split(":");
        assertEquals(3, parts.length, "Hash must have 3 parts.");
        assertEquals("0", parts[1],   "Middle part must be message number 0.");
        assertEquals("HITONIGHT?", parts[2],
            "Last part must be first + last word in caps.");
    }

    @Test
    void testMessageHash_loopTest_allHaveCorrectFormat() {
        // Test hash format for multiple messages in a loop
        String[] messages = {
            "Hi Mike, can you join us for dinner tonight?",
            "Hi Keegan, did you receive the payment?",
            "Hello World this is a test message here"
        };

        for (int i = 0; i < messages.length; i++) {
            QuickChat.Message msg = new QuickChat.Message(
                QuickChat.generateMessageID(), "+2771869300", messages[i], i
            );
            String hash = msg.messageHash;
            String[] parts = hash.split(":");

            assertEquals(3, parts.length,
                "Hash for message " + i + " must have 3 colon-separated parts.");
            assertEquals(2, parts[0].length(),
                "First part of hash for message " + i + " must be 2 chars.");
            assertEquals(String.valueOf(i), parts[1],
                "Middle part must equal message number " + i + ".");
            assertEquals(hash.toUpperCase(), hash,
                "Hash for message " + i + " must be all uppercase.");
        }
    }

    // ════════════════════════════════════════════════════════
    //  4. Message ID is created  (image 3)
    // ════════════════════════════════════════════════════════

    @Test
    void testMessageID_isGenerated_notNull() {
        assertNotNull(message1.messageID, "Message ID should not be null.");
    }

    @Test
    void testMessageID_isExactly10Digits() {
        assertEquals(10, message1.messageID.length(),
            "Auto-generated Message ID should be exactly 10 digits.");
    }

    @Test
    void testMessageID_printed_containsID() {
        String output = message1.printMessages();
        assertTrue(output.contains("Message ID   : " + message1.messageID),
            "Printed output should contain 'Message ID generated: <id>'.");
    }

    // ════════════════════════════════════════════════════════
    //  5. MessageSent – all 3 options  (image 3)
    // ════════════════════════════════════════════════════════

    @Test
    void testSentMessage_option1_returnsSuccessfullySent() {
        String result = message1.sentMessage("1");
        assertEquals("Message successfully sent.", result,
            "Choosing Send should return 'Message successfully sent.'");
    }

    @Test
    void testSentMessage_option2_returnsDeletePrompt() {
        String result = message1.sentMessage("2");
        assertEquals("Press 0 to delete the message.", result,
            "Choosing Disregard should return 'Press 0 to delete the message.'");
    }

    @Test
    void testSentMessage_option3_returnsSuccessfullyStored() {
        String result = message1.sentMessage("3");
        assertEquals("Message successfully stored.", result,
            "Choosing Store should return 'Message successfully stored.'");
    }

    // ════════════════════════════════════════════════════════
    //  6. Total messages  (carried over)
    // ════════════════════════════════════════════════════════

    @Test
    void testTotalMessages_startsAtZero() {
        assertEquals(0, QuickChat.returnTotalMessages(),
            "Total should start at 0.");
    }

    @Test
    void testTotalMessages_incrementsAfterSend() {
        QuickChat.sentMessages.add(new String[]{
            message1.messageID, message1.messageHash,
            message1.recipient, message1.messageText
        });
        assertEquals(1, QuickChat.returnTotalMessages(),
            "Total should be 1 after one sent message.");
    }
}
