package team.chisel.ctmlib;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.data.AnimationFrame;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.util.ResourceLocation;

import com.github.bsideup.jabel.Desugar;

public final class TextureSubmapResource implements IResource {

    @Desugar
    public record SubmapLocation(ResourceLocation source, int columns, int rows, int cellX, int cellY) {}

    private static final String PREFIX = "__chisel_submap/";

    private final IResource source;
    private final byte[] imageData;
    private final int columns;
    private final int rows;

    private TextureSubmapResource(IResource source, byte[] imageData, int columns, int rows) {
        this.source = source;
        this.imageData = imageData;
        this.columns = columns;
        this.rows = rows;
    }

    public static String getSubIconName(ResourceLocation source, int columns, int rows, int cellX, int cellY) {
        return source.getResourceDomain() + ":"
            + PREFIX
            + columns
            + "x"
            + rows
            + "/"
            + cellX
            + "_"
            + cellY
            + "/"
            + source.getResourcePath();
    }

    public static IResource create(ResourceLocation location, IResource source, SubmapLocation submap)
        throws IOException {
        BufferedImage sourceImage;
        try (InputStream stream = source.getInputStream()) {
            sourceImage = ImageIO.read(stream);
        }
        if (sourceImage == null) {
            throw new IOException("Unable to decode " + submap.source);
        }

        BufferedImage cropped = crop(sourceImage, submap);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        if (!ImageIO.write(cropped, "png", output)) {
            throw new IOException("Unable to encode submap resource " + location);
        }

        return new TextureSubmapResource(source, output.toByteArray(), submap.columns, submap.rows);
    }

    private static BufferedImage crop(BufferedImage source, SubmapLocation submap) throws IOException {
        int frameSize = source.getWidth();
        int sourceHeight = source.getHeight();

        if (frameSize < submap.columns || frameSize < submap.rows
            || frameSize % submap.columns != 0
            || frameSize % submap.rows != 0
            || sourceHeight % frameSize != 0) {

            throw new IOException(
                "Texture size " + frameSize
                    + "x"
                    + sourceHeight
                    + " cannot be split into submap "
                    + submap.columns
                    + "x"
                    + submap.rows);
        }

        int cellWidth = frameSize / submap.columns;
        int cellHeight = frameSize / submap.rows;
        int frameCount = sourceHeight / frameSize;

        BufferedImage result = new BufferedImage(cellWidth, cellHeight * frameCount, BufferedImage.TYPE_INT_ARGB);
        int[] pixels = new int[cellWidth * cellHeight];

        for (int frame = 0; frame < frameCount; frame++) {
            int sourceX = submap.cellX * cellWidth;
            int sourceY = frame * frameSize + submap.cellY * cellHeight;

            source.getRGB(sourceX, sourceY, cellWidth, cellHeight, pixels, 0, cellWidth);
            result.setRGB(0, frame * cellHeight, cellWidth, cellHeight, pixels, 0, cellWidth);
        }

        return result;
    }

    public static SubmapLocation parse(ResourceLocation location) {
        String path = location.getResourcePath();

        int prefix = path.indexOf(PREFIX);
        if (prefix < 0 || prefix > 0 && path.charAt(prefix - 1) != '/') return null;

        int gridStart = prefix + PREFIX.length();
        int gridEnd = path.indexOf('/', gridStart);
        if (gridEnd < 0) return null;

        int cellEnd = path.indexOf('/', gridEnd + 1);
        if (cellEnd < 0) return null;

        String grid = path.substring(gridStart, gridEnd);
        String cell = path.substring(gridEnd + 1, cellEnd);

        int gridSeparator = grid.indexOf('x');
        int cellSeparator = cell.indexOf('_');
        if (gridSeparator <= 0 || cellSeparator <= 0) return null;

        try {
            int columns = Integer.parseInt(grid.substring(0, gridSeparator));
            int rows = Integer.parseInt(grid.substring(gridSeparator + 1));
            int cellX = Integer.parseInt(cell.substring(0, cellSeparator));
            int cellY = Integer.parseInt(cell.substring(cellSeparator + 1));

            if (cellX < 0 || cellY < 0 || cellX >= columns || cellY >= rows) {
                return null;
            }

            String sourcePath = path.substring(0, prefix) + path.substring(cellEnd + 1);
            ResourceLocation sourceLocation = new ResourceLocation(location.getResourceDomain(), sourcePath);
            return new SubmapLocation(sourceLocation, columns, rows, cellX, cellY);
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(imageData);
    }

    @Override
    public boolean hasMetadata() {
        return source.hasMetadata();
    }

    @Override
    public IMetadataSection getMetadata(String sectionName) {
        IMetadataSection metadata = source.getMetadata(sectionName);
        if (!(metadata instanceof AnimationMetadataSection animation)) {
            return metadata;
        }

        List<AnimationFrame> frames = new ArrayList<>(animation.getFrameCount());
        for (int i = 0; i < animation.getFrameCount(); i++) {
            if (animation.frameHasTime(i)) {
                frames.add(new AnimationFrame(animation.getFrameIndex(i), animation.getFrameTimeSingle(i)));
            } else {
                frames.add(new AnimationFrame(animation.getFrameIndex(i)));
            }
        }

        int frameWidth = scaleMetadataDimension(animation.getFrameWidth(), columns);
        int frameHeight = scaleMetadataDimension(animation.getFrameHeight(), rows);
        return new AnimationMetadataSection(frames, frameWidth, frameHeight, animation.getFrameTime());
    }

    private static int scaleMetadataDimension(int dimension, int divisions) {
        if (dimension < 0) {
            return dimension;
        }
        if (dimension % divisions != 0) {
            throw new IllegalArgumentException(
                "Animation frame size " + dimension + " is not divisible by submap size " + divisions);
        }
        return dimension / divisions;
    }
}
