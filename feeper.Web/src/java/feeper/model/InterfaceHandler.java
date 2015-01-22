/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.model;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

/**
 *
 * @author gilvani
 */
public class InterfaceHandler {

    public Class<?> GetInterface(String fileName, String className) {

        try {
            File sourceFile = new File(fileName);

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            int result = compiler.run(null, null, null, sourceFile.getPath());

            //erro de compilação
            if (result != 0) {
                return null;
            }

            File root = new File(sourceFile.getParent());

            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{root.toURI().toURL()});
            return Class.forName(className, true, classLoader);

        } catch (Exception e) {
            return null;
        }
    }
}
