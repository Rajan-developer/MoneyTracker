# Money Tracker Application

The Money Tracker application is a powerful tool crafted to empower users in monitoring and managing their finances effectively. Developed using Kotlin and Android components, this app offers a range of features tailored to streamline financial management processes.

## Key Features

- **Income and Expense Tracking:** Users can effortlessly track their income and expenses, recording transactions manually with customizable date and time stamps.
- **Financial Goal Setting:** The app facilitates setting and tracking financial goals, empowering users to work towards achieving their aspirations.
- **Two-Factor Authentication:** Ensuring robust security measures, the system integrates two-factor authentication, requiring users to verify their identity via OTP sent to their registered email address.
- **User-Friendly Interface:** With four main sections—Home, Chart, Category, and Profile—the app ensures ease of navigation and intuitive user experience.

  For the login feature, our application leverages Firebase Authentication. Firebase verifies user credentials and sends a one-time password (OTP) to the mobile number provided during registration. This OTP serves as a secure method of user verification.

Upon receiving the OTP, the user enters it into the application for verification. Once the OTP is successfully verified by Firebase, the user is granted authorized access to the application. This two-step authentication process enhances security and ensures that only verified users can access the app's features and functionalities.

By integrating Firebase Authentication, we prioritize user security and streamline the login process, providing a seamless experience for our users while maintaining the highest standards of authentication.


## Sections Breakdown

1. **Home:** Users can access an overview of their total income, expenses, and remaining balance, along with a detailed list of transactions categorized on a monthly basis. Historical data can be reviewed by selecting past months and years.
2. **Chart:** In this section, users can visualize their income and expenses through pie charts and bar charts, allowing for a comprehensive understanding of spending patterns across different categories. Historical data can be explored by selecting various months and years.
3. **Category:** Offering predefined categories for income and expenses, users have the flexibility to add, edit, and delete categories according to their preferences. The tab design ensures clear visual distinction and instant updates in the category list.
4. **Profile:** Users can access personal information, including total income and expenses accumulated over time, as well as details such as email, name, and address.

With its user-centric design and robust features, the Money Tracker application empowers individuals to take control of their financial well-being with ease and confidence.
