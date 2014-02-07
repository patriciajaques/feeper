/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.model;

import feeper.entity.Pessoa;
import org.hibernate.SQLQuery;

/**
 *
 * @author
 * fabioalves
 */
public class PessoaService extends HibernateUtil<Pessoa> {
    
    public PessoaService() {
        super(Pessoa.class);
    }
    
    public Pessoa validaLoginSenha(String email, String senha)
    {
        try {
            senha = Util.criptoMD5(senha);
            
            SQLQuery query = query("SELECT * FROM Pessoa WHERE Email = :email AND Senha = :senha AND Ativo = 1").addEntity(Pessoa.class);
            query.setString("email", email);
            query.setString("senha", senha);
            
            return (Pessoa)query.list().get(0);
        } catch (Exception e) {
            return null;
        }
    }
    
}
