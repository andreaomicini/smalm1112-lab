// Added in 2026 so the 2011 CArtAgO tutorials build and run today: artifacts in src/java, agents in src/asl, on Jason of
// October 2011 and CArtAgO/C4Jason of October 2010 (../../../lib/, the jars Pianini used the year before; his 2011
// projects pointed at a Jason installation).
//   gradle build                          compile every tutorial
//   gradle :04-Guards:run [-Pconsole]     run one tutorial's .mas2j, centralised, CArtAgO as its environment
//   gradle :11-RemoteWorkspace:node       the CArtAgO node 11's agent joins on localhost (HelloRemoteWorld): start it first
// Jason prints to its console window; with -Pconsole to the terminal instead (a logging.properties written in the
// tutorial's folder, which git ignores; removed again without -Pconsole).
val lib = file("../../../lib")
val consoleLoggingText =
    "handlers = java.util.logging.ConsoleHandler\n.level = INFO\n" +
    "java.util.logging.ConsoleHandler.level = ALL\n" +
    "java.util.logging.ConsoleHandler.formatter = jason.runtime.MASConsoleLogFormatter\n" +
    "java.level=OFF\njavax.level=OFF\nsun.level=OFF\n"

subprojects {
    apply(plugin = "java")
    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    configure<SourceSetContainer> { named("main") { java.setSrcDirs(listOf("src/java")) } }
    dependencies { "implementation"(files("$lib/jason.jar", "$lib/cartago.jar", "$lib/c4jason.jar")) }
    tasks.register<JavaExec>("run") {
        group = "application"
        mainClass.set("jason.infra.centralised.RunCentralisedMAS")
        classpath = project.extensions.getByType<SourceSetContainer>()["main"].runtimeClasspath
        workingDir = projectDir
        args(projectDir.listFiles { f -> f.name.endsWith(".mas2j") }!!.single().name)
        val console = project.hasProperty("console")
        val logging = file("logging.properties")
        doFirst { if (console) logging.writeText(consoleLoggingText) else logging.delete() }
    }
}

project(":11-RemoteWorkspace") {
    tasks.register<JavaExec>("node") {
        group = "application"
        mainClass.set("examples.HelloRemoteWorld")
        classpath = project.extensions.getByType<SourceSetContainer>()["main"].runtimeClasspath
    }
}
