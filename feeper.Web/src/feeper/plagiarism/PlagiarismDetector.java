/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.plagiarism;

import feeper.Data.entity.Exercicio;
import feeper.Data.entity.ExercicioSolucao;
import feeper.Data.entity.ExercicioSolucaoClasse;
import feeper.Data.entity.Pessoa;
import feeper.Data.model.EStatusSolucao;
import feeper.Data.model.FileUtils;
import feeper.Data.service.ExercicioService;
import feeper.Data.service.ExercicioSolucaoClasseService;
import feeper.Data.service.ExercicioSolucaoService;
import feeper.Data.service.TurmaService;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import plag.parser.CachingSimpleSubmissionSimilarityChecker;
import plag.parser.CodeExcluder;
import plag.parser.CodeTokenizer;
import plag.parser.DirectorySubmission;
import plag.parser.ExcludeFilenameFilter;
import plag.parser.ExistingCodeExcluder;
import plag.parser.MultipleCodeExcluder;
import plag.parser.MultipleFilenameFilter;
import plag.parser.SimpleSubmissionSimilarityChecker;
import plag.parser.SimpleTokenSimilarityChecker;
import plag.parser.SubdirectoryFilter;
import plag.parser.Submission;
import plag.parser.SubmissionDetectionResult;
import plag.parser.SubmissionSimilarityChecker;
import plag.parser.TokenList;
import plag.parser.TokenSimilarityChecker;
import plag.parser.java.InterfaceCodeExcluder;
import plag.parser.plaggie.Configuration;

/**
 *
 * @author gilvani
 */
public class PlagiarismDetector {

    public List<PlagiarismParentItem> performCheck(int usuarioID, int turmaId, int exercicioID) throws Exception {

        String basePath = "tmp" + File.separator + usuarioID + File.separator;
        File dir = new File(basePath);
        FileUtils.deleteDirectory(dir);
        dir.mkdirs();

        TurmaService turmaservice = new TurmaService();
        ExercicioService exercicioService = new ExercicioService();
        ExercicioSolucaoService solucaoService = new ExercicioSolucaoService();
        ExercicioSolucaoClasseService classeService = new ExercicioSolucaoClasseService();

        List<Pessoa> alunos = turmaservice.getAlunos(turmaId);
        Exercicio exercicio = exercicioService.getById(exercicioID);
        List<ExercicioSolucao> solucoes = new ArrayList();
        List<ExercicioSolucaoClasse> todasClasses = new ArrayList();

        for (Pessoa aluno : alunos) {

            ExercicioSolucao solucao = solucaoService.getLastByIdExercicio(exercicioID, aluno.getId());

            if (solucao == null || solucao.getIdStatus() != EStatusSolucao.RESOLVIDO) {
                continue;
            }
            solucoes.add(solucao);

            String dirPath = basePath + solucao.getId() + File.separator;
            File submissionDir = new File(dirPath);
            submissionDir.mkdirs();
            List<ExercicioSolucaoClasse> classes = classeService.getbyIdSolucao(solucao.getId());
            todasClasses.addAll(classes);
            for (ExercicioSolucaoClasse classe : classes) {

                String filePath = dirPath + classe.getNomeClasse() + ".java";
                File sourceFile = new File(filePath);
                sourceFile.createNewFile();

                FileWriter fw = new FileWriter(sourceFile.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(classe.getCodigo());
                bw.close();
            }
        }

        List<PlagiarismParentItem> items = new ArrayList<>();

        List<SubmissionDetectionResult> results = performCheck(dir);

        for (SubmissionDetectionResult result : results) {

            int submissionAId = Integer.parseInt(result.getSubmissionA().getName());
            int submissionBId = Integer.parseInt(result.getSubmissionB().getName());

            List<ExercicioSolucaoClasse> classesA = new ArrayList();
            for (ExercicioSolucaoClasse classe : todasClasses) {
                if (classe.getIdSolucao() == submissionAId) {
                    classesA.add(classe);
                }
            }
            List<ExercicioSolucaoClasse> classesB = new ArrayList();
            for (ExercicioSolucaoClasse classe : todasClasses) {
                if (classe.getIdSolucao() == submissionBId) {
                    classesB.add(classe);
                }
            }

            PlagiarismParentItem item = getParentItem(items, alunos, solucoes, submissionAId, classesA);
            Pessoa colega = getBySubmissionId(alunos, submissionBId, solucoes);
            item.addColegaItem(colega, submissionBId, classesB, result.getSimilarityA() * 100);

            //referência Cruzada
            item = getParentItem(items, alunos, solucoes, submissionBId, classesB);
            colega = getBySubmissionId(alunos, submissionAId, solucoes);
            item.addColegaItem(colega, submissionAId, classesA, result.getSimilarityB() * 100);
        }

        return items;
    }

    private PlagiarismParentItem getParentItem(List<PlagiarismParentItem> items, List<Pessoa> alunos, List<ExercicioSolucao> solucoes, int submissionId, List<ExercicioSolucaoClasse> classes) {

        for (PlagiarismParentItem item : items) {
            if (item.getSubmissionId() == submissionId) {
                return item;
            }
        }

        Pessoa aluno = getBySubmissionId(alunos, submissionId, solucoes);
        PlagiarismParentItem item = new PlagiarismParentItem(aluno, submissionId, classes);
        items.add(item);
        return item;
    }

    private Pessoa getBySubmissionId(List<Pessoa> alunos, int submissionId, List<ExercicioSolucao> solucoes) {

        ExercicioSolucao solucao = null;
        for (ExercicioSolucao item : solucoes) {
            if (item.getId() == submissionId) {
                solucao = item;
                break;
            }
        }

        for (Pessoa aluno : alunos) {
            if (aluno.getId() == solucao.getIdAluno()) {
                return aluno;
            }
        }
        return null;
    }

    private List<SubmissionDetectionResult> performCheck(File dir) {
        try {
            // -- Read the configuration file
            File file = new File(getClass().getClassLoader().getResource("plaggie.properties").toURI());
            Configuration config = new Configuration(file);
            config.htmlReport = false;

            // -- Create the code tokenizer object for parsing the source code files
            CodeTokenizer tokenizer = (CodeTokenizer) Class.forName(config.codeTokenizer).newInstance();

            // Create the submissions
            List<DirectorySubmission> submissions = getDirectorySubmissions(dir, config);

            // -- Create the detection results
            return generateDetectionResults(submissions, config, tokenizer);

        } catch (Exception ex) {
            String err = ex.toString();
            int cht = 0;
        }

        return null;
    }

    /**
     * Generates a list of detection results using the given list of
     * submissions. Not all detectoin results are stored in the returned list,
     * they are filtered out according to some configuration parameters.
     */
    private List<SubmissionDetectionResult> generateDetectionResults(List<DirectorySubmission> submissions, Configuration config, CodeTokenizer codeTokenizer) throws Exception {

        // Generate the black list
        HashMap blacklist = new HashMap();
        if (!(config.blacklistFile == null || config.blacklistFile.equals(""))) {
            try {
                BufferedReader bin = new BufferedReader(new FileReader(config.blacklistFile));
                String s;
                Integer dummy = 0;
                while ((s = bin.readLine()) != null) {
                    blacklist.put(s.toUpperCase(), dummy);
                }
                bin.close();
            } catch (FileNotFoundException e) {
                // do nothing
            }

        }

        // Create the code excluder
        ArrayList codeExcluders = new ArrayList();

        if (config.excludeInterfaces) {
            codeExcluders.add(new InterfaceCodeExcluder());
        }

        StringTokenizer tokenizer = new StringTokenizer(config.templates, ",");

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();
            CodeExcluder cE = new ExistingCodeExcluder(createTokenList(token, codeTokenizer), config.minimumMatchLength);
            codeExcluders.add(cE);
        }

        CodeExcluder codeExcluder = new MultipleCodeExcluder(codeExcluders);

        TokenSimilarityChecker tokenChecker = new SimpleTokenSimilarityChecker(config.minimumMatchLength, codeExcluder);

        // No file excluders currently used, therefore null's
        SubmissionSimilarityChecker checker;
        if (config.cacheTokenLists) {
            checker = new CachingSimpleSubmissionSimilarityChecker(tokenChecker, codeTokenizer, new HashMap());
        } else {
            checker = new SimpleSubmissionSimilarityChecker(tokenChecker, codeTokenizer);
        }

        // Generate all the submission detection results
        List<SubmissionDetectionResult> detResults = new ArrayList();

        for (int i = 0; i < submissions.size(); i++) {
            for (int j = 0; j < i; j++) {
                Submission subA = (Submission) submissions.get(i);
                Submission subB = (Submission) submissions.get(j);
                SubmissionDetectionResult detResult = new SubmissionDetectionResult(subA, subB, checker, config.minimumFileSimilarityValueToReport);

                boolean onBlacklist = false;
                if (blacklist.get(detResult.getSubmissionA().getName().toUpperCase()) != null) {
                    detResult.setBlacklistedA(true);
                    onBlacklist = true;
                }
                if (blacklist.get(detResult.getSubmissionB().getName().toUpperCase()) != null) {
                    detResult.setBlacklistedB(true);
                    onBlacklist = true;
                }

                boolean alreadyAdded = false;
                if (onBlacklist) {
                    if (config.showAllBlacklistedResults) {
                        detResults.add(detResult);
                        alreadyAdded = true;
                    }
                }

                if ((detResult.getSimilarityA() >= config.minimumSubmissionSimilarityValue) || (detResult.getSimilarityB() >= config.minimumSubmissionSimilarityValue)) {
                    if (!alreadyAdded) {
                        detResults.add(detResult);
                    }
                }
            }
        }
        return detResults;

    }

    /**
     * Returns a list of submissions, that can be found in the directory given
     * as a parameter. The exact format of the directory hierarchy depends on
     * the configuration, especially parameter severalSubmissionDirectories.
     */
    private List<DirectorySubmission> getDirectorySubmissions(File directory, Configuration config) throws Exception {
        List<DirectorySubmission> submissions = new ArrayList();

        FilenameFilter filter = generateFilenameFilter(config);
        File[] files = directory.listFiles();

        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                try {
                    Integer.parseInt(files[i].getName());
                    DirectorySubmission dirS = new DirectorySubmission(files[i], filter, config.useRecursive);
                    submissions.add(dirS);
                } catch (NumberFormatException e) {
                    // ignore, we just want to handle submission-directories
                }
            }
        }
        return submissions;
    }

    /**
     * Generates a FilenameFilter according to the configuration.
     */
    private FilenameFilter generateFilenameFilter(Configuration config) throws Exception {

        // Generate the filename filter
        ArrayList filters = new ArrayList();

        filters.add((FilenameFilter) Class.forName(config.filenameFilter).newInstance());

        filters.add(new ExcludeFilenameFilter(config.excludeFiles));
        filters.add(new SubdirectoryFilter(config.excludeSubdirectories));

        FilenameFilter filter = new MultipleFilenameFilter(filters);
        return filter;
    }

    /**
     * Returns the token list of the given file.
     */
    private TokenList createTokenList(String filename, CodeTokenizer tokenizer) throws Exception {

        TokenList tokens = tokenizer.tokenize(new File(filename));

        return tokens;
    }
}
