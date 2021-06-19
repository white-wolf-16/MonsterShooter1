class Projectile{
  PVector pos, vel;
  float projspd = 15;
  color c;
  
  Projectile(float x, float y, float sx, float sy, color c){
    this.pos = new PVector (x,y);
    this.vel = new PVector(sx,sy);
    vel.mult(projspd);
    this.c=c;
  }
  
  void update(){
   pos.add(vel);
   drawP();
   hit();
  }
  
  boolean hit(){
    return pos.y < 0 || pos.y > height || pos.x < 0 || pos.x > width;
  }
  
  void drawP(){
    
    pushMatrix(); 
    translate(pos.x,pos.y);
    fill(c);
    circle(0,0,10);
    popMatrix();
    
  }
  
}
