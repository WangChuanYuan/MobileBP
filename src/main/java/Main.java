import dao.ClientDAO;
import dao.ClientDAOImpl;
import entity.Client;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ClientDAO clientDAO = context.getBean(ClientDAOImpl.class);
        Client client = new Client("123", "wcy");
        clientDAO.save(client);
    }

}
