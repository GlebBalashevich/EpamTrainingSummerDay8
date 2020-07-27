package by.balashevich.bookapp.model.dao;

import java.util.StringJoiner;

public class SortTagConverter {
    private static final String DELIMITER = ", ";

    private SortTagConverter(){
    }

    public static String convertTag (String[] sortTag){
        StringJoiner sortQuerySuffix = new StringJoiner(DELIMITER);

        for(String tag : sortTag) {
            switch (tag) {
                case "title":
                    sortQuerySuffix.add(BookTableColumn.TITLE.getLabel());
                    break;
                case "authors":
                    sortQuerySuffix.add(BookTableColumn.AUTHORS.getLabel());
                    break;
                case "yearPublication":
                    sortQuerySuffix.add(BookTableColumn.YEAR_PUBLICATION.getLabel());
                    break;
                case "language":
                    sortQuerySuffix.add(BookTableColumn.LANGUAGE.getLabel());
                    break;
                default:
                    sortQuerySuffix.add(BookTableColumn.BOOKID.getLabel());
            }
        }

        return sortQuerySuffix.toString();
    }
}
