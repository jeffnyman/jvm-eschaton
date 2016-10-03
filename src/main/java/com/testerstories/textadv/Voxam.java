package com.testerstories.textadv;

import java.util.Scanner;

public class Voxam {
    private static Parser parser = new Parser();

    public static void main(String[] args) {
        if (args.length != 0) {
            parseAction(args[0]);
        } else {
            boolean done = false;
            while (!done) {
                System.out.print("> ");
                String input = new Scanner(System.in).useDelimiter(System.getProperty("line.separator")).next();

                if (input.equals("quit")) {
                    done = true;
                } else {
                    parseAction(input);
                }
            }
        }
    }

    private static void parseAction(String input) {
        Action action = parser.parse(input);
        displayAction(action);
    }

    private static void displayAction(Action action) {
        System.out.println("-------------------------");
        System.out.println("Status: " + action.getMessage());
        System.out.println("Command: " + action.getCommandTarget());
        System.out.println("Verb: " + action.getVerb());
        System.out.println("DO Article: " + action.getDirectObjectArticles());
        System.out.println("DO Modifier: " + action.getDirectObjectModifiers());
        System.out.println("Direct Objects: " + action.getDirectObjects());

        /*
        System.out.println("Preposition: " + action.getPreposition());
        System.out.println("Indirect Object (Article): " + action.getIndirectObjectArticle());
        System.out.println("Indirect Object (Modifiers): " + action.getIndirectObjectModifiers());
        System.out.println("Indirect Object: " + action.getIndirectObject());
        */
    }
}
