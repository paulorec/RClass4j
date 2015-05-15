package br.com.facilit.eclipse.ext.rclass.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.IPackageFragment;

import br.com.facilit.eclipse.ext.rclass.service.models.LanguageEntry;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class ClassWriterUtil {

	private static final String TEMPLATE_FOLDER = "/br/com/facilit/eclipse/ext/rclass/content";
	private static final String R_CLASS_TEMPLATE_FILE = "rclass.tpl";
	private static final String OUTPUT_LANG_TEMPLATE_FILE = "outputlclass.tpl";

	private static Configuration freeMarkerConfiguration;

	private Configuration getFreemarkerConfiguration() {

		if (freeMarkerConfiguration != null) {

			return freeMarkerConfiguration;
		}
		freeMarkerConfiguration = new Configuration(Configuration.VERSION_2_3_21);

		freeMarkerConfiguration.setClassForTemplateLoading(this.getClass(), TEMPLATE_FOLDER);
		freeMarkerConfiguration.setTemplateLoader(new ClassTemplateLoader(this.getClass(), TEMPLATE_FOLDER));

		freeMarkerConfiguration.setDefaultEncoding("UTF-8");
		freeMarkerConfiguration.setTemplateExceptionHandler(TemplateExceptionHandler.DEBUG_HANDLER);

		return freeMarkerConfiguration;
	}

	private ClassWriterUtil() {

	}

	private void _writeRClass(File outputFile, IPackageFragment packageFragment, Set<LanguageEntry> languageEntryList) {

		try {

			getFreemarkerConfiguration().getTemplateLoader();

			Template template = getFreemarkerConfiguration().getTemplate(R_CLASS_TEMPLATE_FILE);

			Map<String, Object> context = new HashMap<>();

			context.put("languageEntryList", languageEntryList);
			context.put("packageName", packageFragment.getElementName());
			
			template.process(context, new OutputStreamWriter(new FileOutputStream(outputFile)));

		} catch (IOException e) {

			e.printStackTrace();

		} catch (TemplateException e) {

			e.printStackTrace();
		}
	}

	private void _writeOutPutLanguageFile(File outputFile, List<LanguageEntry> languageEntryList) {

		try {

			getFreemarkerConfiguration().getTemplateLoader();

			Template template = getFreemarkerConfiguration().getTemplate(OUTPUT_LANG_TEMPLATE_FILE);

			Map<String, Object> context = new HashMap<>();

			context.put("languageEntryList", languageEntryList);

			template.process(context, new OutputStreamWriter(new FileOutputStream(outputFile)));

		} catch (IOException e) {

			e.printStackTrace();

		} catch (TemplateException e) {

			e.printStackTrace();
		}
	}

	public static void writeRClass(File outputFile, IPackageFragment packageFragment,
			Set<LanguageEntry> languageEntryList) {

		_instance._writeRClass(outputFile, packageFragment, languageEntryList);
	}

	public static void writeOutPutLanguageFile(List<LanguageEntry> languageEntryList, File outputFile) {

		_instance._writeOutPutLanguageFile(outputFile, languageEntryList);
	}

	private static ClassWriterUtil _instance = new ClassWriterUtil();

}
