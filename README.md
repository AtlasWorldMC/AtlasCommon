# AtlasCommons — Code Utilities 🛠️️

Code Utilities for, Configuration Parsing ️⚙️, Logging 📝, Cryptography 🔐, Multithreading 📀 and much more!

### Features

- 🔗 **HTTP Client:** Simple and Multi-threaded HTTP Client for making requests or downloading.
- ⚙️ **Configuration Parsing:** Easily make configuration file and load them at runtime.
- 📝 **Logging:** Bundles SLF4J by default and allows the changing of logging level at runtime depending on the
  frameworks available in the classpath.
- 🔐 **Cryptography:** Easy and intuitive way to decrypt/encrypt data through using Encryptors, custom encryptors can
  also be made.
- 📀 **Multi-Threading:** Multi-threading made easy with FutureActions and Queuing Worker threads!

### Usage

**Add this to your build.gradle file:**

````groovy
repositories {
    maven {
        url "https://repository.atlasworld.fr/repository/maven-public/"
    }
}

dependencies {
    compileOnly "fr.atlasworld.common:<module>:${version}"
}
````

### Available Modules : 
- **Common:** Included into all other modules it contains the basic components like de file API and the logging;
- **Concurrent:** Contains the FutureActions and some misc utilities for multithreading.
- **Security:** Contains the Encryptors and misc utilities for encrypting/validating/authenticating data.
