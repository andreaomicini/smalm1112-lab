// Added in 2026 so the 2011 Eclipse projects build and run today. BoomerEnv: the world, a Java program with its rules
// in tuProlog (prolog/world.pl, lib/2p.jar), serving the players on port 1099 and showing them in a window.
// BoomerAgent: the Jason player, whose CArtAgO artifact TheGame talks to the world; on Jason of October 2011 and
// CArtAgO/C4Jason of October 2010 (../../../lib/), as lab 5.
//   gradle build                          compile both
//   gradle world                          the world and its window: start it first
//   gradle run [-Pconsole]                BoomerAgent.mas2j, the player (-Pconsole: Jason prints to the terminal)
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
}

project(":BoomerEnv") {
    configure<SourceSetContainer> { named("main") { java.setSrcDirs(listOf("src")) } }
    dependencies { "implementation"(fileTree("lib").also { it.include("*.jar") }) }
}

project(":BoomerAgent") {
    configure<SourceSetContainer> { named("main") { java.setSrcDirs(listOf("src/java")) } }
    // the world answers with tuProlog objects: 2p.jar is on the player's classpath too, as in its .mas2j
    dependencies {
        "implementation"(files("$lib/jason.jar", "$lib/cartago.jar", "$lib/c4jason.jar", rootProject.file("BoomerEnv/lib/2p.jar")))
    }
}

tasks.register<JavaExec>("world") {
    group = "application"
    val env = project(":BoomerEnv")
    mainClass.set("alice.mas.lab6.World")
    classpath = env.extensions.getByType<SourceSetContainer>()["main"].runtimeClasspath
    workingDir = env.projectDir   // prolog/world.pl
}

tasks.register<JavaExec>("run") {
    group = "application"
    val agent = project(":BoomerAgent")
    mainClass.set("jason.infra.centralised.RunCentralisedMAS")
    classpath = agent.extensions.getByType<SourceSetContainer>()["main"].runtimeClasspath
    workingDir = agent.projectDir
    args("BoomerAgent.mas2j")
    val console = project.hasProperty("console")
    val logging = agent.file("logging.properties")
    doFirst { if (console) logging.writeText(consoleLoggingText) else logging.delete() }
}
