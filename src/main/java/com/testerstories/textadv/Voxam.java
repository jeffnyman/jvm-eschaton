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
        System.out.println("Action Parts:");
        System.out.println("-------------------------");
        System.out.println("Target: " + action.getCommandTarget());
        System.out.println("Verb: " + action.getVerb());
        System.out.println("Direct Object (Article): " + action.getDirectObjectArticle());
        System.out.println("Direct Object (Modifiers): " + action.getDirectObjectModifiers());
        System.out.println("Direct Object: " + action.getDirectObject());
        System.out.println("Preposition: " + action.getPreposition());
        System.out.println("Indirect Object (Article): " + action.getIndirectObjectArticle());
        System.out.println("Indirect Object (Modifiers): " + action.getIndirectObjectModifiers());
        System.out.println("Indirect Object: " + action.getIndirectObject());
        System.out.println("-------------------------");
    }
}
