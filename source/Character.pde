class Character{
  
  PVector pos, vel;
  int hp, owidth, oheight;
  float oWidth, oHeight;
  int fix;

  Character(float x, float y, float sx, float sy){
    this.pos = new PVector(x,y);
    this.vel = new PVector(sx,sy);
  }
  
  void decreaseHealth(int dp){
    if (fix == 0) hp=hp-dp; //HitPoints-DamagePoints
  }
  
  boolean hitChar(Character plya) {
    if (dist(pos.x, pos.y, plya.pos.x, plya.pos.y) < oHeight/2 + plya.oHeight/2) {              
      return true;
    }
    return false;
  }
  
  void resolveHit(Character plya) {
      float angle = atan2(pos.y - plya.pos.y, pos.x - plya.pos.x);
      float avgSpeed = (vel.mag() + plya.vel.mag())/2;
      vel.x = avgSpeed * cos(angle);
      vel.y = avgSpeed * sin(angle);
      plya.vel.x = avgSpeed * cos(angle - PI);
      plya.vel.y = avgSpeed * sin(angle - PI);
  }
  
  void update(){
    moveCharacter();
    checkWalls();
  }
  
  void moveCharacter(){
    pos.add(vel);
  }
  
  void accelerate(PVector acc){
    vel.add(acc); 
  }
  
  void checkWalls(){
    if (pos.y > height + oHeight){
      pos.y= -1 * oHeight;
    }
    else if (pos.y < -1 * oHeight){
      pos.y= height + oHeight;
    }
    if (pos.x > width +oWidth){
      pos.x= -1 * oWidth;
    }
    else if (pos.x < -1 * oWidth){
      pos.x= width + oWidth;     
    }  
  }
    
  void drawChar(){
   //dummy 
  }
  
}
