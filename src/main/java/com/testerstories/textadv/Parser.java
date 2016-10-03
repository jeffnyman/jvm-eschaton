package com.testerstories.textadv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.StringTokenizer;

/**
 * Interactive fiction parser.
 *
 * @author Jeff Nyman
 * @version 0.5
 */
class Parser {
    private static final Logger LOG = LoggerFactory.getLogger(Parser.class);
    private Action action;

    /**
     * Parse the player command, identifying its constituent parts.
     *
     * @param playerInput the player input
     * @return an <code>Action</code> object that holds the part of speech
     */
    Action parse(String playerInput) {
        action = new Action();

        String input = validateInput(playerInput);
        if (input.startsWith("FAILED:")) {
            action.setMessage(input);
            return action;
        }

        LOG.debug("Action: " + input);
        System.out.println("ACTION: " + input + "\n");

        // Must check for command targets before verbs.

        String commandTarget = parseCommandTarget(input);

        if (commandTarget != null) {
            input = stripFirstWord(input);
            LOG.debug("Command Target: " + commandTarget);
            System.out.println("COMMAND: " + commandTarget);
            action.setCommandTarget(commandTarget);
        }

        System.out.println("--- playerInput after CommandTarget: " + input);

        // AT THIS POINT: input contains the original action, minus the
        // command target if there was any.

        // NOTE: If a command like "floyd," is entered, it might be thought
        // that the verb has to be checked to see if it's null. But that's
        // not the case because all punctuation is stripped from the end of
        // of command. So the above situation would treat "floyd" as the
        // verb.
        // DESIGN: Does this mean the comma should be ruled out of the
        // remove trailing punctuation? If that was the case, the parseVerb
        // would need to check for an "input" that was empty or null.

        String verb = parseVerb(input);

        if (verb != null) {
            input = stripFirstWord(input);
            LOG.debug("Verb: " + verb);
            System.out.println("VERB: " + verb);
            action.setVerb(verb);
        }

        // AT THIS POINT: input contains the original action, minus the
        // verb.

        System.out.println("--- playerInput after Verb: " + input);

        // BUT: You can have intransitive verbs.
        // NOW: Assuming a valid sentence structure, the parser has to
        // account for direct and indirect objects. One of the first
        // things to do is check for a preposition. An indirect object,
        // if one is present, will occur in a prepositional phrase.

        determineObjectPhrase(input);

        // By this point, all that can be done with parsing the action
        // has been done.

        action.setMessage("Success");

        return action;
    }

    private void determineObjectPhrase(String input) {
        // Holds the original input initially but will be used to keep
        // track of the portion of the input remaining after any given
        // object phrase has been handled.
        String remaining = input;

        while (!remaining.equals("")) {
            // As long as there is some part of the original input, keep
            // processing.
            boolean stillParsing = true;

            // Holds the current object phrase being dealt with. This
            // only matters if there are multiple object phrases.
            String currentPhrase = "";

            StringTokenizer st = new StringTokenizer(remaining);
            // I'm immediately setting remaining to empty, which means the
            // outer loop will exit immediately. However, there is an inner
            // loop that will be processed entirely before that happens.
            remaining = "";

            while (st.hasMoreTokens()) {
                // The purpose of the inner loop is to split the input into
                // the current object phrase and save the rest as the
                // remaining portion.
                String word = st.nextToken();

                if (stillParsing && word.equals("and")) {
                    stillParsing = false;
                    continue;
                }

                if (stillParsing && word.endsWith(",")) {
                    word = word.substring(0, word.length() - 1);
                    currentPhrase += " " + word + " ";
                    stillParsing = false;
                    continue;
                }

                if (stillParsing) {
                    currentPhrase += " " + word + " ";
                } else {
                    remaining += " " + word + " ";
                }

                currentPhrase = currentPhrase.trim();
                remaining = remaining.trim();
            }

            System.out.println("*** input: " + input);
            System.out.println("*** remaining: " + remaining);
            System.out.println("*** current phrase: " + currentPhrase);

            String objectPhrase = parseObjectPhrase(currentPhrase);

            System.out.println("--- Object Phrase (remaining): " + objectPhrase);
        }
    }

    private String parseObjectPhrase(String input) {
        String originalInput = input;

        // The article, if present, will be the first word of the object
        // phrase. So the start of the object phrase is the last article
        // found.
        String article = parseArticle(input);

        if (article != null) {
            LOG.debug("ARTICLE: " + article);
            System.out.println("ARTICLE: " + article);
            action.setDirectObjectArticle(article);
            input = stringAfterWord(input, article);
        }

        System.out.println("--- playerInput after Article: " + input);

        // The object is the last word of the object phrase.
        String object = parseObject(input);

        if (object != null) {
            LOG.debug("OBJECT: " + object);
            System.out.println("OBJECT: " + object);
            action.setDirectObject(object);
            input = stringBeforeWord(input, object);
        }

        System.out.println("--- playerInput after Object: " + input);

        // If any phrase bits remain at this point, they must all
        // be modifiers for the object.
        StringTokenizer st = new StringTokenizer(input);
        String modifier = null;

        while (st.hasMoreTokens()) {
            modifier = st.nextToken();
            LOG.debug("MODIFIER: " + modifier);
            System.out.println("MODIFIER: " + modifier);
            action.setDirectObjectModifier(modifier);
        }

        // At this point it is necessary to return the unparsed portion of
        // the phrase. Anything that was unparsed means it was part of the
        // overall input that was not yet handled.

        if (article != null) {
            System.out.println("--- using first return condition");
            return stringBeforeWord(originalInput, article);
        }

        if (modifier != null) {
        System.out.println("--- using second return condition");
            return stringBeforeWord(originalInput, modifier);
        }

        if (object != null) {
            System.out.println("--- using third return condition");
            return stringBeforeWord(originalInput, object);
        }

        // TODO: DESIGN CONSIDERATION
        // This could return the original 'input' variable which should be
        // empty by the time this point is reached. A null, however, seems
        // equally viable.

        return null;
    }

    /**
     * Return the object in a given object phrase. The last word in
     * the phrase will be the object.
     *
     * @param input the phrase to parse
     * @return the object portion of the phrase
     */
    private String parseObject(String input) {
        StringTokenizer st = new StringTokenizer(input);
        String object = null;

        while (st.hasMoreTokens()) {
            object = st.nextToken();
        }

        return object;
    }

    /**
     * Return the command target of an action. Currently this must be
     * a single word, followed by a comma.
     *
     * @param input the input string to parse
     * @return the command target of the action if present, null otherwise
     */
    private String parseCommandTarget(String input) {
        StringTokenizer st = new StringTokenizer(input);
        String commandTarget = st.nextToken();

        // If there is no comma, there is no command target.
        if (commandTarget.charAt(commandTarget.length() - 1) != ',')
            return null;

        // Strip the ending comma.
        commandTarget = commandTarget.substring(0, commandTarget.length() - 1);

        return commandTarget;
    }

    /**
     * Return the verb portion of a given action. This must be the first word
     * word in the action, unless there is a command target. In that case,
     * the verb must be the word that immediately follows the command target.
     *
     * @param input the input string to parse
     * @return the verb of the action if present, null otherwise
     */
    private String parseVerb(String input) {
        StringTokenizer st = new StringTokenizer(input);
        return st.nextToken();
    }

    /**
     * Returns the article in a string.
     *
     * @param input the command string to parse
     * @return the article portion of an object phrase
     */
    private String parseArticle(String input) {
        StringTokenizer st = new StringTokenizer(input);
        String article = null;
        String articles = ":a:an:the:";

        while (st.hasMoreTokens()) {
            String word = st.nextToken();
            String articleWord = ":" + word + ":";

            if (articles.contains(articleWord)) {
                article = word;
            }
        }

        return article;
    }

    private String validateInput(String input) {
        // Stop parsing if null input.
        if (input == null) {
            return "FAILED: No action was found.";
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
            return "FAILED: The action was empty.";
        }

        return input;
    }

    /**
     * Strip the first word from a string and return the remainder of that
     * string.
     *
     * @param input the input string to parse
     * @return the input string with the first word removed
     */
    private String stripFirstWord(String input) {
        StringTokenizer st = new StringTokenizer(input);
        String firstWord = st.nextToken();
        int index = input.indexOf(firstWord) + firstWord.length();

        return input.substring(index).trim();
    }

    private String stringBeforeWord(String input, String word) {
        StringTokenizer st = new StringTokenizer(input);
        boolean pastWord = false;
        String firstPart = "";

        while (st.hasMoreTokens()) {
            String currentWord = st.nextToken();

            if (word.equals(currentWord))
                pastWord = true;

            if (!pastWord)
                firstPart += currentWord + " ";
        }

        return firstPart.trim();
    }

    private String stringAfterWord(String input, String word) {
        StringTokenizer st = new StringTokenizer(input);
        boolean pastWord = false;
        String lastPart = "";

        while (st.hasMoreTokens()) {
            String currentWord = st.nextToken();

            if (pastWord)
                lastPart += currentWord + " ";

            if (word.equals(currentWord))
                pastWord = true;
        }

        return lastPart.trim();
    }
}