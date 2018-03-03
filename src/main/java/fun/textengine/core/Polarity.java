package fun.textengine.core;


/**
 * Created by Funnyseeker on 22.05.2017.
 */
public enum Polarity {
    NEGATIVE("negative"),
    POSITIVE("positive"),
    NEUTRAL("neutral");

    String key;

    private Polarity(String key) {
        this.key = key;
    }

    public static Polarity getFromKey(String key) {
        key = key.toLowerCase();
        for (Polarity value : values()) {
            if (value.key.equals(key)) {
                return value;
            }
        }
        return null;
    }

    public static Polarity getFromIntesity(Float intensity) {
        return intensity > 0 ? POSITIVE : (intensity == 0 ? NEUTRAL : NEGATIVE);
    }
}
