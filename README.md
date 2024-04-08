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

## Defualt credential for app Login
**Phone Number**  : 9860262259
**Password**  : Test@1234
**OTP** : 123456

## Clean Architecture
Clean Architecture is a software design approach introduced by Robert C. Martin, also known as Uncle Bob. It emphasizes separation of concerns and modularity by organizing code into layers, each with a specific responsibility.

## Project Structure
```
│
├── data                    // Data layer
│   ├── local               // Local data sources (e.g., Room database)
│   ├── remote              // Remote data sources (e.g., Retrofit API service)
│   └── repository          // Repository pattern to provide data to the domain layer
│
├── domain                  // Domain layer (business logic)
│   ├── model               // Domain models/entities
│   ├── repository          // Interfaces defining data operations
│   └── usecase             // Use cases (interactors) orchestrating business logic
│
├── presentation            // Presentation layer (UI)
│   ├── di                  // Dependency Injection (e.g., Dagger or Hilt modules)
│   ├── ui                  // UI components (Fragments, Activities)
│   │   ├── feature         // Feature-specific UI components
│   │   └── shared          // Shared UI components (e.g., custom views, adapters)
│   ├── viewmodel           // ViewModels for UI logic
│   └── utils               // Utility classes and extensions for UI
│
└── utils                   // Utility classes and extensions for the entire application
```

**data**: This layer contains implementations for data sources (local and remote), as well as repository interfaces that define how data is accessed and manipulated. It's responsible for data retrieval and persistence.

**domain**: The domain layer contains business logic, including domain models/entities, repository interfaces defining data operations, and use cases (interactors) that encapsulate complex business logic.

**presentation**: This layer represents the UI part of the application and follows the MVVM architecture pattern. It includes Dependency Injection modules, UI components (Fragments, Activities), ViewModels for UI logic, and utility classes/extensions for UI-related tasks.

**utils**: Utility classes and extensions that can be used across different layers of the application for common tasks such as date formatting, validation, etc.

This structure helps in maintaining a clear separation of concerns, making the codebase easier to understand, test, and maintain. Each layer has its specific responsibilities and dependencies, promoting modularity and scalability.


