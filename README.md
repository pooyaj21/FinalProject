# Project Manager

Welcome to the Project Manager application! This project provides a simple and efficient solution for managing projects, issues, and their statuses. It allows users to create, track, and organize projects, issues, and their transitions.

## Features

- User management: Create, edit, and delete users with different roles (Developer, Qa, etc.).
- Project management: Create and manage projects, including adding boards for organizing issues.
- Issue management: Create, edit, and delete issues with various attributes (title, description, priority, status, etc.).
- Board management: Assign issues to specific boards within a project.
- Issue transitions: Track transitions between different states (To Do, In Progress, Qa, Done) with timestamps.
- Filtering: Filter issues based on priority, type, status, and project.
- SQLite database: Utilize an SQLite database for data storage and retrieval.

## Getting Started

1. Clone the repository: `git clone https://github.com/your-username/project-manager.git`
2. Set up the database: Follow the instructions in the `db-setup.md` file to create the necessary tables in SQLite.
3. Build and run the project: Use your preferred IDE or build tools to compile and run the Java project.

## Usage

- Create and manage users using the user management features.
- Create projects and boards to organize issues.
- Add issues to boards, specifying their attributes.
- Track issue transitions between different states.
- Use the filtering feature to view specific issues based on criteria.

## How to Run

To run the Project Manager application, follow these steps:

1. Clone the repository: `git clone https://github.com/your-username/project-manager.git`
2. Navigate to the `FinalProject App` directory: `cd project-manager/FinalProject App`
3. Follow the instructions provided in the HowToRun file. This file contains the necessary setup and configuration steps for running the application.
4. The default super admin credentials are as follows: Email: admin@a.com, Password: admin.

If you prefer to run the application in an IDE of your choice, you can use the source code. All required libraries are included in the file; you just need to add them to the project. Then, you can run the project from `src/Project/Test/mainApp/java`.

If you encounter any issues during setup or execution, please refer to the troubleshooting section below or open an issue for assistance.


## Troubleshooting

If you encounter any difficulties while setting up or running the application, consider the following steps:

- Double-check that you're following the correct setup instructions in the `db-setup.md` file.
- Ensure that you have the necessary dependencies and libraries installed on your system.
- Verify that you're running the application from the `FinalProject App` folder.

If problems persist, please open an issue in this repository with detailed information about the issue you're facing, and we'll be glad to assist you.

