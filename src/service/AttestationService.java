/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import beans.Attestation;
import dao.IDao;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author Sinponzakra
 */
public class AttestationService  implements IDao<Attestation> {

    @Override
    public boolean create(Attestation o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            session.close();
            return true;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            session.close();
            return false;
        }
    }
    
    
    public Attestation createWithFeedBack(Attestation o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        session.save(o);
        tx.commit();
        session.close();
        return o;
    }

    @Override
    public boolean update(Attestation o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
            session.close();
            return true;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            session.close();
            return false;
        }
    }

    @Override
    public boolean delete(Attestation o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(o);
            tx.commit();
            session.close();
            return true;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            session.close();
            return false;
        }

    }

    @Override
    public List<Attestation> findAll() {
        List<Attestation> attestations = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            attestations = session.createQuery("from Attestation").list();
            tx.commit();
            session.close();
            return attestations;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            session.close();
            return attestations;
        }

    }

    @Override
    public Attestation findById(int id) {
        Attestation a = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            a = (Attestation) session.get(Attestation.class, id);
            tx.commit();
            session.close();
            return a;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            session.close();
            return a;
        }

    }
    
     public int getLastInsertedYear() {
        int year = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            year = (int) session.createQuery("SELECT YEAR(a.id.dateEdition) from Attestation a ORDER BY a.id.dateEdition DESC ").setMaxResults(1).uniqueResult();
            tx.commit();
            session.close();
            return year;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            session.close();
            return year;
        }

    }
    

}


