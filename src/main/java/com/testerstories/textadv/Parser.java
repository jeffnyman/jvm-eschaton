package com.testerstories.textadv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Interactive fiction parser.
 *
 * @author Jeff Nyman
 * @version 0.1
 */
class Parser {
    private static final Logger LOG = LoggerFactory.getLogger(Parser.class);

    /**
     * Parse the player command, identifying its constituent parts.
     *
     * @param input the player input
     */
    Action parse(String input) {
        Action action = new Action();

        // Null commands should not be processed.
        if (input == null) {
            action.setMessage("Failed");
            return action;
        }

        // Remove leading and trailing whitespace.
        input = input.trim();

        // Remove trailing punctuation.
        while (input.length() > 0 &&
                !Character.isLetter(input.charAt(input.length() - 1))) {
            input = input.substring(0, input.length() -1);
        }

        // Empty commands should not be processed.
        if (input.equals("")) {
            action.setMessage("Failed");
            return action;
        }

        LOG.debug("Action: " + input);

        // Tokenize string into individual words.
        String[] string_array = new String[25];
        int string_index = 0;
        String word;

        while (!input.equals("")) {
            int next_word_break = input.indexOf(' ');

            if (next_word_break == -1) {
                // There is only one word left in the input string.
                word = input;
                input = "";

                string_array[string_index] = word;
                string_index++;
            } else {
                // There is another word left in the input string.
                word = input.substring(0, next_word_break);
                input = input.substring(next_word_break + 1);

                string_array[string_index] = word;
                string_index++;
            }
        }

        // At this point, string_index contains the total number of words.
        // The words are entries in string_array. As phrase parsing occurs,
        // the phrase_index will hold a moving snapshot of where the parser
        // is currently pointing.

        LOG.debug("String Array: " + Arrays.toString(string_array));
        LOG.debug("String Index: " + string_index);

        int phrase_index = 0;

        // RULE: FIND COMMAND TARGET
        // The first word of input could be a command target.
        // A command is recognized by an object name followed by a comma.

        int command_index = -1;
        int command_comma;

        word = string_array[phrase_index];
        command_comma = word.indexOf(',');

        if (command_comma != -1) {
            command_index = phrase_index;
            // Strip the comma
            string_array[command_index] = string_array[command_index].substring(0, command_comma);
            phrase_index++;
        }

        if (command_index != -1) {
            LOG.debug("Command Target: " + string_array[command_index]);
            action.setCommandTarget(string_array[command_index]);
        }

        // RULE: FIND VERB
        // The first word of input (exclusing a command target) must be the verb.
        int verb_index;
        verb_index = phrase_index;
        phrase_index++;

        if (verb_index != -1) {
            LOG.debug("Verb Word: " + string_array[verb_index]);
            action.setVerb(string_array[verb_index]);
        }

        // RULE: FIND PREPOSITION
        // Looking for a preposition will indicate if the action has an indirect object.
        String prepositions = ":in:on:to:under:inside:outside:into:with:";
        int phrase_bit = phrase_index;
        int prep_index = -1;

        while ((phrase_bit < string_index) && (prep_index == -1)) {
            if (prepositions.contains(":" + string_array[phrase_bit] + ":")) {
                prep_index = phrase_bit;
                break;
            }
            phrase_bit++;
        }

        if (prep_index != -1) {
            LOG.debug("Preposition: " + string_array[prep_index]);
            action.setPreposition(string_array[prep_index]);
        }

        // RULE: FIND INDIRECT OBJECT
        // If there is a preposition, then there should be an indirect object phrase.
        int phrase_start_index;
        int phrase_end_index;
        int indirect_object_article_index = -1;
        int indirect_object_modifier_index = -1;
        int indirect_object_index = -1;
        String articles = ":a:an:the:";

        if (prep_index != -1) {
            phrase_start_index = prep_index + 1;
            phrase_end_index = string_index - 1;

            // RULE: FIND ARTICLE
            phrase_bit = phrase_start_index;
            while ((phrase_bit < phrase_end_index) && (indirect_object_article_index == -1)) {
                if (articles.contains(":" + string_array[phrase_bit] + ":")) {
                    indirect_object_article_index = phrase_bit;
                }
                phrase_bit++;
            }

            if (indirect_object_article_index != -1) {
                LOG.debug("Indirect Object Article: " + string_array[indirect_object_article_index]);
                action.setIndirectObjectArticle(string_array[indirect_object_article_index]);
            }

            // RULE: FIND OBJECT
            // The last word in the input is the object.
            if (phrase_start_index <= phrase_end_index) {
                indirect_object_index = phrase_end_index;
            }

            // RULE: FIND MODIFIER
            // If any input remains, it is the modifier.
            if (indirect_object_article_index != -1) {
                phrase_bit = indirect_object_article_index + 1;
            } else {
                phrase_bit = phrase_start_index;
            }

            // RULE: Gather modifiers.
            if (indirect_object_index - phrase_bit >= 1) {
                LOG.debug("Indirect Object Modifiers: ");

                for (phrase_index = phrase_bit; phrase_index < indirect_object_index; phrase_index++) {
                    LOG.debug(string_array[phrase_bit] + " ");
                    action.setIndirectObjectModifiers(string_array[phrase_bit]);
                }
                LOG.debug("");
                // THE NEXT LINE IS "EXTENDED"
                indirect_object_modifier_index = phrase_bit;
            }

            if (indirect_object_index != -1) {
                LOG.debug("Indirect Object: " + string_array[indirect_object_index]);
                action.setIndirectObject(string_array[indirect_object_index]);
            }
        }

        // RULE 4: The last word of input is always the direct object.
        int direct_object_index = -1;
        phrase_end_index = verb_index;

        int direct_object;
        if (prep_index != -1) {
            direct_object = prep_index - 1;
        } else {
            direct_object = string_index - 1;
        }

        int phrase_bit_join;
        int first_direct_object_index = -1;
        int first_direct_object_article_index = -1;
        int first_direct_object_modifier_index = -1;

        // This loop handles the possibility of a direct object phrase
        // rather than just a single word that is the subject of the
        // action.
        do {
            phrase_start_index = phrase_end_index + 1;

            if ((phrase_start_index < string_index) && (string_array[phrase_start_index].equals("and"))) {
                phrase_start_index++;
            }

            if (prep_index != -1) {
                phrase_end_index = prep_index - 1;
            } else {
                phrase_end_index = string_index - 1;
            }

            // Find connective "and" or comma to adjust phrase_end_index if necessary
            for (phrase_bit_join = phrase_start_index; phrase_bit_join <= phrase_end_index; phrase_bit_join++) {
                if (string_array[phrase_bit_join].equals("and")) {
                    phrase_end_index = phrase_bit_join - 1;
                    break;
                }

                if (string_array[phrase_bit_join].indexOf(',') != -1) {
                    string_array[phrase_bit_join] = string_array[phrase_bit_join].substring(0, string_array[phrase_bit_join].indexOf(','));
                    phrase_end_index = phrase_bit_join;
                    break;
                }
            }

            // RULE: FIND ARTICLE
            // Check for an article as part of the phrase.
            phrase_bit = phrase_end_index;
            int direct_object_article_index = -1;
            int direct_object_modifier_index = -1;

            while ((phrase_bit >= phrase_start_index) && (direct_object_article_index == -1)) {
                if (articles.contains(":" + string_array[phrase_bit] + ":")) {
                    direct_object_article_index = phrase_bit;

                    // THE IF CONDITION IS FOR "EXTENDED"
                    if (first_direct_object_article_index == -1) {
                        first_direct_object_article_index = phrase_bit;
                    }

                    break;
                }
                phrase_bit--;
            }

            if (direct_object_article_index != -1) {
                LOG.debug("Direct Object Article: " + string_array[direct_object_article_index]);
                action.setDirectObjectArticle(string_array[direct_object_article_index]);
            }

            // RULE: FIND OBJECT
            // The last word in the input is the direct object.
            if (phrase_start_index <= phrase_end_index) {
                direct_object_index = phrase_end_index;

                // THE IF CONDITION IS FOR "EXTENDED
                if (first_direct_object_index == -1) {
                    first_direct_object_index = phrase_end_index;
                }
            }

            // RULE: FIND MODIFIER
            // If any input remains, it is a modifier.
            if (direct_object_article_index != -1) {
                phrase_bit = direct_object_article_index + 1;
            } else {
                phrase_bit = phrase_start_index;
            }

            // RULE: Gather modifiers.
            if (direct_object_index - phrase_bit >= 1) {
                LOG.debug("Direct Object Modifiers: ");

                for (phrase_index = phrase_bit ; phrase_index < direct_object_index; phrase_index++) {
                    LOG.debug(string_array[phrase_index] + " ");
                    action.setDirectObjectModifiers(string_array[phrase_index]);

                    // THE IF CONDITION IS FOR "EXTENDED"
                    if (first_direct_object_modifier_index == -1) {
                        first_direct_object_modifier_index = phrase_bit;
                    }
                }

                LOG.debug("");
                // NEXT LINE IS FOR "EXTENDED"
                direct_object_modifier_index = phrase_bit;
            }

            if (direct_object_index != -1) {
                LOG.debug("Direct Object: " + string_array[direct_object_index]);
                action.setDirectObject(string_array[direct_object_index]);
            }
        } while (phrase_end_index != direct_object);

        // This next section has to handle phrases where the direct and the indirect
        // objects have been reversed. If a preposition was not detected, and if
        // there is another phrase before the direct object phrase, then it is
        // necessary to parse that other phrase as the indirect object phrase. This
        // is "extended" parsing. Comments above indicate where variables were set
        // to accommodate this.

        // Setting the phrase bit to a range it could not possibly have reached.
        phrase_bit = 999;

        if (first_direct_object_article_index != -1) {
            phrase_bit = first_direct_object_article_index;
        } else if (first_direct_object_modifier_index != -1) {
            phrase_bit_join = first_direct_object_modifier_index;
            if (phrase_bit > phrase_bit_join) {
                phrase_bit = phrase_bit_join;
            }
        } else if (first_direct_object_index != -1) {
            phrase_bit = first_direct_object_index;
            if (phrase_bit > phrase_bit_join) {
                phrase_bit = phrase_bit_join;
            }
        }

        if ((indirect_object_index == -1) && (phrase_bit != 999) && (phrase_bit - verb_index > 1)) {
            phrase_start_index = verb_index + 1;
            phrase_end_index = phrase_bit - 1;

            // RULE: FIND ARTICLE
            phrase_bit = phrase_start_index;

            while ((phrase_bit < phrase_end_index) && (indirect_object_article_index == -1)) {
                if (articles.contains(":" + string_array[phrase_bit] + ":")) {
                    indirect_object_article_index = phrase_bit;
                }
                phrase_bit++;
            }

            if (indirect_object_article_index != -1) {
                LOG.debug("Indirect Object Article: " + string_array[indirect_object_article_index]);
                action.setIndirectObjectArticle(string_array[indirect_object_article_index]);
            }

            // RULE: FIND OBJECT
            // The last word in the input is the object.
            if (phrase_start_index <= phrase_end_index) {
                indirect_object_index = phrase_end_index;
            }

            // RULE: Gather modifiers.
            // If any input remains, it is a modifier.
            if (indirect_object_article_index != -1) {
                phrase_bit = indirect_object_article_index + 1;
            } else {
                phrase_bit = phrase_start_index;
            }

            if (indirect_object_index - phrase_bit >= 1) {
                LOG.debug("Indirect Object Modifiers: ");
                for (phrase_index = phrase_bit; phrase_index < indirect_object_index; phrase_index++) {
                    LOG.debug(string_array[phrase_bit] + " ");
                    action.setIndirectObjectModifiers(string_array[phrase_bit]);
                }
                LOG.debug("");
                indirect_object_modifier_index = phrase_bit;
            }

            if (indirect_object_index != -1) {
                LOG.debug("Indirect Object: " + string_array[indirect_object_index]);
                action.setIndirectObject(string_array[indirect_object_index]);
            }
        }

        // By this point, all that can be done with parsing the action
        // has been done.

        action.setMessage("Success");
        return action;
    }
}
