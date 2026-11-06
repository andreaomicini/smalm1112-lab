// Added in 2026 so the 2011 Eclipse project builds and runs today: Java in src/java, agents in src/asl, libraries in lib/
// (JADE 4.1 and Pianini's Jason of October 2011), as in .classpath.
//   gradle build                     compile the internal actions and the Java/JADE agents
//   gradle run [-Pgui] [-Pconsole]   WordGame.mas2j on JADE: the broker and player1-3, Jason agents (-Pgui: JADE's RMA GUI;
//                                    -Pconsole: the agents print to the terminal, not to Jason's console window)
//   gradle join -Pagents="<name>:<class>"   more agents, in a container of their own (e.g. a Java player of lab 1:
//                                    -Pagents="java1:alice.mas.lab1.Player")
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

fun jason(name: String, asl: String) = "$name:jason.infra.jade.JadeAgArch(src/asl/$asl.asl)"

fun JavaExec.jade(vararg extra: String) {
    group = "application"
    mainClass.set("jade.Boot")
    classpath = sourceSets.main.get().runtimeClasspath
    // APDescription.txt and MTPs-*.txt into build/
    args(listOf("-file-dir", "build/") + extra)
}

// -Pconsole: Jason reads logging.properties from the working directory, so run writes one there (git ignores it), from
// Jason's own template with the console window's handler swapped for the terminal; without -Pconsole it is removed.
val consoleLogging = layout.projectDirectory.file("logging.properties").asFile
val consoleLoggingText =
    "handlers = java.util.logging.ConsoleHandler\n.level = INFO\n" +
    "java.util.logging.ConsoleHandler.level = ALL\n" +
    "java.util.logging.ConsoleHandler.formatter = jason.runtime.MASConsoleLogFormatter\n" +
    "java.level=OFF\njavax.level=OFF\nsun.level=OFF\njade.level=OFF\n"

tasks.register<JavaExec>("run") {
    val console = project.hasProperty("console")
    doFirst { if (console) consoleLogging.writeText(consoleLoggingText) else consoleLogging.delete() }
    val agents = listOf(jason("broker", "broker")) + (1..3).map { jason("player$it", "player") }
    jade(*listOfNotNull(if (project.hasProperty("gui")) "-gui" else null, "-agents", agents.joinToString(";")).toTypedArray())
}

tasks.register<JavaExec>("join") {
    val agents = (project.findProperty("agents") as String?) ?: "java1:alice.mas.lab1.Player"
    jade("-container", "-container-name", "c-${System.currentTimeMillis()}", "-agents", agents)
}
