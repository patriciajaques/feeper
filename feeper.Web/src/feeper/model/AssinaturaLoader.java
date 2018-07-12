/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.model;

import feeper.Data.entity.ExercicioClasseAuxiliar;
import feeper.Data.model.ClasseAssinatura.Assinatura;
import feeper.Data.model.ClasseAssinatura.AssinaturaMembro;
import feeper.Data.model.ClasseAssinatura.AssinaturaMembroParametro;
import feeper.Data.model.EmemberType;
import feeper.Data.model.FileUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.tools.Diagnostic;
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
public class AssinaturaLoader {

    public List<ExercicioClasseAuxiliar> CarregaAssinaturas(List<ExercicioClasseAuxiliar> classesAuxiliares, int usuarioID) throws Exception {

        String basePath = "tmp" + File.separator + usuarioID + File.separator;
        File dir = new File(basePath);
        FileUtils.deleteDirectory(dir);
        dir.mkdirs();

        //primeiro move tudo
        for (ExercicioClasseAuxiliar classeAuxiliar : classesAuxiliares) {

            File sourceFile = new File(basePath + classeAuxiliar.getNomeClasse() + ".java");
            sourceFile.createNewFile();

            FileWriter fw = new FileWriter(sourceFile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(classeAuxiliar.getCodigo());
            bw.close();
        }

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        //depois compila tudo
        for (ExercicioClasseAuxiliar classeAuxiliar : classesAuxiliares) {

            File sourceFile = new File(basePath + classeAuxiliar.getNomeClasse() + ".java");

            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, Locale.US, Charset.forName("UTF-8"));
            ArrayList sourceList = new ArrayList();
            sourceList.add(dir);
            fileManager.setLocation(StandardLocation.SOURCE_PATH, sourceList);

            ArrayList filesList = new ArrayList();
            filesList.add(sourceFile);

            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(filesList);
            compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits).call();
        }

        //Depois processa tudo
        for (ExercicioClasseAuxiliar classeAuxiliar : classesAuxiliares) {

            classeAuxiliar.setAssinatura(null);

            try {
                URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{dir.toURI().toURL()});
                Class<?> classe = Class.forName(classeAuxiliar.getNomeClasse(), true, classLoader);

                Assinatura assinatura = new Assinatura();
                assinatura.setNomeClasse(classeAuxiliar.getNomeClasse());

                List<AssinaturaMembro> membros = new ArrayList();

                //fields pega todos, para testar getters e setters
                for (Field field : classe.getDeclaredFields()) {

                    AssinaturaMembro membro = new AssinaturaMembro();
                    membro.setMemberType(EmemberType.FIELD);
                    membro.setModifier(field.getModifiers());
                    membro.setName(field.getName());
                    membro.setType(field.getType().getSimpleName());

                    membros.add(membro);
                }

                //construtores, somente públicos
                for (Constructor constructor : classe.getConstructors()) {

                    AssinaturaMembro membro = new AssinaturaMembro();
                    membro.setMemberType(EmemberType.CONSTRUCTOR);
                    membro.setModifier(constructor.getModifiers());
                    membro.setName(constructor.getName());

                    Parameter[] parameters = constructor.getParameters();
                    List<AssinaturaMembroParametro> parametros = new ArrayList();
                    for (Parameter parameter : parameters) {
                        AssinaturaMembroParametro parametro = new AssinaturaMembroParametro();
                        parametro.setOrdem(parametros.size() + 1);
                        parametro.setName(parameter.getName());
                        parametro.setType(parameter.getType().getSimpleName());
                        parametros.add(parametro);
                    }
                    membro.setParametros(parametros);

                    membros.add(membro);
                }

                //membros, somente públicos
                for (Method method : classe.getMethods()) {

                    AssinaturaMembro membro = new AssinaturaMembro();
                    membro.setMemberType(EmemberType.METHOD);
                    membro.setModifier(method.getModifiers());
                    membro.setType(method.getReturnType().getSimpleName());
                    membro.setName(method.getName());

                    Parameter[] parameters = method.getParameters();
                    List<AssinaturaMembroParametro> parametros = new ArrayList();
                    for (Parameter parameter : parameters) {
                        AssinaturaMembroParametro parametro = new AssinaturaMembroParametro();
                        parametro.setName(parameter.getName());
                        parametro.setType(parameter.getType().getSimpleName());
                        parametros.add(parametro);
                    }
                    membro.setParametros(parametros);

                    membros.add(membro);
                }

                assinatura.setMembros(membros);
                classeAuxiliar.setAssinatura(assinatura);
            } catch (Exception e) {
            }
        }
        return classesAuxiliares;
    }
}
