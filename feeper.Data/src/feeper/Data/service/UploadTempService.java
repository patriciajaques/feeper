/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.UploadTemp;
import feeper.Data.model.HibernateUtil;
import org.hibernate.SQLQuery;

/**
 *
 * @author
 * fabioalves
 */
public class UploadTempService extends HibernateUtil<UploadTemp> {
    
    public UploadTempService() {
        super(UploadTemp.class);
    }
    
    public UploadTemp getByGuid(String guid)
    {
        try {
            
            SQLQuery query = query("select * from UploadTemp where GUID = :guid ").addEntity(UploadTemp.class);
            query.setString("guid", guid);
            return (UploadTemp)query.list().get(0);
            
        } catch (Exception e) {
            return null;
        }
    }
    
}
