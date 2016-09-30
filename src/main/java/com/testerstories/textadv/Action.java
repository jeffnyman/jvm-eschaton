package com.testerstories.textadv;

import java.util.ArrayList;

/**
 * This class holds information about an action that was typed in by the
 * player. The goal of this class is to allow an action to be broken into
 * parts of speech and referenced separately.
 *
 * @author Jeff Nyman
 */
class Action {
    private String commandTarget;
    private String verb;
    private String message;
    private String directObjectArticle;
    private ArrayList<String> directObjectModifiersList = new ArrayList<>();
    private ArrayList<String> indirectObjectModifiersList = new ArrayList<>();

    private ArrayList<String> directObjects = new ArrayList<>();

    private String indirectObject;
    private String preposition;
    private String indirectObjectArticle;

    String getCommandTarget() {
        return commandTarget;
    }

    void setCommandTarget(String commandTarget) {
        this.commandTarget = commandTarget;
    }

    String getVerb() {
        return verb;
    }

    void setVerb(String verb) {
        this.verb = verb;
    }

    String getDirectObject() {
        return String.join(" ", directObjects);
    }

    void setDirectObject(String directObject) {
        directObjects.add(directObject);
    }

    void setMessage(String message) {
        this.message = message;
    }

    String getMessage() {
        return message;
    }

    void setDirectObjectArticle(String directObjectArticle) {
        this.directObjectArticle = directObjectArticle;
    }

    String getDirectObjectArticle() {
        return directObjectArticle;
    }

    void setDirectObjectModifiers(String directObjectModifiers) {
        directObjectModifiersList.add(directObjectModifiers);
    }

    String getDirectObjectModifiers() {
        return String.join(" ", directObjectModifiersList);
    }

    void setIndirectObject(String indirectObject) {
        this.indirectObject = indirectObject;
    }

    void setPreposition(String preposition) {
        this.preposition = preposition;
    }

    String getPreposition() {
        return preposition;
    }

    String getIndirectObject() {
        return indirectObject;
    }

    void setIndirectObjectArticle(String indirectObjectArticle) {
        this.indirectObjectArticle = indirectObjectArticle;
    }

    String getIndirectObjectArticle() {
        return indirectObjectArticle;
    }

    void setIndirectObjectModifiers(String indirectObjectModifiers) {
        indirectObjectModifiersList.add(indirectObjectModifiers);
    }

    String getIndirectObjectModifiers() {
        return String.join(" ", indirectObjectModifiersList);
    }
}
