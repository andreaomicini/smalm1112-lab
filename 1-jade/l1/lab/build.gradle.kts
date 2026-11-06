// Added in 2026 so the 2011 Eclipse project builds and runs today: sources in src/, libraries in lib/, as in .classpath.
//   gradle build                     compile
//   gradle run [-Pgui]               a JADE platform with the broker and the player alice (-Pgui: JADE's RMA GUI)
//   gradle join -Pplayer=bob         one more player, in a container of its own: start players one at a time,
//                                    since each looks for opponents once, when it starts (StartNewGame)
plugins {
    java
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

sourceSets.main {
    java.setSrcDirs(listOf("src"))
}

dependencies {
    implementation(fileTree("lib").also { it.include("*.jar") })
}

fun JavaExec.jade(vararg extra: String) {
    group = "application"
    mainClass.set("jade.Boot")
    classpath = sourceSets.main.get().runtimeClasspath
    // APDescription.txt and MTPs-*.txt into build/, not over the 2011 copies beside the sources
    args(listOf("-file-dir", "build/") + extra)
}

tasks.register<JavaExec>("run") {
    val agents = (findProperty("agents") as String?) ?: "broker:alice.mas.lab1.Broker;alice:alice.mas.lab1.Player"
    jade(*listOfNotNull(if (hasProperty("gui")) "-gui" else null, "-agents", agents).toTypedArray())
}

tasks.register<JavaExec>("join") {
    val name = (findProperty("player") as String?) ?: "bob"
    jade("-container", "-container-name", "c-$name", "-agents", "$name:alice.mas.lab1.Player")
}
