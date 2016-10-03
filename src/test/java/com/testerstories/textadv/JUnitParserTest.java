package com.testerstories.textadv;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JUnitParserTest {
    private ParserOriginal parser;
    private Action action;

    @Before
    public void setUp() {
        parser = new ParserOriginal();
    }

    @After
    public void tearDown() {
        action = null;
        parser = null;
    }

    @Test
    public void handleIntransitiveVerb() {
        action = parser.parse("inventory");
        assertThat(action.getVerb()).isEqualTo("inventory");
        assertThat(action.getDirectObjects()).isEqualTo("");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void handleTransitiveVerb() {
        action = parser.parse("take lantern");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjects()).isEqualTo("lantern");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void handleTransitiveVerbWithArticle() {
        action = parser.parse("take the lantern");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjects()).isEqualTo("lantern");
        assertThat(action.getDirectObjectArticles()).isEqualTo("the");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void handleTransitiveVerbWithModifier() {
        action = parser.parse("take brass lantern");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjects()).isEqualTo("lantern");
        assertThat(action.getDirectObjectModifiers()).isEqualTo("brass");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void handleTransitiveVerbWithArticleAndModifier() {
        action = parser.parse("take the brass lantern");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjects()).isEqualTo("lantern");
        assertThat(action.getDirectObjectArticles()).isEqualTo("the");
        assertThat(action.getDirectObjectModifiers()).isEqualTo("brass");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void handleTransitiveVerbWithMultipleModifiers() {
        action = parser.parse("take the heavy brass lantern");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjects()).isEqualTo("lantern");
        assertThat(action.getDirectObjectArticles()).isEqualTo("the");
        assertThat(action.getDirectObjectModifiers()).isEqualTo("heavy brass");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void handleIntransitiveVerbCommand() {
        action = parser.parse("floyd, run");
        assertThat(action.getCommandTarget()).isEqualTo("floyd");
        assertThat(action.getVerb()).isEqualTo("run");
        assertThat(action.getDirectObjects()).isEqualTo("");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void handleTransitiveVerbCommand() {
        action = parser.parse("floyd, take lantern");
        assertThat(action.getCommandTarget()).isEqualTo("floyd");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjects()).isEqualTo("lantern");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void handleTransitiveVerbWithArticleCommand() {
        action = parser.parse("floyd, take the lantern");
        assertThat(action.getCommandTarget()).isEqualTo("floyd");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjects()).isEqualTo("lantern");
        assertThat(action.getDirectObjectArticles()).isEqualTo("the");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void handleTransitiveVerbWithModifierCommand() {
        action = parser.parse("floyd, take brass lantern");
        assertThat(action.getCommandTarget()).isEqualTo("floyd");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjects()).isEqualTo("lantern");
        assertThat(action.getDirectObjectModifiers()).isEqualTo("brass");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void handleTransitiveVerbWithArticleAndModifierCommand() {
        action = parser.parse("floyd, take the brass lantern");
        assertThat(action.getCommandTarget()).isEqualTo("floyd");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjects()).isEqualTo("lantern");
        assertThat(action.getDirectObjectModifiers()).isEqualTo("brass");
        assertThat(action.getDirectObjectArticles()).isEqualTo("the");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void handleTransitiveVerbWithIndirectObject() {
        action = parser.parse("take lantern with gloves");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjects()).isEqualTo("lantern");
        assertThat(action.getPreposition()).isEqualTo("with");
        assertThat(action.getIndirectObject()).isEqualTo("gloves");
    }

    @Test
    public void handleTransitiveVerbWithIndirectObjectAndArticle() {
        action = parser.parse("take lantern with the gloves");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjects()).isEqualTo("lantern");
        assertThat(action.getPreposition()).isEqualTo("with");
        assertThat(action.getIndirectObjectArticle()).isEqualTo("the");
        assertThat(action.getIndirectObject()).isEqualTo("gloves");
    }

    @Test
    public void handleTransitiveVerbWithIndirectObjectAndModifiers() {
        action = parser.parse("take lantern with the thick gloves");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjects()).isEqualTo("lantern");
        assertThat(action.getPreposition()).isEqualTo("with");
        assertThat(action.getIndirectObjectArticle()).isEqualTo("the");
        assertThat(action.getIndirectObjectModifiers()).isEqualTo("thick");
        assertThat(action.getIndirectObject()).isEqualTo("gloves");
    }

    @Test
    public void handleTransitiveVerbWithDirectAndIndirectArticles() {
        action = parser.parse("put the lantern on the table");
        assertThat(action.getVerb()).isEqualTo("put");
        assertThat(action.getDirectObjects()).isEqualTo("lantern");
        assertThat(action.getDirectObjectArticles()).isEqualTo("the");
        assertThat(action.getPreposition()).isEqualTo("on");
        assertThat(action.getIndirectObjectArticle()).isEqualTo("the");
        assertThat(action.getIndirectObject()).isEqualTo("table");
    }

    @Test
    public void handleTransitiveVerbWithArticlesAndModifiers() {
        action = parser.parse("put the brass lantern on the wooden table");
        assertThat(action.getVerb()).isEqualTo("put");
        assertThat(action.getDirectObjects()).isEqualTo("lantern");
        assertThat(action.getDirectObjectArticles()).isEqualTo("the");
        assertThat(action.getDirectObjectModifiers()).isEqualTo("brass");
        assertThat(action.getPreposition()).isEqualTo("on");
        assertThat(action.getIndirectObjectArticle()).isEqualTo("the");
        assertThat(action.getIndirectObjectModifiers()).isEqualTo("wooden");
        assertThat(action.getIndirectObject()).isEqualTo("table");
    }

    @Test
    public void handleTransitiveVerbWithMultipleDirectObjects() {
        action = parser.parse("get lantern and key");
        assertThat(action.getVerb()).isEqualTo("get");
        assertThat(action.getDirectObjects()).isEqualTo("lantern key");

        action = parser.parse("get lantern, food, key");
        assertThat(action.getVerb()).isEqualTo("get");
        assertThat(action.getDirectObjects()).isEqualTo("lantern food key");

        action = parser.parse("get the lantern, the tasty food, and key");
        assertThat(action.getVerb()).isEqualTo("get");
        assertThat(action.getDirectObjectModifiers()).isEqualTo("tasty");
        assertThat(action.getDirectObjects()).isEqualTo("lantern food key");
    }

    @Test
    public void handleTransitiveVerbWithMultipleDirectObjectsSameArticles() {
        action = parser.parse("get the lantern and the key");
        assertThat(action.getVerb()).isEqualTo("get");
        assertThat(action.getDirectObjects()).isEqualTo("lantern key");
    }

    @Test
    public void handleTransitiveVerbWithMultipleDirectObjectsDifferentArticles() {
        action = parser.parse("get the lantern and a key");
        assertThat(action.getVerb()).isEqualTo("get");
        assertThat(action.getDirectObjects()).isEqualTo("lantern key");
    }

    @Test
    public void handleTransitiveVerbWithMultipleDirectObjectsAndModifiers() {
        action = parser.parse("get the brass lantern and the tasty food and the key");
        assertThat(action.getVerb()).isEqualTo("get");
        assertThat(action.getDirectObjectModifiers()).isEqualTo("brass tasty");
        assertThat(action.getDirectObjects()).isEqualTo("lantern food key");
    }

    @Test
    public void handleReveredDirectAndIndirectObjects() {
        action = parser.parse("feed the hungry cat a tasty treat");
        assertThat(action.getVerb()).isEqualTo("feed");
        assertThat(action.getDirectObjectArticles()).isEqualTo("a");
        assertThat(action.getDirectObjectModifiers()).isEqualTo("tasty");
        assertThat(action.getDirectObjects()).isEqualTo("treat");
        assertThat(action.getIndirectObjectArticle()).isEqualTo("the");
        assertThat(action.getIndirectObjectModifiers()).isEqualTo("hungry");
        assertThat(action.getIndirectObject()).isEqualTo("cat");

        action = parser.parse("give floyd the lantern");
        assertThat(action.getVerb()).isEqualTo("give");
        assertThat(action.getDirectObjects()).isEqualTo("lantern");
        assertThat(action.getDirectObjectArticles()).isEqualTo("the");
        assertThat(action.getIndirectObject()).isEqualTo("floyd");

        action = parser.parse("floyd, give me the lantern");
        assertThat(action.getCommandTarget()).isEqualTo("floyd");
        assertThat(action.getVerb()).isEqualTo("give");
        assertThat(action.getDirectObjectArticles()).isEqualTo("the");
        assertThat(action.getDirectObjects()).isEqualTo("lantern");
        assertThat(action.getIndirectObject()).isEqualTo("me");

        action = parser.parse("give floyd the brass lantern, the tasty smelly food, and the brass oversized duplicate skeleton key");
        assertThat(action.getVerb()).isEqualTo("give");
        assertThat(action.getDirectObjectArticles()).isEqualTo("the");
        assertThat(action.getDirectObjectModifiers()).isEqualTo("brass tasty smelly brass oversized duplicate skeleton");
        assertThat(action.getDirectObjects()).isEqualTo("lantern food key");
        assertThat(action.getIndirectObject()).isEqualTo("floyd");
    }

    @Test
    public void handleMissingAction() {
        action = parser.parse(null);
        assertThat(action.getVerb()).isNull();
        assertThat(action.getDirectObjects()).isEqualTo("");
        assertThat(action.getMessage()).isEqualTo("Failed");
    }

    @Test
    public void handleEmptyAction() {
        action = parser.parse("");
        assertThat(action.getVerb()).isNull();
        assertThat(action.getDirectObjects()).isEqualTo("");
        assertThat(action.getMessage()).isEqualTo("Failed");
    }

    @Test
    public void handlePunctuationRemoval() {
        action = parser.parse("inventory.");
        assertThat(action.getVerb()).isEqualTo("inventory");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void handleActionTrimming() {
        action = parser.parse("   inventory   ");
        assertThat(action.getVerb()).isEqualTo("inventory");
        assertThat(action.getMessage()).isEqualTo("Success");
    }
}
