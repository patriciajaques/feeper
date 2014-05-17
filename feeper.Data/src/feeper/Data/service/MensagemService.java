/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.Mensagem;
import feeper.Data.model.HibernateUtil;
import java.util.List;

public class MensagemService extends HibernateUtil<Mensagem> {
    
    public MensagemService() {
        super(Mensagem.class);
    }
    
    public int getCountMinhasMensagens(int idPessoa)
    {
        MensagemCabecalhoService repoMensagemCabecalho = new MensagemCabecalhoService();
        List<Object> listaDestinatario = repoMensagemCabecalho.getMensagensDestinatario(idPessoa, true, true);
        List<Object> listaRemetente = repoMensagemCabecalho.getMensagensRemetente(idPessoa, true, true);
        return listaDestinatario.size() + listaRemetente.size();
    }
    
}
