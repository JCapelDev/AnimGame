package com.capel.animgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.capel.animgame.AnimGameLauncher;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "AnimGame";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new AnimGameLauncher(), config);
	}
}
