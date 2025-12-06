#!/bin/sh
year="${1:?First argument (year) required}"
day="${2:?Second argument (day) required}"

cd "aoc/src/main/kotlin/com/fwdekker/aoc/" || exit 1
mkdir -p "y$year/" || exit 1
if [ -f "y$year/Day$day.kt" ]; then
    printf "file '%s' already exists.\n" "$(pwd)/y$year/Day$day.kt"
    exit 1
fi
cp "y9999/Day999.kt" "y$year/Day$day.kt" || exit 1
sed -i -e "s/9999/$year/g" -e "s/999/$day/g" "y$year/Day$day.kt" || exit 1
cd - >/dev/null || exit 1

cd "aoc/src/main/resources/" || exit 1
mkdir -p "y$year/" || exit 1
touch "y$year/Day$day.txt" "y$year/Day${day}Sample1.txt" || exit 1
cd - >/dev/null || exit 1

cd "aoc/src/test/kotlin/com/fwdekker/aoc/" || exit 1
mkdir -p "y$year/" || exit 1
if [ -f "y$year/Day${day}test.kt" ]; then
    printf "file '%s' already exists.\n" "$(pwd)/y$year/Day$day.kt"
    exit 1
fi
cp "y9999/Day999Test.kt" "y$year/Day${day}Test.kt" || exit 1
sed -i -e "s/9999/$year/g" -e "s/999/$day/g" "y$year/Day${day}Test.kt" || exit 1
cd - >/dev/null || exit 1
