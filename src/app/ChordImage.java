package app;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ChordImage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final byte[] imageData;
    private final String imageName;
    private final String imageFormat;
    private final long timestamp;

    public ChordImage(byte[] imageData, String imageName, String imageFormat) {
        this.imageData = imageData;
        this.imageName = imageName;
        this.imageFormat = imageFormat;
        this.timestamp = System.currentTimeMillis();
    }

    public ChordImage(BufferedImage image, String imageName, String imageFormat) throws IOException {
        this.imageData = convertImageToBytes(image, imageFormat);
        this.imageName = imageName;
        this.imageFormat = imageFormat;
        this.timestamp = System.currentTimeMillis();
    }

    public byte[] getImageData() {
        return imageData;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageFormat() {
        return imageFormat;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "SerializableImage{" +
                "imageName='" + imageName + '\'' +
                ", imageFormat='" + imageFormat + '\'' +
                ", imageSize=" + imageData.length + " bytes" +
                ", timestamp=" + timestamp +
                '}';
    }

    public static byte[] convertImageToBytes(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, format, baos);
        return baos.toByteArray();
    }

    public static BufferedImage convertBytesToImage(byte[] data) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        return ImageIO.read(bais);
    }
}
