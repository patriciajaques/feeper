/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisinos.feeper.feeper.CorretorJava.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Locale;
import javax.tools.Diagnostic;
import javax.tools.Diagnostic.Kind;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

/**
 *
 * @author gilvani
 */
public class FeeperCompiler {

    public void CompileClass(String fileName) throws CompilationException, IOException {

    	System.out.println(System.getProperty("java.home"));
    	JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        StandardJavaFileManager fileManager = javaCompiler.getStandardFileManager(diagnostics, Locale.forLanguageTag("pt_BR"), Charset.forName("UTF-8"));

        File file = new File(fileName);

        ArrayList sourceList = new ArrayList();
        sourceList.add(file.getParentFile());
        fileManager.setLocation(StandardLocation.SOURCE_PATH, sourceList);

        ArrayList filesList = new ArrayList();
        filesList.add(file);

        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(filesList);
        javaCompiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits).call();

        for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
            if (diagnostic.getKind().equals(Kind.ERROR)) {
                JavaFileObject fileObject = ((JavaFileObject) diagnostic.getSource());
                file = new File(fileObject.getName());
                CompilationException exception = new CompilationException(diagnostic.getMessage(Locale.forLanguageTag("pt_BR")) + " in " + file.getName());
                exception.setSource(diagnostic.getSource().toString());
                exception.setLine(diagnostic.getLineNumber());

                throw exception;
            }
        }

        fileManager.close();
    }

    public void CompileTest(String fileName, ArrayList<String> classPathFileNames) throws CompilationException, IOException {

        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

        StandardJavaFileManager fileManager = javaCompiler.getStandardFileManager(diagnostics, Locale.forLanguageTag("pt_BR"), Charset.forName("UTF-8"));
        ArrayList classPathfiles = new ArrayList();
        for (String classPathFileName : classPathFileNames) {
            classPathfiles.add(new File(classPathFileName));
        }
        fileManager.setLocation(StandardLocation.CLASS_PATH, classPathfiles);

        File file = new File(fileName);

        ArrayList sourceList = new ArrayList();
        sourceList.add(file.getParentFile());
        fileManager.setLocation(StandardLocation.SOURCE_PATH, sourceList);

        ArrayList filesList = new ArrayList();
        filesList.add(file);

        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(filesList);
        javaCompiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits).call();

        for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
            if (diagnostic.getKind().equals(Kind.ERROR)) {
                JavaFileObject fileObject = ((JavaFileObject) diagnostic.getSource());
                file = new File(fileObject.getName());
                CompilationException exception = new CompilationException(diagnostic.getMessage(Locale.forLanguageTag("pt_BR")));
                exception.setSource(diagnostic.getSource().toString());
                exception.setLine(diagnostic.getLineNumber());

                throw exception;
            }
        }

        fileManager.close();
    }

}
