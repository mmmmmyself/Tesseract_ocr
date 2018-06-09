package Util;

import java.io.File;

public class CreateFileUtil {
	public static void createFile(String Savepath)
	{
	    new File(Savepath).mkdirs();
	}

}
