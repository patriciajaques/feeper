/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.model;

import feeper.entity.Pessoa;

/**
 *
 * @author
 * fabioalves
 */
public class PessoaService extends HibernateUtil<Pessoa> {
    
    public PessoaService() {
        super(Pessoa.class);
    }
    
    public String gerarSenha()
    {
        return "senha";
    }
    
}
