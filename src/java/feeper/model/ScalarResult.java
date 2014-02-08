/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.model;

import org.hibernate.type.Type;

/**
 *
 * @author
 * fabioalves
 */
public class ScalarResult {
    
    private String column;
    private Type type;
    
    public ScalarResult(String column, Type type)
    {
        this.column = column;
        this.type = type;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
    
}
