/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.ExercicioInterface;
import feeper.Data.entity.ExercicioInterfaceMembro;
import feeper.Data.model.HibernateUtil;
import java.util.List;
import org.hibernate.SQLQuery;

/**
 *
 * @author gilvani
 */
public class ExercicioInterfaceService extends HibernateUtil<ExercicioInterface> {

    public ExercicioInterfaceService() {
        super(ExercicioInterface.class);
    }

    public ExercicioInterface getbyIdExercicio(int idExercicio) {
        try {

            SQLQuery query = query("select * from ExercicioInterface where IdExercicio = :idExercicio").addEntity(ExercicioInterface.class);

            query.setInteger("idExercicio", idExercicio);
            ExercicioInterface exercicioInterface = (ExercicioInterface) query.list().get(0);

            if (exercicioInterface != null) {
                ExercicioInterfaceMembroService repoMembros = new ExercicioInterfaceMembroService();
                exercicioInterface.setMembros(repoMembros.getbyIdInterface(exercicioInterface.getId()));
            }

            return exercicioInterface;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean salvarInterface(int idExercicio, ExercicioInterface exercicioInterface) {

        exercicioInterface.setIdExercicio(idExercicio);

        Integer interfaceId = exercicioInterface.getId();
        if (interfaceId != null && interfaceId > 0) {
            this.update(exercicioInterface);
        } else {
            this.insert(exercicioInterface);
        }

        List<ExercicioInterfaceMembro> membros = exercicioInterface.getMembros();

        if (membros != null) {
            ExercicioInterfaceMembroService repoMembros = new ExercicioInterfaceMembroService();
            boolean sucess = repoMembros.SaveMembros(exercicioInterface.getId(), membros);

            if (sucess == false) {
                return false;
            }
        }

        return true;
    }
}
