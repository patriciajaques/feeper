/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.ExercicioInterfaceMembro;
import feeper.Data.entity.ExercicioInterfaceMembroParametro;
import feeper.Data.model.HibernateUtil;
import java.util.List;
import org.hibernate.SQLQuery;

/**
 *
 * @author gilvani
 */
public class ExercicioInterfaceMembroService extends HibernateUtil<ExercicioInterfaceMembro> {

    public ExercicioInterfaceMembroService() {
        super(ExercicioInterfaceMembro.class);
    }

    public List<ExercicioInterfaceMembro> getbyIdInterface(int idInterface) {
        try {

            SQLQuery query = query("select * from ExercicioInterfaceMembro where IdInterface = :idInterface").addEntity(ExercicioInterfaceMembro.class);

            query.setInteger("idInterface", idInterface);
            List<ExercicioInterfaceMembro> membros = query.list();

            ExercicioInterfaceMembroParametroService repoParametros = new ExercicioInterfaceMembroParametroService();
            for (ExercicioInterfaceMembro membro : membros) {

                membro.setParametros(repoParametros.getByIdMembro(membro.getId()));
            }

            return membros;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean SaveMembros(int idInterface, List<ExercicioInterfaceMembro> membros) {

        ExercicioInterfaceMembroParametroService repoParametros = new ExercicioInterfaceMembroParametroService();

        for (ExercicioInterfaceMembro membro : membros) {
            membro.setIdInterface(idInterface);

            Integer membroId = membro.getId();
            if (membroId != null && membroId > 0) {
                this.update(membro);
            } else {
                this.insert(membro);
            }

            List<ExercicioInterfaceMembroParametro> parametros = membro.getParametros();

            if (parametros != null) {
                boolean sucess = repoParametros.SaveParametros(membro.getId(), parametros);

                if (sucess == false) {
                    return false;
                }
            }
        }

        return true;
    }
}
