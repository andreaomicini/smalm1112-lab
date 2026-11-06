// Added in 2026: the three Eclipse projects of 2011 as one Gradle build (their .classpath: Philosophers uses
// ProducerConsumers and TuCSoN, ProducerConsumers uses TuCSoN).
rootProject.name = "l7-tucson-solution"
include("TuCSoN", "ProducerConsumers", "Philosophers")
