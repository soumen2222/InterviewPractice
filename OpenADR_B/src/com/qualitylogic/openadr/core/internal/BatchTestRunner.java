package com.qualitylogic.openadr.core.internal;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.qualitylogic.openadr.core.base.TestRunner;
import com.qualitylogic.openadr.core.util.FileUtil;

/**
 * Runs self tests in batch mode. Uses TestRunner behind the scenes.
 */
public class BatchTestRunner {

	/**
	 * SOURCE can be a text file or a directory. Examples include:
	 * 
	 *   src/runner/push-event.txt
	 *   src/runner/workarea.txt
	 *   src/testcases/push/opt/
	 *   
	 * The advantage of using a .txt file is you can use that to take notes. Anything after 
	 * first # is treated as a comment. See the src/runner directory for the complete list 
	 * of preconfigured .txt files.
	 */
	// 	private static final String[] SOURCES = {"src/runner/prompt_050.txt"};
	private static final String[] SOURCES = 
		{"src/runner/push-registerparty.txt",
		 "src/runner/push-event.txt",
		 "src/runner/push-general.txt",
		 "src/runner/push-opt.txt",
		 "src/runner/push-report.txt",
		 "src/runner/push-a.txt",
		 "src/runner/pull-registerparty.txt",
		 "src/runner/pull-event.txt",
		 "src/runner/pull-general.txt",
		 "src/runner/pull-opt.txt",
		 "src/runner/pull-report.txt",
		 "src/runner/pull-a.txt"};

	/**
	 * Append #F to failed tests. If you wish to retry the failed tests, set this to true. 
	 */
	private static final boolean FAILED_ONLY = false;
	
	/**
	 * Set to true for full automation.
	 */
	private static final boolean REGRESSION_MODE = false;

	/**
	 * Set to true if you want 
	 */
	public static final boolean PROMPT_050_VEN_YES = true;
	
	public static void main(String[] args) throws IOException {
		for (String source : SOURCES) {
			File sourceFile = new File(FileUtil.getCurrentPath() + "/" + source);
			if (!sourceFile.exists()) {
				System.out.println("Source " + sourceFile + " does not exist.");
			} else if (sourceFile.isDirectory()) {
				String directory = sourceFile.getAbsolutePath();
		        runTestsInDirectory(directory);
			} else if (sourceFile.isFile()) {
				String filename = sourceFile.getAbsolutePath();
		    	runTestsInFile(filename, FAILED_ONLY);
			}
		}
	}

	private static void runTestsInDirectory(String directory) throws IOException {
		Files.walkFileTree(Paths.get(directory), new FileVisitor());
	}

	private static void runTestsInFile(String filename, boolean failedOnly) throws IOException {
		List<String> lines = IOUtils.readLines(new FileReader(filename));
    	for (String line : lines) {
    		if (!line.trim().startsWith("#")) {
    			if (failedOnly) {
    				if (line.endsWith(STATUS_FAIL)) {
    					line = StringUtils.substringBefore(line, "#").trim();
    					executeTestCase(line);
    				}
    			} else {
					line = StringUtils.substringBefore(line, "#").trim();
            		executeTestCase(line);
    			}
    		}
		}
	}

	private static void executeTestCase(String line) {
		System.out.print("Running " + line + ".");
		TestRunner.executeTestCase(line);

		if (REGRESSION_MODE) {
			while (isVtnRunning() || isVenRunning()) {
				sleep(2000);
				System.out.print(".");
			}
			sleep(1000);
			System.out.println();
		} else {
			pressEnter(" Press enter to start the next test.");
		}
	}

	public static class FileVisitor extends SimpleFileVisitor<Path> {
        private final PathMatcher matcher;

        FileVisitor() {
        	String pattern = "*.java";
            matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        }

        private void find(Path file) {
            Path name = file.getFileName();
            
            if (name != null && matcher.matches(name)) {
            	String thFilename = file.toString();
            	
            	if (thFilename.contains("\\selftest_a_ported\\")) {
            		// ignore selftest_a_ported
            	} else {
        			String thClassName = filenameToClassName(thFilename);
        			
        			executeTestCase(thClassName);
            	}
            }
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            find(file);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException e) {
            System.err.println(e);
            return CONTINUE;
        }
    }

	private static String filenameToClassName(String filename) {
		return StringUtils.substringAfter(filename, "src\\").replace(".java", "").replace('\\', '.');
	}

	private static String pressEnter(String prompt) {
		System.out.print(prompt);

		String input = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			input = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return input;
	}

	private static boolean isVtnRunning() {
		return isRunning("./src/runner/vtn.lock");
	}
	
	private static boolean isVenRunning() {
		return isRunning("./src/runner/ven.lock");
	}

	private static boolean isRunning(String lockFilename) {
		boolean running = false;
		try (RandomAccessFile lockFile = new RandomAccessFile(lockFilename, "rw")) {
			FileLock promptLock = lockFile.getChannel().tryLock();
			
			if (promptLock != null) {
				running = false;
				promptLock.release();
			} else {
				running = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return running;
	}
	
	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

    private static final String STATUS_FAIL = "#F";
}
