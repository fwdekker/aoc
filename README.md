# Advent of Code
My solutions to the [Advent of Code](https://adventofcode.com/), years 2023, 2024, and 2025.
* [Usage instructions are here.](https://github.com/fwdekker/aoc/blob/main/aoc/README.md)
* [The source code is here.](https://github.com/fwdekker/aoc/tree/main/aoc/src/main/kotlin/com/fwdekker/aoc)
* [My puzzle inputs are here.](https://github.com/fwdekker/aoc-inputs) (In a private repo.)

Also includes my solutions for [Project Euler](https://projecteuler.net/).
* [The source code is here.](https://github.com/fwdekker/project-euler) (In a private repo.)

## Git submodules (aka: How to clone)
Advent of Code does not allow sharing puzzle inputs, and Project Euler does not allow sharing solutions.
Therefore, these are stored in separate, private repositories.
These repositories are included as [git submodules](https://git-scm.com/book/en/v2/Git-Tools-Submodules).
In other words: Where needed, this repository contains a link to a specific commit in another repository.

If you do not have access to that repository, you can still clone this project as normal and inspect my solutions.
However, to run my Advent of Code solutions, you will have to provide your own puzzle inputs.
For further information, check [the instructions in the Advent of Code submodule](https://github.com/fwdekker/aoc/blob/main/aoc/README.md).

In general, the following usage instructions apply.
```shell
# Clone without submodules
git clone git@github.com:fwdekker/aoc.git
# Clone with submodules
git clone git@github.com:fwdekker/aoc.git --recurse-submodules

# Add missing submodules after cloning
git submodule update --init --recursive
# Update submodules to respective HEADs
git submodule update --recursive --remote
```

If you have made changes in a submodule rooted at `dir/`, you have to `cd dir` before you can `git commit`.
After doing so, the root repository will be outdated, since it still points to a specific commit, not to the head of your specific branch.
You will have to update the submodule reference manually using a plain `git add dir` from within the root repository.

## Gradle sub-projects (aka: How to run)
To allow Advent of Code and Project Euler to be built separately, but still use a common codebase, the repository has been structured using [Gradle subprojects](https://docs.gradle.org/current/userguide/intro_multi_project_builds.html).
This repository has the following subprojects:
* [`buildSrc`](https://github.com/fwdekker/aoc/tree/main/buildSrc): Common build logic for all subprojects.
* [`std`](https://github.com/fwdekker/aoc/tree/main/std): Utility and helper functions for other subprojects.
* [`aoc`](https://github.com/fwdekker/aoc/tree/main/aoc): Advent of Code.
* [`euler`](https://github.com/fwdekker/project-euler): Project Euler.

Check their respective `README.md`s for more information.

You can run tasks as follows:
```shell
# Run day 4 of Advent of Code year 2023
gradlew :aoc:run -Pday=2023.4
# Run problem 19 of Euler Project
gradlew :euler:run -Pproblem=19

# Run all tests in all subprojects
gradlew test
# Run all tests in subproject 'aoc'
gradlew :aoc:test
# Run tests tagged with "Foo" in subproject 'aoc'
gradlew :aoc:test -Pkotest.tags="Foo"
# Run all tests for AoC 2025
gradlew :aoc:test -Pkotest.tags="2025"
```
