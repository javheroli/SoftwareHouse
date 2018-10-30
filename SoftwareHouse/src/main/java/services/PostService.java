
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PostRepository;
import domain.Actor;
import domain.Administrator;
import domain.Apprentice;
import domain.Discipline;
import domain.Expert;
import domain.Forum;
import domain.Post;

@Service
@Transactional
public class PostService {

	// Managed repository
	@Autowired
	private PostRepository			postRepository;

	// Supporting services
	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ApprenticeService		apprenticeService;

	@Autowired
	Validator						validator;


	// Constructors
	public PostService() {
		super();
	}

	// Simple CRUD methods

	public Post findOne(final int postId) {
		Post result;

		Assert.notNull(postId);

		result = this.postRepository.findOne(postId);
		Assert.notNull(result);

		return result;

	}

	public Collection<Post> loadPostsByThread(final int threadId) {
		Collection<Post> result;

		Assert.notNull(threadId);

		result = this.postRepository.findAllPostsByThreadOrderedByMomentPublication(threadId);
		Assert.notNull(result);

		return result;
	}

	public Post create() {
		Post result;
		Actor principal;
		Collection<Post> posts;
		Date actualMoment;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		result = new Post();
		actualMoment = new Date(System.currentTimeMillis() - 1);
		posts = new ArrayList<Post>();

		result.setIsBestAnswer(false);
		result.setIsDeleted(false);
		result.setIsReliable(false);
		result.setNumPosts(0);
		result.setPosts(posts);
		result.setPublicationMoment(actualMoment);
		result.setWriter(principal);

		return result;
	}

	public Post save(final Post post) {
		final Post saved;
		Post parentPost;
		domain.Thread thread;
		final Actor principal;
		final Actor writer;
		Collection<Post> posts;
		Collection<Post> updated;
		Collection<String> linksAttachments;
		boolean areAllLinksValid;
		Collection<Discipline> disciplines;

		parentPost = post.getParentPost();
		thread = post.getThread();

		Assert.notNull(post);

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(post.getWriter().getId() == principal.getId());

		Assert.isTrue((parentPost == null && thread != null) || (parentPost != null && thread == null));

		linksAttachments = post.getLinksAttachments();
		areAllLinksValid = true;
		for (final String link : linksAttachments)
			if (!(ResourceUtils.isUrl(link) || link.equals(""))) {
				areAllLinksValid = false;
				break;
			}

		Assert.isTrue(areAllLinksValid);

		if (post.getId() == 0) {
			if (parentPost != null)
				Assert.isTrue(!parentPost.getIsDeleted());

			if (principal instanceof Expert && post.getTopic().getWriter() instanceof Apprentice) {
				final Expert expert = (Expert) principal;
				disciplines = post.getTopic().getDisciplines();
				if (expert.getDisciplines().containsAll(disciplines))
					post.setIsReliable(true);
			}

		} else {
			Assert.isTrue(!post.getIsDeleted());
			final Post instrumented;
			instrumented = this.findOne(post.getId());
			Assert.isTrue(instrumented.getWriter().getId() == principal.getId());
			Assert.isTrue(post.getNumPosts() == instrumented.getNumPosts());
			Assert.isTrue(post.getThread() == instrumented.getThread());
			Assert.isTrue(post.getParentPost() == instrumented.getParentPost());
			Assert.isTrue(CollectionUtils.isEqualCollection(post.getPosts(), instrumented.getPosts()));
			Assert.isTrue(post.getPublicationMoment().equals(instrumented.getPublicationMoment()));
			Assert.isTrue(post.getTopic().equals(instrumented.getTopic()));
			Assert.isTrue(post.getIsBestAnswer() == instrumented.getIsBestAnswer());
			Assert.isTrue(post.getIsReliable() == instrumented.getIsReliable());
			Assert.isTrue(!instrumented.getIsDeleted());

		}

		saved = this.postRepository.save(post);

		Assert.notNull(saved);

		if (post.getId() == 0) {
			saved.getTopic().setLastPost(saved);
			saved.getTopic().getForum().setLastPost(saved);
			saved.getTopic().setMomentLastModification(saved.getPublicationMoment());
		} else {
			Date actualMoment;
			actualMoment = new Date(System.currentTimeMillis() - 1);
			saved.getTopic().setMomentLastModification(actualMoment);
		}

		writer = saved.getWriter();
		posts = writer.getPosts();
		updated = new ArrayList<Post>(posts);
		updated.add(saved);
		writer.setPosts(updated);

		if (saved.getThread() != null) {
			thread = saved.getThread();
			posts = thread.getPosts();
			updated = new ArrayList<Post>(posts);
			updated.add(saved);
			thread.setPosts(updated);

		}

		if (saved.getParentPost() != null) {
			parentPost = saved.getParentPost();
			posts = parentPost.getPosts();
			updated = new ArrayList<Post>(posts);
			updated.add(saved);
			parentPost.setPosts(updated);

		}

		if (post.getId() == 0 && principal instanceof Apprentice && saved.getTopic().getWriter() instanceof Apprentice && saved.getTopic().getWriter().getId() != principal.getId()) {
			final Apprentice apprentice = (Apprentice) principal;
			apprentice.setPoints(apprentice.getPoints() + 10);
		}

		if (post.getId() == 0)
			this.updateNumPosts(saved, false);
		return saved;
	}

	private void updateNumPosts(final Post post, final boolean delete) {
		Post parentPost;
		domain.Thread thread;

		parentPost = post.getParentPost();
		thread = post.getThread();

		if (thread != null) {
			Forum forum;
			forum = thread.getForum();
			if (!delete) {
				thread.setNumPosts(thread.getNumPosts() + 1);
				forum.setNumPosts(forum.getNumPosts() + 1);
			} else {
				thread.setNumPosts(thread.getNumPosts() - 1);
				forum.setNumPosts(forum.getNumPosts() - 1);
			}
		} else {
			if (!delete)
				parentPost.setNumPosts(parentPost.getNumPosts() + 1);
			else
				parentPost.setNumPosts(parentPost.getNumPosts() - 1);
			this.updateNumPosts(parentPost, delete);
		}
	}

	public void userDelete(final int postId) {
		Post post;
		List<String> linksAttachments;
		Actor principal;
		Date actualMoment;
		domain.Thread topic;

		post = this.findOne(postId);

		Assert.notNull(post);
		Assert.isTrue(post.getId() != 0);
		Assert.isTrue(!post.getIsDeleted());

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(post.getWriter().getId() == principal.getId());

		post.setText("DELETEDBYUSER");
		post.setIsDeleted(true);

		linksAttachments = new ArrayList<String>();

		post.setLinksAttachments(linksAttachments);

		if (principal instanceof Apprentice && post.getTopic().getWriter() instanceof Apprentice && post.getTopic().getWriter().getId() != principal.getId()) {
			final Apprentice apprentice = (Apprentice) principal;
			apprentice.setPoints(apprentice.getPoints() - 10);
		}

		actualMoment = new Date(System.currentTimeMillis() - 1);
		topic = post.getTopic();

		topic.setMomentLastModification(actualMoment);

		this.updateNumPosts(post, true);

	}

	public void adminDelete(final int postId) {
		Post post;
		List<String> linksAttachments;
		Administrator principal;
		Actor actor;
		Date actualMoment;
		domain.Thread topic;

		post = this.findOne(postId);
		Assert.notNull(post);

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(post);
		Assert.isTrue(post.getId() != 0);
		Assert.isTrue(!post.getIsDeleted());

		post.setText("DELETEDBYADMIN");
		post.setIsDeleted(true);

		linksAttachments = new ArrayList<String>();
		post.setLinksAttachments(linksAttachments);

		actor = post.getWriter();

		if (actor instanceof Apprentice && post.getTopic().getWriter() instanceof Apprentice && post.getTopic().getWriter().getId() != actor.getId()) {
			final Apprentice apprentice = (Apprentice) actor;
			apprentice.setPoints(apprentice.getPoints() - 10);
		}

		actualMoment = new Date(System.currentTimeMillis() - 1);
		topic = post.getTopic();

		topic.setMomentLastModification(actualMoment);

		this.updateNumPosts(post, true);
	}

	public void mark(final int postId) {
		Post post;
		Actor principal;
		Actor writer;
		domain.Thread thread;

		post = this.findOne(postId);
		writer = post.getWriter();

		thread = post.getThread();
		Assert.isTrue(thread != null);
		Assert.isTrue(post.getParentPost() == null);

		principal = this.apprenticeService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(thread.getIsBestAnswerEnabled());
		Assert.isTrue(writer.getId() != principal.getId());

		post.setIsBestAnswer(true);
		thread.setIsBestAnswerEnabled(false);

		if (writer instanceof Apprentice) {
			final Apprentice apprentice = (Apprentice) writer;
			apprentice.setPoints(apprentice.getPoints() + 20);
		}

	}

	public Post findLastPostByForum(final int forumId) {
		Administrator administrator;
		Post result;

		administrator = this.administratorService.findByPrincipal();
		Assert.notNull(administrator);

		result = this.postRepository.findLastPostByForum(forumId);

		return result;
	}

	public void remove(final int postId) {
		Post post;
		Administrator principal;
		Collection<Post> posts, updated;
		Actor writer;

		post = this.findOne(postId);

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(post);
		Assert.isTrue(post.getId() != 0);

		if (post.getThread() != null) {
			domain.Thread thread;
			thread = post.getThread();
			posts = thread.getPosts();
			updated = new ArrayList<Post>(posts);
			updated.remove(post);
			thread.setPosts(updated);
		}

		if (post.getParentPost() != null) {
			Post parentPost;
			parentPost = post.getParentPost();
			posts = parentPost.getPosts();
			updated = new ArrayList<Post>(posts);
			updated.remove(post);
			parentPost.setPosts(updated);
		}

		post.setParentPost(null);
		post.setThread(null);

		writer = post.getWriter();
		posts = writer.getPosts();
		updated = new ArrayList<Post>(posts);
		updated.remove(post);
		writer.setPosts(updated);

		this.postRepository.delete(post);

	}

	// Other business methods

	public Post reconstruct(final Post result, final BindingResult binding) {

		if (result.getId() == 0) {
			Actor principal;
			Collection<Post> posts;
			Date actualMoment;

			principal = this.actorService.findByPrincipal();
			actualMoment = new Date(System.currentTimeMillis() - 1);
			posts = new ArrayList<Post>();

			result.setIsBestAnswer(false);
			result.setIsDeleted(false);
			result.setIsReliable(false);
			result.setNumPosts(0);
			result.setPosts(posts);
			result.setPublicationMoment(actualMoment);
			result.setWriter(principal);

		} else {
			Post post;
			post = this.findOne(result.getId());
			result.setIsBestAnswer(post.getIsBestAnswer());
			result.setIsDeleted(post.getIsDeleted());
			result.setIsReliable(post.getIsReliable());
			result.setNumPosts(post.getNumPosts());
			result.setParentPost(post.getParentPost());
			result.setPosts(post.getPosts());
			result.setPublicationMoment(post.getPublicationMoment());
			result.setThread(post.getThread());
			result.setTopic(post.getTopic());
			result.setWriter(post.getWriter());

		}
		this.validator.validate(result, binding);

		return result;
	}

	public Double getRatioDeletedPosts() {
		Administrator principal;
		Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.postRepository.ratioDeletedPosts();

		if (result == null)
			result = 0.0;

		return result;
	}

}
