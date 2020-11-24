# Minesweep
A minesweeper game written for the University of Helsinki Software Development Methods course autumn 2020.

## Documentation
* [Requirement Specification](documentation/req-specification.md)
* [Timekeeping](documentation/hours-worked.md)

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