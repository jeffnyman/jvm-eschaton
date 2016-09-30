package com.testerstories.textadv;

import j8spec.junit.J8SpecRunner;
import org.junit.runner.RunWith;

import static j8spec.J8Spec.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(J8SpecRunner.class)
public class ParserSpecTest {
    private Parser parser;
    private Action action;

    {
        beforeEach(() -> parser = new Parser());

        afterEach(() -> parser = null);

        describe("parser", () -> {
            context("intransitive verbs", () -> {
                it("action with just a verb", () -> {
                    action = parser.parse("inventory");
                    assertThat(action.getVerb(), is("inventory"));
                });
            });

            context("transitive verbs", () -> {
                it("action with verb and direct object", () -> {
                    action = parser.parse("take lantern");
                    assertThat(action.getVerb(), is("take"));
                    assertThat(action.getDirectObject(), is("lantern"));
                });

                it("action with verb and a direct object that has an article", () -> {
                    action = parser.parse("take the lantern");
                    assertThat(action.getVerb(), is("take"));
                    assertThat(action.getDirectObjectArticle(), is("the"));
                    assertThat(action.getDirectObject(), is("lantern"));
                });

                it("action with verb and a direct object that has a modifier", () -> {
                    action = parser.parse("take brass lantern");
                    assertThat(action.getVerb(), is("take"));
                    assertThat(action.getDirectObjectModifiers(), is("brass"));
                    assertThat(action.getDirectObject(), is("lantern"));
                });

                it("action with verb and a direct object that has an article and modifier", () -> {
                    action = parser.parse("take the brass lantern");
                    assertThat(action.getVerb(), is("take"));
                    assertThat(action.getDirectObjectArticle(), is("the"));
                    assertThat(action.getDirectObjectModifiers(), is("brass"));
                    assertThat(action.getDirectObject(), is("lantern"));
                });
            });
        });
    }
}
