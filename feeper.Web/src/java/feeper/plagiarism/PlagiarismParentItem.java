/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.plagiarism;

import feeper.Data.entity.ExercicioSolucaoClasse;
import feeper.Data.entity.Pessoa;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gilvani
 */
public class PlagiarismParentItem {

    private Pessoa aluno;
    private int submissionId;
    private double maxProbability;
    private List<ExercicioSolucaoClasse> classes;
    private List<PlagiarismChildItem> colegasItems;

    public PlagiarismParentItem(Pessoa aluno, int submissionId, List<ExercicioSolucaoClasse> classes) {
        this.aluno = aluno;
        this.submissionId = submissionId;
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

    public double getMaxProbability() {
        return maxProbability;
    }

    public void setMaxProbability(double maxProbability) {
        this.maxProbability = maxProbability;
    }

    public List<PlagiarismChildItem> getColegasItems() {
        return colegasItems;
    }

    public void setColegasItems(List<PlagiarismChildItem> colegasItems) {
        this.colegasItems = colegasItems;
    }

    public List<ExercicioSolucaoClasse> getClasses() {
        return classes;
    }

    public void setClasses(List<ExercicioSolucaoClasse> classes) {
        this.classes = classes;
    }

    public void addColegaItem(Pessoa colega, int submissionId, List<ExercicioSolucaoClasse> classes, double probability) {

        if (colega.getId() == aluno.getId()) {
            return;
        }

        if (this.colegasItems == null) {
            this.colegasItems = new ArrayList<>();
        }
        PlagiarismChildItem item = new PlagiarismChildItem(colega, submissionId, classes, probability);
        this.colegasItems.add(item);

        if (probability > this.maxProbability) {
            this.maxProbability = probability;
        }
    }
}
