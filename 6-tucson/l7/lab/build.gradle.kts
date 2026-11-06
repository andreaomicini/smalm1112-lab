// Added in 2026 so the 2011 Eclipse projects build and run today: TuCSoN from its sources with tuProlog 2.4 (lib/),
// the exercises on top of it, each project's src/ as in its .classpath.
//   gradle build                          compile all three
//   (the exercises' launchers are yours to write; lab 7's solution runs them with runProducerConsumers, runPhilosophers)
//   gradle :TuCSoN:node                   a TuCSoN node alone (alice.tucson.service.Node)
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

project(":TuCSoN") {
    tasks.register<JavaExec>("node") {
        group = "application"
        mainClass.set("alice.tucson.service.Node")
        classpath = extensions.getByType<SourceSetContainer>()["main"].runtimeClasspath
    }
}
