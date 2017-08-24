package com.scorpio.framework.net.http.interfaces;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class HttpFilePostUploadEntity extends AbstractHttpPostUploadEntity {

	private File mFile;

	public HttpFilePostUploadEntity(File f, String paramName, String postFileName, String contentType) {
		super(paramName, postFileName, contentType);
		mFile = f;
		if ((!mFile.exists()) || !(mFile.isFile())) {
			throw new RuntimeException("Tried to create an EzHttpFilePostUploadEntity from a file that does not exist");
		}
	}

	@Override
	public InputStream getInputStream() {
		try {
			return new FileInputStream(mFile);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public long getSize() {
		return mFile.length();
	}

}
