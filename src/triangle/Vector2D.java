package triangle;

public class Vector2D {
  private double x;
  private double y;

  public Vector2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Vector2D(int x, int y) {
    this.x = (double) x;
    this.y = (double) y;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public Vector2D add(Vector2D toAdd) {
    return new Vector2D(this.x + toAdd.x, this.y + toAdd.y);
  }

  public Vector2D subtract(Vector2D toSubtract){
    return new Vector2D(this.x - toSubtract.x, this.y - toSubtract.y);
  }

  public Vector2D scale(double scalar) {
    return new Vector2D(this.x * scalar, this.y * scalar);
  }

  
}
