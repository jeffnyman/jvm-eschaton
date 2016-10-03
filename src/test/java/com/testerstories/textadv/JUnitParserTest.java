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
    public void intransitive_verb() {
        action = parser.parse("inventory");
        assertThat(action.getVerb()).isEqualTo("inventory");
        assertThat(action.getDirectObjects()).isEqualTo("");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void transitive_verb() {
        action = parser.parse("take lantern");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjects()).isEqualTo("lantern");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void transitive_verb_with_article() {
        action = parser.parse("take the lantern");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjects()).isEqualTo("lantern");
        assertThat(action.getDirectObjectArticles()).isEqualTo("the");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void transitive_verb_with_modifier() {
        action = parser.parse("take brass lantern");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjects()).isEqualTo("lantern");
        assertThat(action.getDirectObjectModifiers()).isEqualTo("brass");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void transitive_verb_with_article_and_modifier() {
        action = parser.parse("take the brass lantern");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjects()).isEqualTo("lantern");
        assertThat(action.getDirectObjectArticles()).isEqualTo("the");
        assertThat(action.getDirectObjectModifiers()).isEqualTo("brass");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void transitive_verb_with_multiple_modifiers() {
        action = parser.parse("take heavy brass lantern");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjects()).isEqualTo("lantern");
        assertThat(action.getDirectObjectModifiers()).isEqualTo("heavy brass");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void transitive_verb_with_article_and_multiple_modifiers() {
        action = parser.parse("take the heavy brass lantern");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjects()).isEqualTo("lantern");
        assertThat(action.getDirectObjectArticles()).isEqualTo("the");
        assertThat(action.getDirectObjectModifiers()).isEqualTo("heavy brass");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void transitive_verb_with_two_direct_objects_using_and() {
        action = parser.parse("take lantern and shovel");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjects()).isEqualTo("lantern shovel");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void transitive_verb_with_articles_with_two_direct_objects_using_and() {
        action = parser.parse("take lantern and the shovel");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjectArticles()).isEqualTo("the");
        assertThat(action.getDirectObjects()).isEqualTo("lantern shovel");
        assertThat(action.getMessage()).isEqualTo("Success");

        action = parser.parse("take the lantern and shovel");
        assertThat(action.getDirectObjectArticles()).isEqualTo("the");
        assertThat(action.getDirectObjects()).isEqualTo("lantern shovel");
        assertThat(action.getMessage()).isEqualTo("Success");

        action = parser.parse("take the lantern and the shovel");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjectArticles()).isEqualTo("the the");
        assertThat(action.getDirectObjects()).isEqualTo("lantern shovel");
        assertThat(action.getMessage()).isEqualTo("Success");

        action = parser.parse("take the lantern and a shovel");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjectArticles()).isEqualTo("the a");
        assertThat(action.getDirectObjects()).isEqualTo("lantern shovel");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void transitive_verb_with_articles_and_modifiers_with_two_direct_objects_using_and() {
        action = parser.parse("take the brass lantern and the sturdy shovel");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjectArticles()).isEqualTo("the the");
        assertThat(action.getDirectObjectModifiers()).isEqualTo("brass sturdy");
        assertThat(action.getDirectObjects()).isEqualTo("lantern shovel");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void transitive_verb_with_more_than_two_direct_objects_using_and() {
        action = parser.parse("take lantern and shovel and key");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjects()).isEqualTo("lantern shovel key");
        assertThat(action.getMessage()).isEqualTo("Success");

        action = parser.parse("get the brass lantern and the tasty food and the key");
        assertThat(action.getVerb()).isEqualTo("get");
        assertThat(action.getDirectObjectModifiers()).isEqualTo("brass tasty");
        assertThat(action.getDirectObjects()).isEqualTo("lantern food key");
    }

    @Test
    public void transitive_verb_with_two_direct_objects_using_comma() {
        action = parser.parse("take lantern, shovel");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjects()).isEqualTo("lantern shovel");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void transitive_verb_with_articles_with_two_direct_objects_using_comma() {
        action = parser.parse("take the lantern, a shovel");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjectArticles()).isEqualTo("the a");
        assertThat(action.getDirectObjects()).isEqualTo("lantern shovel");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void transitive_verb_with_more_than_two_direct_objects_using_comma() {
        action = parser.parse("take lantern, shovel, key");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjects()).isEqualTo("lantern shovel key");
        assertThat(action.getMessage()).isEqualTo("Success");

        action = parser.parse("take the lantern, the shovel, the key");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjectArticles()).isEqualTo("the the the");
        assertThat(action.getDirectObjects()).isEqualTo("lantern shovel key");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void command_with_intransitive_v() {
        action = parser.parse("floyd, run");
        assertThat(action.getCommandTarget()).isEqualTo("floyd");
        assertThat(action.getVerb()).isEqualTo("run");
        assertThat(action.getDirectObjects()).isEqualTo("");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void command_with_transitive_verb() {
        action = parser.parse("floyd, take lantern");
        assertThat(action.getCommandTarget()).isEqualTo("floyd");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjects()).isEqualTo("lantern");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void command_with_transitive_verb_and_article() {
        action = parser.parse("floyd, take the lantern");
        assertThat(action.getCommandTarget()).isEqualTo("floyd");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjects()).isEqualTo("lantern");
        assertThat(action.getDirectObjectArticles()).isEqualTo("the");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void command_with_transitive_verb_and_modifier() {
        action = parser.parse("floyd, take brass lantern");
        assertThat(action.getCommandTarget()).isEqualTo("floyd");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjects()).isEqualTo("lantern");
        assertThat(action.getDirectObjectModifiers()).isEqualTo("brass");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void command_with_transitive_verb_with_article_and_modifier() {
        action = parser.parse("floyd, take the brass lantern");
        assertThat(action.getCommandTarget()).isEqualTo("floyd");
        assertThat(action.getVerb()).isEqualTo("take");
        assertThat(action.getDirectObjects()).isEqualTo("lantern");
        assertThat(action.getDirectObjectModifiers()).isEqualTo("brass");
        assertThat(action.getDirectObjectArticles()).isEqualTo("the");
        assertThat(action.getMessage()).isEqualTo("Success");
    }

    @Test
    public void transitive_verb_with_indirect_object() {
        action = parser.parse("dig hole with shovel");
        assertThat(action.getVerb()).isEqualTo("dig");
        assertThat(action.getDirectObjects()).isEqualTo("hole");
        assertThat(action.getPreposition()).isEqualTo("with");
        assertThat(action.getIndirectObject()).isEqualTo("shovel");
    }

    @Test
    public void transitive_verb_with_indirect_object_and_article() {
        action = parser.parse("dig a hole with shovel");
        assertThat(action.getVerb()).isEqualTo("dig");
        assertThat(action.getDirectObjectArticles()).isEqualTo("a");
        assertThat(action.getDirectObjects()).isEqualTo("hole");
        assertThat(action.getPreposition()).isEqualTo("with");
        assertThat(action.getIndirectObject()).isEqualTo("shovel");

        action = parser.parse("dig a hole with the shovel");
        assertThat(action.getVerb()).isEqualTo("dig");
        assertThat(action.getDirectObjectArticles()).isEqualTo("a");
        assertThat(action.getDirectObjects()).isEqualTo("hole");
        assertThat(action.getPreposition()).isEqualTo("with");
        assertThat(action.getIndirectObjectArticle()).isEqualTo("the");
        assertThat(action.getIndirectObject()).isEqualTo("shovel");
    }

    @Test
    public void transitive_verb_with_indirect_object_with_articles_and_modifiers() {
        action = parser.parse("dig a big hole with the sturdy shovel");
        assertThat(action.getVerb()).isEqualTo("dig");
        assertThat(action.getDirectObjectArticles()).isEqualTo("a");
        assertThat(action.getDirectObjectModifiers()).isEqualTo("big");
        assertThat(action.getDirectObjects()).isEqualTo("hole");
        assertThat(action.getPreposition()).isEqualTo("with");
        assertThat(action.getIndirectObjectArticle()).isEqualTo("the");
        assertThat(action.getIndirectObjectModifiers()).isEqualTo("sturdy");
        assertThat(action.getIndirectObject()).isEqualTo("shovel");
    }

    public void transitive_verb_with_reversed_direct_and_indirect_objects() {
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

        action = parser.parse("give the lantern to floyd");
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

        action = parser.parse("floyd, give the lantern to me");
        assertThat(action.getCommandTarget()).isEqualTo("floyd");
        assertThat(action.getVerb()).isEqualTo("give");
        assertThat(action.getDirectObjectArticles()).isEqualTo("the");
        assertThat(action.getDirectObjects()).isEqualTo("lantern");
        assertThat(action.getPreposition()).isEqualTo("to");
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
