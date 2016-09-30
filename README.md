# JVM Eschaton

_"I am the Eschaton. I am not your God.
I am descended from you, and exist in your future.
Thou shalt not violate causality within my historic light cone.
Or else."_
**Charles Stross, _Singularity Sky_**

_"When you look at the eschaton what you see,
strangely enough, is your own face."_
**Terence McKenna**

Existential Note: this project is not intended to [immanentize the eschaton](https://en.wikipedia.org/wiki/Immanentize_the_eschaton). (Just in case anyone was worried.)

The _eschaton_ is from the ancient Greek term ἔσχατον ‎(éskhaton). The word literally refers to the 'last thing' or even 'the end.' When construed as the latter, the phrase tends to refer to the final events, or last days, of history. In theological circles it also refers to the ultimate destiny of the human race.

A fairly grandiose set of meanings, to say the least. All my current project does, however, is indicate what I believe to be the 'last thing' I tend to put up regarding my experiments with development and automation on the JVM. I intend to use this project as a staging ground for continually trying out new ideas in JVM-based development as well as various forms of testing using JVM-based tools.

Specifically, this project will be used to showcase of series of developments, both of code and test, for pedagogical purposes in some classes I teach. That means this repository will often look a little messy as work is done over time to improve code that is purposely less than stellar in its style and function.

In many ways, the concept of the eschaton is an abstraction of a goal and thus is a child of hope. In a similar fashion, I will be using tests in this repository at various levels of abstraction. Tests are often their own child of hope, after all.

## Text Adventure Parser

As an example project, I started constructing a simple text adventure parser.

Check out the [code for the application](src/main/java/com/testerstories/textadv) as well as the [Java-based tests for the parser](src/test/java/com/testerstories/textadv).

I want to make it clear that with the current commits, this code is _supposed_ to be messy and cumbersome. I've taken it a bit to extremes, granted, but this is _exactly_ what many projects end up looking like.

## Build Tool

This entire repository will use Gradle as the build solution. I use Gradle because I prefer its approach to that of the XML of Maven. I've found that Gradle attempts to maintain the right balance between conventions and configurability.

In general, I also find the build tool choice to be reflective of how someone approaches development. I like my logic to be programmatic when possible, expressive, and intent-revealing. This is the same logic by which I approach my test code.

### Gradle Wrapper

The Gradle Wrapper consists of a `gradlew` shell script (for Linux/Mac), a `gradlew.bat` batch script (for Windows), and a few helper files. The idea is that instead of using the system-wide `gradle` command, you can run the builds via the wrapper script. The wrapper script takes care of downloading and installing Gradle if need be. This uses a specific version of Gradle that the project needs. This means you can safely upgrade (or downgrade) the system-wide Gradle installation without affecting any projects. This is very useful in Continuous Integration build environments because you don't need to install/update Gradle on the servers.

To use this, just call the wrapper script based on your operating system:

* On Mac OS X/Linux: `./gradlew taskName`
* On Windows: `gradlew taskName`

Keep in mind that the point of these wrappers is to let you run the build script even on machines that currently don't have Gradle available. 
