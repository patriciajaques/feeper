/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/novidades")
public class NovidadesController {
    
    @RequestMapping(method=RequestMethod.GET)
    public String getView() {
        return "novidades/list";
    }
    
}