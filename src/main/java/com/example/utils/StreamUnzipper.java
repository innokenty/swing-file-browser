package com.example.utils;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class StreamUnzipper {

    public static boolean unzip(InputStream inputStream, File outputFolder)
            throws IOException {

        ZipInputStream zis = new ZipInputStream(inputStream);

        ZipEntry entry;
        boolean isEmpty = true;
        while ((entry = zis.getNextEntry()) != null) {
            isEmpty = false;
            File newFile = new File(outputFolder, entry.getName());
            if (newFile.getParentFile().mkdirs() && !entry.isDirectory()) {
                FileOutputStream fos = new FileOutputStream(newFile);
                IOUtils.copy(zis, fos);
                IOUtils.closeQuietly(fos);
            }
        }

        IOUtils.closeQuietly(zis);
        return !isEmpty;
    }

    public static File getRandomTempFolder() throws IOException {
        return Files
                .createTempDirectory(UUID.randomUUID().toString())
                .toFile();
    }
}
