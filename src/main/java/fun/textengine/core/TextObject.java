package fun.textengine.core;

/**
 * Created by Funnyseeker on 22.05.2017.
 */
public interface TextObject {
    String getText();

    PolarityObject getPolarityObject();

    String getPositiveGroup();

    String getNegativeGroup();
}
