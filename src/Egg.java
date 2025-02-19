//jump height of egg=400
public class Egg {

    int egg_size_a;
    int egg_size_b;
    int egg_position_x;
    int egg_position_y;
    int egg_jump_speed = 7;
    int i = 0;
    public boolean jump_up = true;
    Sketch sketch;
    Bowl bowl;

    Egg(Sketch sketch, Bowl bowl, int x, int y) {
        this.sketch = sketch;
        egg_size_a = 50;
        egg_size_b = 70;
        egg_position_y = y;
        egg_position_x = x;
        this.bowl = bowl;
    }

    public void attachBowl(Bowl bowl) {
        this.bowl = bowl;
        jump_up = false;
        i = 0;
    }

    public void drawEgg() {
        sketch.fill(255);
        sketch.ellipse(egg_position_x, egg_position_y, egg_size_a, egg_size_b);
    }

    public void jumpEgg() {
        if (i < 50 && jump_up == true) {
            egg_position_y -= egg_jump_speed;
            i++;
            drawEgg();
        } else {
            jump_up = false;
            egg_position_y += egg_jump_speed;
            i--;
            drawEgg();
        }
        if (i == 0) {
            if (!sketch.checkJump(bowl, this))
                drawButton();

            jump_up = true;
            sketch.jumpTriggered = false;
            sketch.move_ball_horizontal = true;
        }
    }

    public void moveEggHorizontally() {
        drawEgg();
        egg_position_x = bowl.position_x;
    }

    public int getBallX() {
        return egg_position_x;
    }

    public int getBallY() {
        return egg_position_y;
    }


    public void drawButton() {
        sketch.playGameOutBeep();
        sketch.noLoop();
        sketch.pushMatrix();
        sketch.translate(0, -(sketch.totalYOffset +sketch. yOffset));
        int buttonX = sketch.window_size_x / 2 - 50;
        int buttonY = sketch.window_size_y / 2 + 50;
        int buttonW = 200;
        int buttonH = 50;
        sketch.fill(255, 20, 100);
        sketch.rect(buttonX, buttonY, buttonW, buttonH, 10);
        sketch.fill(255);
        sketch.textSize(16);
        sketch.textAlign(sketch.CENTER, sketch.CENTER);
        sketch.text("You Are Out!\nPress 'r' to Restart", buttonX + buttonW / 2, buttonY + buttonH / 2);
        sketch.popMatrix();
    }

}