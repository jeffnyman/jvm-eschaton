## Text Adventure Engine

As an example project, I started constructing a simple text adventure engine, called Voxam.

**Why "Voxam"?** The term comes from the early days of fantasy-based text adventures wherein your character would cast variously named spells. One of those was called "voxam" and it was used to "separate the energies of different magics." Since the core of my Voxam project is a parser that separates an action into its parts of speech, this seemed appropriate. (_Are you sorry you asked?_)

The basic idea of such an engine is that it contains a parser. An "action", in this context, is what's being attempted by the player when they provide input to the game. Players input actions to alter the state or behavior of the world that the game is modeling. A "command" is a specific type of action; it's an action that is being directed to another object in the game.

### Parser

Take a moment and bask in how awful the code in the `Parser` class is. At least it's better than the code in the `ParserOriginal` class. They are both functionally equivalent, served by the same tests but with differents levels of design pressure brought to bear.

Awful as that code may be, this is how most projects seem to start off. Perhaps not so obviously as this, but the overall point is valid. The problem is that our projects don't just start off like this. They persist like this. And, eventually, they end like this.

What you have here is essentially a very imperative program. That's certainly the case with `ParserOriginal`. And while `Parser` is slightly more modular, it still leaves a lot to be desired. The tests, however, aren't half-bad. In fact, they show that the parser is actually fairly good at its job.

Speaking to the parser code, you'll see variables introduced all over the place. You'll also see comments sprinkled throughout the code. Some are more helpful than others, but it can be very hard to build up a decent picture of exactly how this parser works. And note that this is the case even with the unit tests. I know _what_ commands will be parsed but it would take a bit of work to extend this parser in any meaningful way.

For example, can you easily tell that there is a fairly massive bit of duplication in place in `ParserOriginal`? Lines 145 to 193 are strikingly similar, in fact functionally identical, to lines 335 to 381. The `parse` method itself is much too large and full of conditionals to be adequately analyzed as part of a data flow algorithm. All of this has been fixed in `Parser`.

But, again, the tests don't really provide any warnings of the problems we now know about. And those tests probably don't provide any warnings for the problems we _don't_ known about. But why not? Because those tests, good as they might look and effective as they might be, never put pressure on design. Or, at least, never did so in a very effective way.

#### Building the Project

You can use the standard Gradle build lifecycle:

* `gradle clean build`

This task has been augmented to make sure that dependent JARs are made available in the library so they can more easily be referenced on the classpath.

#### Running the Project

Once built, you have a few run options. In traditional JVM style, you can either run the class file or the JAR. Another way is use the Gradle `run` task. Currently the entry point for Voxam either accepts an action to parse or will start up a very minimal command line interface that will process typed in actions.

Note that with the command line interface, you can quit the parser engine by typing the action `quit`. (In text adventure games, actions like `quit` are considered "meta-actions," meaning actions that take place out of the world model context.)

##### Via the Class

_To get the command line:_
* `java -cp build/libs/*:build/classes/main/ com.testerstories.textadv.Voxam`

_To pass an action, bypassing the command line:_
* `java -cp build/libs/*:build/classes/main/ com.testerstories.textadv.Voxam "take lantern"`

##### Via the JAR

_To get the command line:_
* `java -cp build/libs/*:build/libs/jvm-eschaton.jar com.testerstories.textadv.Voxam`

_To pass an action, bypassing the command line:_
* `java -cp build/libs/*:build/libs/jvm-eschaton.jar com.testerstories.textadv.Voxam "take lantern"`

Note that the JAR task is extended a bit so that an "über Jar" is created. This means you can run the JAR directly:

_To get the command line:_
* `java -jar build/libs/jvm-eschaton.jar`

_To pass an action, bypassing the command line:_
* `java -jar build/libs/jvm-eschaton.jar "take sword"`

##### Via the Run Task

Note that generally you'll want to use the quiet flag (`-q`) on these next commands to keep Gradle's build reporting to a minimum.

Also, the `run` task will use a `voxamInput` value as default input if this is provided as a project property. A common way to do this would be to store the property and a value for it in the `gradle.properties`.

The simplest way to run Voxam via the task:

* `gradle -q run`

You can choose your own project property like this:

* `gradle -q run -PvoxamInput="cast spell"`

If there is no `voxamInput` project property provided, then the command line interface will be displayed. One thing to note about this, however, is that the regular Gradle output will interfere with the command line operation. It will still work but it will look fairly messy. There are a couple of ways you can get around this. One is set a "dumb" terminal:

* `TERM=dumb gradle -q run`

Another (and the one I would go with) is:

* `gradle --console plain run`

##### Logging

Voxam will log a series of debug statements about its processing. You can modify the `simplelogger.properties` file but you can also send in a system property to do that configuration when you run the application. All you have to do is add the following to any of the commands shown above:

* `java -Dorg.slf4j.simpleLogger.defaultLogLevel=debug ...`

You can also get this logging via the command line interface from the `run` task. A few examples:

* `gradle run -Dorg.slf4j.simpleLogger.defaultLogLevel=debug`
* `gradle --console plain run -Dorg.slf4j.simpleLogger.defaultLogLevel=debug`
