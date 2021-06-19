import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class project_3 extends PApplet {

Player p1;
Stats score;
boolean up, down, left, right, start = true, play = false, end = false;
ArrayList<BasicEnemy> enemies=new ArrayList<BasicEnemy>();
int count, timer=180;


public void setup(){
  
  frameRate(60);
  cursor(CROSS);
  score = new Stats();
  p1 = new Player(width/2,height-100,0,0,1.5f);
  for (int i=0; i<5; i++) 
    enemies.add(new BasicEnemy(random(1, width), random(1, height/2), random(-3, 3), random(-3, 3), 0.9f));
  enemies.add(new BossEnemy(width/2,1,1,1,1.5f));
}


public void draw(){
  if (start){
    fill(0xffD8D8D8,120);
    rect(0,0,width,height);
    fill(0xff4393A0);
    textSize(26);
    textAlign(CENTER);
    text("Survive and destroy the space monsters. Beware of thier Boss.\n Tip: Aim at their body, their legs have mutated to be damage resistant.\n", width/2, height/2-100);
    //textAlign(LEFT);
    text("≡ How To Play \n\n•  WASD for direction\n•  Mouse to aim and space to shoot\n•  Press mouse key to start", width/2, height-200);
    //if(keyPressed && key==' '){
    //  start= false; play=true; end=false;
    //}  
  }
  if (end){
    fill(0xffD8D8D8,120);
    rect(0,0,width,height);
    fill(0xff4393A0);
    textSize(36);
    text("GAME   OVER", width/2,height/2);
  }
  if(play){
    fill(220,220,220);
    rect(0,0,width,height);
    p1.drawChar();
    p1.update();
    for (int i=0; i<enemies.size(); i++) {
      BasicEnemy b1=enemies.get(i);
      b1.update();
    }
    score.drawS();
    if (count >= 10) p1.drawReload();
    if (score.score==6) timer--;
    if (p1.ded==true || (score.score==6 && timer==0)){
      start=false; play=false; end=true;
    }
  }  
}

public void keyPressed() {
  if (key == 'w') up = true;
  if (key == 's') down = true;
  if (key == 'a') left = true;
  if (key == 'd') right = true;
  if (key == 'r') count = 0;
 // if (key == ' ') p1.fireProjectile();
}

public void keyReleased() {
  if (key == 'w') up = false;
  if (key == 's') down = false;
  if (key == 'a') left = false;
  if (key == 'd') right = false;
  if (key == ' ') p1.fireProjectile();
}

public void mousePressed() {
  start= false; play=true; end=false;
}
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
  
  public boolean hitProj (Projectile b){
    return pos.dist(b.pos)< oWidth; 
  }
  
  public void Collided(){
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
  
  
  public void update(){
   drawChar(); 
   moveCharacter();
   checkWalls();
   timer--;
   Collided();
  }
  
  public void decreaseHealth(){
    super.decreaseHealth(1); 
  }
  
  public void dead(){
   vel.x=vel.y=0;
   timer=60;
   oWidth=oHeight=0;
   ded=true;
  }
  
  public void movelegs(){
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
  
  public void drawChar(){
    if (!ded){
    pushMatrix();
    
    translate(pos.x,pos.y);
    
    movelegs();
    
    scale(size);
    stroke(0xff493E5C);
    fill(0xffB59EDF);
    
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
    stroke(0xff493E5C);
    fill(0xffFF79A0);
    
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
class BossEnemy extends BasicEnemy{
  
  ArrayList<Projectile> ps=new ArrayList<Projectile>();
  float firetime;
  
  BossEnemy(float x, float y, float sx, float sy, float size){
    super(x,y,sx,sy,size);
    this.size = size;
    oWidth = 40*size;
    oHeight = 55*size;
    legSpeed = 1;
    hp = 6;
    firetime = 60; 
  }
  
  public void fireProjectile(){
    float angle = atan2(p1.pos.y-pos.y, p1.pos.x-pos.x);
    ps.add(new Projectile(pos.x,pos.y,cos(angle),sin(angle),0xffB600FF));
    firetime=60; 
  }
 
  public void checkProjectile(){
    for (int i = 0; i < ps.size(); i++){
       Projectile p = ps.get(i);
       p.update();
       if (p.hit()) ps.remove(p);
        if (p1.hitProj(p)){
         p1.fix=0;
         p1.decreaseHealth();
         ps.remove(p);
         if (p1.hp<1){
           p1.dead();
           p1.ded=true;
         }
      }  
     }
  }
  
  public void update(){
   super.update();
   if (firetime==0 && p1.ded==false && ded==false) fireProjectile();
   checkProjectile();
   firetime--;
  }
  
  public void dead(){
   super.dead();
   ded=true;
   timer = 180;
  }
  
  public void drawChar(){
    if(!ded){
      pushMatrix();
      
      translate(pos.x,pos.y);
      
      movelegs();
      
      scale(size);
      stroke(0xff242234);
      strokeWeight(2);
      fill(0xff3B3658);
      
      //left legs
      rotate(PI);
      line(0,0,30,my-40);
      line(30,my-40,30+2,my-40+3);
      line(0,0,35,my-15);
      line(35,my-15,35,my-15+3);
      line(0,0,35,my+15);
      line(35,my+15,35,my+15-3);
      line(0,0,30,my+40);
      line(30,my+40,30+2,my+40-3);
      rotate(PI);
      
      //right legs
      line(0,0,30,myy-40);
      line(30,myy-40,30+2,myy-40+3);
      line(0,0,35,myy-15);
      line(35,myy-15,35,myy-15+3);
      line(0,0,35,myy+15);
      line(35,myy+15,35,myy+15-3);
      line(0,0,30,myy+40);
      line(30,myy+40,30+2,myy+40-3);
      
      //body
      ellipse(0,0,40,55);
      fill(0xff242234);
      rect(-11.01f,7,23,8,4);
      
      
      popMatrix();
    }
   if(ded){
    pushMatrix();
    
    translate(pos.x,pos.y);
    scale(size);
    //stroke(#242234);
    //strokeWeight(2);
    fill(0xffFF79A0);
    //left legs
    rotate(PI);
    line(0,0,30,my-40);
    line(30,my-40,30+2,my-40+3);
    //line(0,0,35,my-15);
    //line(35,my-15,35,my-15+3);
    //line(0,0,35,my+15);
    //line(35,my+15,35,my+15-3);
    line(0,0,30,my+40);
    line(30,my+40,30+2,my+40-3);
    rotate(PI);
    
    //right legs
    //line(0,0,30,myy-40);
    //line(30,myy-40,30+2,myy-40+3);
    line(0,0,35,myy-15);
    line(35,myy-15,35,myy-15+3);
    //line(0,0,35,myy+15);
    //line(35,myy+15,35,myy+15-3);
    line(0,0,30,myy+40);
    line(30,myy+40,30+2,myy+40-3);
    
    //body
    ellipse(0,0,40,55);
    //fill(#242234);
    //rect(-11.01,7,23,8,4);
    
    
    popMatrix();
    } 
   
  }
}
class Character{
  
  PVector pos, vel;
  int hp, owidth, oheight;
  float oWidth, oHeight;
  int fix;

  Character(float x, float y, float sx, float sy){
    this.pos = new PVector(x,y);
    this.vel = new PVector(sx,sy);
  }
  
  public void decreaseHealth(int dp){
    if (fix == 0) hp=hp-dp; //HitPoints-DamagePoints
  }
  
  public boolean hitChar(Character plya) {
    if (dist(pos.x, pos.y, plya.pos.x, plya.pos.y) < oHeight/2 + plya.oHeight/2) {              
      return true;
    }
    return false;
  }
  
  public void resolveHit(Character plya) {
      float angle = atan2(pos.y - plya.pos.y, pos.x - plya.pos.x);
      float avgSpeed = (vel.mag() + plya.vel.mag())/2;
      vel.x = avgSpeed * cos(angle);
      vel.y = avgSpeed * sin(angle);
      plya.vel.x = avgSpeed * cos(angle - PI);
      plya.vel.y = avgSpeed * sin(angle - PI);
  }
  
  public void update(){
    moveCharacter();
    checkWalls();
  }
  
  public void moveCharacter(){
    pos.add(vel);
  }
  
  public void accelerate(PVector acc){
    vel.add(acc); 
  }
  
  public void checkWalls(){
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
    
  public void drawChar(){
   //dummy 
  }
  
}
class Player extends Character{
  
  float size, spd = 2, damp = 0.8f;
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
  
  public void dead(){
    ded=true;
  }
  
  public boolean hitProj (Projectile b){
    return pos.dist(b.pos)< oWidth; 
  }
  
  
  public void decreaseHealth(){
    super.decreaseHealth(1); 
  }
  
  public void moveCharacter(){
    super.moveCharacter();
    vel.mult(damp);
  }
  
  public void update(){
    super.update();
    if(up)    accelerate(new PVector (0, -spd));
    if(down)  accelerate(new PVector (0, spd));
    if(left)  accelerate(new PVector (- spd,0));
    if(right) accelerate(new PVector (spd,0));
    checkProjectile();
  }
  
  public void fireProjectile(){
    float angle = atan2(mouseY-pos.y, mouseX-pos.x);
    if (count <= 10){
      ps.add(new Projectile(pos.x,pos.y,cos(angle),sin(angle),0xffA22B4D));
      ++count;
    }
    //print(dist(pos.x,pos.y,mouseX,mouseY));
  }
 
  
  public void checkProjectile(){
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
  
  
  public void drawReload(){
    fill (0xffFF5555,120);
    textFont(mono,32);
    textAlign(CENTER);
    text("'R' to Reload", width/2, height/2);
  }
  
  
  public void drawChar(){ 
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
    fill(0xffA22B4D);
    ellipse(0,5,20,40);
    rect(-10,5,20,45);
    triangle(10,35,10,50,22,60);
    triangle(-10,35,-10,50,-22,60);
    //stripes
    stroke(0xffFF004A);
    strokeWeight(2);
    line(-5.5f,30,5.5f,30);
    line(-5.5f,35,5.5f,35);
    line(-5.5f,40,5.5f,40);
    
    //fire
    noStroke();
    fill(0xffFF6F34);
    triangle(-3,50,-6,56,-1,53);
    triangle(3,50,6,56,1,53);
    fill(0xffFF3E34);
    triangle(-3,50,3,50,0,59);
    
    popMatrix();
  }
  
}
class Projectile{
  PVector pos, vel;
  float projspd = 15;
  int c;
  
  Projectile(float x, float y, float sx, float sy, int c){
    this.pos = new PVector (x,y);
    this.vel = new PVector(sx,sy);
    vel.mult(projspd);
    this.c=c;
  }
  
  public void update(){
   pos.add(vel);
   drawP();
   hit();
  }
  
  public boolean hit(){
    return pos.y < 0 || pos.y > height || pos.x < 0 || pos.x > width;
  }
  
  public void drawP(){
    
    pushMatrix(); 
    translate(pos.x,pos.y);
    fill(c);
    circle(0,0,10);
    popMatrix();
    
  }
  
}
class Stats {
  int score;
  PFont mono = loadFont("MaturaMTScriptCapitals-24.vlw");
  Stats() {
    score = 0;
  }
  
  public void incScore(int i){
      score+=i;
  }
  
  public void drawS() {
    pushStyle();
    noStroke();
    fill(128, 67, 160, 120);
    quad(0,0,250,0,230,30,0,30);
    quad(width,0,width-280,0,width-240,30,width,30);
    quad(width/2-65,0,width/2+65,0,width/2+45,30,width/2-45,30);
    
    fill(0,0,0,170);
    textFont(mono,24);
    text("HP: " + p1.hp, width/2, 20);
    textAlign(LEFT);
    text("Enemies Downed: " + score, 5, 20);
    if (count < 11)  text(count + " ProjectilesFired", width-220, 20);
    if (count == 11) text(count-1 + " ProjectilesFired", width-220, 20);
    popStyle();
  }
}
  public void settings() {  size(1000,800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "project_3" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
