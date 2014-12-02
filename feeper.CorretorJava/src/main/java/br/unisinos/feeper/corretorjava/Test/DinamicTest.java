package br.unisinos.feeper.corretorjava.Test;

import br.unisinos.feeper.corretorjava.FileUtils.CopyFile;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class DinamicTest {

    private final String appResourcesPath;
    private final String destPath;

    public DinamicTest(String appResourcesPath, String destPath) {
        this.appResourcesPath = appResourcesPath;
        this.destPath = destPath;
    }

    public void ExecuteTest() {

        if (this.Prepare() == false) {
            return;
        }
        if (this.Compile() == false) {
            return;
        }
        if (this.Pack() == false) {
            return;
        }
        this.Run();
    }

    private boolean Prepare() {
        try {
            CopyFile.copy(this.appResourcesPath + "\\JarComponents\\MANIFEST.MF", this.destPath + "\\MANIFEST.MF");
            CopyFile.copy(this.appResourcesPath + "\\JarComponents\\junit.jar", this.destPath + "\\junit.jar");
            CopyFile.copy(this.appResourcesPath + "\\JarComponents\\org.hamcrest.core.jar", this.destPath + "\\org.hamcrest.core.jar");
            CopyFile.copy(this.appResourcesPath + "\\JarComponents\\policy.txt", this.destPath + "\\policy.txt");
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return false;
        }
        return true;
    }

    private boolean Compile() {
        try {

            String command = "javac -classpath junit.jar;org.hamcrest.core.jar *.java";

            File dir = new File(this.destPath);
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec(command, new String[0], dir);

            BufferedReader stdError = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
            String error = "";
            String s = null;
            while ((s = stdError.readLine()) != null) {
                error += s + "\n";
            }
            if (error != "") {
                System.out.print(error);
                return false;
            }

        } catch (Exception e) {
            System.out.print(e.getMessage());
            return false;
        }
        return true;
    }

    private boolean Pack() {

        try {

            String command = "jar cfm result.jar MANIFEST.MF *.jar *.class";

            File dir = new File(this.destPath);
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec(command, new String[0], dir);

            BufferedReader stdError = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
            String error = "";
            String s = null;
            while ((s = stdError.readLine()) != null) {
                error += s + "\n";
            }
            if (error != "") {
                System.out.print(error);
                return false;
            }

        } catch (Exception e) {
            System.out.print(e.getMessage());
            return false;
        }
        return true;
    }

    private boolean Run() {
        try {

            String command = "java -Djava.security.manager -Djava.security.policy=policy -jar";
            command += " result.jar";

            File dir = new File(this.destPath);
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec(command, new String[0], dir);

            //lê o resultado do comando
            BufferedReader stdError = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
            String s = null;
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
