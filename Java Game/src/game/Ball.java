package game;

import java.awt.Graphics2D;
import java.awt.Color;


public class Ball
{
  float x, y; // Ball's center x and y (package access)
  float speedX, speedY; // Ball's speed per step in x and y (package access)
  float radius;         // Ball's radius (package access)

  private Color color;
  private static final Color DEFAULT_COLOR = Color.BLUE;
  private int collBall;
  boolean alive = true;
  public Ball(float x, float y, float radius, float speed, float angleInDegree, Color color)
  {
    this.x = x;
    this.y = y;
    this.speedX = ((float)(speed * Math.cos(Math.toRadians(angleInDegree))));
    this.speedY = (-speed * (float)Math.sin(Math.toRadians(angleInDegree)));  
    this.color = color;
    this.radius = radius;

  }
  
  public Ball(float x, float y, float radius, float speed, float angleInDegree, Color color, int collBall)
  {
    this(x, y, radius, speed, angleInDegree, color);
    this.collBall = collBall;
  }
  
  public Ball(float x, float y, float radius, float speed, float angleInDegree)
  {
    this(x, y, radius, speed, angleInDegree, DEFAULT_COLOR);
  }
  
  public void draw(Graphics2D g)
  {
    g.setColor(this.color);
    g.fillOval((int)(this.x - this.radius), (int)(this.y - this.radius), (int)(2.0F * this.radius), (int)(2.0F * this.radius));
  }
  
  public void shrink(final Graphics2D g)
  {
    g.setColor(this.color);
    Thread thread = new Thread()
    {
      public void run()
      {
        while (Ball.this.radius != 0.0F)
        {
          Ball testBall = Ball.this;
          testBall.radius = ((float)(testBall.radius - 0.5D));
          
          try
          {
            Thread.sleep(100L);
          }
          catch (InterruptedException localInterruptedException) {}
          g.setColor(Ball.this.color);
          g.fillOval((int)(Ball.this.x - Ball.this.radius), (int)(Ball.this.y - Ball.this.radius), (int)(2.0F * Ball.this.radius), (int)(2.0F * Ball.this.radius));
        }
        Ball.this.alive = false;
      }
    };
    thread.start();
  }
  
  public void expand(final Graphics2D g)
  {
    g.setColor(this.color);
    final float oldRadius = this.radius;
    this.radius = 0.0F;
    Thread thread = new Thread()
    {
      public void run()
      {
        while (Ball.this.radius != oldRadius)
        {
          Ball testBall = Ball.this;testBall.radius = ((float)(testBall.radius + 0.5D));
          try
          {
            Thread.sleep(10L);
          }
          catch (InterruptedException localInterruptedException) {}
          g.setColor(Ball.this.color);
          g.fillOval((int)(Ball.this.x - Ball.this.radius), (int)(Ball.this.y - Ball.this.radius), (int)(2.0F * Ball.this.radius), (int)(2.0F * Ball.this.radius));
        }
      }
    };
    thread.start();
  }
  
  public void bounceBall(ContainerBox box)
  {
    float ballMinX = box.minX + this.radius;
    float ballMinY = box.minY + this.radius;
    float ballMaxX = box.maxX - this.radius;
    float ballMaxY = box.maxY - this.radius;
    
    this.x += this.speedX;
    this.y += this.speedY;
    if (this.x < ballMinX)
    {
      this.speedX = (-this.speedX);
      this.x = ballMinX;
    }
    else if (this.x > ballMaxX)
    {
      this.speedX = (-this.speedX);
      this.x = ballMaxX;
    }
    if (this.y < ballMinY)
    {
      this.speedY = (-this.speedY);
      this.y = ballMinY;
    }
    else if (this.y > ballMaxY)
    {
      this.speedY = (-this.speedY);
      this.y = ballMaxY;
    }
  }
  
  public float getSpeed()
  {
    return (float)Math.sqrt(this.speedX * this.speedX + this.speedY * this.speedY);
  }
  
  public float getMoveAngle()
  {
    return (float)Math.toDegrees(Math.atan2(-this.speedY, this.speedX));
  }
  
  public float getX()
  {
    return this.x;
  }
  
  public void setRadius(float radius)
  {
    this.radius = radius;
  }
  
  public float getY()
  {
    return this.y;
  }
  
  public void setX(float x)
  {
    this.x = x;
  }
  
  public void setY(float y)
  {
    this.y = y;
  }
  
  public float getRadius()
  {
    return this.radius;
  }
  
  public int getCollBall()
  {
    return this.collBall;
  }
  
  public Color getColor()
  {
    return this.color;
  }
  
  public boolean isAlive()
  {
    return this.alive;
  }
  
  public void setColor(Color color)
  {
    this.color = color;
  }
  

}
