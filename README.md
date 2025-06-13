# FitnessApp

A simple Android fitness application that allows users to log in, view a dashboard of exercises, and see detailed information about each exercise. The app communicates with a backend API using Retrofit for network operations.

## Features

- User authentication with login screen
- Dashboard displaying a list of exercises
- Detailed view for each exercise, including muscle group, equipment, difficulty, calories burned, and description
- Smooth navigation between screens with data passed via Parcelable objects

## Technology Stack

- Kotlin
- Android SDK (min SDK 28, target SDK 34)
- Retrofit for API communication
- RecyclerView for displaying lists
- Parcelize for passing data between activities
- AndroidX libraries and Material Design components

## Installation and Setup

1. Clone this repository:

   ```bash
   git clone https://github.com/bijaylopchan/My_Android_Application.git
2. Open the project in Android Studio.
3. Ensure you have an Android device or emulator running API level 28 or higher.
4. Build and run the app.
5. The app requires connection to a backend API for login and exercise data. Update the API endpoint in RetrofitClient if necessary.

## Usage

1. Launch the app.
2. Enter your username and password on the login screen.
3. Upon successful login, you will be taken to the dashboard displaying exercises.
4. Tap an exercise to view detailed information.



