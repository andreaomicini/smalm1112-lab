// Added in 2026 so the 2011 base code builds today: src/java on Jason of October 2011 (../../../lib/jason.jar, the one
// shipped with lab 4). The agents and the .mas2j are yours to write; lab 3's solution shows how to run them.
//   gradle build
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
    implementation(files("../../../lib/jason.jar"))
}
