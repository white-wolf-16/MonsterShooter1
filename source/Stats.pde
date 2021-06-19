class Stats {
  int score;
  PFont mono = loadFont("MaturaMTScriptCapitals-24.vlw");
  Stats() {
    score = 0;
  }
  
  void incScore(int i){
      score+=i;
  }
  
  void drawS() {
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
