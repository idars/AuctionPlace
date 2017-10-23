/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soap;


/**
 *
 * @author Ben
 */
public class SetBidStatus {
    
    
    private Integer code;
    private String explanation;
    
    public SetBidStatus() {
        code=0;
        explanation = "";
    }
    
    public void setCode(Integer code) {
        this.code = code;
        if (code == 200) this.explanation = "Customer X’s bid has been successfully placed for product Y";
        else this.setExplanation("The bid for product Y has not been placed for customer X");
    }
    
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
    
    
    public Integer getCode() {
        return this.code;
    }
    
    public String getExplanation() {
        return this.explanation;
    }
}
