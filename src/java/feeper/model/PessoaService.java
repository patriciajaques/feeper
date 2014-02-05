/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.model;

import feeper.entity.Pessoa;
import java.security.MessageDigest;
import java.util.List;
import java.util.Random;
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
            
            SQLQuery query = query("SELECT * FROM Pessoa WHERE Email = :email AND Senha = :senha").addEntity(Pessoa.class);
            query.setString("email", email);
            query.setString("senha", senha);
            
            return (Pessoa)query.list().get(0);
        } catch (Exception e) {
            return null;
        }
    }
    
}
