/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.ExercicioInterfaceMembroParametro;
import feeper.Data.model.HibernateUtil;
import java.util.List;
import org.hibernate.SQLQuery;

/**
 *
 * @author gilvani
 */
public class ExercicioInterfaceMembroParametroService extends HibernateUtil<ExercicioInterfaceMembroParametro> {

    public ExercicioInterfaceMembroParametroService() {
        super(ExercicioInterfaceMembroParametro.class);
    }

    public List<ExercicioInterfaceMembroParametro> getByIdMembro(int idMembro) {
        SQLQuery query = query("select * from ExercicioInterfaceMembroParametro where IdMembro = :idMembro order by Ordem, ID").addEntity(ExercicioInterfaceMembroParametro.class);
        query.setInteger("idMembro", idMembro);
        return query.list();
    }

    public boolean SaveParametros(int idMembro, List<ExercicioInterfaceMembroParametro> parametros) {

        for (ExercicioInterfaceMembroParametro parametro : parametros) {
            parametro.setIdMembro(idMembro);

            Integer parametroId = parametro.getId();
            boolean sucess;
            if (parametroId != null && parametroId > 0) {
                sucess = this.update(parametro);
            } else {
                sucess = this.insert(parametro);
            }
            
            if (sucess == false) {
                return false;
            }
        }

        return true;
    }

}
