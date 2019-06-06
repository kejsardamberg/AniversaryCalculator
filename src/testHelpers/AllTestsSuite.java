package testHelpers;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

/**
 * Discovers all JUnit tests in a jar file and runs them in a suite.
 */
@RunWith(AllTestsSuite.AllTestsRunner.class)
public final class AllTestsSuite {
    private final static String JARFILE = "aniversary.jar";

    private AllTestsSuite() {
    }

    public static class AllTestsRunner extends Suite {

        public AllTestsRunner(final Class<?> clazz) throws InitializationError {
            super(clazz, findClasses());
        }

        public static Class<?>[] findClasses() {
            List<String> classFiles = new ArrayList<String>();
            findClasses(classFiles);
            List<Class<?>> classes = convertToClasses(classFiles);
            return classes.toArray(new Class[classes.size()]);
        }

        private static void findClasses(final List<String> classFiles) {
            JarFile jf;
            try {
                jf = new JarFile(JARFILE);
                for (Enumeration<JarEntry> e = jf.entries(); e.hasMoreElements();) {
                    String name = e.nextElement().getName();
                    if (name.endsWith("Tests.class") && !name.contains("$") && !name.endsWith("AllTests.class")) {
                        classFiles.add(name.replaceAll("/", ".").substring(0, name.length() - 6));
                    	System.out.println("Found test class file: '" + name.replaceAll("/", ".").substring(0, name.length() - 6) + "'");
                    }
                }
                jf.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private static List<Class<?>> convertToClasses(
                final List<String> classFiles) {
            List<Class<?>> classes = new ArrayList<Class<?>>();
            for (String name : classFiles) {
            	System.out.println("Converting '" + name + "' to class");
                Class<?> c;
                try {
                    c = Class.forName(name);
                }
                catch (ClassNotFoundException e) {
                    throw new AssertionError(e);
                }
                if (!Modifier.isAbstract(c.getModifiers())) {
                    classes.add(c);
                }
            }
            return classes;
        }
    }

}