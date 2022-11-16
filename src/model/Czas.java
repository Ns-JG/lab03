package model;


public class Czas {

    private int time_interval;
    public Integer current = 0;
    private boolean loop;

    public Czas(int interval) {
        this.time_interval = interval;
        this.loop = true;
    }

    private void tick() { this.current += 1; }

    public boolean time_loop() throws InterruptedException {
        if (this.loop) {
            this.tick();
            Thread.sleep(this.time_interval);
            return true;
        }
        return false;
    }
    public void toggle_loop() { this.loop = !this.loop; }
}
// 1s = 1000 milis / time interval - 1 s