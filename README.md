# Lab – Multiagent Systems LM 2011/2012

![SMA 11·12 course logo](logo/sma1112-tile-green.svg)

The material for the **lab** of the course of Multiagent Systems LM (*Sistemi
Multiagente LM*), Academic Year 2011/2012—module 2, Danilo Pianini, with Andrea
Omicini, Alma Mater Studiorum – Università di Bologna, Cesena.

This repository holds the **code**: the projects given in the lab classes, and
their solutions. The **slides** are published separately, in
`smalm1112-slides` (decks L0–L7); each part below names the decks it goes with.

## The parts

| part | what it is | decks |
| --- | --- | --- |
| [`1-jade`](1-jade/) | Java agents on the JADE platform: the word game | L1 |
| [`2-jason`](2-jason/) | agents in AgentSpeak(L), on Jason | L2, L3 |
| [`3-jasonjade`](3-jasonjade/) | Jason agents on JADE, talking to Java ones | L4 |
| [`4-cartago`](4-cartago/) | environments of artifacts, in CArtAgO: the tutorials | L5 |
| [`5-boomers`](5-boomers/) | Jason, CArtAgO, Java and tuProlog together: the Boomers game | L6 |
| [`6-tucson`](6-tucson/) | tuple centres programmed in ReSpecT, on TuCSoN | L7 |

Inside a part, `l1/`, `l2/`, … are the classes, named after the deck each goes
with. In a class, **`lab/` is the code given at the start**, where there was
any, and **`solution/`** the code given after the class. Both are exactly as
given to the students in 2011; only the build files are new.

## What you need

**A JDK 17 and Gradle 9.** The projects carry no Gradle wrapper; every library
they use is inside the repository, in `lib/` at the root or in the project's own
`lib/`, so nothing is downloaded.

## Running a project

Each project's `build.gradle.kts` says, at its top, what to run. For example:

```bash
cd 1-jade/l1/solution
gradle run                      # a JADE platform with the broker and the player alice
gradle join -Pplayer=bob        # then, in another terminal, one more player
```

The Jason projects print to Jason's console window; add `-Pconsole` to read the
agents on the terminal instead.

## Getting a copy

```bash
git clone <this repository>
```

You do not need to know git to use this. Downloading the repository as an
archive from its web page works just as well.
