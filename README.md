# Developer's Legacy Index

Calculate the relative long-term impact of each individual developer based on git history. This is a score based on the surviving lines of
code contributed by each developer; a surviving line of code is one that is still present in the current main branch.

Older code _can_ be considered more valuable because it has passed the test of time and it has been providing value in production for
longer, some strategies to account for this are available. The absolute score is meaningless, the only value is in comparing scores between
developers. Consider using a normalised result if you want to compare scores across repositories.

Multiple factors can affect the validity of the score (technical debt, development practices, etc.), so take these results with a pinch of
code.

## Build

```
mvn clean verify
```

## Run

```
java -jar target/developer-legacy-index-1.0-SNAPSHOT.jar [weight-strategy] [--normalise-result] repository-path
```

### Options

`--normalise-result` (optional): make the highest score 1 and adjust all other scores accordingly. Available weighting strategies:

- `--no-weight` (default): no weight applied, all surviving lines of code are worth the same.
- `--linear-weight`: the value of a surviving line increases linearly with age.
- `--exponential-weight`: the value of a surviving line increases exponentially with age.
