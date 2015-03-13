/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisinos.feeper.corretorjava.Creators;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gilvani
 */
public class ClassCreator {

    public String getCodigoAdaptado(String codigoOriginal) {

        codigoOriginal = codigoOriginal.substring(0, codigoOriginal.lastIndexOf("}") - 1);
        codigoOriginal += getPrivateFieldsGetter();
        codigoOriginal += "}";
        return codigoOriginal;
    }

    private String getPrivateFieldsGetter() {
        StringBuilder builder = new StringBuilder();
        builder.append("public Object get_Private_Field_Acessor(String fieldName){");
        builder.append("try {");
        builder.append("java.lang.reflect.Field field = this.getClass().getDeclaredField(fieldName);");
        builder.append("return field.get(this);");
        builder.append("} catch (Exception ex) {");
        builder.append("return null;");
        builder.append("}}");
        return builder.toString();
    }
}
