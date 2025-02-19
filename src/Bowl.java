public class Bowl {

    int bowl_speed;
    int position_x, position_y;
    Sketch sketch;
    public int bowl_height=52;
    public int bowl_width=90;

    Bowl(Sketch sketch, int x, int y, int speed) {
        this.sketch = sketch;
        position_x = x;
        position_y = y;
        bowl_speed = speed;
    }

    public void increasePosition(int y) {
        position_y -= y;
    }

    public void setY(int y) {
        position_y = y;
    }
    public void setX(int x) {
        position_x = x;
    }

    public void setSpeed(int bowl_speed) {
        this.bowl_speed = bowl_speed;
    }

    public void drawBowl(int x, int y) {
        sketch.fill(249, 150, 255);
        sketch.noStroke();
        for (int i = 0; i < 15; i++) {
            sketch.ellipse(x, y - 3 * i, 20 + 5 * i, 15);
        }
    }

    void moveBowl() {
        drawBowl(position_x, position_y);
        position_x += bowl_speed;

        if (position_x > sketch.width-50)
            bowl_speed *= (-1);

        if (position_x < 50)
            bowl_speed *= (-1);
    }

    public int getX() {
        return position_x;
    }

    public int getY() {
        return position_y;
    }

}
