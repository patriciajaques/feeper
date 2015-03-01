/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.entity;

import java.util.List;

/**
 *
 * @author gilvani
 */
public class ExercicioCasoTestePasso {

    private int id;
    private int idCasoTeste;
    private int ordem;
    private int operationType;
    private String expectedOutputType;
    private String expectedOutputName;
    private String expectedOutputValue;
    private String objectName;
    private String methodName;
    
    //Dummy property, only for Json Compability
    private Boolean selected;
    
    //auxiliar no angular
    private List<ExercicioCasoTestePassoParametro> inputParameters;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCasoTeste() {
        return idCasoTeste;
    }

    public void setIdCasoTeste(int idCasoTeste) {
        this.idCasoTeste = idCasoTeste;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }
    
    

    public int getOperationType() {
        return operationType;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    public String getExpectedOutputType() {
        return expectedOutputType;
    }

    public void setExpectedOutputType(String ExpectedOutputType) {
        this.expectedOutputType = ExpectedOutputType;
    }

    public String getExpectedOutputName() {
        return expectedOutputName;
    }

    public void setExpectedOutputName(String ExpectedOutputName) {
        this.expectedOutputName = ExpectedOutputName;
    }

    public String getExpectedOutputValue() {
        return expectedOutputValue;
    }

    public void setExpectedOutputValue(String ExpectedOutputValue) {
        this.expectedOutputValue = ExpectedOutputValue;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String ObjectName) {
        this.objectName = ObjectName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String MethodName) {
        this.methodName = MethodName;
    }

    public List<ExercicioCasoTestePassoParametro> getInputParameters() {
        return inputParameters;
    }

    public void setInputParameters(List<ExercicioCasoTestePassoParametro> inputParameters) {
        this.inputParameters = inputParameters;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
