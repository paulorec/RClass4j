package rclass.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import rclass.models.LanguageEntry;
import rclass.models.LanguageEntryImpl;
import rclass.models.ParserException;

public class LanguageEntryProcessorImpl implements LanguageEntryProcessor {

	@Override
	public List<LanguageEntry> getEntryLanguageEntry(List<IFile> fileGroup) {

		List<LanguageEntry> listLanguageEntry = new ArrayList<LanguageEntry>();

		for (IFile iFile : fileGroup) {

			listLanguageEntry.addAll(getEntryLanguageEntry(iFile));
		}

		return listLanguageEntry;
	}

	public List<LanguageEntry> getEntryLanguageEntry(IFile iFile) {

		List<LanguageEntry> listLanguageEntry = new ArrayList<LanguageEntry>();

		try {

			BufferedReader fileInputStream = new BufferedReader(new InputStreamReader(iFile.getContents()));

			for (String line; (line = fileInputStream.readLine()) != null;) {

				if (!line.contains(LINE_TOKEN))
					continue;

				String prefix = line.substring(0, line.indexOf(LINE_TOKEN));

				if (prefix.trim().equals(""))
					continue;

				String sufix = line.substring(line.indexOf(LINE_TOKEN) + 1);

				try {

					listLanguageEntry.add(createLanguageEntry(iFile, prefix, sufix));

				} catch (ParserException e) {

					System.out.println(e);
				}
			}
		} catch (IOException e) {

			e.printStackTrace();

		} catch (CoreException e1) {

			e1.printStackTrace();
		}

		return listLanguageEntry;

	}

	private LanguageEntry createLanguageEntry(IFile iFile, String nativeKey, String value) throws ParserException {

		return createLanguageEntry(nativeKey, getEncodedKey(nativeKey), getJavaKey(nativeKey), iFile.getName(),
				getValue(value));
	}

	private LanguageEntry createLanguageEntry(String nativeKey, String encodedKey, String javaKey, String scope,
			String value) {

		LanguageEntryImpl languageEntry = new LanguageEntryImpl();

		languageEntry.setNativeKey(nativeKey);
		languageEntry.setEncodedKey(encodedKey);
		languageEntry.setJavaKey(javaKey);
		languageEntry.addScopedValue(scope, value);

		return languageEntry;
	}

	private String getValue(String rawValue) throws ParserException {

		rawValue = rawValue.trim();

		return StringUtil.unicodeEscaped(rawValue);
	}

	private String getEncodedKey(String nativeKey) throws ParserException {

		try {
			String javaKey = getJavaKey(nativeKey.trim());
			
			MessageDigest cript = MessageDigest.getInstance("SHA-1");

			cript.reset();

			cript.update(javaKey.getBytes("UTF-8"));

			ByteBuffer byteBuffer = ByteBuffer.wrap(cript.digest());

			int v = (byteBuffer.get() & 0xff);
			  v |= (byteBuffer.get() & 0xff) << 8;
			  v |= byteBuffer.get() << 16;
			  
			return Double.toHexString(v);

		} catch (NoSuchAlgorithmException e) {

			throw new ParserException(e);

		} catch (UnsupportedEncodingException e) {

			throw new ParserException(e);
		}

	}

	private String getJavaKey(String nativeKey) {

		nativeKey = nativeKey.trim();

		return StringUtil.getJavaSafeKey(nativeKey);

	}

	private String LINE_TOKEN = "=";

}
