package triangle;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;

public class Triangle{
  private Vector2D vectors[] = new Vector2D[3];
  private Color strokeColor;
  private Color fillColor;
  private Stroke stroke;
  private static final int EDGES = 3;
  private static final int CORNERS = 3;

  public Triangle (Vector2D vectors[], Color fillColor, Color strokeColor, Stroke stroke) throws IllegalArgumentException{
    if(vectors.length<Triangle.CORNERS){
      throw new IllegalArgumentException(vectors.getClass().getSimpleName()+" array size underflows the required size of "+ Triangle.CORNERS);
    }else if (vectors.length>Triangle.CORNERS) {
      throw new IllegalArgumentException(vectors.getClass().getSimpleName()+" array size overflows the required size of "+ Triangle.CORNERS);
    }
    this.vectors = vectors;
    this.fillColor = fillColor;
    this.strokeColor = strokeColor;
    this.stroke = stroke;
  }

  public Triangle (Dimension dimension, Color fillColor, Color strokeColor, Stroke stroke) throws IllegalArgumentException{
    Dimension size = new Dimension(dimension);

    //checks which dimension is smaller, relative to the width and height of equilateral triangle
    double sqrt3Over2 = Math.sqrt(3) / 2;

    if (dimension.width > dimension.height) {
      size.height = (int) (sqrt3Over2 * size.width);
    } else {
      size.width = (int) (dimension.height/sqrt3Over2 );
    }
    vectors[0] = new Vector2D(0, size.height);
    vectors[1] = new Vector2D(size.width/2.0,0);
    vectors[2] = new Vector2D(size.width, size.height);

    this.fillColor = fillColor;
    this.strokeColor = strokeColor;
    this.stroke = stroke;
  }

  public void concatGraphics2D(Graphics2D graphics2DBuffer){
    Polygon poly = new Polygon();
    for (Vector2D p : vectors) {
      poly.addPoint((int) p.getX(), (int) p.getY());
    }
    graphics2DBuffer.setStroke(stroke);
    graphics2DBuffer.fill(poly);
    graphics2DBuffer.setColor(fillColor);
    graphics2DBuffer.draw(poly);
  }

  public double getArea(){
    Vector2D ab = vectors[1].subtract(vectors[0]);
    Vector2D ac = vectors[2].subtract(vectors[0]);

    return 0.5 * Math.abs(ab.getX() * ac.getY() - ab.getY() * ac.getX());
  }

  public Vector2D[] getVectors(){
    return vectors;
  }
}
