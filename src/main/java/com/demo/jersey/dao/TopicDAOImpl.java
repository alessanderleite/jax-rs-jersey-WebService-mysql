package com.demo.jersey.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.demo.jersey.entities.Topic;

@Transactional
@Repository
public class TopicDAOImpl implements TopicDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Topic> getAllTopics() {
		String hql = "FROM Topic as t ORDER BY t.topicId";
		return (List<Topic>) entityManager.createQuery(hql).getResultList();
	}

	@Override
	public Topic getTopicById(int topicId) {
		return entityManager.find(Topic.class, topicId);
	}

	@Override
	public void addTopic(Topic topic) {
		entityManager.persist(topic);
	}

	@Override
	public void updateTopic(Topic topic) {
		Topic dbTopic = getTopicById(topic.getTopicId());
		dbTopic.setTitle(topic.getTitle());
		dbTopic.setCategory(topic.getCategory());
		entityManager.flush();
	}

	@Override
	public void deleteTopic(int topicId) {
		entityManager.remove(getTopicById(topicId));
	}

	@Override
	public boolean topicExists(String title, String category) {
		String hql = "FROM Topic as topic WHERE topic.title = ?1 and topic.category = ?2";
		int count = entityManager.createQuery(hql)
				.setParameter(1, title)
				.setParameter(2, category)
				.getResultList().size();
		
		return count > 0 ? true : false;
	}

}
