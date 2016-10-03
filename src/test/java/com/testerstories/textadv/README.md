## Testing the Parser

### Unit Tests

There are a set of parser tests. To execute them:

_JUnit_
* `gradle clean test`

_TestNG_
* `gradle clean testng`

You can also run all tests together and get an aggregate report:

* `gradle clean check`

The tests are not fully replicated in both types of test library. The TestNG test adds a way to conceptualize test conditions by providing data in a way that may make understanding what is being tested and why more clear. This is a theme that is important to consider because you are considering the level of abstraction and the mode of expression that you are using for tests.

The `ParserSpecTest` is an example of trying a specification-based approach to the test expression. These tests are run via the JUnit runner and, as such, are reported as part of its execution. Like the TestNG set, this set of tests is currently not as fleshed out as the pure JUnit tests in `ParserTest` because the goal here is to show another method of expression.

### Cucumber Tests

You can run a feature file (`Parser.feature`) by doing the following:
 
* `gradle clean cucumber`

This will solely run the Cucumber runner (`CukeRunner`). This is yet another example of raising the abstraction level with a natural language interface, using Gherkin as the structuring element.

### Test Insight

Keep in mind a key point: it's possible to have incredibly terrible code and yet still have good code coverage of it. Having tests, particularly if they did not drive your design, can give a false sense of confidence of some key aspects of quality: discoverability, maintainability, extensibility and so on.

But can you use the tests to draw conclusions?

Consider, for example, that this action does work and would pass a test:

* _"floyd, hit a marauding alien with the golden sword"_

Yet, this one would fail:

* _"floyd, hit a marauding alien with the golden sword of the ancients"_

And, in fact, even this much simpler action would fail:

* _"hit alien with the sword of the ancients"_

And this even simpler action would similarly fail:

* _"take sword of the ancients"_

Given the tests that are already passing, and given the above, it's clear what the parser is _not_ handling. And that's really easy to see when someone points it out to you. But was the combined system -- code and test -- enough to allow you to reason about these kinds of cases?
