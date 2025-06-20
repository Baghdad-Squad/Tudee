# the cute tudee app

> A simple and intuitive personal task management app built for Android using Room, Jetpack Navigation, Koin, and supporting both English and Arabic languages.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Dependencies](#dependencies)
- [Getting Started](#getting-started)
- [Design System](#design-system)
- [Screenshots](#screenshots)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

---

## 🌟 Overview

the cute tudee app is a lightweight task management application designed to help users organize their daily tasks efficiently.  
The app stores data locally using the **Room database** and ensures a seamless user experience with **Jetpack Navigation 2**. It adheres to **SOLID principles** and maintains a clean, modular architecture.

Key features include:

- Local storage using Room Database
- Smooth navigation using Jetpack Navigation 2
- Dependency injection with Koin
- ViewModel-based architecture
- Support for multiple languages (English and Arabic)
- Dark mode and light mode support
- Onboarding screen for first-time users
- Full task management (create, view, edit, delete)

---

## 🚀 Features

### 🔹 Onboarding Screen
- Appears only on the first launch.
- Guides users through initial setup.

### 📊 Home Screen
- Displays statistics about today’s tasks.
- Shows task counts by status (To Do, In Progress, Done).

### ✏️ Task Creation
Users can create new tasks with:
- Title
- Description
- Priority
- Category
- Due date

### 🔍 Task Details
- View full details of any task.
- Update task status (To Do → In Progress → Done).

### 🔍 Task Filtering
- View all tasks based on a selected date.

### 🗂️ Category Management
- Predefined categories (e.g., Work, Personal, Study).
- Add custom categories with images from the device.
- Edit or delete custom categories.

### 🌗 Dark Mode & Light Mode
- Switch between dark and light themes.

### 🌍 Localization
- Automatically adapts to the device's language settings (English and Arabic).

### 📱 Responsive UI
- Supports various screen sizes and orientations.

---

## 🏗️ Architecture

The app follows a **simple yet robust architecture**:

1. ### ViewModels
    - Each screen has its own `ViewModel`.
    - ViewModels depend on an abstraction called `TasksServices`.

2. ### TasksServices
    - Provides domain-level models (entities).
    - Maps data retrieved from Room DAOs into domain entities.

3. ### Room Database
    - Stores task data locally.
    - Uses DAOs to interact with the database.

4. ### Dependency Injection
    - Uses **Koin** for DI.
    - Ensures higher-level modules do not depend on lower-level ones.

5. ### SOLID Principles
    - Adheres strictly to SOLID principles .

6. ### Clean Architecture (Lightweight)
    - Maintains a simple architecture without over-engineering.
    - Includes only necessary components.

---

## 🛠️ Dependencies

- **Android Jetpack Components:**
    - Room Database
    - Jetpack Navigation 2
    - ViewModel
    - LiveData
    - DataStore (for theme preferences)

- **Koin:** For dependency injection.

- **Material Design 3:** For UI components.

- **Glide:** For image loading (optional).

- **JUnit / Espresso:** For unit and UI testing.

---

## ▶️ Getting Started

### Prerequisites

- Android Studio 4.2+
- JDK 11+
- Gradle 7.0+

### Installation

1. Clone the project:
   ```bash
   git clone https://github.com/Baghdad-Squad/Tudee.git 