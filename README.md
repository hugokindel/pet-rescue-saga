# Pet Rescue Saga

![Pet Rescue Saga](https://hk-backup.s3.eu-west-3.amazonaws.com/images/logo.png)

Pet Rescue Saga is game made in Java (1.8+) which aims at being a fun and simple game which can be played either in a terminal or with a graphical interface (using the Swing GUI widget toolkit). It is being developed by two students.

## Features

- Pet Rescue Sage inspired gameplay.
- 6 playable levels.
- Multiple command line options.
- Command line interface.
- Complete graphical interface (with music support).
- Logging system:
	- Saves each outputs into log files.
	- Supports for ANSI codes (colors, clearing).
	- Supports for Unicode (through ANSI code).
	- Specific support for IntelliJ IDEA to provide a better debugging experience (better debugging experience of outputs than in a terminal).
- Easy to understands level files (permitting modding).
- Possibility to add custom levels to load.

## How to use

### From sources

#### Compiling using an IDE:

1) Create a fresh Java project in your preferred IDE.
2) Add the source code to the source folder of your project.
3) Compile.

You can finally run the program if you copy the data directory into the working directory of your project.

#### Compiling from the command line:
1) Make sure to have the `java` and `javac` command available from your path.
2) Open a terminal in the root directory of the project.
3) Compile using the following command:
`javac -encoding utf8 src/com/g10/prs/*.java src/com/g10/prs/common/*.java src/com/g10/prs/common/print/*.java src/com/g10/prs/entity/*.java src/com/g10/prs/level/*.java src/com/g10/prs/njson/*.java src/com/g10/prs/option/*.java src/com/g10/prs/power/*.java src/com/g10/prs/util/*.java src/com/g10/prs/view/*.java src/com/g10/prs/view/cli/*.java src/com/g10/prs/view/gui/*.java`

You can finally run the program using `java -cp src src/com/g10/prs/Program.java`.

### From a binary release

If you have a build version of the game (containing **both** the .jar and the data folder), you can play the game directly, just use one of the following command:

- To show every command line options you should use `java -jar prs.jar -h`.
- To play the game with the command line interface, either  use `java -jar prs.jar` or `java -jar prs.jar --view=cli`.
- To play the game with the graphical interface use `java -jar prs.jar --view=gui`.

## Command line options

The following command line options are available:

- **-v, --version** - Show program's version number and exit.
- **-h, --help** - Show the help message and exit.
- **-d, --debug** - Add a few debugging informations during runtime (only in the command line interface).
- **-s, --seed** - Specifies a seed to use, useful to reproduce a specific level (obtain the same colors for each block, and generates the same block for levels that supports refilling).
- **--view=\<value\>** - Defines which view should be used (either *cli* or *gui*).

## Contributors

- [KINDEL Hugo](https://github.com/ForxVT)
- [JAUROYON Maxime](https://github.com/Maxime-Jauroyon)

## Copyright

Some assets are copyrighted by their rightful authors, either people at King or King directly. We do not pretend to be the original authors of those specific files (please look at the text files included in each assets directory). They are used for educational purposes only.