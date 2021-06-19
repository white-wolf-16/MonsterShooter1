class BasicEnemy extends Character{
  
  float my, myy, size, legSpeed = 2, timer, spdfix;
  boolean legs = true, ded=false;
  BasicEnemy(float x, float y, float sx, float sy, float size){
    super(x,y,sx,sy);
    this.size = size;
    oWidth=40*size;
    oHeight=55*size;
    hp=2;
  }
  
  boolean hitProj (Projectile b){
    return pos.dist(b.pos)< oWidth; 
  }
  
  void Collided(){
       p1.fix=1;
       for (int j = 0; j < enemies.size(); j++){
        if(ded==false){
          if (hitChar(p1)){
            p1.fix--;
            resolveHit(p1);
            if (p1.fix==0) p1.decreaseHealth();
            if (p1.hp<1){
            start=false; play=false; end=true;
          }
         }
        }
       }
  }
  
  
  void update(){
   drawChar(); 
   moveCharacter();
   checkWalls();
   timer--;
   Collided();
  }
  
  void decreaseHealth(){
    super.decreaseHealth(1); 
  }
  
  void dead(){
   vel.x=vel.y=0;
   timer=60;
   oWidth=oHeight=0;
   ded=true;
  }
  
  void movelegs(){
   if (legs){
      my+= legSpeed; myy-= legSpeed;
    }
    else{
      my-= legSpeed; myy+= legSpeed;
      }
    if(my==(10+legSpeed) || myy==(10+legSpeed))
      legs=false;
    else if (my==(0-legSpeed) || myy==(0-legSpeed))
      legs=true;
    
    my = constrain(my,0,10);
    myy = constrain(myy,-10,0); 
  }
  
  void drawChar(){
    if (!ded){
    pushMatrix();
    
    translate(pos.x,pos.y);
    
    movelegs();
    
    scale(size);
    stroke(#493E5C);
    fill(#B59EDF);
    
    //left legs
    rotate(PI);
    line(0,0,30,my-40);
    line(30,my-40,30+2,my-40+3);
    line(0,0,35,my-0);
    line(35,my-2,35,my+0+3);
    line(0,0,30,my+40);
    line(30,my+40,30+2,my+40-3);
    rotate(PI);
    
    //right legs
    line(0,0,30,myy-40);
    line(30,myy-40,30+2,myy-40+3);
    line(0,0,35,myy-0);
    line(35,myy-2,35,myy+0+3);
    line(0,0,30,myy+40);
    line(30,myy+40,30+2,myy+40-3);
    
    //body
    ellipse(0,0,40,55);
    
    
    
    popMatrix();
    }
    
    if(ded){
    pushMatrix();
    translate(pos.x,pos.y);
    
    movelegs();
    
    scale(size);
    stroke(#493E5C);
    fill(#FF79A0);
    
    //left legs
    rotate(PI);
    //line(0,0,30,my-40);
    //line(30,my-40,30+2,my-40+3);
    line(0,0,35,my-0);
    line(35,my-2,35,my+0+3);
    line(0,0,30,my+40);
    line(30,my+40,30+2,my+40-3);
    rotate(PI);
    
    //right legs
    //line(0,0,30,myy-40);
    //line(30,myy-40,30+2,myy-40+3);
    //line(0,0,35,myy-0);
    //line(35,myy-2,35,myy+0+3);
    line(0,0,30,myy+40);
    line(30,myy+40,30+2,myy+40-3);
    
    //body
    ellipse(0,0,40,55);
    popMatrix();
    }
   }
}
