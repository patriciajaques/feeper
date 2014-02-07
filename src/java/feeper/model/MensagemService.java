/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.model;

import feeper.entity.Mensagem;

public class MensagemService extends HibernateUtil<Mensagem> {
    
    public MensagemService() {
        super(Mensagem.class);
    }
    
    public int getCountMinhasMensagens(int idPessoa)
    {
        return 2;
    }
    
}
