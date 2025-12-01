# AoC: Advent of Code
My solutions to the [Advent of Code](https://adventofcode.com/).

Uses utility and helper functions from the [`std` subproject](https://github.com/fwdekker/aoc/tree/main/std).

[Click here if you just want to read my solutions.](https://github.com/fwdekker/aoc/tree/main/aoc/src/main/kotlin/com/fwdekker/aoc)

## Usage instructions
If you want to run my solutions, you will have to provide your own puzzle inputs, since those may not be shared according to the terms of Advent of Code.
Puzzle inputs are stored under `aoc/src/main/resources`, with a subdirectory for each year.
For example, the puzzle input for Day 20 of Year 2023 is stored as `aoc/src/main/resources/y2023/Day20.txt`.
Do not use leading zeroes for the day number:
Day 3 is stored as `Day3.txt`, **not** as `Day03.txt`.

To run a solution, simply execute `gradlew :aoc:run -Pday=2023.4` on the command line.
