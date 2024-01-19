# AtlasCommons — Code Utilities 🛠️️

Code Utilities for, Configuration Parsing ️⚙️, Logging 📝, Cryptography 🔐, Multithreading 📀 and much more!

### Features

- 🔗 **HTTP Client:** Simple and Multi-threaded HTTP Client for making requests or downloading.
- ⚙️ **Configuration Parsing:** Easily make configuration file and load them at runtime.  
- 📝 **Logging:** Bundles SLF4J by default and allows the changing of logging level at runtime depending on the frameworks available in the classpath.
- 🔐 **Cryptography:** Easy and intuitive way to decrypt/encrypt data through using Encryptors, custom encryptors can also be made.
- 📀 **Multi-Threading:** Multi-threading made easy with FutureActions and Queuing Worker threads!

### Usage
**Add this to your build.gradle file:**
````groovy
repositories {
    maven {
        url "https://repository.atlasworld.fr/repository/maven-private/"
        credentials {
            username "$maven_user" // Replace this or define it in your gradle.properties file.
            password "$maven_password" // Same thing
        }
    }
}

dependencies {
    compileOnly "fr.atlasworld:common:${module_api_version}"
}
````