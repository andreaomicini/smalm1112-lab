// Added in 2026 so the 2011 Eclipse projects build and run today: TuCSoN from its sources with tuProlog 2.4 (lib/),
// the exercises on top of it, each project's src/ as in its .classpath.
//   gradle build                          compile all three
//   gradle runProducerConsumers           PCLauncher: a TuCSoN node on localhost, 100 consumers and a producer
//   gradle runPhilosophers                DPLauncher: the node, the chops and spec.rsp in table@localhost, the philosophers
//   gradle :TuCSoN:node                   a TuCSoN node alone (alice.tucson.service.Node)
// Each launcher runs in its project's folder, where it finds tucson_conf.properties and spec.rsp.
subprojects {
    apply(plugin = "java-library")
    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    configure<SourceSetContainer> {
        named("main") {
            java.setSrcDirs(listOf("src"))
            // Dropbox's conflicted copies of three TuCSoN classes, in the archive as given: not compiled
            java.exclude("**/* (*conflicted copy*).java")
            // Eclipse put src/'s other files on the classpath too: the node boots from config/BOOT.PL and boot_spec.rsp
            resources.setSrcDirs(listOf("src"))
            resources.exclude("**/*.java")
        }
    }
}

project(":TuCSoN") {
    dependencies { "api"(fileTree("lib").also { it.include("*.jar") }) }
    // two sources carry Italian comments in Mac OS Roman ("andrà", "è"); only comments, the code is ASCII
    tasks.withType<JavaCompile>().configureEach { options.encoding = "MacRoman" }
}
project(":ProducerConsumers") {
    dependencies { "implementation"(project(":TuCSoN")) }
}
project(":Philosophers") {
    dependencies { "implementation"(project(":TuCSoN")); "implementation"(project(":ProducerConsumers")) }
}

fun launcher(task: String, proj: String, main: String) = tasks.register<JavaExec>(task) {
    group = "application"
    val p = project(proj)
    mainClass.set(main)
    classpath = p.extensions.getByType<SourceSetContainer>()["main"].runtimeClasspath
    workingDir = p.projectDir
}

launcher("runProducerConsumers", ":ProducerConsumers", "alice.mas.lab7.PCLauncher")
launcher("runPhilosophers", ":Philosophers", "alice.mas.lab7.DPLauncher")
project(":TuCSoN") {
    tasks.register<JavaExec>("node") {
        group = "application"
        mainClass.set("alice.tucson.service.Node")
        classpath = extensions.getByType<SourceSetContainer>()["main"].runtimeClasspath
    }
}
