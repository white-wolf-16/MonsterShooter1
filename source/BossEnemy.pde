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
  
  void fireProjectile(){
    float angle = atan2(p1.pos.y-pos.y, p1.pos.x-pos.x);
    ps.add(new Projectile(pos.x,pos.y,cos(angle),sin(angle),#B600FF));
    firetime=60; 
  }
 
  void checkProjectile(){
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
  
  void update(){
   super.update();
   if (firetime==0 && p1.ded==false && ded==false) fireProjectile();
   checkProjectile();
   firetime--;
  }
  
  void dead(){
   super.dead();
   ded=true;
   timer = 180;
  }
  
  void drawChar(){
    if(!ded){
      pushMatrix();
      
      translate(pos.x,pos.y);
      
      movelegs();
      
      scale(size);
      stroke(#242234);
      strokeWeight(2);
      fill(#3B3658);
      
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
      fill(#242234);
      rect(-11.01,7,23,8,4);
      
      
      popMatrix();
    }
   if(ded){
    pushMatrix();
    
    translate(pos.x,pos.y);
    scale(size);
    //stroke(#242234);
    //strokeWeight(2);
    fill(#FF79A0);
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
