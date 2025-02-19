import processing.core.PApplet;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Sketch extends PApplet {

    int window_size_x = 800;
    int window_size_y = 1000;
    public boolean jumpTriggered = false;
    int present_bowl_index=0;
    int speedOfBowl=0;
    Egg e;
    Bowl[] b = new Bowl[2];
    int start_x=window_size_x/2;
    int start_y=window_size_y/2+300;
    public boolean move_ball_horizontal = true;
    public boolean translate_down=false;
    float totalYOffset = 0;  // Accumulates permanent translation
    float yOffset = 0;
    int jump_count = 0;
    Clip backgroundClip;
    Clip successClip;
    Clip gameOutClip;

    public void settings() {
        size(window_size_x, window_size_y);
    }

    public void setup() {
        b[present_bowl_index]=new Bowl(this,start_x,start_y, 0);
        b[1]=new Bowl(this,start_x,start_y-300, 0);
        e = new Egg(this,b[present_bowl_index],start_x,start_y- b[present_bowl_index].bowl_height);

        loadBackgroundMusic();
        loadSuccessMusic();
        loadGameOutBeep();

        playBackgroundMusic();
    }

    public void draw() {
        background(19);
        if(translate_down){
            if (yOffset < 300) {
                yOffset += 15;  // Adjust translation speed
            } else {
                translate_down = false;
                totalYOffset += yOffset; // Lock the translation permanently
                yOffset = 0;
            }
        }

        text(" Score : "+jump_count + " ", width-50, 50);

        pushMatrix();
        translate(0, totalYOffset + yOffset);

        if (jumpTriggered) {
            e.jumpEgg();
            move_ball_horizontal = false;
        }

        if(move_ball_horizontal)
            e.moveEggHorizontally();

        else
            e.drawEgg();

        b[present_bowl_index].moveBowl();
        b[1-present_bowl_index].moveBowl();

        popMatrix();

        if(checkJump(b[1-present_bowl_index],e) ) {
            playSuccessMusic();
            jump_count++;
            present_bowl_index=1-present_bowl_index;
            e.attachBowl(b[present_bowl_index]);
            b[1-present_bowl_index].increasePosition(600);
            speedOfBowl=speedOfBowl+2;
            b[1-present_bowl_index].setSpeed(speedOfBowl);
            if(jump_count>1){
                translate_down=true;
            }
            e.egg_position_y = b[present_bowl_index].position_y-55;
            e.egg_position_x = b[present_bowl_index].position_x;
            move_ball_horizontal = true;
            e.jump_up = true;
            jumpTriggered=false;
        }

    }

    public void keyPressed() {
        if (key == ' ')
            jumpTriggered = true;
        if (key == 'p')
            noLoop();
        if (key == 'r')
            resetGame();
    }

    public void mousePressed() {
        if (mouseButton == LEFT) {
            jumpTriggered = true;
        }
    }

    public boolean checkJump(Bowl b, Egg e) {
        float distance = dist(b.getX(), b.getY(), e.getBallX(), e.getBallY());
        return (distance < 60 && e.jump_up==false);
    }

    public void resetGame() {
        jumpTriggered = false;
        translate_down = false;
        totalYOffset = 0;
        yOffset = 0;
        move_ball_horizontal = true;
        speedOfBowl = 0;
        present_bowl_index = 0;
        jump_count = 0;
        b[0].setY(start_y);
        b[1].setY(start_y - 300);
        b[0].setX(start_x);
        b[1].setX(start_x);
        e = new Egg(this, b[present_bowl_index], start_x, start_y - b[present_bowl_index].bowl_height);
        b[0].setSpeed(0);
        b[1].setSpeed(0);
        backgroundClip.setFramePosition(0);
        loop();
    }

    public void loadBackgroundMusic() {
        try {
            InputStream musicStream = getClass().getClassLoader().getResourceAsStream("resources/music.wav");
            if (musicStream == null) {
                System.out.println("Background music not found.");
                return;
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(musicStream);
            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(audioIn);
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);  // Loop background music
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadSuccessMusic() {
        try {
            InputStream musicStream = getClass().getClassLoader().getResourceAsStream("resources/success.wav");
            if (musicStream == null) {
                System.out.println("Success music not found.");
                return;
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(musicStream);
            successClip = AudioSystem.getClip();
            successClip.open(audioIn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadGameOutBeep() {
        try {
            InputStream musicStream = getClass().getClassLoader().getResourceAsStream("resources/out.wav");
            if (musicStream == null) {
                System.out.println("Game-out beep sound not found.");
                return;
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(musicStream);
            gameOutClip = AudioSystem.getClip();
            gameOutClip.open(audioIn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playBackgroundMusic() {
        if (backgroundClip != null && !backgroundClip.isRunning()) {
            backgroundClip.setFramePosition(0);
            backgroundClip.start();
        }
    }

    public void playSuccessMusic() {
        if (successClip != null) {
            successClip.setFramePosition(0);
            successClip.start();
        }
    }

    public void playGameOutBeep() {
        if (gameOutClip != null) {
            gameOutClip.setFramePosition(0);
            gameOutClip.start();
        }
    }

}