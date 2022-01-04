package maze.gui;


import java.io.File;
import java.io.FilenameFilter;
import java.util.*;

@SuppressWarnings("rawtypes")
public class AIReflector2<T> {
	private final static String suffix = ".class";
	
	private Map<String,Class> name2type;
	
	private FilenameFilter filter = new FilenameFilter(){public boolean accept(File dir, String name) {
			return name.endsWith(suffix);
		}};
		
	public AIReflector2(Class superType, String packageName) {
		this.name2type = new TreeMap<String,Class>();
		
		String targetDirName = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		String pathname = targetDirName + packageName.replace('.', File.separatorChar);
		pathname = pathname.replace("%20", " ");
		File targetDir = new File(pathname);
		if (!targetDir.isDirectory()) {throw new IllegalArgumentException(targetDir + " is not a directory");}
		for (File f: targetDir.listFiles(filter)) {
			String name = f.getName();
			name = name.substring(0, name.length() - suffix.length());
			try {
				Class type = Class.forName(packageName + "." + name);
				Object obj = type.newInstance();
				if (superType.isInstance(obj)) {
					name2type.put(name, type);
				}
				// If an exception is thrown, we omit the type.
				// Hence, ignore all three exceptions.
			} catch (ClassNotFoundException e) {
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			}
		}
	}
	
	public ArrayList<String> getTypeNames() {
		return new ArrayList<String>(name2type.keySet());
	}
	
	public String toString() {
		String result = "Available:";
		for (String s: name2type.keySet()) {
			result += " " + s;
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public T newInstanceOf(String typeName) throws InstantiationException, IllegalAccessException {
		return (T)name2type.get(typeName).newInstance();
	}
}
