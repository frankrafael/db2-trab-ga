package com.repositories;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.entities.BaseEntity;

@Component
public abstract class BaseRepository<E extends BaseEntity> {
	@PersistenceContext
	private EntityManager em;

	private Class<E> clazz;

	public BaseRepository(Class<E> clazz) {
		this.clazz = clazz;
	}

	public E findById(int id) {
		return em.find(this.clazz, id);
	}

	@Transactional
	public void deleteById(int id) {
		E obj = this.findById(id);
		em.remove(obj);
	}

	@Transactional
	public E persist(E instance) {
		em.persist(instance);
		return instance;
	}
	
	@Transactional
	public E merge(E instance) {
		em.merge(instance);
		return instance;
	}

	public Collection<E> findAll() {
		TypedQuery<E> query = em.createQuery("SELECT x FROM " + this.clazz.getName() + " x", this.clazz);

		return query.getResultList();
	}

	@Transactional
	public E updateById(int id, E instance) {
		em.merge(instance);
		return instance;
	}

	public int countAll() {
		return this.findAll().size();
	}
	@Transactional
	public void deleteAll() {
		em.createQuery("DELETE FROM " + this.clazz.getName() + " AS x", this.clazz).executeUpdate();
	}
}
