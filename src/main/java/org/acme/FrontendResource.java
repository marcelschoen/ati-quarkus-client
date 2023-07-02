package org.acme;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.Template;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/game")
public class FrontendResource {

	@Inject
	private ImageHandler imageHandler;

	@Inject
	Template mainView;

	private BufferedImage gameViewBuffer = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
	private Graphics2D gameViewGraphics = gameViewBuffer.createGraphics();

	@GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance showFrontend() {
		String base64 = "UNKNOWN";

		gameViewGraphics.setPaint ( Color.RED );
		gameViewGraphics.fillRect ( 0, 0, gameViewBuffer.getWidth(), gameViewBuffer.getHeight() );
		int x = 0;
		for(int i = 0; i < 10; i++) {
			gameViewGraphics.drawImage(imageHandler.getImage(i), x, 20, Color.YELLOW, null);
			x += 20;
		}

	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    try {
		    ImageIO.write(gameViewBuffer, "png", out);
			byte[] data = out.toByteArray();
		    base64 = Base64.getEncoder().encodeToString(data);
	    } catch (IOException e) {
		    throw new RuntimeException(e);
	    }

		return mainView.data("mainViewData", base64);
    }
}
