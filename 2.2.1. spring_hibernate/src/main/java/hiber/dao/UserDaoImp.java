package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }


   //@SuppressWarnings("unchecked")
   @Override
   public List<User> listUsers() {
      TypedQuery<User> query = sessionFactory.getCurrentSession()
              .createQuery("SELECT u FROM User u LEFT JOIN FETCH u.car", User.class);
      return query.getResultList();
   }

   @Override
   public User findUserByCar(String model, int series) {
      try {
         TypedQuery<User> query = sessionFactory.getCurrentSession()
                 .createQuery("SELECT u FROM User u JOIN FETCH u.car c WHERE c.model = :model AND c.series = :series", User.class);
         query.setParameter("model", model);
         query.setParameter("series", series);
         return query.getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }
}
