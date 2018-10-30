
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ForumRepository;
import domain.Administrator;
import domain.Forum;
import domain.Post;

@Service
@Transactional
public class ForumService {

	// Managed repository
	@Autowired
	private ForumRepository			forumRepository;

	// Supporting services

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private PostService				postService;

	@Autowired
	private Validator				validator;


	// Constructors
	public ForumService() {
		super();
	}

	// Simple CRUD methods

	public Forum findOne(final int forumId) {
		Forum result;
		result = this.forumRepository.findOne(forumId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Forum> findAll() {
		Collection<Forum> result;
		result = this.forumRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Forum create() {
		Forum result;
		Administrator principal;
		Collection<domain.Thread> threads;

		principal = this.administratorService.findByPrincipal();

		Assert.notNull(principal);

		result = new Forum();

		threads = new ArrayList<domain.Thread>();
		result.setThreads(threads);
		result.setLastPost(null);
		result.setNumPosts(0);

		return result;
	}

	public Forum save(final Forum forum) {
		Forum saved;
		Administrator principal;

		Assert.notNull(forum);

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		if (forum.getId() != 0) {
			Forum instrumented;
			instrumented = this.findOne(forum.getId());
			Assert.isTrue(CollectionUtils.isEqualCollection(forum.getThreads(), instrumented.getThreads()));
			Assert.isTrue(forum.getNumPosts() == instrumented.getNumPosts());
			Assert.isTrue(forum.getLastPost() == instrumented.getLastPost());
		}

		saved = this.forumRepository.save(forum);

		Assert.notNull(saved);

		return saved;
	}

	public void delete(final Forum forum) {
		Administrator principal;

		Assert.notNull(forum);
		Assert.isTrue(forum.getId() != 0);

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		forum.setLastPost(null);

		this.forumRepository.delete(forum);

	}

	public void updateLastPost(final int forumId) {
		Administrator principal;
		Forum forum;
		Post lastPost;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		forum = this.findOne(forumId);
		lastPost = this.postService.findLastPostByForum(forumId);

		forum.setLastPost(lastPost);

	}

	// Other business methods
	public Forum reconstruct(final Forum result, final BindingResult binding) {

		if (result.getId() == 0) {
			Collection<domain.Thread> threads;
			threads = new ArrayList<domain.Thread>();
			result.setThreads(threads);
			result.setLastPost(null);
			result.setNumPosts(0);
		} else {
			Forum forum;
			forum = this.findOne(result.getId());
			result.setThreads(forum.getThreads());
			result.setNumPosts(forum.getNumPosts());
			result.setLastPost(forum.getLastPost());
		}

		this.validator.validate(result, binding);
		return result;
	}

	public Double getAvgThreadsPerForum() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.forumRepository.avgThreadsPerForum();

		if (result == null)
			result = 0.0;

		return result;
	}

	public Double getMinThreadsPerForum() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.forumRepository.minThreadsPerForum();

		if (result == null)
			result = 0.0;

		return result;
	}

	public Double getMaxThreadsPerForum() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.forumRepository.maxThreadsPerForum();

		if (result == null)
			result = 0.0;

		return result;
	}

	public Double getStdDeviationThreadsPerForum() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.forumRepository.stdDeviationThreadsPerForum();

		if (result == null)
			result = 0.0;

		return result;
	}

}
