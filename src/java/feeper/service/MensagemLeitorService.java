/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.service;

import feeper.entity.MensagemLeitor;
import feeper.model.HibernateUtil;

/**
 *
 * @author
 * fabioalves
 */
public class MensagemLeitorService extends HibernateUtil<MensagemLeitor> {
    
    public MensagemLeitorService() {
        super(MensagemLeitor.class);
    }
    
}
