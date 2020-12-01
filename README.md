# Minesweep
A minesweeper game written for the University of Helsinki Software Development Methods course autumn 2020.

## Releases
* [Week 5](https://github.com/emsknock/minesweep/releases/tag/v0.1-alpha)

## Documentation
* [Requirement Specification](documentation/req-specification.md)
* [Timekeeping](documentation/hours-worked.md)
* [Architecture](documentation/architecture.md)

## Command line
Run the program:
```bash
$ cd minesweep && mvn compile exec:java -Dexec.mainClass=minesweep.Main
```
Run tests:
```bash
$ cd minesweep && mvn test
```
Create test coverage report:
```bash
$ cd minesweep && mvn test jacoco:report
```
Run checkstyle:
```bash
$ cd minesweep && mvn jxr:jxr checkstyle:checkstyle
```
Create jar:
```bash
$ cd minesweep && mvn package
```