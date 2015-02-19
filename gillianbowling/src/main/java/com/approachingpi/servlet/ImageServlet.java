package com.approachingpi.servlet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gillianbowling.data.repositories.ConfigurationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.awt.image.BufferedImageGraphicsConfig;

public class ImageServlet extends HttpServlet {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImageServlet.class);

	@Inject
	ConfigurationRepository configuration;

	public ImageServlet() {

	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// this was an attempt to fix an image loading issue on linux
		//		try {
		//			Class.forName("java.awt.color.ICC_ColorSpace");
		//		} catch (ClassNotFoundException e) {
		//			LOGGER.error("error loading colorspace", e);
		//		}
		//		try {
		//			Class.forName("sun.java2d.cmm.lcms.LCMS");
		//		} catch (ClassNotFoundException e) {
		//			LOGGER.error("error loading colorspace", e);
		//		}
		super.init(config);
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String pathInfo = (req.getPathInfo() == null) ? "" : req.getPathInfo();

		pathInfo = pathInfo.replace("+", " ");

		String[] pathSplit = pathInfo.split("/");

		if (pathSplit.length > 1) {
			boolean resize = false;
			int requestedWidth = 0;
			int requestedHeight = 0;
			int maxWidthOrHeight = 0;

			String imageSize = pathSplit[1];
			if (!"".equals(imageSize) && !"original".equalsIgnoreCase(imageSize) && (imageSize.indexOf('x') > -1)) {
				String[] imageSizeSplit = imageSize.split("x");
				if (imageSizeSplit.length > 0) {
					try {
						requestedWidth = Integer.parseInt(imageSizeSplit[0]);
					} catch (Exception e) {
						// do nothing;
					}
				}
				if (imageSizeSplit.length > 1) {
					try {
						requestedHeight = Integer.parseInt(imageSizeSplit[1]);
					} catch (Exception e) {
						// do nothing;
					}
				}
				if (requestedWidth > 0 || requestedHeight > 0) {
					resize = true;
				}
			} else {
				try {
					maxWidthOrHeight = Integer.parseInt(imageSize);
					if (maxWidthOrHeight > 0) {
						resize = true;
					}
				} catch (NumberFormatException e) {
					// do nothing
				}
			}

			String fileUri = configuration.getString("media.dir");
			if (!fileUri.endsWith(File.separator)) {
				fileUri = fileUri + File.separator;
			}
			for (int i = 2; i < pathSplit.length; i++) {
				if (i > 2) {
					fileUri += File.separator;
				}
				fileUri += pathSplit[i];
			}

			// check the file extension so we can serve up other types of documents
			// of course we can only resize images.
			String fileExtension = fileUri.substring(fileUri.lastIndexOf("."));
			String fileExtensionOutput = fileExtension;
			if (!".jpg".equalsIgnoreCase(fileExtension)
					&& !".gif".equalsIgnoreCase(fileExtension)
					&& !".png".equalsIgnoreCase(fileExtension)
					&& !".pdf".equalsIgnoreCase(fileExtension)
					) {
				resize = false;
			}

			LOGGER.debug("file:{}, width:{}, height:{}, maxWidthOrheight:{}", new String[]{
					fileUri,
					Integer.toString(requestedWidth),
					Integer.toString(requestedHeight),
					Integer.toString(maxWidthOrHeight)
				});
			LOGGER.debug("fileUri:{} width:{} height:{}", fileUri, requestedWidth, requestedHeight);

			File file = new File(fileUri);
			if (!file.exists()) {
				//PrintWriter out = res.getWriter();
				res.setStatus(HttpServletResponse.SC_NOT_FOUND);
				LOGGER.warn("File Not Found: {}", file.getAbsolutePath());
			} else {

				File fileOutput = file;
				if (resize) {
					String fileUriSized = configuration.getString("media.cache.dir");
					if (fileUriSized.endsWith(File.separator)) {
						fileUriSized = fileUriSized.substring(0, fileUriSized.length() - 1);
					}
					for (int i = 1; i < pathSplit.length; i++) {
						fileUriSized += File.separator;
						fileUriSized += pathSplit[i];

						if (i == pathSplit.length - 2) {

							LOGGER.debug("checking for folder:{}", fileUriSized);
							File currentPathFile = new File(fileUriSized);
							if (!currentPathFile.exists()) {
								LOGGER.debug("creating folder:{}", fileUriSized);
								if (!currentPathFile.mkdirs()) {
									LOGGER.error("Cannot create directory:{}", fileUriSized);
								}
							}
						}
					}

					if (".pdf".equalsIgnoreCase(fileExtension)) {
						fileUriSized = fileUriSized.substring(0, fileUriSized.length() - 4);
						fileUriSized = fileUriSized + ".png";
						fileExtensionOutput = ".png";
					}

					File fileSized = new File(fileUriSized);
					if (fileSized.exists()) {
						fileOutput = fileSized;
					} else {
						BufferedImage imageOriginal = ImageIO.read(file);

						int width = imageOriginal.getWidth(null);
						int height = imageOriginal.getHeight(null);

						if (requestedWidth == 0 && requestedHeight == 0 && maxWidthOrHeight > 0) {
							if (height > width) {
								requestedHeight = maxWidthOrHeight;
							} else {
								requestedWidth = maxWidthOrHeight;
							}
						}

						if (requestedWidth > width) {
							requestedWidth = width;
						}
						if (requestedHeight > height) {
							requestedHeight = height;
						}

						String imageFormat = "JPG";
						if (".gif".equalsIgnoreCase(fileExtensionOutput)) {
							imageFormat = "GIF";
						} else if (".png".equalsIgnoreCase(fileExtensionOutput)) {
							imageFormat = "PNG";
						}

						if (this.generateImage(imageOriginal, fileSized, requestedWidth, requestedHeight, 100,
								imageFormat, configuration)) {
							fileOutput = fileSized;
						} else {
							fileOutput = file;
						}
					}
				}

				FileInputStream in = null;
				try {
					in = new FileInputStream(fileOutput);
					int ilength = in.available();
					byte[] filebytes = new byte[ilength];
					in.read(filebytes);
					in.close();

					// set the content type of the response
					if (".gif".equalsIgnoreCase(fileExtensionOutput)) {
						res.setContentType("image/gif");
					} else if (".jpg".equalsIgnoreCase(fileExtensionOutput)) {
						res.setContentType("image/jpeg");
					} else if (".png".equalsIgnoreCase(fileExtensionOutput)) {
						res.setContentType("image/png");
					} else if (".pdf".equalsIgnoreCase(fileExtensionOutput)) {
						res.setContentType("application/pdf");
					} else {
						res.setContentType("application/unknown");
					}
					res.setContentLength(filebytes.length);

					ServletOutputStream ouputStream = res.getOutputStream();
					ouputStream.write(filebytes, 0, filebytes.length);
					ouputStream.flush();
				} finally {
					if (in != null) {
						in.close();
					}
				}

			}
		} else {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	public boolean generateImage(BufferedImage image, File scaledFile, int new_width, int new_height, int quality, String format, ConfigurationRepository config) throws IOException {
		double w = image.getWidth(null);
		double h = image.getHeight(null);

		double nw = 0;
		double nh = 0;
		double scalex = 0;

		if (new_width == 0 && new_height > 0) {
			nh = new_height;
			scalex = nh / h;
			nw = scalex * w;

		} else if (new_width > 0 && new_height == 0) {
			nw = new_width;
			scalex = nw / w;
			nh = scalex * h;

		} else if (new_width > 0 && new_height > 0) {
			nw = new_width;
			nh = new_height;

		} else {
			return false;
		}

		//BufferedImage thumbImage = new BufferedImage((int) nw, (int) nh, 1);
		//Graphics2D graphics2D = thumbImage.createGraphics();
		//graphics2D.setBackground(Color.WHITE);
		//graphics2D.setPaint(Color.WHITE);
		//graphics2D.fillRect(0, 0, (int) nw, (int) nh);
		//graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		//graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		//graphics2D.drawImage(image, 0, 0, (int) nw, (int) nh, null);
		//ImageIO.write(thumbImage, format, scaledFile);

		//BufferedImage imageScale = this.createCompatibleImage(image);
		//imageScale = this.blurImage(imageScale);
		//imageScale = this.resize(imageScale, (int) nw, (int) nh);
		BufferedImage imageScale = this.getScaledInstance(image, (int) nw, (int) nh, RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);
		ImageIO.write(imageScale, format, scaledFile);

		//GraphicsConfiguration gc = image.createGraphics().getDeviceConfiguration();
		//BufferedImage thumbImage = gc.createCompatibleImage((int) nw, (int) nh, Transparency.OPAQUE);
		//Graphics2D g2d = thumbImage.createGraphics();
		//g2d.setComposite(AlphaComposite.Src);
		//g2d.drawImage(image, 0, 0, (int) nw, (int) nh, null);
		//g2d.dispose();
		//ImageIO.write(thumbImage, format, scaledFile);

		// this is an attempt to get the image quality better than ImageIO writes
		//BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(scaledFile));
		//JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		//JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(thumbImage);
		//param.setQuality((float) quality / 100.0f, false);
		//encoder.encode(thumbImage, param);
		return true;
	}


	public BufferedImage resize(BufferedImage image, int width, int height) {
		int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.setComposite(AlphaComposite.Src);

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}

	public BufferedImage createCompatibleImage(BufferedImage image) {
		GraphicsConfiguration gc = BufferedImageGraphicsConfig.getConfig(image);
		int w = image.getWidth();
		int h = image.getHeight();
		BufferedImage result = gc.createCompatibleImage(w, h, Transparency.TRANSLUCENT);
		Graphics2D g2 = result.createGraphics();
		g2.drawRenderedImage(image, null);
		g2.dispose();
		return result;
	}

	public BufferedImage blurImage(BufferedImage image) {
		float ninth = 1.0f / 9.0f;
		float[] blurKernel = {
				ninth, ninth, ninth,
				ninth, ninth, ninth,
				ninth, ninth, ninth
		};

		Map<RenderingHints.Key, Object> map = new HashMap<>();

		map.put(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		map.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		RenderingHints hints = new RenderingHints(map);
		BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, blurKernel), ConvolveOp.EDGE_NO_OP, hints);
		return op.filter(image, null);
	}

	/**
	 * http://today.java.net/pub/a/today/2007/04/03/perils-of-image-getscaledinstance.html
	 * Convenience method that returns a scaled instance of the
	 * provided {@code BufferedImage}.
	 *
	 * @param img		   the original image to be scaled
	 * @param targetWidth   the desired width of the scaled instance,
	 *                      in pixels
	 * @param targetHeight  the desired height of the scaled instance,
	 *                      in pixels
	 * @param hint		  one of the rendering hints that corresponds to
	 *                      {@code RenderingHints.KEY_INTERPOLATION} (e.g.
	 *                      {@code RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR},
	 *                      {@code RenderingHints.VALUE_INTERPOLATION_BILINEAR},
	 *                      {@code RenderingHints.VALUE_INTERPOLATION_BICUBIC})
	 * @param higherQuality if true, this method will use a multi-step
	 *                      scaling technique that provides higher quality than the usual
	 *                      one-step technique (only useful in downscaling cases, where
	 *                      {@code targetWidth} or {@code targetHeight} is
	 *                      smaller than the original dimensions, and generally only when
	 *                      the {@code BILINEAR} hint is specified)
	 * @return a scaled version of the original {@code BufferedImage}
	 */
	public BufferedImage getScaledInstance(BufferedImage img,
										   int targetWidth,
										   int targetHeight,
										   Object hint,
										   boolean higherQuality) {
		int type = (img.getTransparency() == Transparency.OPAQUE) ?
				BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
		BufferedImage ret = (BufferedImage) img;
		int w, h;
		if (higherQuality) {
			// Use multi-step technique: start with original size, then
			// scale down in multiple passes with drawImage()
			// until the target size is reached
			w = img.getWidth();
			h = img.getHeight();
		} else {
			// Use one-step technique: scale directly from original
			// size to target size with a single drawImage() call
			w = targetWidth;
			h = targetHeight;
		}

		do {
			if (higherQuality && w > targetWidth) {
				w /= 2;
				if (w < targetWidth) {
					w = targetWidth;
				}
			}

			if (higherQuality && h > targetHeight) {
				h /= 2;
				if (h < targetHeight) {
					h = targetHeight;
				}
			}

			BufferedImage tmp = new BufferedImage(w, h, type);
			Graphics2D g2 = tmp.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
			g2.drawImage(ret, 0, 0, w, h, null);
			g2.dispose();

			ret = tmp;
		} while (w != targetWidth || h != targetHeight);

		return ret;
	}

}
