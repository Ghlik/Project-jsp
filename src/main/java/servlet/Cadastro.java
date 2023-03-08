import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@WebServlet("/cadastro")
public class Cadastro extends HttpServlet {

    @Override
    protected void doPost(@NotNull HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("Nome");
        String mail = req.getParameter("E-mail");
        String brand = req.getParameter("Marca");
        String model = req.getParameter("Modelo");
        String dateDeparture = req.getParameter("data_de_retirada");
        String dateReturn = req.getParameter("data_de_devolucao");

        LocalDate inicioAluguel = LocalDate.parse(dateDeparture);
        DateTimeFormatter dataAluguelFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dateDeparture = inicioAluguel.format(dataAluguelFormatter);

        LocalDate fimAluguel = LocalDate.parse(dateReturn);
        DateTimeFormatter dataDevolucaoFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dateReturn = fimAluguel.format(dataDevolucaoFormatter);

        long diarias = ChronoUnit.DAYS.between(inicioAluguel, fimAluguel);
        double preco = servlet.Preco.calcularValor(diarias);

        if (
                name.equals("") || mail.equals("")
                        || brand.equals("") || model.equals("")
                        || dateDeparture.equals("") || dateReturn.equals("")
        ) {
            resp.setContentType("text/html");
            resp.getWriter().println("<h3>Dados obrigatórios não preenchidos!</h3>");
        } else {
            req.setAttribute("name", name);
            req.setAttribute("mail", mail);
            req.setAttribute("brand", brand);
            req.setAttribute("model", model);
            req.setAttribute("data_retirada", dateDeparture);
            req.setAttribute("data_devolucao", dateReturn);
            req.setAttribute("diarias", diarias);
            req.setAttribute("preco", preco);

            RequestDispatcher rd = req.getRequestDispatcher("data.jsp");
            rd.forward(req, resp);
        }
    }
}