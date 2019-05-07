package com.oop.platformer.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.oop.platformer.GameClass;

import java.awt.*;

public class DesktopLauncher {
    public static void main(String[] arg) {

        //New Lightweight Java Game Library Application Configuration
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //Adding icons for the application
        config.addIcon("Icon/Icon128.png", Files.FileType.Internal);
        config.addIcon("Icon/Icon64.png", Files.FileType.Internal);
        config.addIcon("Icon/Icon32.png", Files.FileType.Internal);

        //Title and app size.
        config.title = "Invasion 3102";
        config.width = (int) screenSize.getWidth();
        config.height = (int) screenSize.getHeight();

        /*
        New Lightweight Java Game Library Application that runs our GameClass with the specified
        screen size and configuration
        */
        new LwjglApplication(new GameClass((float) config.width, (float) config.height), config);
    }
}
