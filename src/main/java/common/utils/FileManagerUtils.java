package common.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileManagerUtils {

    public static List<String> getFolderPathFiles(String pathToFolder) {
        try (Stream<Path> paths = Files.walk(Paths.get(pathToFolder))) {
            return paths.filter(Files::isRegularFile).map(String::valueOf)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new IllegalStateException(("Não foi possível localizar a pasta no caminho" + pathToFolder));
        }
    }

    public static String getFileName(String fullPath) {
        return FilenameUtils.getBaseName(fullPath);
    }

    public File tempFeatureCreate(String newLoc, String jarLoc, String[] fileNames) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File newFile = null;
        for (String fileName : fileNames) {
            URL resource = classLoader.getResource(jarLoc + fileName);
            File f = new File(newLoc);
            if (!f.exists()) {
                f.mkdirs();
            }
            newFile = new File(newLoc + File.separator + fileName);
            if (!newFile.exists()) {
                newFile.createNewFile();
                FileUtils.copyURLToFile(resource, newFile);
            }
        }
        return newFile;
    }


    public File tempFileCreate(String newLoc, String jarLoc, String fileName) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(jarLoc + fileName);
        File f = new File(newLoc);
        if (!f.exists()) {
            f.mkdirs();
        }
        File newFile = new File(newLoc + File.separator + fileName);
        if (!newFile.exists()) {
            newFile.createNewFile();
            FileUtils.copyURLToFile(resource, newFile);
        }
        return newFile;
    }

    public void tempDirectoryDelete(String path) {
        try {
            FileUtils.forceDelete(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String execFeaturesFolder(String newLoc, String testFolder) throws IOException {
        File tempFolder = new File(newLoc);
        if (!tempFolder.exists())
            tempFolder.mkdirs();

        if ("LO" .equals(System.getProperty("environment.name"))) {
            FileUtils.copyDirectory(new File(ClassLoader.getSystemClassLoader().getResource(testFolder).getPath()), tempFolder);
        } else {
            String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            String decodedPath = URLDecoder.decode(path, "UTF-8");
            JarFile jarFile = new JarFile(decodedPath);
            try {
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.getName().contains(testFolder) && entry.getName().endsWith(".features")) {
                        InputStream featureInputStream = jarFile.getInputStream(entry);
                        FileUtils.copyInputStreamToFile(featureInputStream,
                                new File(tempFolder.getAbsolutePath() + File.separator + entry.getName().replaceAll(testFolder, "")));
                    }
                }
            } catch (Exception e) {
                throw new IOException(e);
            } finally {
                jarFile.close();
            }
        }

        return newLoc;
    }
}

