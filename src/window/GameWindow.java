package window;

import java.awt.event.*;
import java.awt.Window;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;

import game.Game;
import menu.MenuPage;
import settings.SettingsPage;

public class GameWindow extends JFrame implements ActionListener{

    //Var for singleton design
    private static GameWindow _instance;

    private MenuPage _menupage;
    private SettingsPage _settingspage;
    private Game _game;

    private enum WindowLayout {
        FULLSCREEN, WINDOW
    }

    public GameWindow() {

        //Use it as singleton instance
        _instance = this;

        _menupage = new MenuPage();
        this.add(_menupage);

        this.resizeWindow(WindowLayout.FULLSCREEN); //Use own method instead of setExtendedState() to not rely on wm
        this.setTitle("Jump'n run game");
        this.setResizable(false);
        this.setVisible(true);

        this.repaint();
    }

    //Method for singleton design
    public static GameWindow getInstance() {
        if (_instance == null) _instance = new GameWindow();
        return _instance;
    }

    public void resizeWindow(WindowLayout wl) {
        if (wl == WindowLayout.WINDOW) {this.setUndecorated(false);}
        else {this.setUndecorated(true);}

        for ( Window w : Window.getWindows() ) {
        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow( w );
        }
    }

    private void launchMenu() {}

    private void launchSettings() {}

    private void launchGame() {
        //Doesn't need to care about _settingspage as you can't access game from settings
        this.remove(_menupage);
        _game = new Game();
        this.add(_game);
        this.setVisible(true);
        this.repaint();
        _menupage = null; //Make _menupage ready for garbage collector
    }

    //Central ActionListener for buttons that call other pages
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (_menupage != null) {
            if (source == _menupage.startButton) {launchGame();}
            else {
                if (source == _menupage.settingsButton) {launchSettings();}
                else {throw new IllegalArgumentException("ActionEvent not known");}
            }
        }
    }
}