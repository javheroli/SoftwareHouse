
package controllers.administrator;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ApplicationService;
import services.ContestService;
import services.ForumService;
import services.PostService;
import services.ResearchService;
import services.ThreadService;
import controllers.AbstractController;
import domain.Actor;
import domain.Contest;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	// Services

	@Autowired
	ForumService		forumService;

	@Autowired
	ThreadService		threadService;

	@Autowired
	ContestService		contestService;

	@Autowired
	ResearchService		researchService;

	@Autowired
	ApplicationService	applicationService;

	@Autowired
	PostService			postService;

	@Autowired
	ActorService		actorService;


	// Constructors

	public DashboardAdministratorController() {
		super();
	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result;

		Double getRatioAcceptedApplications, getRatioDueApplications, getRatioPendingApplications, getRatioRejectedApplications, getRatioResearchesNotStarted;
		Double getRatioDeletedPosts, getAvgThreadsPerForum, getMinThreadsPerForum, getMaxThreadsPerForum, getStdDeviationThreadsPerForum;
		Double getAvgPostsPerThread, getMinPostsPerThread, getMaxPostsPerThread, getStdDeviationPostsPerThread;
		Double getAvgApplicationsPerContest, getMinApplicationsPerContest, getMaxApplicationsPerContest, getStdDeviationApplicationsPerContest;
		Double getAvgProblemsPerContest, getMinProblemsPerContest, getMaxProblemsPerContest, getStdDeviationProblemsPerContest;
		Double getAvgInvestmentsPerResearch, getMinInvestmentsPerResearch, getMaxInvestmentsPerResearch, getStdDeviationInvestmentsPerResearch;

		Collection<Actor> findActorsWith10PercentMorePostsThanAverage;

		Collection<Contest> findEvaluatedContests;

		final List<List<Double>> getAvgMinMaxMarksForEvaluatedContests;

		getRatioAcceptedApplications = this.applicationService.getRatioAcceptedApplications();
		getRatioDueApplications = this.applicationService.getRatioDueApplications();
		getRatioPendingApplications = this.applicationService.getRatioPendingApplications();
		getRatioRejectedApplications = this.applicationService.getRatioRejectedApplications();

		getRatioResearchesNotStarted = this.researchService.getRatioResearchesNotStarted();

		getRatioDeletedPosts = this.postService.getRatioDeletedPosts();

		getAvgThreadsPerForum = this.forumService.getAvgThreadsPerForum();
		getMinThreadsPerForum = this.forumService.getMinThreadsPerForum();
		getMaxThreadsPerForum = this.forumService.getMaxThreadsPerForum();
		getStdDeviationThreadsPerForum = this.forumService.getStdDeviationThreadsPerForum();

		getAvgPostsPerThread = this.threadService.getAvgPostsPerThread();
		getMinPostsPerThread = this.threadService.getMinPostsPerThread();
		getMaxPostsPerThread = this.threadService.getMaxPostsPerThread();
		getStdDeviationPostsPerThread = this.threadService.getStdDeviationPostsPerThread();

		getAvgApplicationsPerContest = this.contestService.getAvgApplicationsPerContest();
		getMinApplicationsPerContest = this.contestService.getMinApplicationsPerContest();
		getMaxApplicationsPerContest = this.contestService.getMaxApplicationsPerContest();
		getStdDeviationApplicationsPerContest = this.contestService.getStdDeviationApplicationsPerContest();

		getAvgProblemsPerContest = this.contestService.getAvgProblemsPerContest();
		getMinProblemsPerContest = this.contestService.getMinProblemsPerContest();
		getMaxProblemsPerContest = this.contestService.getMaxProblemsPerContest();
		getStdDeviationProblemsPerContest = this.contestService.getStdDeviationProblemsPerContest();

		getAvgInvestmentsPerResearch = this.researchService.getAvgInvestmentsPerResearch();
		getMinInvestmentsPerResearch = this.researchService.getMinInvestmentsPerResearch();
		getMaxInvestmentsPerResearch = this.researchService.getMaxInvestmentsPerResearch();
		getStdDeviationInvestmentsPerResearch = this.researchService.getStdDeviationInvestmentsPerResearch();

		findActorsWith10PercentMorePostsThanAverage = this.actorService.findActorsWith10PercentMorePostsThanAverage();

		findEvaluatedContests = this.contestService.findEvaluatedContests();

		getAvgMinMaxMarksForEvaluatedContests = this.contestService.getAvgMinMaxMarksForEvaluatedContests();

		result = new ModelAndView("administrator/dashboard");

		result.addObject("getRatioAcceptedApplications", getRatioAcceptedApplications);
		result.addObject("getRatioDueApplications", getRatioDueApplications);
		result.addObject("getRatioPendingApplications", getRatioPendingApplications);
		result.addObject("getRatioRejectedApplications", getRatioRejectedApplications);
		result.addObject("getRatioResearchesNotStarted", getRatioResearchesNotStarted);
		result.addObject("getRatioDeletedPosts", getRatioDeletedPosts);
		result.addObject("getAvgThreadsPerForum", getAvgThreadsPerForum);
		result.addObject("getMinThreadsPerForum", getMinThreadsPerForum);
		result.addObject("getMaxThreadsPerForum", getMaxThreadsPerForum);
		result.addObject("getStdDeviationThreadsPerForum", getStdDeviationThreadsPerForum);
		result.addObject("getAvgPostsPerThread", getAvgPostsPerThread);
		result.addObject("getMinPostsPerThread", getMinPostsPerThread);
		result.addObject("getMaxPostsPerThread", getMaxPostsPerThread);
		result.addObject("getStdDeviationPostsPerThread", getStdDeviationPostsPerThread);
		result.addObject("getAvgApplicationsPerContest", getAvgApplicationsPerContest);
		result.addObject("getMinApplicationsPerContest", getMinApplicationsPerContest);
		result.addObject("getMaxApplicationsPerContest", getMaxApplicationsPerContest);
		result.addObject("getStdDeviationApplicationsPerContest", getStdDeviationApplicationsPerContest);
		result.addObject("getAvgProblemsPerContest", getAvgProblemsPerContest);
		result.addObject("getMinProblemsPerContest", getMinProblemsPerContest);
		result.addObject("getMaxProblemsPerContest", getMaxProblemsPerContest);
		result.addObject("getStdDeviationProblemsPerContest", getStdDeviationProblemsPerContest);
		result.addObject("getAvgInvestmentsPerResearch", getAvgInvestmentsPerResearch);
		result.addObject("getMinInvestmentsPerResearch", getMinInvestmentsPerResearch);
		result.addObject("getMaxInvestmentsPerResearch", getMaxInvestmentsPerResearch);
		result.addObject("getStdDeviationInvestmentsPerResearch", getStdDeviationInvestmentsPerResearch);
		result.addObject("findActorsWith10PercentMorePostsThanAverage", findActorsWith10PercentMorePostsThanAverage);
		result.addObject("findEvaluatedContests", findEvaluatedContests);
		result.addObject("getAvgMinMaxMarksForEvaluatedContests", getAvgMinMaxMarksForEvaluatedContests);

		return result;

	}
}
