package com.testerstories.textadv;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestNGParserTest {
    private Parser parser;

    @BeforeMethod
    public void setUp() {
        parser = new Parser();
    }

    @AfterMethod
    public void tearDown() {
        parser = null;
    }

    @DataProvider(name="actions")
    public Object[][] actions() {
        return new Object[][] {
                { "inventory",                   null,    "inventory", "",        null },
                { "take lantern",                null,    "take",      "lantern", null },
                { "floyd, take lantern",         "floyd", "take",      "lantern", null },
                { "put lantern on table",        null,    "put",       "lantern", "table"},
                { "floyd, put lantern on table", "floyd", "put",       "lantern", "table"}
        };
    }

    @Test(dataProvider = "actions")
    public void sampleActions(String input, String target, String verb, String directObject, String indirectObject) {
        Action action = parser.parse(input);
        assertThat(action.getCommandTarget()).isEqualTo(target);
        assertThat(action.getVerb()).isEqualTo(verb);
        assertThat(action.getDirectObject()).isEqualTo(directObject);
        assertThat(action.getIndirectObject()).isEqualTo(indirectObject);
        assertThat(action.getMessage()).isEqualTo("Success");
    }
}
