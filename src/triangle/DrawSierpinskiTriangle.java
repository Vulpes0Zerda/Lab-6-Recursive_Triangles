package triangle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import resizable.ResizableImage;

/**
 * Implement your Sierpinski Triangle here.
 *
 *
 * You only need to change the drawTriangle
 * method!
 *
 *
 * If you want to, you can also adapt the
 * getResizeImage() method to draw a fast
 * preview.
 *
 */
public class DrawSierpinskiTriangle implements ResizableImage {
    
    int drawDepth = 1;
    int strokeWidth = 1;
    BasicStroke stroke = new BasicStroke(strokeWidth);
    Graphics2D gBuffer;
    ArrayList<Triangle> triangles;

    /**
     * change this method to implement the triangle!
     * @param size the outer bounds of the triangle
     * @return an Image containing the Triangle
     */
    private BufferedImage drawTriangle(Dimension size) {

        BufferedImage bufferedImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        this.gBuffer = (Graphics2D) bufferedImage.getGraphics();

        Triangle rootTriangle = new Triangle(size, getColorBySeed(drawDepth), getColorBySeed(drawDepth), stroke);
        System.out.println(rootTriangle);

        // setting rendering hints for all Triangles
        gBuffer.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        gBuffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gBuffer.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        gBuffer.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        gBuffer.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        gBuffer.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        gBuffer.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        gBuffer.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        triangles = new ArrayList<>();
        triangles.add(rootTriangle);
        drawDepth++;
        Vector2D rootVectors[] = rootTriangle.getVectors();
        recursiveTriangle(rootVectors[0],rootVectors[1],rootVectors[2]);
        drawDepth--;


        for (Triangle triangle : triangles) {
            triangle.concatGraphics2D(gBuffer);
        }


        return bufferedImage;
    }

    private void recursiveTriangle(Vector2D a, Vector2D b, Vector2D c){
        Vector2D abMid = a.add(b).scale(0.5);
        Vector2D bcMid = b.add(c).scale(0.5);
        Vector2D caMid = c.add(a).scale(0.5);

        Vector2D sizingTriangle[] = {abMid, bcMid, caMid};

        if (new Triangle(sizingTriangle, null, null, null).getArea()>2){

            // first block to generate and draw the lower left triangle
            Vector2D[] firstVectors = {a, abMid, caMid};
            Triangle leftTriangle = new Triangle(firstVectors, getColorBySeed(drawDepth), getColorBySeed(drawDepth), stroke);
            triangles.add(leftTriangle);

            // second block to generate and draw the upper middle triangle
            Vector2D[] secondVectors = {abMid, b, bcMid};
            Triangle upperTriangle = new Triangle(secondVectors, getColorBySeed(drawDepth), getColorBySeed(drawDepth), stroke);
            triangles.add(upperTriangle);

            // third block to generate and draw the lower right triangle
            Vector2D[] thirdVectors = {caMid, bcMid, c};
            Triangle rightTriangle = new Triangle(thirdVectors, getColorBySeed(drawDepth), getColorBySeed(drawDepth), stroke);
            triangles.add(rightTriangle);

            // third block to generate and draw the lower right triangle
            Vector2D[] forthVectors = {abMid, bcMid, caMid};
            Triangle flippedTriangle = new Triangle(forthVectors, getColorBySeed(drawDepth), getColorBySeed(drawDepth), stroke);
            triangles.add(flippedTriangle);


            drawDepth++;
            recursiveTriangle(a, abMid, caMid);
            recursiveTriangle(abMid, b, bcMid);
            recursiveTriangle(caMid, bcMid, c);
            drawDepth--;
        }
    }



    private Color getColorBySeed(int seed){
        return new Color((seed*20 + 170)% 256 , (seed*20+ 0)% 256 , (seed*20+ 85)% 256);
    }

    BufferedImage bufferedImage;
    Dimension bufferedImageSize;

    @Override
    public Image getImage(Dimension triangleSize) {
        if (triangleSize.equals(bufferedImageSize))
            return bufferedImage;
        bufferedImage = drawTriangle(triangleSize);
        bufferedImageSize = triangleSize;
        return bufferedImage;
    }

    @Override
    public Image getResizeImage(Dimension size) {
        BufferedImage bufferedImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gBuffer = (Graphics2D) bufferedImage.getGraphics();
        gBuffer.setColor(Color.pink);
        int border = 2;
        gBuffer.drawRect(border, border, size.width - 2 * border, size.height - 2 * border);
        return bufferedImage;
    }
}
