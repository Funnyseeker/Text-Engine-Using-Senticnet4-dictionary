package fun.textengine.dictionaries;

/**
 * Created by Funnyseeker on 24.05.2017.
 */
public interface Dictionary {
    enum Dictionaries {
        EN(0, "senticnet4/senticnet4.rdf.xml", "Dictionary"),
        DE(1, "senticnet4/data_de.txt", "De_Dictionary");

        private int code;
        private String filePath;
        private String tableName;

        Dictionaries(int code, String filePath, String tableName) {
            this.code = code;
            this.filePath = filePath;
            this.tableName = tableName;
        }

        public static Dictionaries getByCode(int code) {
            for (Dictionaries dictionary : Dictionaries.values()) {
                if (dictionary.code == code) {
                    return dictionary;
                }
            }
            return EN;
        }

        public int getCode() {
            return code;
        }

        public String getFilePath() {
            return filePath;
        }

        public String getTableName() {
            return tableName;
        }
    }
}
