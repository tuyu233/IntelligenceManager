package util;

public class ClassUtil {
	public static String classToName(Class<?> c){
		String packages[] = c.getName().split("\\.");
		String className = packages[packages.length-1];
		return className;
	}
}
