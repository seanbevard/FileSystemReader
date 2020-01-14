package fileSystemReader;

import java.util.Scanner;
import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class FileSystemReader {

	private static int totalDirectories;
	private static int totalFiles;
	private static int totalFileSize;

	public static void main(String[] args) throws Exception {
		int totals = 0;
		//Ask user for directory to scan and save to a variable
		System.out.println("Please enter a directory to scan:");
		Scanner userInputScan = new Scanner(System.in);
		String userInput = userInputScan.nextLine();
		
		Path currentPath = Paths.get(userInput);
		System.out.println();
		System.out.println("******Structure of " + userInput + " ******");
		
		//call logic
		listDirectory(currentPath, 0);
		
		System.out.println();
		System.out.println("------Stats------");
		System.out.println("Folder Count: " + totalDirectories);
		System.out.println("File Count: " + totalFiles);
		System.out.println("Total File Size: " + totalFileSize + "kb");
	}
	
	public static void listDirectory(Path path,int level) throws Exception{
		
		BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
		
		//If current path is a folder, go into it by calling back this method
		//otherwise, it's a file, just output it
		if(attributes.isDirectory()) {
			String[] args;
			//increment total
			FileSystemReader.totalDirectories++;
			
			DirectoryStream<Path> paths = Files.newDirectoryStream(path);
			System.out.println(formatTree(level) + "++" + path.getFileName());
			for(Path thisPath: paths) {
				listDirectory(thisPath, level + 1);
			}
		} else {
			//increment file count
			FileSystemReader.totalFiles++;
			
			//create file to get size
			File file = path.toFile();
			//convert to kb
			long fileSizeInKB = file.length() / 1024;
			System.out.println(formatTree(level) + "--" + path.getFileName() + " | " + fileSizeInKB + "kb");
			//increment total file size
			FileSystemReader.totalFileSize += fileSizeInKB;
		}
	}
	
	public static String formatTree(int level) {
		//format levels with spaces
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < level; i++) {
			builder.append("     ");
		}
		return builder.toString();
	}

}
