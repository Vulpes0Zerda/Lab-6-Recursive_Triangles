package triangle;

import resizable.ResizableFrame;

/**
 * Main Class for the Sierpinski Triangle.
 * Implement your recursive Triangle in Triangle.
 */
public class Main {
    public static void main(String[] args) {
        new ResizableFrame(new DrawSierpinskiTriangle());
    }
}
