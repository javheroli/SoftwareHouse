
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ThreadRepository;
import domain.Actor;
import domain.Administrator;
import domain.Apprentice;
import domain.Discipline;
import domain.Forum;
import domain.Post;

@Service
@Transactional
public class ThreadService {

	// Managed repository
	@Autowired
	private ThreadRepository		threadRepository;

	// Supporting services
	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private Validator				validator;


	// Constructors
	public ThreadService() {
		super();
	}

	// Simple CRUD methods

	public domain.Thread findOne(final int threadId) {
		domain.Thread result;

		Assert.notNull(threadId);

		result = this.threadRepository.findOne(threadId);
		Assert.notNull(result);

		return result;

	}

	public Collection<domain.Thread> loadThreadsByForum(final int forumId) {
		Collection<domain.Thread> result;

		Assert.notNull(forumId);

		result = this.threadRepository.findAllThreadsByForumOrderedByMomentLastModification(forumId);
		Assert.notNull(result);

		return result;

	}

	public domain.Thread create() {
		domain.Thread result;
		Actor principal;
		Collection<Post> posts;
		Date actualMoment;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		result = new domain.Thread();

		actualMoment = new Date(System.currentTimeMillis() - 1);

		posts = new ArrayList<Post>();

		result.setPosts(posts);
		result.setNumPosts(0);
		result.setWriter(principal);
		result.setStartMoment(actualMoment);
		result.setMomentLastModification(actualMoment);
		result.setIsBestAnswerEnabled(principal instanceof Apprentice);
		result.setLastPost(null);
		return result;
	}

	public domain.Thread save(final domain.Thread thread) {
		final domain.Thread saved;
		final Forum forum;
		final Actor principal;
		Collection<domain.Thread> threads, updated;
		Collection<String> linksAttachments;
		boolean areAllLinksValid;

		Assert.notNull(thread);

		forum = thread.getForum();

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(thread.getWriter().getId() == principal.getId());

		if (thread.getId() == 0) {

		} else {
			Date actualMoment;

			actualMoment = new Date(System.currentTimeMillis() - 1);
			thread.setMomentLastModification(actualMoment);
			final domain.Thread instrumented = this.findOne(thread.getId());
			Assert.isTrue(instrumented.getWriter().getId() == principal.getId());
			Assert.isTrue(thread.getStartMoment().equals(instrumented.getStartMoment()));
			Assert.isTrue(thread.getNumPosts() == instrumented.getNumPosts());
			Assert.isTrue(thread.getLastPost() == instrumented.getLastPost());
			Assert.isTrue(CollectionUtils.isEqualCollection(thread.getPosts(), instrumented.getPosts()));
			Assert.isTrue(CollectionUtils.isEqualCollection(thread.getDisciplines(), instrumented.getDisciplines()));
			Assert.isTrue(thread.getForum().getId() == instrumented.getForum().getId());
			Assert.isTrue(thread.getIsBestAnswerEnabled() == instrumented.getIsBestAnswerEnabled());
		}

		linksAttachments = thread.getLinksAttachments();
		areAllLinksValid = true;
		for (final String link : linksAttachments)
			if (!(ResourceUtils.isUrl(link) || link.equals(""))) {
				areAllLinksValid = false;
				break;
			}

		Assert.isTrue(areAllLinksValid);

		saved = this.threadRepository.save(thread);

		Assert.notNull(saved);

		if (thread.getId() == 0 && principal instanceof Apprentice) {
			final Apprentice apprentice = (Apprentice) principal;
			apprentice.setPoints(apprentice.getPoints() + 5);
		}

		threads = forum.getThreads();
		updated = new ArrayList<domain.Thread>(threads);
		updated.add(saved);
		forum.setThreads(updated);

		threads = principal.getThreads();
		updated = new ArrayList<domain.Thread>(threads);
		updated.add(saved);
		principal.setThreads(updated);

		return saved;

	}

	public void delete(final domain.Thread thread) {
		Forum forum;
		Administrator principal;
		Actor writer;
		Collection<domain.Thread> threads, updated;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(thread);
		Assert.isTrue(thread.getId() != 0);

		forum = thread.getForum();

		threads = forum.getThreads();
		updated = new ArrayList<domain.Thread>(threads);
		updated.remove(thread);
		forum.setThreads(updated);

		threads = principal.getThreads();
		updated = new ArrayList<domain.Thread>(threads);
		updated.remove(thread);
		principal.setThreads(updated);

		writer = thread.getWriter();
		if (writer instanceof Apprentice) {
			final Apprentice apprentice = (Apprentice) writer;
			apprentice.setPoints(apprentice.getPoints() - 5);
		}

		forum.setNumPosts(forum.getNumPosts() - thread.getNumPosts());

		thread.setLastPost(null);
		forum.setLastPost(null);

		this.threadRepository.delete(thread);

	}

	public domain.Thread reconstruct(final domain.Thread result, final BindingResult binding) {

		if (result.getId() == 0) {
			Date actualMoment;
			Actor principal;
			Collection<Post> posts;

			actualMoment = new Date(System.currentTimeMillis() - 1);
			principal = this.actorService.findByPrincipal();

			posts = new ArrayList<Post>();

			result.setMomentLastModification(actualMoment);
			result.setPosts(posts);
			result.setNumPosts(0);
			result.setWriter(principal);
			result.setStartMoment(actualMoment);
			result.setIsBestAnswerEnabled(principal instanceof Apprentice);
			result.setLastPost(null);

			if (result.getDisciplines() == null) {
				Collection<Discipline> disciplines;
				disciplines = new ArrayList<Discipline>();
				result.setDisciplines(disciplines);
			}

		} else {
			domain.Thread thread;

			thread = this.findOne(result.getId());
			result.setDisciplines(thread.getDisciplines());
			result.setForum(thread.getForum());
			result.setIsBestAnswerEnabled(thread.getIsBestAnswerEnabled());
			result.setLastPost(thread.getLastPost());
			result.setNumPosts(thread.getNumPosts());
			result.setPosts(thread.getPosts());
			result.setStartMoment(thread.getStartMoment());
			result.setWriter(thread.getWriter());
			result.setMomentLastModification(thread.getMomentLastModification());
		}

		this.validator.validate(result, binding);

		return result;
	}
	// Other business methods

	public Collection<domain.Thread> findAllByDiscipline(final int disciplineId) {
		Collection<domain.Thread> result;

		result = this.threadRepository.findAllByDiscipline(disciplineId);
		Assert.notNull(result);

		return result;
	}

	public Double getAvgPostsPerThread() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.threadRepository.avgPostsPerThread();

		if (result == null)
			result = 0.0;

		return result;
	}

	public Double getMinPostsPerThread() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.threadRepository.minPostsPerThread();

		if (result == null)
			result = 0.0;

		return result;
	}

	public Double getMaxPostsPerThread() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.threadRepository.maxPostsPerThread();

		if (result == null)
			result = 0.0;

		return result;
	}

	public Double getStdDeviationPostsPerThread() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.threadRepository.stdDeviationPostsPerThread();

		if (result == null)
			result = 0.0;

		return result;
	}

}
