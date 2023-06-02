package dao.impl;

import dao.GenreDao;
import model.Genre;
import org.hibernate.Session;
import util.HibernateUtil;

public class GenreDaoImpl implements GenreDao {

    @Override
    public Genre getById(Long id) {
        Session session = HibernateUtil.createSessionFactory().getCurrentSession();
        session.beginTransaction();

        Genre genre = session.get(Genre.class, id);

        session.getTransaction().commit();

        return genre;
    }

}
