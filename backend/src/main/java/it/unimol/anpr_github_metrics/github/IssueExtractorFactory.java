package it.unimol.anpr_github_metrics.github;

import com.jcabi.github.Github;

import java.util.HashMap;
import java.util.Map;

/** This class provide a factory to use the Issue Extractor
 * @author Simone Scalabrino.
 */
public class IssueExtractorFactory {
    public enum InstantiationStrategy {
        PRODUCTION,
        TESTING
    }
    private static InstantiationStrategy instantiationStrategy = InstantiationStrategy.PRODUCTION;
    private static Map<Github, IssueExtractorImpl> implementors;

    static {
        implementors = new HashMap<>();
    }

    public static void setImplementorStrategy(InstantiationStrategy strategy) {
        if (strategy == null)
            throw new RuntimeException("Invalid implementation");

        instantiationStrategy = strategy;
    }

    public static IssueExtractor getInstance(Github github) {
        switch (instantiationStrategy) {
            case PRODUCTION:
                if (!implementors.containsKey(github))
                    implementors.put(github, new IssueExtractorImpl(github));

                return implementors.get(github);
            case TESTING:
                return new IssueExtractorStub();
            default:
                throw new RuntimeException("Invalid implementation");
        }
    }
}
