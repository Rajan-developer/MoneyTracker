package com.nchl.moneytracker.presentation.otp

import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


// Function to send OTP via email
fun sendOtpByEmail(email: String, otp: String) {
    // Sender's email credentials
    val senderEmail = "rajanshresthance@gmail.com"
    //val senderPassword = "20510104r@j@n"
    val senderPassword = "jhub bvml czie avhs"

    // Mail properties
    val properties = Properties().apply {
//        put("mail.smtp.host", "smtp.example.com") // Change this to your SMTP server
//        put("mail.smtp.port", "587") // Change this to the SMTP port
//        put("mail.smtp.auth", "true")
//        put("mail.smtp.starttls.enable", "true")

        put("mail.smtp.host", "smtp.gmail.com");
        put("mail.smtp.socketFactory.port", "465");
        put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        put("mail.smtp.auth", "true");
        put("mail.smtp.port", "465");
    }



    // Authenticator to authenticate the sender's email credentials
    val authenticator = object : Authenticator() {
        override fun getPasswordAuthentication(): PasswordAuthentication {
            return PasswordAuthentication(senderEmail, senderPassword)
        }
    }

    // Session to create the mail session
    val session = Session.getInstance(properties, authenticator)

    try {
        // Create a MimeMessage object
        val message = MimeMessage(session)

        // Set the sender's email address
        message.setFrom(InternetAddress(senderEmail))

        // Set the recipient's email address
        message.addRecipient(Message.RecipientType.TO, InternetAddress(email))

        // Set the email subject
        message.subject = "Your One-Time Password (OTP)"

        // Set the email content (OTP message)
        message.setText("Your OTP is: $otp")

        // Send the message
        Transport.send(message)

        println("OTP sent successfully to $email")
    } catch (e: MessagingException) {
        println("Failed to send OTP: ${e.message}")
    }
}