/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.plagiarism;

import feeper.Data.entity.ExercicioSolucaoClasse;
import feeper.Data.entity.Pessoa;
import java.util.List;

/**
 *
 * @author gilvani
 */
public class PlagiarismChildItem {

    private Pessoa aluno;
    private int submissionId;
    private double probability;
    private List<ExercicioSolucaoClasse> classes;

    public PlagiarismChildItem(Pessoa aluno, int submissionId, List<ExercicioSolucaoClasse> classes, double probability) {
        this.aluno = aluno;
        this.submissionId = submissionId;
        this.probability = probability;
        this.classes = classes;
    }

    public Pessoa getAluno() {
        return aluno;
    }

    public void setAluno(Pessoa aluno) {
        this.aluno = aluno;
    }

    public int getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(int submissionId) {
        this.submissionId = submissionId;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public List<ExercicioSolucaoClasse> getClasses() {
        return classes;
    }

    public void setClasses(List<ExercicioSolucaoClasse> classes) {
        this.classes = classes;
    }
}
