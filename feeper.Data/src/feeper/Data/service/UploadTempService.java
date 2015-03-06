/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.UploadTemp;
import feeper.Data.model.HibernateUtil;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;

/**
 *
 * @author fabioalves
 */
public class UploadTempService extends HibernateUtil<UploadTemp> {

    public UploadTempService() {
        super(UploadTemp.class);
    }

    public UploadTemp getByGuid(String guid) {
        Transaction transaction = currentSession().beginTransaction();
        try {
            SQLQuery query = currentSession().createSQLQuery("select * from UploadTemp where GUID = :guid ").addEntity(UploadTemp.class);
            query.setString("guid", guid);
            List<UploadTemp> data = query.list();
            transaction.commit();
            if (data.isEmpty()) {
                return null;

            } else {
                return data.get(0);
            }
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }

}
