package br.com.facilit.eclipse.ext.rclass.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

public class ResourceUtil {

	public static String getResourceCheckSum(List<IFile> iFileList) throws NoSuchAlgorithmException, IOException,
			CoreException {

		MessageDigest md = MessageDigest.getInstance("MD5");

		for (IFile iFile : iFileList) {

			try (InputStream is = iFile.getContents()) {

				DigestInputStream dis = new DigestInputStream(is, md);

				while (dis.read() > -1)
					;
			}
		}

		StringBuffer sb = new StringBuffer();

		for (byte b : md.digest()) {

			sb.append(String.format("%02x", b & 0xff));
		}

		return sb.toString();
	}
}
