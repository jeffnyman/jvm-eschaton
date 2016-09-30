package com.testerstories.textadv;

import cucumber.api.java8.En;

import static org.assertj.core.api.Assertions.assertThat;

public class ParserSteps implements En {
    private Parser parser;
    private Action action;

    public ParserSteps() {
        Given("^the parser$", () -> {
            parser = new Parser();
        });

        When("^the action \"([^\"]*)\" is parsed$", (String input) -> {
            action = parser.parse(input);
        });

        Then("^the verb is recognized as \"([^\"]*)\"$", (String verb) -> {
            assertThat(action.getVerb()).isEqualTo(verb);
        });

        Then("^the direct object is recognized as \"([^\"]*)\"$", (String directObject) -> {
            assertThat(action.getDirectObject()).isEqualTo(directObject);
        });

        Then("^the direct object article is recognized as \"([^\"]*)\"$", (String directObjectArticle) -> {
            assertThat(action.getDirectObjectArticle()).isEqualTo(directObjectArticle);
        });

        Then("^the direct object modifier is recognized as \"([^\"]*)\"$", (String directObjectModifier) -> {
            assertThat(action.getDirectObjectModifiers()).isEqualTo(directObjectModifier);
        });
    }
}
