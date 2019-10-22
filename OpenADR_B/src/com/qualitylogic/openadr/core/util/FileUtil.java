package com.qualitylogic.openadr.core.util;

import java.io.File;
import java.io.IOException;

public final class FileUtil {
	public static String getCurrentPath() {
		String currentPath = null;
		File currentDir = new File(".");
		try {
			currentPath = currentDir.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return currentPath;
	}
	
	private FileUtil() {
	}
}
