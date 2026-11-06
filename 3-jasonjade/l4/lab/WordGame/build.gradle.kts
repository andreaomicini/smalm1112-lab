// Added in 2026 so the 2011 Eclipse project builds and runs today: Java in src/java, agents in src/asl, libraries in lib/
// (JADE 4.1 and Pianini's Jason of October 2011), as in .classpath.
//   gradle build                     compile the internal actions and the Java/JADE agents (the .asl agents are yours to write)
plugins {
    java
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

sourceSets.main {
    java.setSrcDirs(listOf("src/java"))
}

dependencies {
    implementation(fileTree("lib").also { it.include("*.jar") })
}

