package team.chisel.ctmlib;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;

import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.data.AnimationFrame;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.util.ResourceLocation;

import com.github.bsideup.jabel.Desugar;

public final class TextureSubmapResource implements IResource {

    @Desugar
    public record SubmapLocation(ResourceLocation source, int columns, int rows, int cellX, int cellY) {}

    @Desugar
    private record SubmapCacheKey(ResourceLocation source, int columns, int rows) {}

    @Nullable
    private static HashMap<SubmapCacheKey, byte[][]> imageCache;

    private static final String PREFIX = "__chisel_submap/";

    private final IResource resource;
    private final SubmapLocation submap;
    private final boolean cacheable;
    private byte[] imageData;

    public TextureSubmapResource(IResource resource, SubmapLocation submap, boolean cacheable) {
        this.resource = resource;
        this.submap = submap;
        this.cacheable = cacheable;
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

    public static void beginTextureStitch() {
        imageCache = new HashMap<>();
    }

    public static void endTextureStitch() {
        imageCache = null;
    }

    @Override
    public InputStream getInputStream() {
        if (imageData == null) {
            try {
                imageData = getCachedImageData(resource, submap);
            } catch (IOException e) {
                throw new RuntimeException("Failed to generate submap resource " + submap.source(), e);
            }
        }

        return new ByteArrayInputStream(imageData);
    }

    private byte[] getCachedImageData(IResource resource, SubmapLocation submap) throws IOException {
        if (!cacheable || imageCache == null) {
            return generateCell(resource, submap);
        }

        SubmapCacheKey key = new SubmapCacheKey(submap.source(), submap.columns(), submap.rows());
        byte[][] cells = imageCache.get(key);
        if (cells == null) {
            cells = generateSubmap(resource, submap);
            imageCache.put(key, cells);
        }

        return cells[submap.cellX() * submap.rows() + submap.cellY()];
    }

    private static byte[][] generateSubmap(IResource resource, SubmapLocation submap) throws IOException {
        BufferedImage source = read(resource, submap.source());
        byte[][] cells = new byte[submap.columns() * submap.rows()][];

        for (int x = 0; x < submap.columns(); x++) {
            for (int y = 0; y < submap.rows(); y++) {
                BufferedImage cropped = crop(source, submap.columns(), submap.rows(), x, y);
                cells[x * submap.rows() + y] = encode(cropped);
            }
        }

        return cells;
    }

    private static byte[] generateCell(IResource resource, SubmapLocation submap) throws IOException {
        BufferedImage source = read(resource, submap.source());
        BufferedImage cropped = crop(source, submap.columns(), submap.rows(), submap.cellX(), submap.cellY());
        return encode(cropped);
    }

    private static BufferedImage read(IResource resource, ResourceLocation location) throws IOException {
        BufferedImage image;
        try (InputStream stream = resource.getInputStream()) {
            image = ImageIO.read(stream);
        }
        if (image == null) {
            throw new IOException("Unable to decode " + location);
        }
        return image;
    }

    private static byte[] encode(BufferedImage image) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("png");
        ImageWriter writer = writers.next();

        ImageWriteParam param = writer.getDefaultWriteParam();
        if (param.canWriteCompressed()) {
            // disable compression so we don't spend time on compressing and decompressing to just get the image bytes
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(1.0f);
        }

        try (ImageOutputStream imageOutput = new MemoryCacheImageOutputStream(output)) {
            writer.setOutput(imageOutput);
            writer.write(null, new IIOImage(image, null, null), param);
        } finally {
            writer.dispose();
        }

        return output.toByteArray();
    }

    private static BufferedImage crop(BufferedImage source, int columns, int rows, int cellX, int cellY)
        throws IOException {

        int frameSize = source.getWidth();
        int sourceHeight = source.getHeight();

        if (frameSize < columns || frameSize < rows
            || frameSize % columns != 0
            || frameSize % rows != 0
            || sourceHeight % frameSize != 0) {

            throw new IOException(
                "Texture size " + frameSize
                    + "x"
                    + sourceHeight
                    + " cannot be split into submap "
                    + columns
                    + "x"
                    + rows);
        }

        int cellWidth = frameSize / columns;
        int cellHeight = frameSize / rows;
        int frameCount = sourceHeight / frameSize;

        BufferedImage result = new BufferedImage(cellWidth, cellHeight * frameCount, BufferedImage.TYPE_INT_ARGB);
        int[] pixels = new int[cellWidth * cellHeight];

        for (int frame = 0; frame < frameCount; frame++) {
            int sourceX = cellX * cellWidth;
            int sourceY = frame * frameSize + cellY * cellHeight;

            source.getRGB(sourceX, sourceY, cellWidth, cellHeight, pixels, 0, cellWidth);
            result.setRGB(0, frame * cellHeight, cellWidth, cellHeight, pixels, 0, cellWidth);
        }

        return result;
    }

    @Override
    public boolean hasMetadata() {
        return resource.hasMetadata();
    }

    @Override
    public IMetadataSection getMetadata(String sectionName) {
        IMetadataSection metadata = resource.getMetadata(sectionName);
        if (!(metadata instanceof AnimationMetadataSection animation)) {
            return metadata;
        }
        if (animation.getFrameWidth() < 0 && animation.getFrameHeight() < 0) {
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

        int frameWidth = scaleMetadataDimension(animation.getFrameWidth(), submap.columns());
        int frameHeight = scaleMetadataDimension(animation.getFrameHeight(), submap.rows());
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
