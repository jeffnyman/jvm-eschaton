package com.testerstories.textadv;

import j8spec.junit.J8SpecRunner;
import org.junit.runner.RunWith;

import static j8spec.J8Spec.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(J8SpecRunner.class)
public class ParserSpecTest {
    private ParserOriginal parser;
    private Action action;

    {
        beforeEach(() -> parser = new ParserOriginal());

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
                    assertThat(action.getDirectObjects(), is("lantern"));
                });

                it("action with verb and a direct object that has an article", () -> {
                    action = parser.parse("take the lantern");
                    assertThat(action.getVerb(), is("take"));
                    assertThat(action.getDirectObjectArticles(), is("the"));
                    assertThat(action.getDirectObjects(), is("lantern"));
                });

                it("action with verb and a direct object that has a modifier", () -> {
                    action = parser.parse("take brass lantern");
                    assertThat(action.getVerb(), is("take"));
                    assertThat(action.getDirectObjectModifiers(), is("brass"));
                    assertThat(action.getDirectObjects(), is("lantern"));
                });

                it("action with verb and a direct object that has an article and modifier", () -> {
                    action = parser.parse("take the brass lantern");
                    assertThat(action.getVerb(), is("take"));
                    assertThat(action.getDirectObjectArticles(), is("the"));
                    assertThat(action.getDirectObjectModifiers(), is("brass"));
                    assertThat(action.getDirectObjects(), is("lantern"));
                });
            });
        });
    }
}
