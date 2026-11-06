// Added in 2026: the fourteen CArtAgO tutorials of 2011, each an Eclipse/Jason project of its own, as one Gradle build.
rootProject.name = "l5-cartago-solution"
file(".").listFiles { f -> f.isDirectory && f.name.matches(Regex("\\d\\d[a-z]?-.*")) }!!.sorted().forEach { include(it.name) }
