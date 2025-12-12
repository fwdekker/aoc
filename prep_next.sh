#!/bin/sh
install_template() {
    template="${1:?}"
    target="${2:?}"

    if [ -f "$target" ]; then
        printf "file '%s' already exists.\n" "$target"
        return 1
    fi

    mkdir -p "${target%/*}" || return 1
    cp "$template" "$target" || return 1
}

install_placeholders() {
    for target in "$@"; do
        mkdir -p -- "${target%/*}"
        touch -- "$target"
    done
}


project="${1:?First argument (project) required}"

if [ "$project" = "aoc" ]; then
    year="${2:?Second argument (year) required}"
    day="${3:?Third argument (day) required}"

    {
        root="$(pwd)/aoc/src/main/kotlin/com/fwdekker/aoc/"
        template="$root/y9999/AllDays.kt"
        target="$root/y$year/AllDays.kt"

        if [ -f "$target" ]; then
            install_template "$template" "$target" || exit 1
            sed -i -e "s/9999/$year/g" "$target" || exit 1
        fi
    }

    {
        root="$(pwd)/aoc/src/main/kotlin/com/fwdekker/aoc/"
        template="$root/y9999/Day999.kt"
        target="$root/y$year/Day$day.kt"

        install_template "$template" "$target" || exit 1
        sed -i -e "s/9999/$year/g" -e "s/999/$day/g" "$target" || exit 1
    }

    {
        root="aoc/src/main/resources/"

        install_placeholders "$root/y$year/Day$day.txt" "$root/y$year/Day${day}Sample1.txt" || exit 1
    }

    {
        root="$(pwd)/aoc/src/test/kotlin/com/fwdekker/aoc/"
        template="$root/y9999/Day999Test.kt"
        target="$root/y$year/Day${day}Test.kt"

        install_template "$template" "$target" || exit 1
        sed -i -e "s/9999/$year/g" -e "s/999/$day/g" -e "/Ignored/d" "$target" || exit 1
    }
elif [ "$project" = "euler" ]; then
    problem="${2:?Second argument (problem) required}"

    {
        root="euler/src/main/kotlin/com/fwdekker/euler/"
        template="$root/Problem9999.kt"
        target="$root/Problem${problem}.kt"

        install_template "$template" "$target" || exit 1
        sed -i -e "s/9999/$problem/g" "$target" || exit 1
    }

    {
        root="euler/src/test/kotlin/com/fwdekker/euler/"
        template="$root/Problem9999Test.kt"
        target="$root/Problem${problem}Test.kt"

        install_template "$template" "$target" || exit 1
        sed -i -e "s/9999/$problem/g" "$target" -e "/Ignored/d" || exit 1
    }
else
    printf "Unknown project '%s'. Aborting.\n" "$project"
    exit 1
fi
