/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisinos.feeper.corretorjava.Entities;

/**
 *
 * @author gilvani
 */
public class ExercicioCasoTestePasso {

    public Integer id;
    public Integer idCasoTeste;
    public String ObjectType;
    public String ObjectName;
    public String MethodName;
    public String ExpectedOutputType;
    public String ExpectedOutputName;
    public String ExpectedOutputValue;
    public ExercicioCasoTestePassoParametro[] inputParameters;
}
