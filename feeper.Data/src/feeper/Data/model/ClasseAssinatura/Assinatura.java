/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.model.ClasseAssinatura;

import java.util.List;

/**
 *
 * @author gilvani
 */
public class Assinatura {
    
    private String nomeClasse;
    
    private List<AssinaturaMembro> membros;

    public String getNomeClasse() {
        return nomeClasse;
    }

    public void setNomeClasse(String nomeClasse) {
        this.nomeClasse = nomeClasse;
    }

    public List<AssinaturaMembro> getMembros() {
        return membros;
    }

    public void setMembros(List<AssinaturaMembro> membros) {
        this.membros = membros;
    }
    
    
}
