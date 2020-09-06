package ru.geekbrains;

import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        EntityManager em = emf.createEntityManager();

/*        em.getTransaction().begin();
        List<Object> list = new ArrayList<>();
        list.add(new User(null, "ivan", "ivan"));
        list.add(new User(null, "petr", "petr"));
        list.add(new User(null, "alex", "alex"));
        list.add(new Product(null, "Banana", new BigDecimal("99.69")));
        list.add(new Product(null, "Apple", new BigDecimal("105.50")));
        list.add(new Product(null, "Orange", new BigDecimal("78.25")));
        for(Object o : list){
            em.persist(o);
        }

        em.createNativeQuery("INSERT INTO products_users (product_id, user_id) VALUES" +
                " (1,1), (1,2), (1,3), (2,1), (2,2), (3,3)").executeUpdate();

        em.getTransaction().commit();*/

//        em.getTransaction().begin();
//        List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
/*        List<Product> products = em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
        em.getTransaction().commit();*/

/*        for(User u : users){
            List<Product> products = u.getProducts();
            for(Product p : products){
                System.out.println(u.getLogin() + " - " + p.getTitle() + " - " + p.getPrice());
            }
        }*/

/*        for(Product p : products){
            List<User> users = p.getUsers();
            for(User u : users){
                System.out.println(p.getTitle() + " - " + u.getLogin());
            }
        }*/

        em.getTransaction().begin();
        User user = em.createQuery("from User where login = :login", User.class)
                .setParameter("login", "petr")
                .getSingleResult();
        em.remove(user);
/*        Product product = em.createQuery("from Product where title = :title", Product.class)
                .setParameter("title", "Orange")
                .getSingleResult();
        em.remove(product);*/
        em.getTransaction().commit();

        em.close();

    }
}
