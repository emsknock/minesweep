# Minesweep
A minesweeper game written for the University of Helsinki Software Development Methods course autumn 2020.

## Releases
* [Week 5](https://github.com/emsknock/minesweep/releases/tag/v0.1-alpha)
* [Week 6](https://github.com/emsknock/minesweep/releases/tag/v0.2-alpha)

## Documentation
* [Requirement Specification](documentation/req-specification.md)
* [Timekeeping](documentation/hours-worked.md)
* [Architecture](documentation/architecture.md)
* [User manual](documentation/user-manual.md)

## Command line
Run the program:
```bash
$ mvn compile exec:java -Dexec.mainClass=minesweep.Main
```
Run tests:
```bash
$ mvn test
```
Create test coverage report:
```bash
$ mvn test jacoco:report
```
Run checkstyle:
```bash
$ mvn jxr:jxr checkstyle:checkstyle
```
Create jar:
```bash
$ mvn package
```