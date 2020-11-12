package it.unimol.anpr_github_metrics.recommender;

import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class provides methods to compute text similarity based on Vector Space Model
 * @author Stefano Dalla Palma
 */
public class VSM {

    public static double computeTextualSimilarity(String first, String second) {

        HashMap<String, Double> firstDocument = (HashMap<String, Double>) getVector(first);
        HashMap<String, Double> secondDocument = (HashMap<String, Double>) getVector(second);

        return VSM.vsm(firstDocument, secondDocument);
    }

    private static Map<String, Double> getVector(String text) {
        Map<String, Double> map = new HashMap();

        String[] tokens = text.split("\\s+");
        PorterStemmer stemmer = new PorterStemmer();

        for (String word : tokens) {
            word = stemmer.stem(word);
            
            if (!map.containsKey(word)) {
                map.put(word, 1D);
            } else {
                map.put(word, map.get(word) + 1);
            }
        }

        return map;
    }

    private static double vsm(Map<String, Double> v1, Map<String, Double> v2) {
        Set<String> both = new HashSet(v1.keySet());
        both.addAll(v2.keySet());

        double[] d1 = new double[both.size()];
        double[] d2 = new double[both.size()];

        int i = 0;
        for (String key : both) {
            d1[i] = 0;
            d2[i] = 0;

            if (v1.containsKey(key)) {
                d1[i] = v1.get(key);
            }

            if (v2.containsKey(key)) {
                d2[i] = v2.get(key);
            }

            i++;
        }

        RealVector vector1 = new ArrayRealVector(d1);
        RealVector vector2 = new ArrayRealVector(d2);

        try {
            return vector1.cosine(vector2);
        } catch (MathArithmeticException e) {
            return Double.NaN;
        }
    }
}