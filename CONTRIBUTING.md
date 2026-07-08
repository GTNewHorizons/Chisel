Contributing
============
Before contributing, make sure you're familiar with the [GT:NH Contribution Guidelines](https://github.com/GTNewHorizons/.github/blob/master/CONTRIBUTING.md)
___
Setting up a Development Environment
------------------------------------
Chisel is open-source under the GPL v2 license.  As a result, you may contribute to the development of the mod via pull requests.

To set up the mod as to allow you to make changes, do the following:

1. Clone the repository onto your local system.
2. Open a command prompt from the repository folder.
3. Run the command `gradlew setupDecompWorkspace` (using `./gradlew` if on Mac/Linux)

*The next steps vary based on what IDE you wish to use*

### IntelliJ IDEA
1. Run `gradlew idea`.
2. Open IntelliJ and point to either the project folder or the `build.gradle` file.
3. After opening the project in IntelliJ, run `gradlew genIntellijRuns` in the command terminal you opened earlier.

### Eclipse

1. Run `gradlew eclipse`.
2. Open Eclipse and import the folder as a project.

You now have a functional local copy of Chisel, ready to develop on.
___
Formatting
----------

When coding for the GTNH branch, please run `gradlew spotlessApply` before posting a PR.
