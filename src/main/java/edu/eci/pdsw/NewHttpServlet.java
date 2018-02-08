/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw;

import edu.eci.pdsw.stubs.datasourcestub.Client;
import edu.eci.pdsw.stubs.datasourcestub.ClientNotFoundException;
import edu.eci.pdsw.stubs.datasourcestub.DataSourceStub;
import java.io.IOException;
import java.io.Writer;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author 2104784
 */

@WebServlet(
    urlPatterns = "/clientes"
)
public class NewHttpServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
       
        DataSourceStub ds = DataSourceStub.getInstance();
        try {
            Optional<Integer> optName = Optional.ofNullable(Integer.valueOf(req.getParameter("id")));
            Client c = ds.getClientById(optName.get());
            String[] info = new String[]{c.getName(),c.getEmail(),c.getAddress()};
            String htmlinfo = 
                    "<!DOCTYPE html>" +
                    "<body>" +
                    "<table class=\"client\" cellspacing=\"11\" cellpadding=\"10\" border=\"1\">" +
                    "<tr>" +
                    "    <th>ID</th>" +
                    "    <th>Nombre</th>" +
                    "    <th>Email</th>" +
                    "    <th>Direccion</th>" +
                    "  </tr>" +
                    "  <tr>" +
                    "    <th>"+optName.get()+"</th>" +
                    "    <td>"+info[0]+"</td>" +
                    "    <td>"+info[1]+"</td>" +
                    "    <td>"+info[2]+"</td>"  +
                    "</table>" +
                    "</body>" +
                    "</html>";
            resp.setStatus(200);
            Writer responseWriter=resp.getWriter();
            responseWriter.write(htmlinfo);
            responseWriter.flush();
            
        } catch (ClientNotFoundException ex) {
            resp.setStatus(400);
            Writer responseWriter=resp.getWriter();
            responseWriter.write(404 +" No existe el cliente con el identificador dado");       
            Logger.getLogger(NewHttpServlet.class.getName()).log(Level.SEVERE, null, ex);
            responseWriter.flush();
        }catch(NumberFormatException ex){
            resp.setStatus(400);
            Writer responseWriter=resp.getWriter();
            responseWriter.write(400 +" Identificador dado no valido");  
            responseWriter.flush();
        }
    }
    
    
    
}
