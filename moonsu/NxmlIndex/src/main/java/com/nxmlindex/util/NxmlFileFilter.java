package com.nxmlindex.util;

import java.io.File;
import java.io.FileFilter;

public class NxmlFileFilter implements FileFilter{

	private final String[] sucessFileExtensions = new String[] {"nxml"};
	
	@Override
	public boolean accept(File file) {
		// TODO Auto-generated method stub
		for (String extension : sucessFileExtensions)
        {
          if (file.getName().toLowerCase().endsWith(extension))
          {
            return true;
          }
        }
        return false;
	}

}
