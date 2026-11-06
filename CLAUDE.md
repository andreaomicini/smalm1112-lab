# CLAUDE.md

Guidance for Claude Code (claude.ai/code) working in this repository.

## What this is

The **lab material** of Multiagent Systems LM, A.Y. 2011/2012, module 2 (Danilo
Pianini, with Andrea Omicini), Alma Mater Studiorum, Università di Bologna,
Cesena. **A recovery**: the code given in the 2011 lab classes, as it was, made
to build and run again with build files only. Shaped after `as2021-lab`, as
`smalm1112-slides` is after its siblings. **Read `README.md` first.**

## Provenance

- **`v1-taught`** (author Danilo Pianini, dated 2011-12-06 18:31, the newest
  file): the 13 archives attached to the course's APICe Lab page
  (`Course.SmaLm1112.Lab`, on `Course.SmaLm1112M2.Lab` since 2026-11-06), what
  the students downloaded, plus `protocol.jpg` (L3). Left out: the Eclipse
  workspaces (`.metadata`), `bin/`, `.class`, and `3code.tar.gz` (its two files
  are in `3code-asl.tar.gz`). Kept as given: the `*~` files, `.stop___MAS`,
  three Dropbox "conflicted copy" files in L7.
- `l1/lab` is `1solution.tar.gz` ("code as developed before the lesson"),
  `l1/solution` is `1solutionrt.tar.gz` (after it, broker with referee).
- Pianini's own folder of 2011 (`~/Documents/corsi/2010-2015/2011:2012/
  pianini/2011-12/SMA/`) holds the same code plus Eclipse settings and a few
  later edits (L1 `AskForValidity.java`, L3 `broker.asl`, L7 `PCLauncher.java`):
  not in this repository. The run's comparison:
  `~/Documents/Claude/2026.11.06 – SMA-LM 11·12 lab code/`.

## Building, the rules

- **The 2011 code is not changed**, not a line: every commit after
  `v1-taught` adds build files beside it. Bugs are reported, not fixed.
- **Gradle 9 (Homebrew) on JDK 17** (`JAVA_HOME=/opt/homebrew/opt/openjdk@17`),
  sources compiled as Java 8; no wrapper. Each project reads its sources where
  its Eclipse `.classpath` put them (`src/`, `src/java`).
- **`lib/` at the root**: `jason.jar` (Pianini's Jason of October 2011, shipped
  with L4), `cartago.jar` and `c4jason.jar` (October 2010, from his 2010/11 lab:
  the 2011 projects pointed at a Jason installation). The jars of May 2012 and of
  2014 behave the same on L5.
- **Inside a task, `hasProperty`, `findProperty` and `extensions` are the
  task's**: use `project.` (a `-Pgui` that did nothing, 2026-11-06).
- **Jason reads `logging.properties` from the working directory only** (not from
  `-Djava.util.logging.config.file`): `-Pconsole` writes it there, git ignores
  it, a run without `-Pconsole` removes it.

## Running, the traps

- **Never open windows on Andrea's screen in a test**, and kill the JVM, not
  only `gradle`: `timeout gradle run` leaves the JavaExec JVM alive. Run in a
  process group of its own, `JAVA_TOOL_OPTIONS=-Djava.awt.headless=true`, `-Pconsole`;
  `ps` after every run. Two Jason console windows were left open this way on
  2026-11-06.
- **L1**: players look for opponents once, when they start; started together
  they call each other cheaters forever (5 million lines in 40 s). `run` starts
  one, `join` the others.
- **L3, L4**: the players resend cached words forever, as in 2011 (the L4 deck's
  screenshot `runold.png` shows the same messages).
- **L5**: `10-MultipleWorkspaces` ends on "Wrong workspace default" (its own
  second `quitWorkspace`); `11-RemoteWorkspace` needs `:11-RemoteWorkspace:node`
  first; `07a-GUI` is a window.
- **L6**: `World` always opens its monitor window; a headless test needs a
  stand-in `WorldMonitor` first on the classpath, outside the repository.
- **L7**: TuCSoN's sources read as Mac OS Roman (comments); the node boots from
  `src/`'s non-Java files (`config/BOOT.PL`, `boot_spec.rsp`), on the classpath
  as resources. With 101 agents at once some connections are refused (Java's
  default backlog of 50).

## Committing

- **Only when Andrea asks**, except for the recovery itself, which he asked for.
  The `claude:` prefix on every commit. Run the CI's builds locally before every
  push; commits touching only notes carry `[skip ci]`.
