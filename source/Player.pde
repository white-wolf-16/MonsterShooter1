class Player extends Character{
  
  float size, spd = 2, damp = 0.8;
  ArrayList<Projectile> ps=new ArrayList<Projectile>();
  boolean ded=false;
  PFont mono = loadFont("MaturaMTScriptCapitals-24.vlw");
  
  Player(float x, float y, float sx, float sy, float size){
   super(x,y,sx,sy); 
   this.size = size;
   oWidth=20*size;
   oHeight=50*size;
   hp=20;
  }
  
  void dead(){
    ded=true;
  }
  
  boolean hitProj (Projectile b){
    return pos.dist(b.pos)< oWidth; 
  }
  
  
  void decreaseHealth(){
    super.decreaseHealth(1); 
  }
  
  void moveCharacter(){
    super.moveCharacter();
    vel.mult(damp);
  }
  
  void update(){
    super.update();
    if(up)    accelerate(new PVector (0, -spd));
    if(down)  accelerate(new PVector (0, spd));
    if(left)  accelerate(new PVector (- spd,0));
    if(right) accelerate(new PVector (spd,0));
    checkProjectile();
  }
  
  void fireProjectile(){
    float angle = atan2(mouseY-pos.y, mouseX-pos.x);
    if (count <= 10){
      ps.add(new Projectile(pos.x,pos.y,cos(angle),sin(angle),#A22B4D));
      ++count;
    }
    //print(dist(pos.x,pos.y,mouseX,mouseY));
  }
 
  
  void checkProjectile(){
    if(count <= 10){
    for (int i = 0; i < ps.size(); i++){
       Projectile p = ps.get(i);
       p.update();
       if (p.hit()) ps.remove(p);
       for (int j = 0; j < enemies.size(); j++){
         //print(enemies.get(j).ded);
        //if (enemies.get(j).ded==false){
        if (enemies.get(j).hitProj(p)){
         enemies.get(j).decreaseHealth();
         ps.remove(p);
         if (enemies.get(j).hp<1){
           score.incScore(1);
           enemies.get(j).dead();
         }
        }
        if (enemies.get(j).hp<1 && enemies.get(j).timer<0) enemies.remove(j);
      }  
     }
    }
   //}
  }
  
  
  void drawReload(){
    fill (#FF5555,120);
    textFont(mono,32);
    textAlign(CENTER);
    text("'R' to Reload", width/2, height/2);
  }
  
  
  void drawChar(){ 
    pushMatrix();
    
    translate(pos.x,pos.y);
    
    float dist = dist(0, 0, (mouseX-pos.x), (mouseY-pos.y));
    if ((mouseY-pos.y) < 0) rotate((-acos((mouseX-pos.x)/dist)+PI/2));
    else rotate((acos((mouseX-pos.x)/dist)+PI/2));
    
    //fill(0);
    //rect(0,0,oWidth,oHeight);
    scale(size);
    
    //body
    noStroke();
    fill(#A22B4D);
    ellipse(0,5,20,40);
    rect(-10,5,20,45);
    triangle(10,35,10,50,22,60);
    triangle(-10,35,-10,50,-22,60);
    //stripes
    stroke(#FF004A);
    strokeWeight(2);
    line(-5.5,30,5.5,30);
    line(-5.5,35,5.5,35);
    line(-5.5,40,5.5,40);
    
    //fire
    noStroke();
    fill(#FF6F34);
    triangle(-3,50,-6,56,-1,53);
    triangle(3,50,6,56,1,53);
    fill(#FF3E34);
    triangle(-3,50,3,50,0,59);
    
    popMatrix();
  }
  
}
