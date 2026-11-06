// Added in 2026 so the 2011 Jason project builds and runs today: internal actions in src/java, agents in src/asl, on
// Jason of October 2011 (../../../../lib/jason.jar, the one shipped with lab 4), centralised infrastructure.
//   gradle build                     compile the internal actions
//   gradle run [-Pconsole]           WordGame.mas2j: df, broker and player1-10
// Jason prints to its console window; with -Pconsole to the terminal instead (a logging.properties written in the
// project's folder, which git ignores; removed again without -Pconsole).
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
    implementation(files("../../../../lib/jason.jar"))
}

val consoleLoggingText =
    "handlers = java.util.logging.ConsoleHandler\n.level = INFO\n" +
    "java.util.logging.ConsoleHandler.level = ALL\n" +
    "java.util.logging.ConsoleHandler.formatter = jason.runtime.MASConsoleLogFormatter\n" +
    "java.level=OFF\njavax.level=OFF\nsun.level=OFF\n"

tasks.register<JavaExec>("run") {
    group = "application"
    mainClass.set("jason.infra.centralised.RunCentralisedMAS")
    classpath = sourceSets.main.get().runtimeClasspath
    args("WordGame.mas2j")
    val console = project.hasProperty("console")
    val logging = file("logging.properties")
    doFirst { if (console) logging.writeText(consoleLoggingText) else logging.delete() }
}
