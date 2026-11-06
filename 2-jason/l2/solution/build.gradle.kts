// Added in 2026 so the 2011 Jason projects run today, on Jason of October 2011 (../../../lib/jason.jar, the one shipped
// with lab 4), centralised infrastructure as in their .mas2j.
//   gradle runHelloworld [-Pconsole]   helloworld/helloworld.mas2j
//   gradle runFactorial [-Pconsole]    factorial/factorial.mas2j
// Jason prints to its console window; with -Pconsole to the terminal instead (a logging.properties written in the
// project's folder, which git ignores; removed again without -Pconsole).
plugins {
    java
}

dependencies {
    implementation(files("../../../lib/jason.jar"))
}

val consoleLoggingText =
    "handlers = java.util.logging.ConsoleHandler\n.level = INFO\n" +
    "java.util.logging.ConsoleHandler.level = ALL\n" +
    "java.util.logging.ConsoleHandler.formatter = jason.runtime.MASConsoleLogFormatter\n" +
    "java.level=OFF\njavax.level=OFF\nsun.level=OFF\n"

fun mas(task: String, dir: String) = tasks.register<JavaExec>(task) {
    group = "application"
    mainClass.set("jason.infra.centralised.RunCentralisedMAS")
    classpath = sourceSets.main.get().runtimeClasspath
    workingDir = file(dir)
    args("$dir.mas2j")
    val console = project.hasProperty("console")
    val logging = file("$dir/logging.properties")
    doFirst { if (console) logging.writeText(consoleLoggingText) else logging.delete() }
}

mas("runHelloworld", "helloworld")
mas("runFactorial", "factorial")
