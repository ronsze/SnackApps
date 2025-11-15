# SnackApps
A multi-purpose application composed of feature modules with various functions.
<br><br>

# Development Environment
| Item | Version
| :-- | :----
| **JDK** | **11**
| **comileSDK** | **API Level 36** (Android 16)
| **minSDK** | **API Level 28** (Android 9)
| **Gradle** | **8.12.3**
| **Kotlin** | **2.2.21**
<br>

### Build
The app contains the usual debug and release build variants.
<br>

# Architecture
This project uses the MVI pattern and a Single Activity architecture.
<br>
### MVI
| Component | Role | Flow
| :-- | :--- | :--
| State | Stores the screen state and is owned by the ViewModel | ViewModel -> View
| Intent | Sends user actions to the ViewModel | View -> ViewModel
| Effect | Delivers side-effects or one-time events to the screen | ViewModel -> View

<br>

# Modulization
This project uses a multi-module structure, and common configurations for each module are managed in build-logic.
<br>

### Module Structure
| Category | Role |
| :-- | :-- |
| app | Hosts the `SingleActivity` and manages global navigation
| core | Defines classes and functions used across the app
| faeture | Acts as the `presentation layer`, defining UI and ViewModel for each screen and managing related resources
<br>

# Testing

<br><br>

# Branches
| Category | Description |
| :-- | :-- |
| main | Code released to the store
| release | Code submitted for store review
| dev | Code currently under development
<br>
