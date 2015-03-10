package com.github.borione.util;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Icon;

public class ImageUtils {

	/**
	 * This method reads an image from the file
	 *
	 * @param fileLocation -- > eg. "C:/testImage.jpg"
	 * @return BufferedImage of the file read
	 */
	public static BufferedImage readImage(String fileLocation) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(fileLocation));
			System.out.println("Image Read. Image Dimension: " + img.getWidth()	+ "w X " + img.getHeight() + "h");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	/**
	 * This method writes a non-empty buffered image to a file
	 *
	 * @param img -- > BufferedImage
	 * @param fileLocation --> e.g. "C:/testImage.jpg"
	 * @param extension --> e.g. "jpg","gif","png"
	 */
	public static void writeImage(BufferedImage img, String fileLocation, String extension) {
		if(!img.equals(getEmptyImage(img.getWidth(), img.getHeight()))) {	// TODO: L'immagine ritornata da getEmptyImage() e' nera e non trasparente.
			try {
				BufferedImage bi = img;
				File outputfile = new File(fileLocation);
				ImageIO.write(bi, extension, outputfile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Rotates an image. Actually rotates a new copy of the image.
	 * 
	 * @param img The image to be rotated
	 * @param angle The angle in degrees
	 * @return The rotated image
	 */
	public static Image rotate(Image img, double angle)	{
		double sin = Math.abs(Math.sin(Math.toRadians(angle)));
		double cos = Math.abs(Math.cos(Math.toRadians(angle)));

		int w = img.getWidth(null);
		int h = img.getHeight(null);

		int neww = (int) Math.floor(w*cos + h*sin);
		int newh = (int) Math.floor(h*cos + w*sin);

		BufferedImage bimg = toBufferedImage(getEmptyImage(neww, newh));
		Graphics2D g = bimg.createGraphics();

		g.translate((neww-w)/2, (newh-h)/2);
		g.rotate(Math.toRadians(angle), w/2, h/2);
		g.drawRenderedImage(toBufferedImage(img), null);
		g.dispose();

		return toImage(bimg);
	}

	/**
	 * Converts a given Image into a BufferedImage
	 * 
	 * @param img The Image to be converted
	 * @return The converted BufferedImage
	 */
	public static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}
		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();
		// Return the buffered image
		return bimage;
	}

	/**
	 * Converts the given Icon into a BufferedImage.
	 * @param icon The icon to be converted.
	 * @return The converted BufferedImage.
	 */
	public static BufferedImage toBufferedImage(Icon icon) {
		BufferedImage bi = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.createGraphics();
		// paint the Icon to the BufferedImage.
		icon.paintIcon(null, g, 0, 0);
		g.dispose();

		return bi;
	}

	/**
	 * Converts a given BufferedImage into an Image
	 * 
	 * @param bimage The BufferedImage to be converted
	 * @return The converted Image
	 */
	public static Image toImage(BufferedImage bimage) {
		// Casting is enough to convert from BufferedImage to Image
		Image img = (Image) bimage;
		return img;
	}

	/**
	 * Creates an empty image with transparency.
	 * 
	 * @param width The width of required image.
	 * @param height The height of required image.
	 * @return The created image.
	 */
	public static Image getEmptyImage(int width, int height) {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		return toImage(img);
	}

	/**
	 * Merges all the {@linkplain BufferedImage BufferedImages} int the list into one {@linkplain BufferedImage}.
	 * @param list The {@linkplain List} of all the {@linkplain BufferedImage BufferedImages} to merge.
	 * @return The merged {@linkplain BufferedImage}.
	 */
	public static BufferedImage merge(List<BufferedImage> list) {
		// create the new image, canvas size is the max. of all image sizes
		ArrayList<Integer> widths = new ArrayList<Integer>();
		ArrayList<Integer> heights = new ArrayList<Integer>();

		for (BufferedImage bufferedImage : list) {
			widths.add(bufferedImage.getWidth());
			heights.add(bufferedImage.getHeight());
		}

		int w = NumberUtils.max(widths);
		int h = NumberUtils.max(heights) + (int) Math.pow(list.size() - 1, 2);

		BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

		// paint all images, preserving the alpha channels
		Graphics g = combined.getGraphics();		
		for (int i = 0; i < list.size(); i ++) {
			BufferedImage img = list.get(i);
			g.drawImage(img, 0, (int) Math.pow(i, 2), null);
		}

		return combined;
	}

	public static BufferedImage resize(Image original, int width, int height) {
		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage((Image) original, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}

	public static BufferedImage resizeBetter(Image original, int width, int height) {
		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(original, 0, 0, width, height, null);
		g.dispose();
		g.setComposite(AlphaComposite.Src);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		return resizedImage;
	}

	public static Image getImageFromWeb(String imageUrl) throws IOException {
		URL url = new URL(imageUrl);

		return ImageIO.read(url);
	}

}
