package net.minecraft.client.particle.chroma.utils;

import com.google.gson.annotations.*;
import java.lang.annotation.*;
import java.lang.reflect.*;
import com.google.gson.*;
import java.util.jar.*;
import java.net.*;
import java.util.*;
import java.io.*;
import org.apache.logging.log4j.*;

public class FileUtils
{
    public static void saveObject(final String name, final Object object, final File dir) throws Exception {
        try {
            final File file = new File(dir, name + ".json");
            final FileWriter writer = new FileWriter(file);
            final Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
            writer.write(gson.toJson(object));
            writer.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void loadObject(final String name, final Object object, final File dir) throws Exception {
        final File modFile = new File(dir, name + ".json");
        checkDirectory(dir);
        if (!modFile.exists()) {
            saveObject(name, object, dir);
            return;
        }
        final BufferedReader reader = new BufferedReader(new FileReader(modFile));
        String fields = "";
        String line;
        while ((line = reader.readLine()) != null) {
            fields = fields + "\n" + line;
        }
        reader.close();
        final JsonObject jText = new JsonParser().parse(fields).getAsJsonObject();
        for (final Field field : object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent((Class<? extends Annotation>)Expose.class)) {
                field.setAccessible(true);
                final JsonElement jsonElement = jText.get(field.getName());
                if (jsonElement != null) {
                    final Object obj = field.get(object);
                    final Field old = field;
                    field.set(object, new Gson().fromJson(jsonElement, (Class)field.getType()));
                    if (obj instanceof String[][] && field.get(object) instanceof String[][]) {
                        final String[][] s = (String[][])field.get(object);
                        final String[][] ss = (String[][])obj;
                        try {
                            final int version = Integer.parseInt(s[s.length - 1][0]);
                            final int newVersion = Integer.parseInt(ss[ss.length - 1][0]);
                            if (newVersion > version) {
                                field.set(object, obj);
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            field.set(object, obj);
                        }
                        saveObject(name, object, dir);
                    }
                    else if (obj instanceof String[] && field.get(object) instanceof String[]) {
                        final String[] s2 = (String[])field.get(object);
                        final String[] ss2 = (String[])obj;
                        try {
                            final int version = Integer.parseInt(s2[s2.length - 1]);
                            final int newVersion = Integer.parseInt(ss2[ss2.length - 1]);
                            if (newVersion > version) {
                                field.set(object, obj);
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            field.set(object, obj);
                        }
                        saveObject(name, object, dir);
                    }
                }
            }
        }
        for (final Field field : object.getClass().getSuperclass().getDeclaredFields()) {
            if (field.isAnnotationPresent((Class<? extends Annotation>)Expose.class)) {
                field.setAccessible(true);
                final JsonElement jsonElement = jText.get(field.getName());
                if (jsonElement != null) {
                    field.set(object, new Gson().fromJson(jsonElement, (Class)field.getType()));
                }
            }
        }
    }
    
    public static List<String> readFileLines(final File file) throws Exception {
        final List<String> lines = new ArrayList<String>();
        final BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }
    
    public static List<String> readUrlResponse(final URL url) {
        try {
            final List<String> lines = new ArrayList<String>();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            for (final String s : reader.readLine().split(":")) {
                lines.add(s);
            }
            reader.close();
            return lines;
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static List<String> readUrlResponseFull(final URL url) {
        try {
            final List<String> lines = new ArrayList<String>();
            final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (!inputLine.isEmpty()) {
                    lines.add(inputLine);
                }
            }
            in.close();
            return lines;
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static List<String> readUrlResponseFull2(final URL url) {
        try {
            final List<String> lines = new ArrayList<String>();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            int count = 1;
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                if (!inputLine.isEmpty()) {
                    String line = inputLine.replaceAll("<br />", "").replaceAll("<br>", "");
                    line = (((int)reader.lines().count() == count) ? line.substring(0, line.length() - 2) : line);
                    lines.add(line);
                }
                ++count;
            }
            reader.close();
            return lines;
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static void printFile(final File dir, final List list) throws Exception {
        final PrintWriter writer = new PrintWriter(dir);
        for (final Object o : list) {
            final String string = o.toString();
            writer.println(string);
        }
        writer.close();
    }
    
    public static void printFile(final File dir, final String... list) throws Exception {
        final PrintWriter writer = new PrintWriter(dir);
        for (final Object o : list) {
            final String string = o.toString();
            writer.println(string);
        }
        writer.close();
    }
    
    public static void checkDirectory(final File dir) {
        if (!dir.exists()) {
            dir.mkdir();
        }
    }
    
    public static ArrayList<String> getClassNamesFromPackage(String packageName) throws Exception {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final ArrayList<String> names = new ArrayList<String>();
        packageName = packageName.replace(".", "/");
        final URL packageURL = classLoader.getResource(packageName);
        if (packageURL.getProtocol().equals("jar")) {
            String jarFileName = URLDecoder.decode(packageURL.getFile(), "UTF-8");
            jarFileName = jarFileName.substring(5, jarFileName.indexOf("!"));
            System.out.println(">" + jarFileName);
            @SuppressWarnings("resource")
			final JarFile jf = new JarFile(jarFileName);
            final Enumeration<JarEntry> jarEntries = jf.entries();
            while (jarEntries.hasMoreElements()) {
                String entryName = jarEntries.nextElement().getName();
                if (entryName.startsWith(packageName) && entryName.length() > packageName.length() + 5) {
                    entryName = entryName.substring(packageName.length(), entryName.lastIndexOf(46));
                    names.add(entryName);
                }
            }
        }
        else {
            final URI uri = new URI(packageURL.toString());
            final File folder = new File(uri.getPath());
            final File[] listFiles;
            final File[] contenent = listFiles = folder.listFiles();
            for (final File actual : listFiles) {
                String entryName = actual.getName();
                entryName = entryName.substring(0, entryName.lastIndexOf(46));
                names.add(entryName);
            }
        }
        return names;
    }
    
    public static List makeList(final Iterable iter) {
        final List list = new ArrayList();
        for (final Object item : iter) {
            list.add(item);
        }
        return list;
    }
    
    public static File getWorkingDirectory() {
        final String userHome = System.getProperty("user.home", ".");
        File workingDirectory = null;
        switch (OperatingSystem.getCurrentPlatform()) {
            case LINUX: {
                workingDirectory = new File(userHome, ".minecraft/");
                break;
            }
            case WINDOWS: {
                final String applicationData = System.getenv("APPDATA");
                final String folder = (applicationData != null) ? applicationData : userHome;
                workingDirectory = new File(folder, ".minecraft/");
                break;
            }
            case OSX: {
                workingDirectory = new File(userHome, "Library/Application Support/minecraft");
                break;
            }
            default: {
                workingDirectory = new File(userHome, "minecraft/");
                break;
            }
        }
        return workingDirectory;
    }
    
    public enum OperatingSystem
    {
        LINUX("linux", new String[] { "linux", "unix" }), 
        WINDOWS("windows", new String[] { "win" }), 
        OSX("osx", new String[] { "mac" }), 
        UNKNOWN("unknown", new String[0]);
        
        private static final Logger LOGGER;
        private final String name;
        private final String[] aliases;
        
        private OperatingSystem(final String name, final String[] aliases) {
            this.name = name;
            this.aliases = ((aliases == null) ? new String[0] : aliases);
        }
        
        public String getName() {
            return this.name;
        }
        
        public String[] getAliases() {
            return this.aliases;
        }
        
        public boolean isSupported() {
            return this != OperatingSystem.UNKNOWN;
        }
        
        public String getJavaDir() {
            final String separator = System.getProperty("file.separator");
            final String path = System.getProperty("java.home") + separator + "bin" + separator;
            if (getCurrentPlatform() == OperatingSystem.WINDOWS && new File(path + "javaw.exe").isFile()) {
                return path + "javaw.exe";
            }
            return path + "java";
        }
        
        public static OperatingSystem getCurrentPlatform() {
            final String osName = System.getProperty("os.name").toLowerCase();
            for (final OperatingSystem os : values()) {
                for (final String alias : os.getAliases()) {
                    if (osName.contains(alias)) {
                        return os;
                    }
                }
            }
            return OperatingSystem.UNKNOWN;
        }
        
        public static void openLink(final URI link) {
            try {
                final Class<?> desktopClass = Class.forName("java.awt.Desktop");
                final Object o = desktopClass.getMethod("getDesktop", (Class<?>[])new Class[0]).invoke(null, new Object[0]);
                desktopClass.getMethod("browse", URI.class).invoke(o, link);
            }
            catch (Throwable e2) {
                if (getCurrentPlatform() == OperatingSystem.OSX) {
                    try {
                        Runtime.getRuntime().exec(new String[] { "/usr/bin/open", link.toString() });
                    }
                    catch (IOException e1) {
                        OperatingSystem.LOGGER.error("Failed to open link " + link.toString(), (Throwable)e1);
                    }
                }
                else {
                    OperatingSystem.LOGGER.error("Failed to open link " + link.toString(), e2);
                }
            }
        }
        
        public static void openFolder(final File path) {
            final String absolutePath = path.getAbsolutePath();
            final OperatingSystem os = getCurrentPlatform();
            Label_0140: {
                if (os == OperatingSystem.OSX) {
                    try {
                        Runtime.getRuntime().exec(new String[] { "/usr/bin/open", absolutePath });
                        return;
                    }
                    catch (IOException e) {
                        OperatingSystem.LOGGER.error("Couldn't open " + path + " through /usr/bin/open", (Throwable)e);
                        break Label_0140;
                    }
                }
                if (os == OperatingSystem.WINDOWS) {
                    final String cmd = String.format("cmd.exe /C start \"Open file\" \"%s\"", absolutePath);
                    try {
                        Runtime.getRuntime().exec(cmd);
                        return;
                    }
                    catch (IOException e2) {
                        OperatingSystem.LOGGER.error("Couldn't open " + path + " through cmd.exe", (Throwable)e2);
                    }
                }
                try {
                    final Class<?> desktopClass = Class.forName("java.awt.Desktop");
                    final Object desktop = desktopClass.getMethod("getDesktop", (Class<?>[])new Class[0]).invoke(null, new Object[0]);
                    desktopClass.getMethod("browse", URI.class).invoke(desktop, path.toURI());
                }
                catch (Throwable e3) {
                    OperatingSystem.LOGGER.error("Couldn't open " + path + " through Desktop.browse()", e3);
                }
            }
        }
        
        static {
            LOGGER = LogManager.getLogger();
        }
    }
}