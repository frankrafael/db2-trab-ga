package com.daos;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.entities.Member;
import com.entities.User;

@Component
public class MemberDao {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void persist(Member member) {
		Member retrievedMember = this.retrieve(member.getID());
		if (retrievedMember != null) {
			em.merge(member);
		} else {
			member.setLastChangedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
			em.persist(member);
		}

	}

	@Transactional
	public Member retrieve(int id) {
		return em.find(Member.class, id);
	}

	@Transactional
	public void update(Member member) {
		em.merge(member);
	}

	public List<Member> getMemberByUserID(int userID) {
		TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m WHERE m.user.ID = :userID", Member.class)
				.setParameter("userID", userID);

		return query.getResultList();
	}

	public int getMemberCount() {
		TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m", Member.class);

		return query.getResultList().size();
	}

}
