package controller;

import Common.MessageDAO;
import Common.StudentDAO;
import model.Database;
import model.Message;
import model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("message")
public class MessageController {

    @Autowired
    private  Database db;

    @RequestMapping(method = RequestMethod.GET)
    public String get(HttpSession session) {
        List<Message> messages=null;

        messages = MessageDAO.getMessageList(db);

        session.setAttribute("messages",messages);
        session.setAttribute("itemPerPage",5);
        session.setAttribute("begin_num","1");


        String idStr = (String)session.getAttribute("studentID");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            Student student = StudentDAO.getStudent(db, id);
            session.setAttribute("isAdmin", student.isAdmin());
        } else {
            session.setAttribute("isAdmin", false);
        }


        return "message";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String post(HttpSession session,@RequestParam("postType") String type) {
        List<Message> messages=null;
        messages = MessageDAO.getMessageList(db);

        System.out.println("begin:"+type);

        session.setAttribute("messages",messages);
        session.setAttribute("itemPerPage",5);
        session.setAttribute("begin_num",type);

        return "message";
    }
}
