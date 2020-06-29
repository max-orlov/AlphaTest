package servlets;

import com.google.gson.Gson;
import dao.DAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/main")
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF8");

        Long boxId = null;
        String color = null;

        if (req.getParameter("box_id") != null) {
            boxId = Long.valueOf(req.getParameter("box_id"));
        }
        if (req.getParameter("item_color") != null) {
            color = req.getParameter("item_color");
        }

        List result = new ArrayList();

        if (boxId == null) {
            result.addAll((List) DAO.executeSQLQuery("SELECT id FROM item WHERE contained_in IS NULL AND color = '" + color + "'"));
        } else {
            result.addAll((List) DAO.executeSQLQuery("WITH RECURSIVE rec (id, color) AS (" +
                    "SELECT id, color " +
                    "FROM item " +
                    "WHERE contained_in IN (select id from box where contained_in = " + boxId + ") " +
                    "UNION " +
                    "SELECT item.id, item.color " +
                    "FROM item, rec " +
                    "WHERE item.contained_in = rec.id) " +
                    "SELECT id FROM rec WHERE color = '" + color + "'"));

            result.addAll((List) DAO.executeSQLQuery("SELECT id " +
                    "FROM item " +
                    "WHERE contained_in = " + boxId +
                    " AND color = '" + color + "'"));
        }
        if (boxId == null && color == "") {
            result.addAll((List) DAO.executeSQLQuery("SELECT id FROM item WHERE contained_in IS NULL AND color IS NULL"));
        }
        if (color == "") {
            result.addAll((List) DAO.executeSQLQuery("WITH RECURSIVE rec (id, color) AS (" +
                    "SELECT id, color " +
                    "FROM item " +
                    "WHERE contained_in IN (select id from box where contained_in = " + boxId + ") " +
                    "UNION " +
                    "SELECT item.id, item.color " +
                    "FROM item, rec " +
                    "WHERE item.contained_in = rec.id) " +
                    "SELECT id FROM rec WHERE color IS NULL"));

            result.addAll((List) DAO.executeSQLQuery("SELECT id " +
                    "FROM item " +
                    "WHERE contained_in = " + boxId +
                    " AND color IS NULL"));
        }

        resp.getWriter().write(new Gson().toJson(result));
        result.clear();

    }
}
