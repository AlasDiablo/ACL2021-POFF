package fr.poweroff.labyrinthe.engine;

import fr.poweroff.labyrinthe.engine.menu.BestScore;
import fr.poweroff.labyrinthe.engine.menu.Fin;
import fr.poweroff.labyrinthe.engine.menu.Level;
import fr.poweroff.labyrinthe.engine.menu.Menu;
import fr.poweroff.labyrinthe.model.PacmanPainter;
import fr.poweroff.labyrinthe.utils.Score;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;

public class DrawingPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * la clase chargee de Dessiner
     */
    private final GamePainter painter;
    /**
     * la taille des images
     */
    private final int width;
    private final int height;
    /**
     * image suivante est l'image cachee sur laquelle dessiner
     */
    private BufferedImage nextImage;
    /**
     * image en cours est l'image entrain d'etre affichee
     */
    private BufferedImage currentImage;
    private boolean isInMenu;
    private int menuPointerX;
    private int menuPointerY;
    private float menuAnimation;
    private float menuAnimationAdder;

    /**
     * constructeur Il construit les images pour doublebuffering ainsi que le
     * Panel associe. Les images stockent le painter et on demande au panel la
     * mise a jour quand le painter est fini
     */
    public DrawingPanel(GamePainter painter) {
        super();
        this.width = painter.getWidth();
        this.height = painter.getHeight();
        this.setPreferredSize(new Dimension(this.width, this.height));
        this.painter = painter;
        this.isInMenu = false;
        this.menuAnimation = 0f;
        this.menuAnimationAdder = 0.6f;

        // cree l'image buffer et son graphics
        this.nextImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB
        );
        this.currentImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB
        );
    }

    /**
     * demande de mettre a jour le rendu de l'image sur le Panel. Creer une
     * nouvelle image vide sur laquelle dessiner
     */
    public void drawGame() {
        // generer la nouvelle image
        this.painter.draw(this.nextImage);

        // inverses les images doublebuffereing
        BufferedImage temp = this.currentImage;
        // l'image a dessiner est celle qu'on a construite
        this.currentImage = this.nextImage;
        // l'ancienne image est videe
        this.nextImage = temp;
        this.nextImage.getGraphics()
                .fillRect(0, 0, this.width, this.height);
        // met a jour l'image a afficher sur le panel
        this.repaint();
    }

    public void drawNiveau(int menuPosition) {
        var graphics = currentImage.getGraphics();
        graphics.drawImage(Level.getSprites(), 0, 0, getWidth(), getHeight(), 0, 0,
                getWidth(), getHeight(), null
        );
        this.isInMenu = true;
        this.menuPointerX = 150;
        this.menuPointerY = 174 + 70 * menuPosition;
        this.repaint();
    }

    public void drawMenu(int menuPosition) {
        var graphics = currentImage.getGraphics();
        graphics.drawImage(Menu.getSprites(), 0, 0, getWidth(), getHeight(), 0, 0,
                getWidth(), getHeight(), null
        );
        this.isInMenu = true;
        this.menuPointerX = 150;
        this.menuPointerY = 188 + 56 * menuPosition;
        this.repaint();
    }

    /**
     * redefinit la methode paint consiste a dessiner l'image en cours
     *
     * @param graphics graphics pour dessiner
     */
    public void paint(Graphics graphics) {
        super.paint(graphics);
        graphics.drawImage(this.currentImage, 0, 0, getWidth(), getHeight(), 0, 0,
                getWidth(), getHeight(), null
        );
        if (this.isInMenu) {
            this.menuAnimation += this.menuAnimationAdder;
            if (this.menuAnimation >= 10f || this.menuAnimation <= 0f)
                this.menuAnimationAdder = -this.menuAnimationAdder;
            graphics.setColor(Color.ORANGE);
            graphics.fillPolygon(this.triangle(this.menuPointerX + (int) Math.floor(this.menuAnimation), this.menuPointerY));
            this.isInMenu = false;
        }
    }

    private Polygon triangle(int x, int y) {
        var xs = new int[]{x, 20 + x, x};
        var ys = new int[]{y, 10 + y, 20 + y};
        return new Polygon(xs, ys, 3);
    }

    public void drawFin(String name) {
        var graphics = currentImage.getGraphics();
        graphics.drawImage(Fin.getSprites(), 0, 0, getWidth(), getHeight(), 0, 0,
                getWidth(), getHeight(), null
        );
        var font = new Font("Courier New", Font.BOLD, 28);
        graphics.setFont(font);
        graphics.drawString(name, 217, 378);

        graphics.drawString(String.valueOf(((PacmanPainter) this.painter).pacmanGame.getScore()), 217, 430);
        this.repaint();
    }

    public void drawBestScore() {
        var graphics = currentImage.getGraphics();
        graphics.drawImage(BestScore.getSprites(), 0, 0, getWidth(), getHeight(), 0, 0,
                getWidth(), getHeight(), null
        );
        var font = new Font("Courier New", Font.BOLD, 32);
        graphics.setFont(font);
        var i = new AtomicInteger(0);
        Score.getScores().forEach(jsonObject -> {
            var score = jsonObject.get("name").getAsString();
            score += " - ";
            score += jsonObject.get("score").getAsInt();
            graphics.drawString(score, 180, 280 + 42 * i.getAndIncrement());
        });
        //graphics.drawString(String.valueOf(score.getBestScore()), 250, 300);
        this.repaint();
    }
}
