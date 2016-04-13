package com.XmlFind.Virtue.InShakespeare;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class Tragic {

	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner input = new Scanner(System.in);
		final Logger log = Logger.getLogger(Tragic.class);

		try {
			System.out.println("Enter Directory XML files are located in: ");
			String fileLocation = input.next();
			File f = new File(fileLocation);
			ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
			for (int i = 0; i < files.size(); i++) {
				FindWord word = new FindWord(files.get(i));
			}
		} catch (NullPointerException e) {
			log.error(e);
		}
	}
}
