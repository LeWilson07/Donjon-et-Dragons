package map;

public class Obstacle {
    private int m_x;
    private int m_y;

    public Obstacle(int x, int y) {
        this.m_x = x;
        this.m_y = y;
    }

    public int getX() {
        return m_x;
    }

    public int getY() {
        return m_y;
    }
}
