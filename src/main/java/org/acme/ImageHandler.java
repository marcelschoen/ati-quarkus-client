package org.acme;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Singleton;

@ApplicationScoped
public class ImageHandler {

	private BufferedImage[] tiles = new BufferedImage[256];

	void onStart(@Observes StartupEvent ev) {
		try {
			BufferedImage tileset = ImageIO.read(ImageHandler.class.getResourceAsStream ("/images/Tileset_128x128.png"));
			int tileCounter = 0;
			int y = 0;
			for(int row = 0; row < 16; row ++) {
				int x = 0;
				for(int col = 0; col < 16; col ++) {
					tiles[tileCounter++] = tileset.getSubimage(x, y, 8, 8);
					x += 8;
				}
				y += 8;
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public BufferedImage getImage(int number) {
		return tiles[number];
	}
}
