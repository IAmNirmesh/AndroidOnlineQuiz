package rahul.nirmesh.onlinequiz.common;

import java.util.ArrayList;
import java.util.List;

import rahul.nirmesh.onlinequiz.model.Question;
import rahul.nirmesh.onlinequiz.model.User;

/**
 * Created by NIRMESH on 03-Jan-18.
 */

public class Common {
    public static String categoryId, categoryName;
    public static User currentUser;
    public static List<Question> questionList = new ArrayList<>();

    public static final String STR_PUSH = "pushNotification";
}
