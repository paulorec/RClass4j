package rclass.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.NoSuchFileException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class ClassWriterUtil {

	private static final String TEMPLATE_FOLDER = "/rclass/content/";
	private static final String TEMPLATE_FILE = "rclass.tpl";

	private static Configuration freeMarkerConfiguration;

	private File getContentFolder() throws NoSuchFileException {

		URL url = getClass().getClassLoader().getResource(TEMPLATE_FOLDER);

		if (url == null)
			throw new NoSuchFileException(TEMPLATE_FOLDER);

		try {

			return new File(url.toURI());

		} catch (URISyntaxException e) {

			throw new NoSuchFileException(url.toString());
		}
	}

	private Configuration getFreemarkerConfiguration() {

		if (freeMarkerConfiguration != null) {

			return freeMarkerConfiguration;
		}
		freeMarkerConfiguration = new Configuration(
				Configuration.VERSION_2_3_21);
		try {

			freeMarkerConfiguration
					.setDirectoryForTemplateLoading(getContentFolder());

			freeMarkerConfiguration.setDefaultEncoding("UTF-8");
			freeMarkerConfiguration
					.setTemplateExceptionHandler(TemplateExceptionHandler.DEBUG_HANDLER);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return freeMarkerConfiguration;
	}

	private ClassWriterUtil() {

	}

	private void _writeClass(Set<String> keys, File file) {

		try {

			Template template = getFreemarkerConfiguration().getTemplate(
					TEMPLATE_FILE);

			Map<String, Object> context = new HashMap<>();

			context.put("keys", keys);

			template.process(context, new OutputStreamWriter(
					new FileOutputStream(file)));

		} catch (IOException e) {

			e.printStackTrace();

		} catch (TemplateException e) {

			e.printStackTrace();
		}
	}

	public static void writeClass(Set<String> keys, File file) {

		_instance._writeClass(keys, file);
	}

	private static ClassWriterUtil _instance = new ClassWriterUtil();

}
