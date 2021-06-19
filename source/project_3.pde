Player p1;
Stats score;
boolean up, down, left, right, start = true, play = false, end = false;
ArrayList<BasicEnemy> enemies=new ArrayList<BasicEnemy>();
int count, timer=180;


void setup(){
  size(1000,800);
  frameRate(60);
  cursor(CROSS);
  score = new Stats();
  p1 = new Player(width/2,height-100,0,0,1.5);
  for (int i=0; i<5; i++) 
    enemies.add(new BasicEnemy(random(1, width), random(1, height/2), random(-3, 3), random(-3, 3), 0.9));
  enemies.add(new BossEnemy(width/2,1,1,1,1.5));
}


void draw(){
  if (start){
    fill(#D8D8D8,120);
    rect(0,0,width,height);
    fill(#4393A0);
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
    fill(#D8D8D8,120);
    rect(0,0,width,height);
    fill(#4393A0);
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

void keyPressed() {
  if (key == 'w') up = true;
  if (key == 's') down = true;
  if (key == 'a') left = true;
  if (key == 'd') right = true;
  if (key == 'r') count = 0;
 // if (key == ' ') p1.fireProjectile();
}

void keyReleased() {
  if (key == 'w') up = false;
  if (key == 's') down = false;
  if (key == 'a') left = false;
  if (key == 'd') right = false;
  if (key == ' ') p1.fireProjectile();
}

void mousePressed() {
  start= false; play=true; end=false;
}
